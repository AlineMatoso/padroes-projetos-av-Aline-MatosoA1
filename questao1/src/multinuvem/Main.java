package multinuvem;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== INICIANDO SISTEMA DE PROVISIONAMENTO MULTINUVEM ===");
        System.out.printf("Configuração carregada -> Região: %s | Orçamento Máximo: R$ %.2f\n\n",
                Configuracao.getInstance().getRegiaoPadrao(),
                Configuracao.getInstance().getOrcamentoMaximo());

        CloudFactory awsFactory = new AwsFactory();
        CloudFactory azureFactory = new AzureFactory();

        ProvisionadorAmbiente provAws = new ProvisionadorAmbiente(awsFactory);
        ProvisionadorAmbiente provAzure = new ProvisionadorAmbiente(azureFactory);

        // RF08: 4 provisionamentos (2 AWS, 1 Azure, 1 Recusado), sendo pelo menos 2 simultâneos
        Thread t1 = new Thread(() -> provAws.provisionar("Prod-Web-AWS", 2, 500));     // ~146 + 11.50 + 0.40 = 157.90 (ACEITO)
        Thread t2 = new Thread(() -> provAzure.provisionar("Homolog-API-Az", 3, 1000)); // ~262.80 + 20.00 + 0.50 = 283.30 (ACEITO)
        Thread t3 = new Thread(() -> provAws.provisionar("Analytics-AWS", 1, 2000));    // ~73 + 46.00 + 0.40 = 119.40 (ACEITO)
        Thread t4 = new Thread(() -> provAzure.provisionar("BigData-Cluster", 10, 5000)); // ~876 + 100 + 0.50 = 976.50 (RECUSADO)

        // Dispara todos simultaneamente
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Aguarda conclusão de todas as threads
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        // RF07: Imprime histórico completo ao final
        HistoricoAuditoria.getInstance().imprimirHistorico();
    }
}
