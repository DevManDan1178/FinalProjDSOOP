public class User extends SuperUser {
    TaskManager taskManager;

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
        //TODO
    }

    /**
     * Sets the task to completed in the user's task manager
     *
     * @param task task to set as completed
     * @return true if just set to completed | false if nothing to set or if already set (nothing changed)
     */
    public boolean setTaskCompleted(Task task) {
        return false; //TODO
    }

    /**
     * Gets the User's data in CSV format
     *
     * @return CSV format of user's data
     */
    public String getExportData() {
        String tasksExportData = "";
        for (Task task : taskManager.getTasks()) {
            tasksExportData += task.getExportData();
        }
        return String.format("%s,%s", super.getExportData(), tasksExportData);
    }
}
