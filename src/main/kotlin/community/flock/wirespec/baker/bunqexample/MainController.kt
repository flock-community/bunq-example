package community.flock.wirespec.baker.bunqexample

import com.bunq.sdk.Config
import com.bunq.sdk.Signing
import com.bunq.sdk.generated.Sdk
import com.bunq.sdk.generated.endpoint.READ_User
import com.bunq.sdk.handler
import com.bunq.sdk.initContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class MainController {

    private val config = Config(
        apiKey = "sandbox_83f4f88a10706750ec2fdcbc1ce97b582a986f2846d33dcaaa974d95",
        serviceName = "PeterScript",
        publicKeyFile = File("../../public_key.pem"),
        privateKeyFile = File("../../private_key.pem"),
    )

    val signing = Signing(config)
    val context = initContext(config)
    val sdk = Sdk(handler(signing, context))


    @GetMapping("/main")
    suspend fun main(): String? {
        val req = READ_User.Request(
            itemId = context.userId,
            CacheControl = null,
            UserAgent = config.serviceName,
            XBunqLanguage = null,
            XBunqRegion = null,
            XBunqClientRequestId = null,
            XBunqGeolocation = null,
            XBunqClientAuthentication = config.apiKey
        )
        val res = sdk.rEAD_User(req)
        when (res) {
            is READ_User.Response200 -> return res.body.UserPerson?.public_nick_name
            else -> error("Unexpected response body: ${res.status}")
        }

    }
}
