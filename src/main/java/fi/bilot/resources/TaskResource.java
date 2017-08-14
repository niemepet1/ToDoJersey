package fi.bilot.resources;

import fi.bilot.service.Task;
import fi.bilot.service.TaskList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
@Path("/")
public class TaskResource {
    private final TaskList taskList = new TaskList();

    private JsonObject mapToJson(Task task) {
        return Json.createObjectBuilder()
                .add("uri", "tasks/" + task.id)
                .add("description", task.description)
                .add("isCompleted", task.isCompleted())
                .build();
    }

    private JsonArray mapToJson(TaskList taskList) {
        final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Task task : taskList.tasks) {
            final JsonObject jsonObject = mapToJson(task);
            jsonArrayBuilder.add(jsonObject);
        }

        return jsonArrayBuilder.build();
    }

    @GET
    @Path("tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getIncompleteTasks(
            @DefaultValue("false") @QueryParam("includeCompleted") Boolean includeCompleted) {
        final Task task1 = new Task(0, "Some different task");
        final Task task2 = new Task(1, "Another task");
        taskList.addTask(task1);
        taskList.addTask(task2);

        task1.setCompleted();

        if (includeCompleted == true) {
            return mapToJson(taskList);
        }
        else {
            final TaskList incompleteTasks = taskList.getIncompleteTasks();
            return mapToJson(incompleteTasks);
        }
    }

    @GET
    @Path("tasks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getTask(@PathParam("id") int id) {
        final Task task1 = new Task(0, "Some different task");
        final Task task2 = new Task(1, "Another task");
        taskList.addTask(task1);
        taskList.addTask(task2);

        task1.setCompleted();
        
        final Task task = taskList.getTask(id);
        return mapToJson(task);
    }
}
