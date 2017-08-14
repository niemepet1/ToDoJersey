package fi.bilot.resources;

import fi.bilot.service.Task;
import fi.bilot.service.TaskList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
@Path("/")
public class TaskResource {
    private final TaskList taskList = new TaskList();

    private JsonArray mapToJson(TaskList taskList) {
        final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Task task: taskList.tasks) {
            final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("description", task.description)
                    .add("isCompleted", task.isCompleted());
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        
        return jsonArrayBuilder.build();
    }
    /*
    @GET
    @Path("tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getTasks() {
        final Task task1 = new Task("Some different task");
        final Task task2 = new Task("Another task");
        taskList.addTask(task1);
        taskList.addTask(task2);
        
        return mapToJson(taskList);
    }
*/

    @GET
    @Path("tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getIncompleteTasks(
            @DefaultValue("false") @QueryParam("includeCompleted") Boolean includeCompleted) {
        final Task task1 = new Task("Some different task");
        final Task task2 = new Task("Another task");
        taskList.addTask(task1);
        taskList.addTask(task2);

        task1.setCompleted();

        if(includeCompleted == true) {
            return mapToJson(taskList);
        } else {
            final TaskList incompleteTasks = taskList.getIncompleteTasks();
            return mapToJson(incompleteTasks);
        }
    }
}
