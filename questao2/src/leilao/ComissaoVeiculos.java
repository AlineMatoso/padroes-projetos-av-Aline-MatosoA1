package leilao;

public class ComissaoVeiculos implements RegraComissao {
    private static final double PERCENTUAL = 0.08;
    private static final double TETO_MAXIMO = 5000.00;

    @Override
    public double calcularComissao(double valorVenda) {
        return Math.min(valorVenda * PERCENTUAL, TETO_MAXIMO);
    }

    @Override
    public String getNomeRegra() { return "Veículos (8%, teto R$ 5.000)"; }
}
