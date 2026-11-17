package multinuvem;

public class AzureFactory implements CloudFactory {
    @Override
    public MaquinaVirtual criarMaquinaVirtual(int sequencial) {
        return new AzureMaquinaVirtual(sequencial);
    }

    @Override
    public Armazenamento criarArmazenamento(int sequencial) {
        return new AzureArmazenamento(sequencial);
    }

    @Override
    public FilaMensagens criarFilaMensagens(int sequencial) {
        return new AzureFilaMensagens(sequencial);
    }

    @Override
    public String getNomeProvedor() {
        return "Azure";
    }
}
