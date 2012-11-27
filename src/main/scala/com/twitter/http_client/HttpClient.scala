package com.twitter.http_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import com.twitter.finagle.Service
import org.jboss.netty.handler.codec.http._

abstract class HttpClient {
  val baseURI:          String
  val httpVersion:      HttpVersion = HttpVersion.HTTP_1_1
  val connectionLimit:  Int         = 1

  lazy val client: Service[Request, Response] = ClientBuilder()
      .codec(new RichHttp[Request](Http()))
      .hosts(baseURI)
      .hostConnectionLimit(connectionLimit)
      .build()

  def get(path: String) = new FinatraHttpRequest(client).get(path)

}