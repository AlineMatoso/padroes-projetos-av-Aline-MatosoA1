package leilao;

public class ComissaoEletronicos implements RegraComissao {
    private static final double PERCENTUAL = 0.12;
    private static final double MINIMO = 50.00;

    @Override
    public double calcularComissao(double valorVenda) {
        return Math.max(valorVenda * PERCENTUAL, MINIMO);
    }

    @Override
    public String getNomeRegra() { return "Eletrônicos (12%, mín R$ 50)"; }
}
