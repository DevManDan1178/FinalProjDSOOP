import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class TaskManager implements TaskOperations {
    private List<Task> tasks;

    /**
     * Gets the tasks to do and sets them in a queue based on the priority obtained from the comparator
     * @param sortBy comparator for the task importance
     * @return queue of tasks (highest importance first)
     */
    public Queue<Task> getTasksToDo(Task.TaskComparator sortBy) {
        return null; //TODO
    }

    /**
     * Exports the tasks into CSV format
     * @param fileName the name of the file to export to
     */
    public void exportTasks(String fileName) {
        //TODO
    }

    /**
     * Gets the list of tasks obtained from a file with tasks in CSV format in it
     * @param fileName name of the file with task data in CSV format
     * @return list of tasks extracted from the file
     */
    public List<Task> importTasks(String fileName) {
        //TODO
        return null;
    }

    /**
     * Gets a list of tasks with the keyword in the title | NOT CASE SENSITIVE
     * @param keyword keyword to filter for
     * @return list of tasks with the keyword in the title
     */
    public List<Task> searchForTasks(String keyword) {
        return null; //TODO
    }

    /**
     * Gets a list of tasks with the keyword in the title and completed or not based on the completion status filter | NOT CASE SENSITIVE
     * @param keyword keyword to filter for
     * @param completionStatusFilter if the tasks should be completed or not completed
     * @return list of tasks with keyword in title and the appropriate completion status
     */
    public List<Task> searchForTasks(String keyword, boolean completionStatusFilter) {
        return null; //TODO
    }

    /**
     * Exports the tasks into a file with CSV format
     * @param tasks the tasks to export
     * @param fileName the name of the file to export to
     */
    public static void exportTasks(Collection<Task> tasks, String fileName) {
        //TODO
    }

    @Override
    public boolean addTask(Task task) {
        return false; //TODO
    }

    @Override
    public void deleteTask(Task task) {
        //TODO
    }

    @Override
    public void sortTasks(Task.TaskComparator sortBy) {
        //TODO
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
