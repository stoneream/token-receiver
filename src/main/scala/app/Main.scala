package app

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}

import java.net.InetSocketAddress
import java.util.concurrent.{ExecutorService, Executors}

object Main extends App {
  private def temporaryHttpServer(onCallback: Map[String, String] => Unit)(executorService: ExecutorService): Unit = {
    val httpServer = HttpServer.create(new InetSocketAddress(8080), 0)

    val handler = new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        try {
          // getRawQueryがヌルポ吐くときがある
          val queryParameters = exchange.getRequestURI.getRawQuery
            .split("&")
            .collect { case s"$key=$value" =>
              key -> value
            }
            .toMap

          onCallback(queryParameters)
        } catch {
          case e: Throwable => e.printStackTrace()
        } finally {
          exchange.sendResponseHeaders(200, 0)
          exchange.getResponseBody.close()

          httpServer.stop(0)
          executorService.shutdown()
        }
      }
    }
    httpServer.createContext("/callback", handler)
    httpServer.setExecutor(executorService)
    httpServer.start()
  }

  temporaryHttpServer(params => {
    println(s"params => $params")
  })(Executors.newSingleThreadExecutor())
}
