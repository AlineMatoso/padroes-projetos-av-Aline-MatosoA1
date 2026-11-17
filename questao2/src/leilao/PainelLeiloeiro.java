package leilao;

public class PainelLeiloeiro implements ObservadorLote {
    private int totalLances = 0;
    private double maiorLance = 0.0;

    @Override
    public void notificarNovoLance(Lote lote, Participante autor, double valor) {
        totalLances++;
        if (valor > maiorLance) {
            maiorLance = valor;
        }
        System.out.printf("  [Painel Leiloeiro] Lote '%s' | Maior Lance Atual: R$ %.2f | Total de Lances: %d\n",
                lote.getNome(), maiorLance, totalLances);
    }
}
