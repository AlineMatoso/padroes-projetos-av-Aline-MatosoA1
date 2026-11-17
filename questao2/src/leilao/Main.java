package leilao;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CASA DE LEILÕES ONLINE ===\n");

        // Criando 2 lotes de categorias distintas
        Lote quadro = new Lote("Quadro Modernista", "Arte", 1000.00, 1500.00, new ComissaoArte());
        Lote carro = new Lote("Sedan Executivo", "Veículos", 50000.00, 65000.00, new ComissaoVeiculos());

        // Criando 3 participantes, painel e auditor
        Participante ana = new Participante("Ana");
        Participante bruno = new Participante("Bruno");
        Participante carla = new Participante("Carla");

        PainelLeiloeiro painelQuadro = new PainelLeiloeiro();
        PainelLeiloeiro painelCarro = new PainelLeiloeiro();
        Auditor auditor = new Auditor();

        // Inscrevendo interessados nos lotes
        quadro.adicionarObservador(ana);
        quadro.adicionarObservador(bruno);
        quadro.adicionarObservador(carla);
        quadro.adicionarObservador(painelQuadro);
        quadro.adicionarObservador(auditor);

        carro.adicionarObservador(bruno);
        carro.adicionarObservador(carla);
        carro.adicionarObservador(painelCarro);
        carro.adicionarObservador(auditor);

        // --- LANCES NO LOTE 1 (Quadro) ---
        System.out.println("--- Rodada de Lances: Quadro Modernista ---");
        quadro.darLance(ana, 1020.00);   // Recusado (< 1050.00)
        quadro.darLance(ana, 1100.00);   // Aceito
        quadro.darLance(bruno, 1300.00); // Aceito

        // Saída de um participante do acompanhamento
        System.out.println("\n[Sistema] Carla deixou de acompanhar o lote 'Quadro Modernista'.");
        quadro.removerObservador(carla);

        quadro.darLance(carla, 1600.00); // Aceito (Carla não recebe notificação de si mesma, Ana e Bruno recebem)

        // Troca de regra de comissão (Campanha: aplicando regra de Eletrônicos no lote de Arte)
        quadro.setRegraComissao(new ComissaoEletronicos());

        // Encerrando Lote 1 (Vendido: 1600 >= reserva 1500)
        quadro.encerrar();
        quadro.darLance(ana, 2000.00); // Tentativa após encerramento (Recusado)

        // --- LANCES NO LOTE 2 (Carro) ---
        System.out.println("\n--- Rodada de Lances: Sedan Executivo ---");
        carro.darLance(bruno, 55000.00); // Aceito, mas abaixo da reserva de 65000.00
        carro.encerrar(); // Encerrado como NÃO VENDIDO

        // --- FATURAS E ACERTOS ---
        System.out.println("\n--- Faturamento e Acerto ---");
        // Fatura com 3 serviços adicionais, sendo EmbalagemEspecial contratada 2 vezes (Decorator)
        ItemFatura faturaQuadro = new VendaBase(quadro);
        faturaQuadro = new SeguroTransporte(faturaQuadro);
        faturaQuadro = new CertificadoAutenticidade(faturaQuadro);
        faturaQuadro = new EmbalagemEspecial(faturaQuadro);
        faturaQuadro = new EmbalagemEspecial(faturaQuadro); // Contratado 2x

        FaturaComprador.imprimir(faturaQuadro);
        quadro.imprimirAcertoVendedor();
        carro.imprimirAcertoVendedor();

        // Histórico completo do Auditor
        auditor.imprimirAuditoria();
    }
}
