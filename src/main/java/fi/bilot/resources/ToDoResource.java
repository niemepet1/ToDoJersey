package fi.bilot.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

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
}
