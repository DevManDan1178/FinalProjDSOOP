public interface TaskOperations {
    /**
     * Adds a task to the list of tasks
     * @param task task to add
     * @return true if was just added | false if was already there (nothing was done)
     */
    boolean addTask(Task task);

    /**
     * Deletes the task from the list of tasks
     *
     * @param task task to delete
     * @return true if was deleted | false if nothing happened (not there to begin with)
     */
    boolean deleteTask(Task task);

    /**
     * Sorts the tasks in the list of tasks
     */
    void sortTasks(Task.TaskComparator sortBy);
}
