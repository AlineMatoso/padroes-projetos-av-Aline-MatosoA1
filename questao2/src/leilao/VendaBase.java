package leilao;

public class VendaBase implements ItemFatura {
    private final Lote lote;

    public VendaBase(Lote lote) {
        if (!lote.isVendido()) {
            throw new IllegalStateException("Não é possível gerar fatura de um lote não vendido.");
        }
        this.lote = lote;
    }

    @Override
    public String getDescricao() {
        return String.format("Lote: %s (Comprador: %s)\n  - Valor de Venda: R$ %.2f",
                lote.getNome(), lote.getMaiorOfertante().getNome(), lote.getLanceAtual());
    }

    @Override
    public double getValorTotal() {
        return lote.getLanceAtual();
    }

    @Override
    public double getValorVendaBase() {
        return lote.getLanceAtual();
    }
}
