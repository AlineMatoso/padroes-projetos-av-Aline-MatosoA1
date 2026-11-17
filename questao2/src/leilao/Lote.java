package leilao;

import java.util.ArrayList;
import java.util.List;

public class Lote {
    private final String nome;
    private final String categoria;
    private final double precoInicial;
    private final double precoReserva;

    private double lanceAtual;
    private Participante maiorOfertante;
    private boolean encerrado = false;
    private boolean vendido = false;

    private final List<ObservadorLote> observadores = new ArrayList<>();
    private RegraComissao regraComissao;

    public Lote(String nome, String categoria, double precoInicial, double precoReserva, RegraComissao regraInicial) {
        this.nome = nome;
        this.categoria = categoria;
        this.precoInicial = precoInicial;
        this.precoReserva = precoReserva;
        this.lanceAtual = precoInicial; // RF01
        this.regraComissao = regraInicial;
    }

    // Gestão de Observadores (RF03 / RNF01)
    public void adicionarObservador(ObservadorLote obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public void removerObservador(ObservadorLote obs) {
        observadores.remove(obs);
    }

    private void notificarObservadores(Participante autor, double valor) {
        for (ObservadorLote obs : observadores) {
            obs.notificarNovoLance(this, autor, valor);
        }
    }

    // Troca de Strategy em tempo de execução (RF07 / RNF02)
    public void setRegraComissao(RegraComissao novaRegra) {
        this.regraComissao = novaRegra;
        System.out.printf("[Campanha] Regra de comissão do lote '%s' alterada para: %s\n",
                nome, novaRegra.getNomeRegra());
    }

    // Registro de Lances (RF02)
    public boolean darLance(Participante participante, double valor) {
        if (encerrado) {
            System.out.printf("[Lance Recusado] Lote '%s' já está encerrado.\n", nome);
            return false;
        }

        double minimoExigido = lanceAtual * 1.05;
        // Pequena tolerância para imprecisão de ponto flutuante
        if (valor + 1e-6 < minimoExigido) {
            System.out.printf("[Lance Recusado] %s ofertou R$ %.2f em '%s'. Mínimo exigido (+5%%): R$ %.2f\n",
                    participante.getNome(), valor, nome, minimoExigido);
            return false;
        }

        this.lanceAtual = valor;
        this.maiorOfertante = participante;
        System.out.printf("[Lance Aceito] %s ofertou R$ %.2f no lote '%s'\n",
                participante.getNome(), valor, nome);

        notificarObservadores(participante, valor);
        return true;
    }

    // Encerramento (RF05)
    public void encerrar() {
        this.encerrado = true;
        if (maiorOfertante != null && lanceAtual >= precoReserva) {
            this.vendido = true;
            System.out.printf("\n>>> LOTE '%s' ENCERRADO: VENDIDO para %s por R$ %.2f! <<<\n",
                    nome, maiorOfertante.getNome(), lanceAtual);
        } else {
            this.vendido = false;
            System.out.printf("\n>>> LOTE '%s' ENCERRADO: NÃO VENDIDO (Maior lance R$ %.2f < Reserva R$ %.2f) <<<\n",
                    nome, lanceAtual, precoReserva);
        }
    }

    // Acerto do Vendedor (RF10)
    public void imprimirAcertoVendedor() {
        if (!vendido) {
            System.out.printf("Acerto Vendedor (%s): Lote não vendido, sem comissão.\n", nome);
            return;
        }
        double comissao = regraComissao.calcularComissao(lanceAtual);
        double liquido = lanceAtual - comissao;
        System.out.println("--------------------------------------------------");
        System.out.println("ACERTO DO VENDEDOR - Lote: " + nome + " (" + categoria + ")");
        System.out.println("Regra Aplicada  : " + regraComissao.getNomeRegra());
        System.out.printf("Valor de Venda  : R$ %.2f\n", lanceAtual);
        System.out.printf("Comissão Casa   : R$ %.2f\n", comissao);
        System.out.printf("Valor Líquido   : R$ %.2f\n", liquido);
        System.out.println("--------------------------------------------------");
    }

    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getLanceAtual() { return lanceAtual; }
    public boolean isVendido() { return vendido; }
    public Participante getMaiorOfertante() { return maiorOfertante; }
}
