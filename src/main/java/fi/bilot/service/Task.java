package fi.bilot.service;

import java.util.Date;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class Task {
    public final int id;
    public final String description;
    public final Date dueDate;

    private boolean completed = false;

    public Task(int id, String description, Date dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}
