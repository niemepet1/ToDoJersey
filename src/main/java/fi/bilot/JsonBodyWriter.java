package fi.bilot;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class JsonBodyWriter implements MessageBodyWriter<JsonStructure> {
    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }
    
    @Override
    public long getSize(JsonStructure t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return 0L;
    }
    
    @Override
    public void writeTo(JsonStructure t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        try (final JsonWriter writer = Json.createWriter(out)) {
            writer.write(t);
        }
    }
}
