package imdb;

import com.umutavci.imdb.ImdbApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static graphql.Assert.assertTrue;

@Nested
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ImdbApplication.class)
@AutoConfigureGraphQlTester
@TestPropertySource(properties = "spring.security.enabled=false" )
public class ActorGraphQLApiTest {

    @Autowired
    private GraphQlTester graphQlTester;
    private Integer testActorId;

    @BeforeEach
    public void setUp() {
        String createMutation = """
            mutation {
                createActor(input: { name: "Test Actor", birthDate: "1990-01-01" }) {
                    id
                    name
                    birthDate
                }
            }
        """;
        testActorId = graphQlTester.document(createMutation)
                .execute()
                .path("createActor.id")
                .entity(Integer.class)
                .get();

        String createSecondActor = """
            mutation {
                createActor(input: { name: "Second Actor", birthDate: "1985-05-10" }) {
                    id
                }
            }
        """;
        graphQlTester.document(createSecondActor).execute();
    }

    @Test
    public void testGetActor() {
        String query = "query { getActor(id: " + testActorId + ") { id name birthDate } }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();

        response.path("getActor.id").entity(Integer.class).isEqualTo(testActorId);
        response.path("getActor.name").entity(String.class).isEqualTo("Test Actor");
        response.path("getActor.birthDate").entity(LocalDate.class).isEqualTo(LocalDate.of(1990, 1, 1));
    }


    @Test
    public void testGetAllActors() {
        String query = "query { getAllActors { id name birthDate } }";
        List<?> actors = graphQlTester.document(query)
                .execute()
                .path("getAllActors")
                .entityList(Object.class)
                .get(); // Listeyi alıyoruz

        assertTrue(actors.size() >= 0, "Actors list should have size greater than or equal to 0");
    }

    @Test
    public void testCreateActor() {
        String mutation = "mutation { createActor(input: { name: \"Test Actor\", birthDate: \"1990-01-01\" }) { id name birthDate } }";
        graphQlTester.document(mutation)
                .execute()
                .path("createActor.id").entity(Integer.class).matches(id -> id > 0) // Yeni ID pozitif olmalı
                .path("createActor.name").entity(String.class).isEqualTo("Test Actor")
                .path("createActor.birthDate").entity(String.class).isEqualTo("1990-01-01");
    }

    @Test
    public void testUpdateActor() {
        String createMutation = "mutation { createActor(input: { name: \"Old Actor\", birthDate: \"1980-05-10\" }) { id } }";
        Integer actorId = graphQlTester.document(createMutation)
                .execute()
                .path("createActor.id").entity(Integer.class).get();

        String updateMutation = "mutation { updateActor(id: " + actorId + ", input: { name: \"Updated Actor\", birthDate: \"1985-12-25\" }) { id name birthDate } }";
        graphQlTester.document(updateMutation)
                .execute()
                .path("updateActor.id").entity(Integer.class).isEqualTo(actorId)
                .path("updateActor.name").entity(String.class).isEqualTo("Updated Actor")
                .path("updateActor.birthDate").entity(String.class).isEqualTo("1985-12-25");
    }

    @Test
    public void testDeleteActor() {
        String createMutation = "mutation { createActor(input: { name: \"To Delete\", birthDate: \"1995-03-15\" }) { id } }";
        Integer actorId = graphQlTester.document(createMutation)
                .execute()
                .path("createActor.id").entity(Integer.class).get();

        String deleteMutation = "mutation { deleteActor(id: " + actorId + ") }";
        graphQlTester.document(deleteMutation)
                .execute()
                .path("deleteActor").entity(Boolean.class).isEqualTo(true);
    }
}