package fi.bilot.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import fi.bilot.dao.ToDoDao;
import fi.bilot.model.ToDo;



public class ToDoResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

	public ToDoResource(final UriInfo uriInfo, final Request request, final String id)
	{
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	// Application integration
	@GET
	@Produces(
	{ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ToDo getTodo()
	{
		final ToDo todo = ToDoDao.instance.getModel().get(id);
		if (todo == null)
		{
			throw new RuntimeException("Get: Todo with " + id + " not found");
		}
		return todo;
	}

	// for the browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public ToDo getToDoHTML()
	{
		final ToDo todo = ToDoDao.instance.getModel().get(id);
		if (todo == null)
		{
			throw new RuntimeException("Get: Todo with " + id + " not found");
		}
		return todo;
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response putToDo(final JAXBElement<ToDo> todo)
	{
		final ToDo c = todo.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteToDo()
	{
		final ToDo c = ToDoDao.instance.getModel().remove(id);
		if (c == null)
		{
			throw new RuntimeException("Delete: Todo with " + id + " not found");
		}
	}

	private Response putAndGetResponse(final ToDo todo)
	{
		Response res;
		if (ToDoDao.instance.getModel().containsKey(todo.getId()))
		{
			res = Response.noContent().build();
		}
		else
		{
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		ToDoDao.instance.getModel().put(todo.getId(), todo);
		return res;
	}
}
