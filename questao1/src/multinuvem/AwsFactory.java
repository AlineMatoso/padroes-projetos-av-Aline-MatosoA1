package multinuvem;

public class AwsFactory implements CloudFactory {
    @Override
    public MaquinaVirtual criarMaquinaVirtual(int sequencial) {
        return new AwsMaquinaVirtual(sequencial);
    }

    @Override
    public Armazenamento criarArmazenamento(int sequencial) {
        return new AwsArmazenamento(sequencial);
    }

    @Override
    public FilaMensagens criarFilaMensagens(int sequencial) {
        return new AwsFilaMensagens(sequencial);
    }

    @Override
    public String getNomeProvedor() {
        return "Aws";
    }
}
