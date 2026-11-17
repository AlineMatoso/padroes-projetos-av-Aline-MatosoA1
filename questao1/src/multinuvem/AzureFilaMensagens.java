package multinuvem;

public class AzureFilaMensagens implements FilaMensagens {
    private final String id;

    public AzureFilaMensagens(int sequencial) {
        this.id = "sb-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoFixoMes() { return 0.50; }
}
