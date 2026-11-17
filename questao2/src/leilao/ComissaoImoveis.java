package leilao;

public class ComissaoImoveis implements RegraComissao {
    private static final double PERCENTUAL = 0.03;
    private static final double TAXA_FIXA = 1500.00;

    @Override
    public double calcularComissao(double valorVenda) {
        return (valorVenda * PERCENTUAL) + TAXA_FIXA;
    }

    @Override
    public String getNomeRegra() { return "Imóveis (3% + R$ 1.500)"; }
}
