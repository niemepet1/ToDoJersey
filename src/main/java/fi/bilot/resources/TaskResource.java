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

    @POST
    @Path("tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(JsonObject taskEntity) {
        if(taskEntity.containsKey("description")){
            final String description = taskEntity.getString("description");
            final Task task = taskList.createTask(description);

            final JsonObject responseEntity = Json.createObjectBuilder()
                    .add("uri", "tasks/" + task.id)
                    .build();

            return Response.status(201)
                    .entity(responseEntity.toString())
                    .build();
        } else {
            final JsonObject responseEntity = Json.createObjectBuilder()
                    .add("message", "Description property was not specified")
                    .build();

            return Response.status(400)
                    .entity(responseEntity.toString())
                    .build();
        }

    }

    @DELETE
    @Path("tasks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("id") int id){
        taskList.createTask("Some different task");
        taskList.createTask("Another task");

        final Task task = taskList.getTask(id);

        if(task == null){
            final JsonObject responseEntity = Json.createObjectBuilder()
                    .add("message", "task with id "+ id +" not found")
                    .build();

            return Response.status(404)
                    .entity(responseEntity.toString())
                    .build();

        } else {
            taskList.deleteTask(task);
            return Response.status(204)
                    .build();
        }
    }
}
