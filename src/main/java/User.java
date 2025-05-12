import java.util.ArrayList;
import java.util.Objects;

public class User extends SuperUser {
    private TaskManager taskManager;

    public User(String name) {
        super(name);
        taskManager = new TaskManager();
    }

    public User(String name, int id) {
        super(name, id);
        this.taskManager = new TaskManager();
    }

    /**
     * Clears the user's task manager's tasks
     */
    public void clearTasks() {
        taskManager.setTasks(new ArrayList<Task>());
    }

    /**
     * Sets the task to completed in the user's task manager
     *
     * @param task task to set as completed (must be NonRegularTask)
     * @return true if just set to completed | false if nothing to set or if already set (nothing changed)
     */
    public boolean setTaskCompleted(Task task) {
        if (taskManager.getTasks().contains(task) && task instanceof NonRegularTask NRTask) {
            NRTask.closeTask();
            return true;
        }
        return false;
    }

    /**
     * Gets the User's data in CSV format
     *
     * @return CSV format of user's data
     */
    public String getExportData() {
        String tasksExportData = "";
        for (Task task : taskManager.getTasks()) {
            tasksExportData += task.getExportData().replace(',', '▀') + ",";
        }
        return String.format("%s,%s", super.getExportData(), tasksExportData.substring(0, tasksExportData.length() - 1));
    }

    @Override
    public String toString() {
        return "User{" +
                "taskManager=" + taskManager +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(taskManager, user.taskManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taskManager);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        if (taskManager == null) {
            return;
        }
        this.taskManager = taskManager;
    }
}
