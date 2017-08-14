package fi.bilot.resources;

import fi.bilot.service.Task;
import fi.bilot.service.TaskList;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    private JsonArray mapToJson(Collection<Task> tasks) {
        final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Task task : tasks) {
            final JsonObject jsonObject = mapToJson(task);
            jsonArrayBuilder.add(jsonObject);
        }

        return jsonArrayBuilder.build();
    }

    private JsonArray mapToJson(TaskList taskList) {
        return mapToJson(taskList.getTasks());
    }

    @GET
    @Path("tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getIncompleteTasks(
            @DefaultValue("false") @QueryParam("includeCompleted") Boolean includeCompleted) {
        taskList.createTask("Some different task");
        taskList.createTask("Another task");

        if (includeCompleted == true) {
            return mapToJson(taskList);
        }
        else {
            final Collection<Task> incompleteTasks = taskList.getIncompleteTasks();
            return mapToJson(incompleteTasks);
        }
    }

    @GET
    @Path("tasks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getTask(@PathParam("id") int id) {
        taskList.createTask("Some different task");
        taskList.createTask("Another task");

        final Task task = taskList.getTask(id);
        return mapToJson(task);
    }

    // {uri: "tasks/8" }
    // tasks/8
    @POST
    @Path("tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(JsonObject taskEntity) {
        final String decription = taskEntity.getString("descritpion");
        final Task task = taskList.createTask(decription);

        final JsonObject responseEntity = Json.createObjectBuilder()
                .add("uri", "tasks/" + task.id)
                .build();

        return Response.status(201)
                .entity(responseEntity.toString())
                .build();
    }
}
