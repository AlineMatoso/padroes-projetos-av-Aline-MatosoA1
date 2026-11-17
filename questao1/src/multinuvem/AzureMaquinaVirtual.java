package multinuvem;

public class AzureMaquinaVirtual implements MaquinaVirtual {
    private final String id;

    public AzureMaquinaVirtual(int sequencial) {
        this.id = "vm-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoHora() { return 0.12; }
}
