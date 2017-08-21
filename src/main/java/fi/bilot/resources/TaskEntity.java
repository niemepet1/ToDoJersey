package fi.bilot.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.bilot.service.Task;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Aug 16, 2017.
 */
public class TaskEntity {
    @JsonProperty("uri")
    final String uri;
    @JsonProperty("description")
    final String description;
    @JsonProperty("completed")
    final boolean completed;

    public TaskEntity(Task task) {
        uri = "tasks/" + task.id;
        description = task.description;
        completed = task.isCompleted();
    }
}
