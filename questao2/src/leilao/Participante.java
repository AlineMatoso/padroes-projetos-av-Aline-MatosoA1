package leilao;

public class Participante implements ObservadorLote {
    private final String nome;

    public Participante(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void notificarNovoLance(Lote lote, Participante autor, double valor) {
        // RF03: Comunicado a todos que acompanham, exceto a quem deu o lance
        if (this != autor) {
            System.out.printf("  [Notificação -> %s] Novo lance no lote '%s': R$ %.2f (por %s)\n",
                    nome, lote.getNome(), valor, autor.getNome());
        }
    }
}
