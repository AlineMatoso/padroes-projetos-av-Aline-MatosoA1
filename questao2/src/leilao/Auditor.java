package leilao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Auditor implements ObservadorLote {
    private final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void notificarNovoLance(Lote lote, Participante autor, double valor) {
        String registro = String.format("[%s] Lote: %s | Participante: %s | Lance: R$ %.2f",
                LocalDateTime.now().format(FMT), lote.getNome(), autor.getNome(), valor);
        logs.add(registro);
        System.out.println("  [Auditor] Registrado: " + registro);
    }

    public void imprimirAuditoria() {
        System.out.println("\n--- REGISTROS DO AUDITOR ---");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
