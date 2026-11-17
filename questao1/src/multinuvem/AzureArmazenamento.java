package multinuvem;

public class AzureArmazenamento implements Armazenamento {
    private final String id;

    public AzureArmazenamento(int sequencial) {
        this.id = "blob://container-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoGbMes() { return 0.02; }
}
