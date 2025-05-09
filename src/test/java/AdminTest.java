import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class AdminTest {
    @Test
    public void testAdminGetExportData() {
        Admin admin = new Admin("name", 1);
        String expected = "name,1,Ad";
        String actual = admin.getExportData();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAdminFromExportData() {
        SuperUser expected = new Admin("name", 1);
        SuperUser actual = SuperUser.fromExportData("name,1,Ad");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCheckUserTasks() {
        Admin admin = new Admin("name", 1);
        User user = new User("name");
        Task dummyTask = new RegularTask("Title", "desc", 1, LocalDateTime.now(), 24);
        user.getTaskManager().addTask(dummyTask);
        List<Task> expected = new LinkedList<>(List.of(dummyTask));
        List<Task> actual = admin.checkUserTasks(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGiveUserTasks() {
        Admin admin = new Admin("name", 1);
        User user = new User("name");
        Task dummyTask = new RegularTask("Title", "desc", 1, LocalDateTime.now(), 24);
        boolean expectedReturn = true;
        boolean actualReturn = admin.giveUserTask(user, dummyTask);

        List<Task> expectedTasks = new LinkedList<>(List.of(dummyTask));
        List<Task> actualTasks = user.getTaskManager().getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testFaultyGiveUserTasks() {
        Admin admin = new Admin("name", 1);
        User user = new User("name");
        Task dummyTask = new RegularTask("Title", "desc", 1, LocalDateTime.now(), 24);
        user.getTaskManager().addTask(dummyTask);
        boolean expectedReturn = false;
        boolean actualReturn = admin.giveUserTask(user, dummyTask);

        List<Task> expectedTasks = new LinkedList<>(List.of(dummyTask));
        List<Task> actualTasks = user.getTaskManager().getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

}
