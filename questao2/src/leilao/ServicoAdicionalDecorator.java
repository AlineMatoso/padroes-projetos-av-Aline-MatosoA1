package leilao;

public abstract class ServicoAdicionalDecorator implements ItemFatura {
    protected final ItemFatura itemEnvolvido;

    public ServicoAdicionalDecorator(ItemFatura itemEnvolvido) {
        this.itemEnvolvido = itemEnvolvido;
    }

    @Override
    public double getValorVendaBase() {
        return itemEnvolvido.getValorVendaBase();
    }
}
