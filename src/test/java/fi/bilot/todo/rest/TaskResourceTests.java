package fi.bilot.todo.rest;

import fi.bilot.JsonBodyReader;
import fi.bilot.JsonBodyWriter;
import fi.bilot.resources.JsonExceptionMapper;
import fi.bilot.resources.TaskResource;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Aug 21, 2017.
 */
public class TaskResourceTests {
    private static final URI BASE_URI = URI.create("http://localhost:8080");
    private HttpServer server;

    @Before
    public void setUpService() throws IOException {
        final ResourceConfig config = new ResourceConfig()
                .register(JsonBodyReader.class)
                .register(JsonBodyWriter.class)
                .register(JsonExceptionMapper.class)
                .register(TaskResource.class);

        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
        server.start();
    }

    @After
    public void shotdownService() {
        server.shutdown();
    }

    private JsonObject readJson(Response response) {
        final String responseData = response.readEntity(String.class);
        final StringReader stringReader = new StringReader(responseData);
        final JsonReader jsonReader = Json.createReader(stringReader);

        return jsonReader.readObject();
    }

    @Test
    public void testGetTask() {
        final JsonObject requestEntity = Json.createObjectBuilder()
                .add("description", "Some task")
                .build();

        final String requestData = requestEntity.toString();
        final Response createTaskResponse = ClientBuilder.newClient()
                .target(BASE_URI)
                .path("tasks")
                .request()
                .post(Entity.json(requestData));

        final JsonObject createTaskEntity = readJson(createTaskResponse);
        final String taskUri = createTaskEntity.getString("uri");

        final JsonObject expectedTaskJsonObject = Json.createObjectBuilder()
                .add("uri", taskUri)
                .add("description", "Some task")
                .add("completed", false)
                .build();

        final Response response = ClientBuilder.newClient()
                .target(BASE_URI)
                .path(taskUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        final int status = response.getStatus();
        Assert.assertEquals(200, status);

        final JsonObject taskJsonObject = readJson(response);
        Assert.assertEquals(expectedTaskJsonObject, taskJsonObject);
    }

    @Test
    public void testGetNonExistentTask() {
        final Response response = ClientBuilder.newClient()
                .target(BASE_URI)
                .path("tasks/10")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        final int status = response.getStatus();
        Assert.assertEquals(404, status);

        final JsonObject jsonObject = readJson(response);
        final String message = jsonObject.getString("message", null);
        Assert.assertNotNull(message);
    }

}
