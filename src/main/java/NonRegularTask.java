import java.time.LocalDateTime;

public class NonRegularTask extends Task {
    private boolean completed;

    public NonRegularTask(String title, String description, int taskPriority, LocalDateTime dueDate) {
        this(title, description, taskPriority, dueDate, false);
    }

    public NonRegularTask(String title, String description, int taskPriority, LocalDateTime dueDate, boolean completed) {
        super(title, description, taskPriority, dueDate);
        this.completed = completed;
    }

    /**
     * Sets the task to completed and sets priority to -1
     */
    public void closeTask() {
        completed = true;
        setDescription("(Completed) " + getDescription());
        setTaskPriority(-1);
    }

    @Override
    public String getExportData() {
        return String.format("%s,%s,NRT", super.getExportData(), completed);
    }

    public boolean isCompleted() {
        return completed;
    }

    //no setCompleted(), instead use closeTask()
}
