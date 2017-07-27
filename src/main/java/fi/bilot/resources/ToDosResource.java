package fi.bilot.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import fi.bilot.dao.ToDoDao;
import fi.bilot.model.ToDo;


// Will map the resource to the URL todos
@Path("/todos")
public class ToDosResource
{
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<ToDo> getToDosBrowser()
	{
		final List<ToDo> todos = new ArrayList<ToDo>();
		todos.addAll(ToDoDao.instance.getModel().values());
		return todos;
	}

	// Return the list of todos for applications
	@GET
	@Produces(
	{ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ToDo> getToDos()
	{
		final List<ToDo> todos = new ArrayList<ToDo>();
		todos.addAll(ToDoDao.instance.getModel().values());
		return todos;
	}

	// returns the number of todos
	// Use http://localhost:8080/fi.bilot.model...../rest/todos/count
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount()
	{
		final int count = ToDoDao.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newToDo(@FormParam("id") final int id, @FormParam("summary") final String summary,
			@FormParam("description") final String description, @Context final HttpServletResponse servletResponse)
			throws IOException
	{
		final ToDo todo = new ToDo(id, summary, description);
		ToDoDao.instance.insertToDo(todo);

		servletResponse.sendRedirect("../create_todo.html");
	}

	// Defines that the next path parameter after todos is
	// treated as a parameter and passed to the TodoResources
	// Allows to type http://localhost:8080/fi.bilot...../rest/todos/1
	// 1 will be treated as parameter todo and passed to ToDoResource
	@GET
	@Path("{todo}")
	//@Produces(MediaType.TEXT_PLAIN)
	public ToDoResource getToDo(@PathParam("todo") final int id)
	{
		//return "we are her displaying the task!";
		return new ToDoResource(uriInfo, request, id);
	}

	@PUT
	@Path("{todo}")
	@Consumes(MediaType.APPLICATION_XML)
	public void putToDo(@PathParam("todo") final String id)
	{
		System.out.println("Doing nothing with put request"); //return "we are her displaying the task!"; //return new
		//ToDoResource(uriInfo, request, id);
	}

}
