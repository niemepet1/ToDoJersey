package fi.bilot.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class TaskList {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public int getSize() {
        return tasks.size();
    }

    public void deleteTask(Task task) {
        tasks.remove(task.id);
    }

    public int getHighestId() {
        final Set<Integer> ids = tasks.keySet();

        int highestId = -1;
        for (int id : ids) {
            if (id > highestId) {
                highestId = id;
            }
        }

        return highestId;
    }

    public Task createTask(String description, Date dueDate) {
        final int highestId = getHighestId();
        final int taskId = highestId + 1;

        if (dueDate != null) {
            final Date currentDate = new Date();
            if (dueDate.before(currentDate)) {
                throw new IllegalArgumentException("Due date is in the past");
            }
        }

        final Task task = new Task(taskId, description, dueDate);
        tasks.put(task.id, task);

        return task;
    }

    public Task createTask(String description) {
        return createTask(description, null);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Collection<Task> getTasks() {
        return tasks.values();
    }

    public Collection<Task> getIncompleteTasks() {
        final List<Task> incompleteTasks = new LinkedList<>();

        for (Task task : tasks.values()) {
            if (!task.isCompleted()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    public void completeTask(String description) {
        for (Task task : tasks.values()) {
            if (task.description.equals(description)) {
                System.out.println("Completing a task...");
                task.setCompleted();
            }
        }
        System.out.println("Finished searching tasks to complete...");
    }
}
