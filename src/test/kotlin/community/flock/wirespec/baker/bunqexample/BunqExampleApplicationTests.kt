package community.flock.wirespec.baker.bunqexample

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import com.bunq.sdk.generated.Sdk
import com.bunq.sdk.generated.endpoint.READ_User
import kotlinx.coroutines.runBlocking

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BunqExampleApplicationTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `test main endpoint returns user nickname`(): Unit = runBlocking {

        // When & Then
        webTestClient.get()
            .uri("/main")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("Donald")
    }

}
