package com.twitter.http_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import com.twitter.util.Future

object App {
  def main(args: Array[String]) = {
    val client: Service[HttpRequest, HttpResponse] = ClientBuilder()
        .codec(Http())
        .hosts("localhost:4567")
        .hostConnectionLimit(1)
        .build()

    val request: DefaultHttpRequest           = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/foo")                      // 2
    val responseFuture: Future[HttpResponse]  = client(request).onSuccess {
        response => println("Received response: " + response)
    }

    val body = new String(responseFuture.get().getContent.array)
    println("**************************************")
    println(body)
    println("**************************************")
  }
}
