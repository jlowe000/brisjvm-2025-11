import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.io.IOException;

public class ModuleImportDemo11 {
    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://httpbin.org/json"))
            .build();
        
        HttpResponse response = client.send(request, BodyHandlers.ofString());
        
        long duration = System.currentTimeMillis() - start;
        System.out.println("Response status: " + response.statusCode());
        System.out.println("Execution time: " + duration + "ms");
        System.out.println("Lines of imports: 7");
    }
}
