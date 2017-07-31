package fi.bilot.service;

import java.util.Collection;
import java.util.LinkedList;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class TaskList {
    private final Collection<Task> tasks = new LinkedList<>();

    public int getSize() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
