package community.flock.wirespec.baker.bunqexample

import com.bunq.sdk.BUNQ_SANDBOX_SERVER
import com.bunq.sdk.BunqApiException
import com.bunq.sdk.BunqSigningKeysGenerator
import com.bunq.sdk.Config
import com.bunq.sdk.bunqClient
import com.bunq.sdk.generated.endpoint.ReadUser
import com.bunq.sdk.initContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(@Value("\${bunq.api-key}") apiKey: String) {

    private val context = initContext(
        Config(
            bunqServer = BUNQ_SANDBOX_SERVER,
            serviceName = "PeterScript",
            apiKey = apiKey,
            signingKeys = BunqSigningKeysGenerator.generateBunqSigningKeys(),
        )
    )
    private val client = bunqClient(context)

    @GetMapping("/main")
    suspend fun main(): String? = try {
        when (val res = client.readUser(context.userId)) {
            is ReadUser.Response200 -> res.body.UserPerson?.public_nick_name
            is ReadUser.Response400 -> error("bunq answered 400: ${res.body.Error?.firstOrNull()?.error_description}")
        }
    } catch (e: BunqApiException) {
        throw IllegalStateException(e.message, e)
    }
}
