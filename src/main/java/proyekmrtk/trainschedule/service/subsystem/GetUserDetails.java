package proyekmrtk.trainschedule.service.subsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import proyekmrtk.trainschedule.model.UserDetails;

@Component
public class GetUserDetails {
    @Value("${app.key}")
    private String key;

    @Value("${app.main-domain}")
    private String root;

    public UserDetails useHTTP(String jwt) {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        // create the HTTP request
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(this.root + "authenticate"))
                .header("Cookie", String.format("%s=%s", this.key, jwt))
                .GET().build();

        // send the HTTP request asynchronously
        CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        // deserialize the JSON response body asynchronously into a UserDetails object using Jackson
        var objectMapper = new ObjectMapper();
        CompletableFuture<UserDetails> userDetailsFuture = future.thenApplyAsync(httpResponse -> {
            try {
                return objectMapper.readValue(httpResponse.body(), UserDetails.class);
            } catch (Exception e) {
                return null;
            }
        });

        return userDetailsFuture.join();
    }
}
