package example

import io.circe.generic.extras.Configuration
import io.github.stoneream.token_receiver.TemporaryHttpServer
import sttp.client3.HttpClientSyncBackend

import java.security.SecureRandom
import java.util.concurrent.Executors
import scala.util.Random

object Main extends App {
  implicit val jsonConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  val random = new Random(new SecureRandom())
  val clientId = "client-id-here"
  val clientSecret = "client-secret-here"
  val redirectUri = "http://localhost:8080/callback"
  val scopes = List("openid")
  val state = random.alphanumeric.take(32).mkString("")

  val executorService = Executors.newSingleThreadExecutor()

  val authorizationUrl = GoogleOAuth2Helper.makeAuthorizationUrl(clientId, redirectUri, scopes, state)

  println(s"authorization url => $authorizationUrl")

  new TemporaryHttpServer({ params =>
    val client = HttpClientSyncBackend()

    for {
      callbackState <- params.get("state")
      code <- params.get("code")
      if callbackState == state
    } yield {
      GoogleOAuth2Helper.accessTokenRequest(clientId, clientSecret, redirectUri, code)(client).fold(
        _ => ???,
        accessToken => {
          println(s"access token here => $accessToken")
        }
      )
    }
  })(executorService).start()
}
