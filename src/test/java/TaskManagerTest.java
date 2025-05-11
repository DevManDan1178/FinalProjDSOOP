import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.manipulation.Ordering;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskManagerTest {
    @Test
    public void testAddTask() {
        TaskManager taskManager = new TaskManager();
        Task task = new NonRegularTask("T", "d", 1, LocalDateTime.now());
        boolean expectedReturn = true;
        boolean actualReturn = taskManager.addTask(task);

        List<Task> expectedTasks = new LinkedList<>(List.of(task));
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testAddNullTask() {
        TaskManager taskManager = new TaskManager();
        boolean expectedReturn = false;
        boolean actualReturn = taskManager.addTask(null);

        List<Task> expectedTasks = new LinkedList<>();
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testAddTaskRedundant() {
        TaskManager taskManager = new TaskManager();
        Task task = new NonRegularTask("T", "d", 1, LocalDateTime.now());
        taskManager.addTask(task);
        boolean expectedReturn = false;
        boolean actualReturn = taskManager.addTask(task);

        List<Task> expectedTasks = new LinkedList<>(List.of(task));
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testDeleteTask() {
        TaskManager taskManager = new TaskManager();
        Task task = new NonRegularTask("T", "d", 1, LocalDateTime.now());
        taskManager.addTask(task);
        boolean expectedReturn = true;
        boolean actualReturn = taskManager.deleteTask(task);

        List<Task> expectedTasks = new LinkedList<>();
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testDeleteNullTask() {
        TaskManager taskManager = new TaskManager();
        boolean expectedReturn = false;
        boolean actualReturn = taskManager.deleteTask(null);

        List<Task> expectedTasks = new LinkedList<>();
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testDeleteTaskRedundant() {
        TaskManager taskManager = new TaskManager();
        Task task = new NonRegularTask("T", "d", 1, LocalDateTime.now());
        boolean expectedReturn = false;
        boolean actualReturn = taskManager.deleteTask(task);

        List<Task> expectedTasks = new LinkedList<>();
        List<Task> actualTasks = taskManager.getTasks();

        Assertions.assertEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void testSortTasksPriority() {
        TaskManager taskManager = new TaskManager();
        List<Task> expected = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            Task task = new NonRegularTask("Task" + i, "", i, LocalDateTime.now().plusDays(15 - i));
            taskManager.addTask(task);
            expected.add(task);
        }
        taskManager.sortTasks(new Task.TaskComparator(Task.TaskComparator.TaskCompareType.Priority));
        List<Task> actual = taskManager.getTasks();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSortTasksDueDate() {
        TaskManager taskManager = new TaskManager();
        List<Task> expected = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            Task task = new NonRegularTask("Task" + i, "", i, LocalDateTime.now().plusDays(15 - i));
            taskManager.addTask(task);
            expected.addFirst(task);
        }
        taskManager.sortTasks(new Task.TaskComparator(Task.TaskComparator.TaskCompareType.DueDate));
        List<Task> actual = taskManager.getTasks();

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testSearchForTasks() {
        TaskManager taskManager = new TaskManager();
        Task task1 = new RegularTask("Clean the floor", "", 1, LocalDateTime.now().plusDays(1), 48);
        Task task2 = new RegularTask("Clean the toilets", "", 1, LocalDateTime.now().plusDays(2), 48);
        Task task3 = new NonRegularTask("Fix the toilet lights", "", 1, LocalDateTime.now().plusDays(1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        List<Task> expected = new LinkedList<>(List.of(task1, task2));
        List<Task> actual = taskManager.searchForTasks("Clean");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSearchForTasksWithCompletionFilterFalse() {
        TaskManager taskManager = new TaskManager();
        Task task1 = new RegularTask("Clean the floor", "", 1, LocalDateTime.now().plusDays(1), 48);
        Task task2 = new RegularTask("Clean the toilets", "", 1, LocalDateTime.now().plusDays(2), 48);
        Task task3 = new NonRegularTask("Fix the toilet lights", "", 1, LocalDateTime.now().plusDays(1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        List<Task> expected = new LinkedList<>(List.of(task2, task3));
        List<Task> actual = taskManager.searchForTasks("toilet", false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSearchForTasksWithCompletionFilterTrue() {
        TaskManager taskManager = new TaskManager();
        Task task1 = new RegularTask("Clean the floor", "", 1, LocalDateTime.now().plusDays(1), 48);
        Task task2 = new RegularTask("Clean the toilets", "", 1, LocalDateTime.now().plusDays(2), 48);
        Task task3 = new NonRegularTask("Fix the toilet lights", "", 1, LocalDateTime.now().plusDays(1), true);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        List<Task> expected = new LinkedList<>(List.of(task3));
        List<Task> actual = taskManager.searchForTasks("toilet", true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetTasksToDo() {
        TaskManager taskManager = new TaskManager();
        Task task1 = new RegularTask("Clean the floor", "", 2, LocalDateTime.now().plusDays(1), 48);
        Task task2 = new RegularTask("Clean the toilets", "", 1, LocalDateTime.now().plusDays(2), 48);
        Task task3 = new NonRegularTask("Fix the toilet lights", "", 3, LocalDateTime.now().plusDays(1), false);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        Queue<Task> expected = new LinkedList<>();
        expected.add(task3);
        expected.add(task1);
        expected.add(task2);

        Queue<Task> actual = taskManager.getTasksToDo(new Task.TaskComparator(Task.TaskComparator.TaskCompareType.Priority));
        Assertions.assertEquals(expected, actual);
    }
}
