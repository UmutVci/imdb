package imdb;

import com.umutavci.imdb.infrastructure.persistence.entities.Director;
import com.umutavci.imdb.infrastructure.persistence.repositories.DirectorJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
@WithMockUser(username = "rev@example.com", roles = "USER")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestApplication.class   // <–– Burada TestApplication kullanıyoruz
)
@AutoConfigureHttpGraphQlTester
public class ReviewGraphQLApiTest {

    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Autowired
    private DirectorJpaRepository directorRepo;     // ← Director repo

    private Integer testReviewerId;
    private Integer testDirectorId;
    private Integer testMovieId;
    private Integer testReviewId;

    @BeforeEach
    void setUp() {
        // 1) Review yazacak user
        testReviewerId = graphQlTester.document("""
            mutation {
              createUser(input: {
                username: "reviewer",
                email:    "rev@example.com",
                pass:     "pass123"
              }) { id }
            }
        """)
                .execute()
                .path("createUser.id").entity(Integer.class).get();

        // 2) Director entity’yi doğrudan repoya kaydet
        Director director = new Director();
        director.setName("Test Director");
        director.setBirthDate(LocalDate.of(1970, 7, 30));
        Director saved = directorRepo.save(director);
        testDirectorId = saved.getId().intValue();

        // 3) Film oluştururken gerçek directorId kullan
        testMovieId = graphQlTester.document(String.format("""
            mutation {
              createMovie(input: {
                title:       "Test Movie",
                releaseDate: "2020-01-01",
                genre:       "Drama",
                description: "A wonderful test movie",
                directorId:  %d
              }) { id }
            }
        """, testDirectorId))
                .execute()
                .path("createMovie.id").entity(Integer.class).get();

        // 4) Review oluştur
        testReviewId = graphQlTester.document(String.format("""
            mutation {
              createReview(input: {
                rating:  5,
                comment: "Excellent!",
                movieId: %d,
                userId:  %d
              }) { id }
            }
        """, testMovieId, testReviewerId))
                .execute()
                .path("createReview.id").entity(Integer.class).get();
    }

    @Test
    void testGetReview() {
        graphQlTester.document(String.format("""
            query {
              getReview(id: %d) {
                id rating comment movieId userId
              }
            }
        """, testReviewId))
                .execute()
                .path("getReview.id").entity(Integer.class).isEqualTo(testReviewId)
                .path("getReview.rating").entity(Integer.class).isEqualTo(5)
                .path("getReview.comment").entity(String.class).isEqualTo("Excellent!")
                .path("getReview.movieId").entity(Integer.class).isEqualTo(testMovieId)
                .path("getReview.userId").entity(Integer.class).isEqualTo(testReviewerId);
    }

    @Test
    void testGetAllReviews() {
        graphQlTester.document("""
            query { getAllReviews { id rating } }
        """)
                .execute()
                .path("getAllReviews[*].id")
                .entityList(Integer.class)
                .satisfies(list -> assertThat(list).contains(testReviewId));
    }

    @Test
    void testUpdateReview() {
        graphQlTester.document(String.format("""
            mutation {
              updateReview(id: %d, input: {
                rating:  3,
                comment: "It was okay",
                movieId: %d,
                userId:  %d
              }) { id rating comment }
            }
        """, testReviewId, testMovieId, testReviewerId))
                .execute()
                .path("updateReview.rating").entity(Integer.class).isEqualTo(3)
                .path("updateReview.comment").entity(String.class).isEqualTo("It was okay");
    }

    @Test
    void testDeleteReview() {
        graphQlTester.document(String.format("""
            mutation { deleteReview(id: %d) }
        """, testReviewId))
                .execute()
                .path("deleteReview").entity(Boolean.class).isEqualTo(true);
    }
}