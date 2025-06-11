package imdb;

import com.umutavci.imdb.ImdbApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ImdbApplication.class)
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "spring.security.enabled=false")
public class DirectorGraphQLApiTest {

    @Autowired
    private HttpGraphQlTester graphQlTester;

    private Integer testDirectorId;

    @BeforeEach
    public void setUp() {
        String createMutation = """
            mutation {
                createDirector(input: { name: "Test Director", birthDate: "1970-01-01" }) {
                    id
                    name
                    birthDate
                }
            }
        """;
        testDirectorId = graphQlTester.document(createMutation)
                .execute()
                .path("createDirector.id")
                .entity(Integer.class)
                .get();
    }

    @Test
    public void testGetDirector() {
        String query = "query { getDirector(id: " + testDirectorId + ") { id name birthDate } }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();

        response.path("getDirector.id").entity(Integer.class).isEqualTo(testDirectorId);
        response.path("getDirector.name").entity(String.class).isEqualTo("Test Director");
        response.path("getDirector.birthDate").entity(LocalDate.class).isEqualTo(LocalDate.of(1970, 1, 1));
    }

    @Test
    public void testGetAllDirectors() {
        String query = "query { getAllDirectors { id name birthDate } }";
        List<?> directors = graphQlTester.document(query)
                .execute()
                .path("getAllDirectors")
                .entityList(Object.class)
                .get();

        assertTrue(directors.size() > 0, "Directors list should have at least one director");
    }

    @Test
    public void testCreateDirector() {
        String mutation = "mutation { createDirector(input: { name: \"New Director\", birthDate: \"1980-02-02\" }) { id name birthDate } }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("createDirector.id").entity(Integer.class).matches(id -> id > 0);
        response.path("createDirector.name").entity(String.class).isEqualTo("New Director");
        response.path("createDirector.birthDate").entity(LocalDate.class).isEqualTo(LocalDate.of(1980, 2, 2));
    }

    @Test
    public void testUpdateDirector() {
        String updateMutation = "mutation { updateDirector(id: " + testDirectorId + ", input: { name: \"Updated Director\", birthDate: \"1975-03-03\" }) { id name birthDate } }";
        HttpGraphQlTester.Response response = graphQlTester.document(updateMutation).execute();

        response.path("updateDirector.id").entity(Integer.class).isEqualTo(testDirectorId);
        response.path("updateDirector.name").entity(String.class).isEqualTo("Updated Director");
        response.path("updateDirector.birthDate").entity(LocalDate.class).isEqualTo(LocalDate.of(1975, 3, 3));
    }

    @Test
    public void testDeleteDirector() {
        String deleteMutation = "mutation { deleteDirector(id: " + testDirectorId + ") }";
        HttpGraphQlTester.Response response = graphQlTester.document(deleteMutation).execute();

        response.path("deleteDirector").entity(Boolean.class).isEqualTo(true);
    }
}