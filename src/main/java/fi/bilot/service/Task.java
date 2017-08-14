package fi.bilot.service;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class Task {
    public final int id;
    public final String description;
    private boolean completed = false;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}
