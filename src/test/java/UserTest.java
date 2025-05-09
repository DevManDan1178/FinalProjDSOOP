import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class UserTest {
    @Test
    public void testUserGetExportData() {
        User user = new User("name", 1);
        String expected = "name,1,";
        String actual = user.getExportData();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUserFromExportData() {
        SuperUser expected = new User("name", 1);
        SuperUser actual = SuperUser.fromExportData("name,1,");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testClearTasks() {
        User user = new User("name", 1);
        user.getTaskManager().addTask(new RegularTask("Water the plants", "", 1, LocalDateTime.now().plusHours(5), 24));
        user.getTaskManager().addTask(new NonRegularTask("Fix the chair", "fix the chair", 3, LocalDateTime.now().plusDays(3)));
        List<Task> expected = new LinkedList<Task>();
        user.clearTasks();
        List<Task> actual = user.getTaskManager().getTasks();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSetTaskCompleted() {
        User user = new User("name", 1);
        LocalDateTime duedate = LocalDateTime.now().plusDays(4);
        NonRegularTask actualTask = new NonRegularTask("Coding project", "do it", 5, duedate);
        NonRegularTask expectedTask = new NonRegularTask("(Completed) Coding project", "do it", -1, duedate, true);
        user.getTaskManager().addTask(actualTask);

        boolean expectedReturn = true;
        boolean actualReturn = user.setTaskCompleted(actualTask);

        Assertions.assertEquals(expectedReturn, actualReturn);
        Assertions.assertEquals(expectedTask, actualTask);
    }
}
