import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.List;

import java.time.LocalDateTime;

public class UsersTest {
    @Test
    public void testFaultyExportData() {
        SuperUser expected = null;
        SuperUser actual = SuperUser.fromExportData("This,is,incorrect,export,data");
        Assertions.assertEquals(expected, actual);
    }

    //Test Admin class
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

    //Test User class
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
