package imdb;

import com.umutavci.imdb.ImdbApplication;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ImdbApplication.class)
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "spring.security.enabled=false")
public class UserGraphQLApiTest {


    @Autowired
    private HttpGraphQlTester graphQlTester;

    private Integer testUserId;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserJpaRepository userRepository;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        String createMutation = """
            mutation {
                createUser(input: { username: "testuser", email: "test@example.com", pass: "pass123" }) {
                    id
                    username
                    email
                }
            }
        """;
        testUserId = graphQlTester.document(createMutation)
                .execute()
                .path("createUser.id")
                .entity(Integer.class)
                .get();
    }

    @Test
    public void testGetUser() {
        String query = "query { getUser(id: " + testUserId + ") { id username email } }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();

        response.path("getUser.id").entity(Integer.class).isEqualTo(testUserId);
        response.path("getUser.username").entity(String.class).isEqualTo("testuser");
        response.path("getUser.email").entity(String.class).isEqualTo("test@example.com");
    }

    @Test
    public void testGetAllUsers() {
        String query = "query { getAllUsers { id username } }";
        List<?> users = graphQlTester.document(query).execute().path("getAllUsers").entityList(Object.class).get();
        assertTrue(users.size() > 0, "Users list should have at least one user");
    }

    @Test
    public void testMe() {
        String query = "query { me }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();
        response.path("me").entity(String.class).isNotEqualTo(null); // Mevcut bir string dönerse yeterli
    }

    @Test
    public void testCreateUser() {
        String mutation = "mutation { createUser(input: { username: \"newuser\", email: \"new@example.com\", pass: \"pass456\" }) { id username email } }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("createUser.id").entity(Integer.class).matches(id -> id > 0);
        response.path("createUser.username").entity(String.class).isEqualTo("newuser");
        response.path("createUser.email").entity(String.class).isEqualTo("new@example.com");
    }

    @Test
    public void testUpdateUser() {
        String mutation = "mutation { updateUser(id: " + testUserId + ", input: { username: \"updateduser\", email: \"updated@example.com\", pass: \"newpass\" }) { id username email } }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("updateUser.id").entity(Integer.class).isEqualTo(testUserId);
        response.path("updateUser.username").entity(String.class).isEqualTo("updateduser");
        response.path("updateUser.email").entity(String.class).isEqualTo("updated@example.com");
    }

    @Test
    public void testDeleteUser() {
        String mutation = "mutation { deleteUser(id: " + testUserId + ") }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();
        response.path("deleteUser").entity(Boolean.class).isEqualTo(true);
    }

    @Test
    public void testLogin() {
        String mutation = """
            mutation {
                login(input: {
                    email: "test@example.com",
                    pass: "pass123"
                })
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("login")
                .entity(Boolean.class)
                .isEqualTo(true);
    }
}
