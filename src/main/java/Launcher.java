
import fi.bilot.JsonBodyReader;
import fi.bilot.JsonBodyWriter;
import fi.bilot.resources.JsonExceptionMapper;
import fi.bilot.resources.TaskResource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class Launcher {
    public static void main(String... arguments) throws URISyntaxException, IOException {
        final ResourceConfig config = new ResourceConfig()
                .register(JsonBodyReader.class)
                .register(JsonBodyWriter.class)
                .register(JsonExceptionMapper.class)
                .register(JacksonFeature.class)
                .register(TaskResource.class);

        final URI baseUri = new URI("http://localhost:8080");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();

        System.out.println("Server started on " + baseUri + ".");
        System.in.read();
        server.shutdown();
    }
}
