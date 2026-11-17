package multinuvem;

public class AwsFilaMensagens implements FilaMensagens {
    private final String id;

    public AwsFilaMensagens(int sequencial) {
        this.id = "sqs-" + sequencial;
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getCustoFixoMes() { return 0.40; }
}
