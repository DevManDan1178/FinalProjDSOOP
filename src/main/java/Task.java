import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public abstract class Task implements Comparable<Task> {
    private String title;
    private String description;
    private int taskPriority;
    private LocalDateTime dueDate;

    public Task(String title, String description, int taskPriority, LocalDateTime dueDate) {
        this.description = description;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.title = title;
    }

    /**
     * Reschedules the task's due date if it is after the current day
     *
     * @param newDueDate the new date to reschedule to
     * @return true if successful | false if unsuccessful (invalid date)
     */
    public boolean reschedule(LocalDateTime newDueDate) {
        if (newDueDate.isAfter(LocalDateTime.now())) {
            dueDate = newDueDate;
            return true;
        }
        return false;
    }

    /**
     * Gets the CSV format of the task data (string)
     *
     * @return task data as CSV format
     */
    public String getExportData() {
        return String.format("%s,%s,%s,%s", title, description, taskPriority, dueDate);
    }

    /**
     * Creates a task from the CSV formatted data
     *
     * @param data data in CSV format (string)
     * @return the corresponding task
     */
    public static Task fromExportData(String data) {
        String[] arguments = data.split(",");
        if (arguments.length != 6) {
            System.out.println("Invalid export data - " + data);
            return null;
        }
        try {
            return switch (arguments[5]) {
                case "NRT" ->
                        new NonRegularTask(arguments[0], arguments[1], Integer.parseInt(arguments[2]), LocalDateTime.parse(arguments[3]), Boolean.parseBoolean(arguments[4]));
                case "RT" ->
                        new RegularTask(arguments[0], arguments[1], Integer.parseInt(arguments[2]), LocalDateTime.parse(arguments[3]), Double.parseDouble(arguments[4]));
                default -> throw new IllegalArgumentException("Invalid task data - " + data);
            };
        } catch (Exception e) {
            System.out.println("Error extracting export data - " + data);
            return null;
        }
    }

    @Override
    public int compareTo(Task o) {
        return taskPriority - o.taskPriority;
    }

    public static class TaskComparator implements Comparator<Task> {
        /**
         * By priority | By due date | default compares both, prioritising
         */
        TaskCompareType compareType;

        public TaskComparator(TaskCompareType compareType) {
            this.compareType = compareType;
        }

        /**
         * Used to compare dates while sorting by default
         *
         * @param date1 date of the first element
         * @param date2 date of the second element
         * @return positive if first is earlier, negative if first is later, number corresponds to the difference in minutes between them
         */
        public static int minutesBetweenDates(LocalDateTime date1, LocalDateTime date2) {
            return -((date1.getYear() - date2.getYear()) * 525600 +
                    (date1.getDayOfYear() - date2.getDayOfYear()) * 1440 +
                    (date1.getHour() - date2.getHour()) * 60 +
                    (date1.getMinute() - date2.getMinute()));

        }

        @Override
        public int compare(Task o1, Task o2) {
            return switch (compareType) {
                case Priority -> o1.getTaskPriority() - o2.getTaskPriority();
                case DueDate -> o1.getDueDate().compareTo(o2.getDueDate());
                default ->
                        (o1.getTaskPriority() - o2.getTaskPriority()) * 7200 + minutesBetweenDates(o1.getDueDate(), o2.getDueDate());
            };
        }

        public enum TaskCompareType {
            Priority, DueDate, Default
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskPriority=" + taskPriority +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskPriority == task.taskPriority && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskPriority, dueDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    //no setDueDate(), use reschedule instead
}
