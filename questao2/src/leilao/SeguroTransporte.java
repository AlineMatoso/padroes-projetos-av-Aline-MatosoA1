package leilao;

public class SeguroTransporte extends ServicoAdicionalDecorator {
    private static final double PERCENTUAL = 0.015; // 1.5%

    public SeguroTransporte(ItemFatura itemEnvolvido) {
        super(itemEnvolvido);
    }

    private double custoServico() {
        return getValorVendaBase() * PERCENTUAL;
    }

    @Override
    public String getDescricao() {
        return itemEnvolvido.getDescricao() +
                String.format("\n  + Seguro de Transporte (1,5%%): R$ %.2f", custoServico());
    }

    @Override
    public double getValorTotal() {
        return itemEnvolvido.getValorTotal() + custoServico();
    }
}
