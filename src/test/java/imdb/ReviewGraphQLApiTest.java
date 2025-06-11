package imdb;

import com.umutavci.imdb.ImdbApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ImdbApplication.class)
@AutoConfigureHttpGraphQlTester
@Import(TestSecurityConfig.class)
@TestPropertySource(properties = "spring.security.enabled=false")
public class ReviewGraphQLApiTest {

    @Autowired
    private HttpGraphQlTester graphQlTester;

    private Integer testReviewId;
    private Integer testMovieId;
    private Integer testUserId;

    @BeforeEach
    public void setUp() {
        String createUser = "mutation { createUser(input: { username: \"testuser\", email: \"test@example.com\", pass: \"pass\" }) { id } }";
        testUserId = graphQlTester.document(createUser).execute().path("createUser.id").entity(Integer.class).get();

        String createMovie = "mutation { createMovie(input: { title: \"Test Movie\", releaseDate: \"2020-01-01\", genre: \"Action\", description: \"A test movie\" }) { id } }";
        testMovieId = graphQlTester.document(createMovie).execute().path("createMovie.id").entity(Integer.class).get();

        String createReview = """
            mutation {
                createReview(input: { rating: 5, comment: "Great movie", movieId: %d, userId: %d }) {
                    id
                    rating
                }
            }
        """.formatted(testMovieId, testUserId);
        testReviewId = graphQlTester.document(createReview).execute().path("createReview.id").entity(Integer.class).get();
    }

    @Test
    public void testGetReview() {
        String query = "query { getReview(id: " + testReviewId + ") { id rating comment movieId userId } }";
        HttpGraphQlTester.Response response = graphQlTester.document(query).execute();

        response.path("getReview.id").entity(Integer.class).isEqualTo(testReviewId);
        response.path("getReview.rating").entity(Integer.class).isEqualTo(5);
        response.path("getReview.comment").entity(String.class).isEqualTo("Great movie");
        response.path("getReview.movieId").entity(Integer.class).isEqualTo(testMovieId);
        response.path("getReview.userId").entity(Integer.class).isEqualTo(testUserId);
    }

    @Test
    public void testGetAllReviews() {
        String query = "query { getAllReviews { id rating } }";
        List<?> reviews = graphQlTester.document(query).execute().path("getAllReviews").entityList(Object.class).get();
        assertTrue(reviews.size() > 0, "Reviews list should have at least one review");
    }

    @Test


    public void testCreateReview() {
        String mutation = """
            mutation {
                createReview(input: { rating: 4, comment: "Good movie", movieId: %d, userId: %d }) {
                    id
                    rating
                }
            }
        """.formatted(testMovieId, testUserId);
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("createReview.id").entity(Integer.class).matches(id -> id > 0);
        response.path("createReview.rating").entity(Integer.class).isEqualTo(4);
    }

    @Test
    public void testUpdateReview() {
        String mutation = """
            mutation {
                updateReview(id: %d, input: { rating: 3, comment: "Okay movie", movieId: %d, userId: %d }) {
                    id
                    rating
                }
            }
        """.formatted(testReviewId, testMovieId, testUserId);
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();

        response.path("updateReview.id").entity(Integer.class).isEqualTo(testReviewId);
        response.path("updateReview.rating").entity(Integer.class).isEqualTo(3);
    }

    @Test
    public void testDeleteReview() {
        String mutation = "mutation { deleteReview(id: " + testReviewId + ") }";
        HttpGraphQlTester.Response response = graphQlTester.document(mutation).execute();
        response.path("deleteReview").entity(Boolean.class).isEqualTo(true);
    }
}
