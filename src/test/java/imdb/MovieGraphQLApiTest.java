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
import java.util.Map;

import static graphql.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ImdbApplication.class)
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "spring.security.enabled=false")
public class MovieGraphQLApiTest {

    @Autowired
    private HttpGraphQlTester graphQlTester;

    private Integer testMovieId;
    private Integer testDirectorId;
    private Integer testUserId;

    @BeforeEach
    public void setUp() {
        String createDirector = """
            mutation {
                createDirector(input: { name: "Test Director", birthDate: "1970-01-01" }) {
                    id
                }
            }
        """;
        testDirectorId = graphQlTester.document(createDirector).execute().path("createDirector.id").entity(Integer.class).get();

        String createMovie = """
            mutation {
                createMovie(input: { title: "Test Movie", releaseDate: "2020-01-01", genre: "Action", description: "A test movie", directorId: %d }) {
                    id
                    title
                }
            }
        """.formatted(testDirectorId);
        testMovieId = graphQlTester.document(createMovie).execute().path("createMovie.id").entity(Integer.class).get();
        String createUser = """
            mutation {
                createUser(input: { username: "testuser", email: "test@example.com", pass: "pass123" }) {
                    id
                }
            }
        """;
        testUserId = graphQlTester.document(createUser).execute().path("createUser.id").entity(Integer.class).get();

        String createReview1 = """
            mutation {
                createReview(input: { rating: 4, comment: "Good movie", movieId: %d, userId: %d }) {
                    id
                }
            }
        """.formatted(testMovieId, testUserId);
        graphQlTester.document(createReview1).execute();

        String createReview2 = """
            mutation {
                createReview(input: { rating: 6, comment: "Great movie", movieId: %d, userId: %d }) {
                    id
                }
            }
        """.formatted(testMovieId, testUserId);
        graphQlTester.document(createReview2).execute();
    }

    @Test
    public void testGetMovie() {
        String query = "query { getMovie(id: " + testMovieId + ") { id title releaseDate genre description directorId } }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();

        response.path("getMovie.id").entity(Integer.class).isEqualTo(testMovieId);
        response.path("getMovie.title").entity(String.class).isEqualTo("Test Movie");
        response.path("getMovie.releaseDate").entity(LocalDate.class).isEqualTo(LocalDate.of(2020, 1, 1));
        response.path("getMovie.genre").entity(String.class).isEqualTo("Action");
        response.path("getMovie.description").entity(String.class).isEqualTo("A test movie");
        response.path("getMovie.directorId").entity(Integer.class).isEqualTo(testDirectorId);
    }

    @Test
    public void testGetAllMovies() {
        String query = "query { getAllMovies { id title } }";
        List<?> movies = graphQlTester.document(query).execute().path("getAllMovies").entityList(Object.class).get();
        assertTrue(movies.size() > 0, "Movies list should have at least one movie");
    }

    @Test
    public void testSearchMovieByName() {
        String query = "query { searchMovieByName(name: \"Test\") { id title } }";
        List<?> movies = graphQlTester.document(query).execute().path("searchMovieByName").entityList(Object.class).get();
        assertTrue(movies.size() > 0, "Search should return at least one movie");
    }

    @Test
    public void testSortMovieByDescName() {
        String query = "query { sortMovieByDescName { id title } }";
        List<?> movies = graphQlTester.document(query).execute().path("sortMovieByDescName").entityList(Object.class).get();
        assertTrue(movies.size() > 0, "Sorted movies list should have at least one movie");
    }

    @Test
    public void testSortMovieByBetterReviewPoint() {
        String query = "query { sortMovieByBetterReviewPoint { id title } }";
        List<?> movies = graphQlTester.document(query).execute().path("sortMovieByBetterReviewPoint").entityList(Object.class).get();
        assertTrue(movies.size() > 0, "Sorted movies list should have at least one movie");
    }

    @Test
    public void testShowAllActorsInMovie() {
        String query = "query { showAllActorsInMovie(movieId: " + testMovieId + ") { id name } }";
        List<?> actors = graphQlTester.document(query).execute().path("showAllActorsInMovie").entityList(Object.class).get();
        assertTrue(actors.size() >= 0, "Actors list should be non-negative");
    }

    @Test
    public void testFindAverageRankingInMovie() {
        String query = "query { findAverageRankingInMovie(movieId: " + testMovieId + ") }";
        Integer avg = graphQlTester.document(query)
                .execute()
                .path("findAverageRankingInMovie")
                .entity(Integer.class)
                .get();

        assertEquals(5, avg, "Average ranking should be 5 based on added reviews");
    }

    @Test
    public void testCreateMovie() {
        String mutation = """
            mutation {
                createMovie(input: { title: "New Movie", releaseDate: "2021-02-02", genre: "Drama", description: "A new movie", directorId: %d }) {
                    id
                    title
                }
            }
        """.formatted(testDirectorId);
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("createMovie.id").entity(Integer.class).matches(id -> id > 0);
        response.path("createMovie.title").entity(String.class).isEqualTo("New Movie");
    }

    @Test
    public void testUpdateMovie() {
        String mutation = """
            mutation {
                updateMovie(id: %d, input: { title: "Updated Movie", releaseDate: "2022-03-03", genre: "Comedy", description: "Updated desc", directorId: %d }) {
                    id
                    title
                }
            }
        """.formatted(testMovieId, testDirectorId);
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("updateMovie.id").entity(Integer.class).isEqualTo(testMovieId);
        response.path("updateMovie.title").entity(String.class).isEqualTo("Updated Movie");
    }

    @Test
    public void testDeleteMovie() {
        String mutation = "mutation { deleteMovie(id: " + testMovieId + ") }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();
        response.path("deleteMovie").entity(Boolean.class).isEqualTo(true);
    }

    @Test
    public void testAddActorInMovie() {
        String createActor = "mutation { createActor(input: { name: \"Test Actor\", birthDate: \"1990-01-01\" }) { id } }";
        Integer actorId = graphQlTester.document(createActor).execute().path("createActor.id").entity(Integer.class).get();

        String mutation = "mutation { addActorInMovie(movieId: " + testMovieId + ", actorId: " + actorId + ") { id name } }";
        List<?> actors = graphQlTester.document(mutation).execute().path("addActorInMovie").entityList(Object.class).get();
        assertTrue(actors.size() > 0, "Actors list should have at least one actor after adding");
    }

    @Test
    public void testRemoveActorInMovie() {
        String createActor = "mutation { createActor(input: { name: \"Test Actor\", birthDate: \"1990-01-01\" }) { id } }";
        Integer actorId = graphQlTester.document(createActor).execute().path("createActor.id").entity(Integer.class).get();

        String addActor = "mutation { addActorInMovie(movieId: " + testMovieId + ", actorId: " + actorId + ") { id } }";
        graphQlTester.document(addActor).execute();

        String mutation = "mutation { removeActorInMovie(movieId: " + testMovieId + ", actorId: " + actorId + ") { id name } }";
        List<?> actors = graphQlTester.document(mutation)
                .execute()
                .path("removeActorInMovie")
                .entityList(Object.class)
                .get();

        assertFalse(actors.stream().anyMatch(actor -> {
            Map<?, ?> actorMap = (Map<?, ?>) actor;
            return actorMap.get("id").equals(actorId);
        }), "Actor with ID " + actorId + " should be removed from the movie");
    }

}
