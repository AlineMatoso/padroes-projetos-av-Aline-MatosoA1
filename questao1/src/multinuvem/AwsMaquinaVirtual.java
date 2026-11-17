package multinuvem;

public class AwsMaquinaVirtual implements MaquinaVirtual {
    private final String id;

    public AwsMaquinaVirtual(int sequencial) {
        this.id = "i-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoHora() { return 0.10; }
}
