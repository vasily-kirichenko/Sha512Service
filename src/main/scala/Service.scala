import java.nio.charset.Charset
import java.security.MessageDigest

import akka.actor.ActorSystem
import akka.http.javadsl.model.MediaType.Multipart
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Multipart, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

case class SlackRequest
  (token: String,
   teamId: String,
   teamDomain: String,
   channelId: String,
   channelName: String,
   userId: String,
   userName: String,
   command: String,
   text: String,
   responseUrl: String)

object Service {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("web-server-system")
    implicit val materializer = ActorMaterializer()

    val route =
      post {
          path("sha512") {
            formFields(('token, 'team_id, 'team_domain, 'channel_id, 'channel_name, 'user_id,
              'user_name, 'command, 'text, 'response_url)).as(SlackRequest) { req: SlackRequest =>
              complete {
                MessageDigest.getInstance("SHA-512").digest(req.text.getBytes("UTF-8"))
              }
            }
          }
        }

    val port = 27000
    Http().bindAndHandle(route, "0.0.0.0", port)
    println(s"Server is online at http://localhost:$port")
  }
}
