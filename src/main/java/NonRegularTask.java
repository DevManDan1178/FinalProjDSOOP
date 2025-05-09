import java.time.LocalDateTime;
import java.util.Objects;

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
        setTitle("(Completed) " + getTitle());
        setTaskPriority(-1);
    }

    @Override
    public String getExportData() {
        return String.format("%s,%s,NRT", super.getExportData(), completed);
    }

    @Override
    public String toString() {
        return "NonRegularTask{" +
                "completed=" + completed +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NonRegularTask that = (NonRegularTask) o;
        return completed == that.completed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), completed);
    }

    public boolean isCompleted() {
        return completed;
    }

    //no setCompleted(), instead use closeTask()
}
