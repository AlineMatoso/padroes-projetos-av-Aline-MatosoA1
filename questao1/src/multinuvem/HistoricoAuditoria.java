package multinuvem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Singleton Thread-Safe via Double-Checked Locking + Synchronized List (RNF05)
public class HistoricoAuditoria {
    private static volatile HistoricoAuditoria instance;
    private final List<String> registros;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private HistoricoAuditoria() {
        this.registros = Collections.synchronizedList(new ArrayList<>());
    }

    public static HistoricoAuditoria getInstance() {
        if (instance == null) {
            synchronized (HistoricoAuditoria.class) {
                if (instance == null) {
                    instance = new HistoricoAuditoria();
                }
            }
        }
        return instance;
    }

    public void registrar(String ambiente, String provedor, String resultado) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String linha = String.format("[%s] Ambiente: %-15s | Provedor: %-6s | Resultado: %s",
                timestamp, ambiente, provedor, resultado);
        registros.add(linha);
    }

    public void imprimirHistorico() {
        System.out.println("\n==================== HISTÓRICO DE AUDITORIA ====================");
        synchronized (registros) {
            for (String reg : registros) {
                System.out.println(reg);
            }
        }
        System.out.println("==================================================================");
    }
}
