package app

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import com.typesafe.scalalogging.Logger

import java.net.InetSocketAddress
import java.util.concurrent.{ExecutorService, Executors}

object Main extends App {
  private val logger = Logger(getClass.getName)

  private def launchTemporaryHttpServer(onCallback: Map[String, String] => Unit)(executorService: ExecutorService): Unit = {
    val httpServer = HttpServer.create(new InetSocketAddress(8080), 0)

    val handler = new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        try {
          logger.debug(s"${exchange.getRequestMethod} - ${exchange.getRequestURI}")

          // getRawQueryがnullのことがある
          val queryParameters = Option(exchange.getRequestURI.getRawQuery).fold {
            Map.empty[String, String]
          } {
            _.split("&").collect { case s"$key=$value" =>
              key -> value
            }.toMap
          }

          onCallback(queryParameters)
        } catch {
          case e: Throwable => e.printStackTrace()
        } finally {
          exchange.sendResponseHeaders(200, 0)
          exchange.getResponseBody.close()

          logger.debug("shutdown start")

          httpServer.stop(0)
          executorService.shutdown()

          logger.debug("shutdown end")
        }
      }
    }
    httpServer.createContext("/callback", handler)
    httpServer.setExecutor(executorService)
    httpServer.start()

    logger.info(s"listening for http on ${httpServer.getAddress}")
  }

  launchTemporaryHttpServer(params => {
    println(s"params => $params")
  })(Executors.newSingleThreadExecutor())
}
