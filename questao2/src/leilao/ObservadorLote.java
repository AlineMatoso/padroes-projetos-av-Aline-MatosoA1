package leilao;

public interface ObservadorLote {
    void notificarNovoLance(Lote lote, Participante autor, double valor);
}
