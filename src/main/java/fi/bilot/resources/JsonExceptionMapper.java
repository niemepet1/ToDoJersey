package fi.bilot.resources;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Aug 21, 2017.
 */
public class JsonExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        final String message = exception.getMessage();
        final JsonObject responseEntity = Json.createObjectBuilder()
                .add("message", message)
                .build();

        final Response response = exception.getResponse();
        final int status = response.getStatus();

        return Response.status(status)
                .entity(responseEntity.toString())
                .build();
    }
}
