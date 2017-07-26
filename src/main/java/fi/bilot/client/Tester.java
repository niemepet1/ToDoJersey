package fi.bilot.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import fi.bilot.model.ToDo;


public class Tester
{

	public static void main(final String[] args)
	{
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget service = client.target(getBaseURI());

		// create one todo
		final ToDo todo = new ToDo("3", "Blabla");
		Response response;
		response = service.path("rest").path("todos").path(todo.getId()).request(MediaType.APPLICATION_XML)
				.put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);

		System.out.println("Header string: " + response.getStringHeaders());

		System.out.println("Where am i ? ");
		// Return code should be 201 = created resource
		//System.out.println(response.getStatus());

		// Get the ToDos
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));

		// Get JSON for application

		// Get XML for application
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));

		// Get ToDo with id 1
		final Response checkDelete = service.path("rest").path("todos/1").request().accept(MediaType.APPLICATION_XML).get();

		// Delete Todo with id 1
		service.path("rest").path("todos/1").request().delete();

		// Get all Todos, ID 1 should be deleted
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));

		// Create a todo
		final Form form = new Form();
		form.param("id", "4");
		form.param("summary", "Demonstration of the client lib for forms");
		response = service.path("rest").path("todos").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
				Response.class);
		System.out.println("Form response " + response.getStatus());

		// Get all Todos, ID 4 should have been created
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));

	}

	private static URI getBaseURI()
	{
		return UriBuilder.fromUri("http://localhost:8080/ToDoJersey").build();
	}

}
