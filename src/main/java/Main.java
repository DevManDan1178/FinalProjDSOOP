import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = LocalDateTime.now().minusDays(1);
        System.out.println(now.getDayOfYear() - soon.getDayOfYear());

    }

}
