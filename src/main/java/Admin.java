import java.util.List;

public class Admin extends SuperUser{
    public Admin(String name) {
        super(name);
    }

    public Admin(String name, int id) {
        super(name, id);
    }

    /**
     * Checks the tasks a user has
     * @param user user to check
     * @return list of tasks the user has
     */
    public List<Task> checkUserTasks(User user) {
        return null; //TODO
    }

    /**
     * Gives the task to the user
     * @param user the user to give to
     * @param task the task to give
     * @return true if just added the task, false if task was already there (nothing was done)
     */
    public boolean giveUserTask(User user, Task task) {
        return false; //TODO
    }

    /**
     * Used to obtain CSV format of the data members
     * @return CSV format of the data
     */
    public String getExportData() {
        return String.format("%s,%s", super.getExportData(), "Ad");
    }
}
