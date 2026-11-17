package leilao;

public class EmbalagemEspecial extends ServicoAdicionalDecorator {
    private static final double CUSTO_FIXO = 180.00;

    public EmbalagemEspecial(ItemFatura itemEnvolvido) {
        super(itemEnvolvido);
    }

    @Override
    public String getDescricao() {
        return itemEnvolvido.getDescricao() +
                String.format("\n  + Embalagem Especial: R$ %.2f", CUSTO_FIXO);
    }

    @Override
    public double getValorTotal() {
        return itemEnvolvido.getValorTotal() + CUSTO_FIXO;
    }
}
