package leilao;

public class CertificadoAutenticidade extends ServicoAdicionalDecorator {
    private static final double CUSTO_FIXO = 300.00;

    public CertificadoAutenticidade(ItemFatura itemEnvolvido) {
        super(itemEnvolvido);
    }

    @Override
    public String getDescricao() {
        return itemEnvolvido.getDescricao() +
                String.format("\n  + Certificado de Autenticidade: R$ %.2f", CUSTO_FIXO);
    }

    @Override
    public double getValorTotal() {
        return itemEnvolvido.getValorTotal() + CUSTO_FIXO;
    }
}
