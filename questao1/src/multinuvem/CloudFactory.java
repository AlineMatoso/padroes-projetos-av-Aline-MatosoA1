package multinuvem;

public interface CloudFactory {
    MaquinaVirtual criarMaquinaVirtual(int sequencial);
    Armazenamento criarArmazenamento(int sequencial);
    FilaMensagens criarFilaMensagens(int sequencial);
    String getNomeProvedor();
}
