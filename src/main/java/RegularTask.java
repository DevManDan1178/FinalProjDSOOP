import java.time.LocalDateTime;

public class RegularTask extends Task {
    private double timeIntervalHours;

    public RegularTask(String title, String description, int taskPriority, LocalDateTime dueDate, double timeIntervalHours) {
        super(title, description, taskPriority, dueDate);
        this.timeIntervalHours = timeIntervalHours;
    }

    /**
     * Reschedules the due date to the next due date after skipping ([?] times)
     *
     * @param amount amount of times to skip
     * @return the new due date it was rescheduled to
     */
    public LocalDateTime skip(int amount) {
        LocalDateTime newDueDate = getDueDate()
                .plusHours((long) (timeIntervalHours * amount))
                .plusMinutes((long) ((timeIntervalHours % 1) * 60));
        reschedule(newDueDate);
        return newDueDate;
    }

    /**
     * Reschedules the due date to the next time (use when task is completed)
     *
     * @return the new due date
     */
    public LocalDateTime refresh() {
        return skip(1);
    }

    @Override
    public String getExportData() {
        return String.format("%s,%s,RT", super.getExportData(), timeIntervalHours);
    }

    public double getTimeIntervalHours() {
        return timeIntervalHours;
    }

    public void setTimeIntervalHours(double timeIntervalHours) {
        this.timeIntervalHours = timeIntervalHours;
    }
}
