package multinuvem;

public class AwsArmazenamento implements Armazenamento {
    private final String id;

    public AwsArmazenamento(int sequencial) {
        this.id = "s3://bucket-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoGbMes() { return 0.023; }
}
