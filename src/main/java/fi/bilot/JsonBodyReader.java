package fi.bilot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Aug 14, 2017.
 */
public class JsonBodyReader implements MessageBodyReader<JsonStructure> {
    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }

    @Override
    public JsonStructure readFrom(Class<JsonStructure> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        try (final JsonReader reader = Json.createReader(in)) {
            return reader.read();
        }
    }
}
