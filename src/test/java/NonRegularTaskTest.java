import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class NonRegularTaskTest {
    @Test
    public void testNRTGetExportData() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, dateTime);
        System.out.println(task.getExportData());
        String expected = String.format("%s,%s,%s,%s,%s,NRT", "T", "D", 1, dateTime, false);
        String actual = task.getExportData();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testNRTClose() {
        NonRegularTask task = new NonRegularTask("T", "D", 1, LocalDateTime.now());
        task.closeTask();
        Assertions.assertTrue(task.isCompleted());
        Assertions.assertEquals("(Completed) D", task.getDescription());
        Assertions.assertEquals(-1, task.getTaskPriority());
    }

    @Test
    public void testFromExportDataNRT() {
        Task expected = new NonRegularTask("T", "D", 1, LocalDateTime.now());
        String exportData = expected.getExportData();
        Task actual = Task.fromExportData(exportData);
        Assertions.assertEquals(expected, actual);
    }
}
