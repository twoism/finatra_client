package com.twitter.http_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import com.twitter.util.Future

object App {
  def main(args: Array[String]) = {
    val client: Service[Request, Response] = ClientBuilder()
        .codec(new RichHttp[Request](Http()))
        .hosts("localhost:4567")
        .hostConnectionLimit(1)
        .build()


    val request           = Request(HttpVersion.HTTP_1_1, HttpMethod.GET, "/foo")                   // 2
    val responseFuture: Future[Response]  = client(request).onSuccess {
        response => println("Received response: " + response)
    }

    val body = responseFuture.get().contentString
    println("**************************************")
    println(body)
    println("**************************************")
  }
}
