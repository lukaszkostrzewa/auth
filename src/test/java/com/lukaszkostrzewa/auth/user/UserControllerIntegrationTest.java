package com.lukaszkostrzewa.auth.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszkostrzewa.auth.AuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    private static final String LOGIN = "Test123";
    private static final String TOO_SHORT_LOGIN = "A";
    private static final String PASSWORD = "Test12345";
    private static final String TOO_WEAK_PASSWORD = "test";

    @LocalServerPort
    private int port;
    private final HttpClient client = HttpClient.newHttpClient();
    @Autowired
    private MessageSource messageSource;

    @Test
    public void headShouldReturn200ForNewlyAddedUser() throws IOException, InterruptedException {
        addUser(LOGIN, PASSWORD);

        var response = sendHeadRequest();

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    public void headShouldReturn404WhenUserDoesNotExists() throws IOException, InterruptedException {

        var response = sendHeadRequest();

        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    public void postShouldReturn400IfRequestIsIncorrect() throws IOException, InterruptedException {

        var response = addUser(TOO_SHORT_LOGIN, TOO_WEAK_PASSWORD);

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body())
            .contains(message("user.validation.login.length"))
            .contains(message("user.validation.password.length"))
            .contains(message("user.validation.password.characters"));
    }

    @Test
    public void postShouldReturn400IfUserExists() throws IOException, InterruptedException {
        addUser(LOGIN, PASSWORD);

        var response = addUser(LOGIN, PASSWORD);

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body())
            .contains(message("user.validation.login.notUnique"));
    }

    private HttpResponse<String> sendHeadRequest() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
            .method("HEAD", noBody())
            .uri(URI.create(createURLWithPort("/users/" + LOGIN)))
            .build();
        return send(request);
    }

    private HttpResponse<String> addUser(String login, String password) throws IOException, InterruptedException {
        User user = new User(null, login, password);
        var request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(user)))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .uri(URI.create(createURLWithPort("/users")))
            .build();
        return send(request);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String message(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
