package multinuvem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Singleton Thread-Safe via Initialization-on-demand holder (RNF04)
public class Configuracao {
    private String regiaoPadrao;
    private double orcamentoMaximo;

    private Configuracao() {
        Properties props = new Properties();
        // Tenta ler de questao1/config.properties ou ./config.properties
        try (InputStream is = abrirArquivoConfig()) {
            if (is != null) {
                props.load(is);
                this.regiaoPadrao = props.getProperty("regiao.padrao", "us-east-1");
                this.orcamentoMaximo = Double.parseDouble(props.getProperty("orcamento.maximo", "500.00"));
            } else {
                // Fallback seguro caso executado de diretório inesperado
                this.regiaoPadrao = "sa-east-1";
                this.orcamentoMaximo = 500.00;
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar config.properties", e);
        }
    }

    private InputStream abrirArquivoConfig() {
        try {
            return new FileInputStream("config.properties");
        } catch (IOException e1) {
            try {
                return new FileInputStream("questao1/config.properties");
            } catch (IOException e2) {
                return null;
            }
        }
    }

    private static class Holder {
        private static final Configuracao INSTANCE = new Configuracao();
    }

    public static Configuracao getInstance() {
        return Holder.INSTANCE;
    }

    public String getRegiaoPadrao() { return regiaoPadrao; }
    public double getOrcamentoMaximo() { return orcamentoMaximo; }
}
