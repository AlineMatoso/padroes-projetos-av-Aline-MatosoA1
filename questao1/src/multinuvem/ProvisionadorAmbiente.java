package multinuvem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Client do Abstract Factory: não conhece classes concretas (RNF01, RNF02, RNF06)
public class ProvisionadorAmbiente {
    private static final int HORAS_MES = 730;
    private static final AtomicInteger seqVm = new AtomicInteger(1);
    private static final AtomicInteger seqStorage = new AtomicInteger(1);
    private static final AtomicInteger seqFila = new AtomicInteger(1);

    private final CloudFactory factory;

    public ProvisionadorAmbiente(CloudFactory factory) {
        this.factory = factory;
    }

    public void provisionar(String nomeAmbiente, int qtdVms, int qtdGbArmazenamento) {
        Configuracao config = Configuracao.getInstance();
        HistoricoAuditoria auditoria = HistoricoAuditoria.getInstance();

        // Cria protótipo unitário para calcular custo (ou consulta tabela da fábrica)
        MaquinaVirtual vmRef = factory.criarMaquinaVirtual(0);
        Armazenamento storageRef = factory.criarArmazenamento(0);
        FilaMensagens filaRef = factory.criarFilaMensagens(0);

        double custoVms = qtdVms * vmRef.getCustoHora() * HORAS_MES;
        double custoStorage = qtdGbArmazenamento * storageRef.getCustoGbMes();
        double custoFila = filaRef.getCustoFixoMes();
        double custoTotal = custoVms + custoStorage + custoFila;

        // RF05: Verifica orçamento antes de criar recursos reais
        if (custoTotal > config.getOrcamentoMaximo()) {
            String motivo = String.format("RECUSADO (Custo R$ %.2f excede orçamento máximo de R$ %.2f)",
                    custoTotal, config.getOrcamentoMaximo());
            auditoria.registrar(nomeAmbiente, factory.getNomeProvedor(), motivo);
            synchronized (System.out) {
                System.out.printf("[RECUSADO] Ambiente '%s' (%s) -> Custo estimado R$ %.2f excede limite de R$ %.2f\n",
                        nomeAmbiente, factory.getNomeProvedor(), custoTotal, config.getOrcamentoMaximo());
            }
            return;
        }

        // Instancia os recursos com sequenciais atômicos thread-safe (RF03)
        List<String> idsVms = new ArrayList<>();
        for (int i = 0; i < qtdVms; i++) {
            idsVms.add(factory.criarMaquinaVirtual(seqVm.getAndIncrement()).getId());
        }
        Armazenamento storage = factory.criarArmazenamento(seqStorage.getAndIncrement());
        FilaMensagens fila = factory.criarFilaMensagens(seqFila.getAndIncrement());

        // RF07: Registra auditoria
        auditoria.registrar(nomeAmbiente, factory.getNomeProvedor(),
                String.format("ACEITO (Custo: R$ %.2f)", custoTotal));

        // RF06: Imprime resumo sincronizado para não embaralhar saída das threads
        synchronized (System.out) {
            System.out.println("--------------------------------------------------");
            System.out.println("PROVISIONAMENTO ACEITO");
            System.out.println("Ambiente : " + nomeAmbiente);
            System.out.println("Provedor : " + factory.getNomeProvedor());
            System.out.println("Região   : " + config.getRegiaoPadrao());
            System.out.println("VMs      : " + idsVms);
            System.out.println("Storage  : " + storage.getId() + " (" + qtdGbArmazenamento + " GB)");
            System.out.println("Fila     : " + fila.getId());
            System.out.printf("Custo Est: R$ %.2f / mês\n", custoTotal);
            System.out.println("--------------------------------------------------");
        }
    }
}
