package leilao;

public class EntregaExpressa extends ServicoAdicionalDecorator {
    private static final double CUSTO_FIXO = 450.00;

    public EntregaExpressa(ItemFatura itemEnvolvido) {
        super(itemEnvolvido);
    }

    @Override
    public String getDescricao() {
        return itemEnvolvido.getDescricao() +
                String.format("\n  + Entrega Expressa: R$ %.2f", CUSTO_FIXO);
    }

    @Override
    public double getValorTotal() {
        return itemEnvolvido.getValorTotal() + CUSTO_FIXO;
    }
}
