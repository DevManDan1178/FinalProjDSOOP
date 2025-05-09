import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class RegularTaskTest {
    @Test
    public void testFromExportDataRT() {
        Task expected = new RegularTask("T", "D", 1, LocalDateTime.now(), 24);
        String exportData = expected.getExportData();
        Task actual = Task.fromExportData(exportData);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testRTSkip() {
        RegularTask task = new RegularTask("T", "D", 1, LocalDateTime.now(), 24);
        LocalDateTime expected = task.getDueDate().plusHours(24 * 4);
        LocalDateTime actual = task.skip(4);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRTGetExportData() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new RegularTask("T", "D", 1, dateTime, 24.0);
        String expected = String.format("%s,%s,%s,%s,%s,RT", "T", "D", 1, dateTime, 24.0);
        String actual = task.getExportData();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRTRefresh() {
        RegularTask task = new RegularTask("T", "D", 1, LocalDateTime.now(), 24);
        LocalDateTime expected = task.getDueDate().plusHours(24);
        LocalDateTime actual = task.refresh();
        Assertions.assertEquals(expected, actual);
    }

}
