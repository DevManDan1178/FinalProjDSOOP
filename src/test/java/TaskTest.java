import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class TaskTest {
    @Test
    public void testPreReschedule() {
        LocalDateTime expected = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, expected);
        boolean expectedReturn = false;
        boolean actualReturn = task.reschedule(expected.minusDays(5));
        LocalDateTime actual = task.getDueDate();
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testPostReschedule() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new NonRegularTask("T", "D", 1, dateTime);
        boolean expectedReturn = true;
        boolean actualReturn = task.reschedule(dateTime.plusDays(5));
        LocalDateTime expected = dateTime.plusDays(5);
        LocalDateTime actual = task.getDueDate();
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }


    @Test
    public void testFromInvalidExportData() {
        Task expected = null;
        Task actual = Task.fromExportData("this is not valid export data");
        Assertions.assertEquals(expected, actual);
    }

    //Used in the TaskComparator
    @Test
    public void testMinBetweenDates() {
        int expected = 20000;
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime inALotOfMinutes = dateTime.plusMinutes(expected);
        int actual = Task.TaskComparator.minutesBetweenDates(dateTime, inALotOfMinutes);
        Assertions.assertEquals(expected, actual);
    }


}
