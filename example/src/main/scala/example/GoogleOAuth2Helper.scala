package example

import io.circe
import io.circe.generic.extras.Configuration
import sttp.client3.{Identity, ResponseException, SttpBackend, UriContext, basicRequest}
import sttp.model.Uri
import io.circe.generic.extras.auto._
import sttp.client3.circe.asJson

/**
 * Using OAuth 2.0 for Web Server Applications  |  Authorization  |  Google Developers : https://developers.google.com/identity/protocols/oauth2/web-server
 */
object GoogleOAuth2Helper {

  private implicit val jsonConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  def makeAuthorizationUrl(clientId: String, redirectUri: String, scopes: List[String], state: String): Uri = {
    val baseUri = uri"https://accounts.google.com/o/oauth2/v2/auth"
    baseUri.withParams(
      "client_id" -> clientId,
      "redirect_uri" -> redirectUri,
      "response_type" -> "code",
      "scope" -> scopes.mkString(" "),
      "access_type" -> "online",
      "state" -> state
    )
  }

  def accessTokenRequest(clientId: String, clientSecret: String, redirectUri: String, code: String)(
      client: SttpBackend[Identity, Any]
  ): Either[ResponseException[String, circe.Error], AccessTokenResponse] = {
    val uri = uri"https://oauth2.googleapis.com/token"
    val body = Map(
      "client_id" -> clientId,
      "client_secret" -> clientSecret,
      "redirect_uri" -> redirectUri,
      "grant_type" -> "authorization_code",
      "code" -> code
    )
    val request = basicRequest.body(body).post(uri).response(asJson[AccessTokenResponse])

    request.send(client).body
  }

  case class AccessTokenResponse(accessToken: String, expiresIn: Int, scope: String, tokenType: String)

}
