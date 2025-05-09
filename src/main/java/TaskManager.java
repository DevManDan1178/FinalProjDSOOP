import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TaskManager implements TaskOperations {
    private List<Task> tasks;
    private final static String pathInResources = "src/main/resources/";

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Gets the tasks to do and sets them in a queue based on the priority obtained from the comparator
     *
     * @param sortBy comparator for the task importance
     * @return queue of tasks (highest importance first)
     */
    public Queue<Task> getTasksToDo(Task.TaskComparator sortBy) {
        LinkedList<Task> sortedTasks = new LinkedList<Task>(getTasks());
        sortedTasks.sort(sortBy);
        return (Queue<Task>) sortedTasks;
    }

    /**
     * Exports the tasks into CSV format
     *
     * @param fileName the name of the file to export to
     */
    public void exportTasks(String fileName) {
        exportTasks(tasks, fileName);
    }

    /**
     * Gets the list of tasks obtained from a file with tasks in CSV format in it
     *
     * @param fileName name of the file with task data in CSV format
     * @return list of tasks extracted from the file
     */
    public static List<Task> importTasks(String fileName) {
        File file = new File(pathInResources + fileName);
        List<Task> importedTasks = new LinkedList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                Task exportedTask = Task.fromExportData(reader.nextLine());
                if (exportedTask != null) {
                    importedTasks.add(exportedTask);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR OBTAINING FILE - " + fileName);
        }
        return importedTasks;
    }

    /**
     * Gets a list of tasks with the keyword in the title | NOT CASE SENSITIVE
     *
     * @param keyword keyword to filter for
     * @return list of tasks with the keyword in the title
     */
    public List<Task> searchForTasks(String keyword) {
        return tasks.stream()
                .filter(
                        task -> (task.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                )
                .toList();
    }

    /**
     * Gets a list of tasks with the keyword in the title and completed or not based on the completion status filter | NOT CASE SENSITIVE
     *
     * @param keyword                keyword to filter for
     * @param completionStatusFilter if the tasks should be completed or not completed (Regular tasks count as not completed)
     * @return list of tasks with keyword in title and the appropriate completion status
     */
    public List<Task> searchForTasks(String keyword, boolean completionStatusFilter) {
        return tasks.stream()
                .filter(task -> {
                    if (task instanceof NonRegularTask NRTask) {
                        if (NRTask.isCompleted() != completionStatusFilter) {
                            return false;
                        }
                    } else if (completionStatusFilter) {
                        return false;
                    }
                    return task.getTitle().toLowerCase().contains(keyword.toLowerCase());
                })
                .toList();
    }

    /**
     * Exports the tasks into a file with CSV format
     *
     * @param exportTasks the tasks to export
     * @param fileName    the name of the file to export to
     */
    public static void exportTasks(Collection<Task> exportTasks, String fileName) {
        File file = new File(pathInResources + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : exportTasks) {
                writer.write(task.getExportData() + "\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR OBTAINING FILE - " + fileName);
        }
    }

    @Override
    public boolean addTask(Task task) {
        if (task == null || tasks.contains(task)) {
            return false;
        }
        tasks.add(task);
        return true;
    }

    @Override
    public boolean deleteTask(Task task) {
        if (task == null) {
            return false;
        }
        if (tasks.contains(task)) {
            tasks.remove(task);
            return true;
        }
        return false;
    }

    @Override
    public void sortTasks(Task.TaskComparator sortBy) {
        tasks.sort(sortBy);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
