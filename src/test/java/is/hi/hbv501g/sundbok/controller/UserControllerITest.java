package is.hi.hbv501g.sundbok.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerITest {
    @Autowired
    private TestRestTemplate template;


    @Test
    public void getUser() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/user", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from User Controller!");
    }

    @Test
    public void registerUser() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/user/register", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from User Controller!");
    }
}
