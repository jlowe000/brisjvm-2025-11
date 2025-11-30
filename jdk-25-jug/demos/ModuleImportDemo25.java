import module java.net.http;
import module java.base;

public class ModuleImportDemo25 {
    void main() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://httpbin.org/json"))
            .build();
        
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        long duration = System.currentTimeMillis() - start;
        IO.println("Response status: " + response.statusCode());
        IO.println("Execution time: " + duration + "ms");
        IO.println("Lines of imports: 1");
    }
}
