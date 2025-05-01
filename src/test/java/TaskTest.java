import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class TaskTest {
    @Test
    public void testPreReschedule() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, dateTime);
        task.reschedule(dateTime.minusDays(5));
        LocalDateTime expected = dateTime;
        LocalDateTime actual = task.getDueDate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPostReschedule() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, dateTime);
        task.reschedule(dateTime.plusDays(5));
        LocalDateTime expected = dateTime.plusDays(5);
        LocalDateTime actual = task.getDueDate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testNRTGetExportData() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, dateTime);
        System.out.println(task.getExportData());
        String expected = String.format("%s,%s,%s,%s,%s,NRT", "T", "D", 1, dateTime,  false);
        String actual = task.getExportData();
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
    public void testFromExportDataNRT() {
        Task expected = new NonRegularTask("T", "D", 1, LocalDateTime.now());
        String exportData = expected.getExportData();
        Task actual = Task.fromExportData(exportData);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFromExportDataRT() {
        Task expected = new RegularTask("T", "D", 1, LocalDateTime.now(), 24);
        String exportData = expected.getExportData();
        Task actual = Task.fromExportData(exportData);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFromInvalidExportData() {
        Task expected = null;
        Task actual = Task.fromExportData("this is not valid export data");
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
    public void testRTRefresh() {
        RegularTask task = new RegularTask("T", "D", 1, LocalDateTime.now(), 24);
        LocalDateTime expected = task.getDueDate().plusHours(24);
        LocalDateTime actual = task.refresh();
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
    public void testMinBetweenDates() {
        int expected = 20000;
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime inALotOfMinutes = dateTime.plusMinutes(expected);
        int actual = Task.TaskComparator.minutesBetweenDates(dateTime, inALotOfMinutes);
        Assertions.assertEquals(expected, actual);
    }

}
