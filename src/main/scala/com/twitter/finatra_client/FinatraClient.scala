package com.twitter.finatra_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import com.twitter.finagle.Service
import org.jboss.netty.handler.codec.http._

abstract class FinatraClient {
  val baseURI:          String
  val httpVersion:      HttpVersion = HttpVersion.HTTP_1_1
  val connectionLimit:  Int         = 1

  lazy val client: Service[Request, Response] = ClientBuilder()
      .codec(new RichHttp[Request](Http()))
      .hosts(baseURI)
      .hostConnectionLimit(connectionLimit)
      .build()

  def get(path: String)     = new FinatraHttpRequest(client).method(HttpMethod.GET).path(path)
  def post(path: String)    = new FinatraHttpRequest(client).method(HttpMethod.POST).path(path)
  def put(path: String)     = new FinatraHttpRequest(client).method(HttpMethod.PUT).path(path)
  def delete(path: String)  = new FinatraHttpRequest(client).method(HttpMethod.DELETE).path(path)
  def head(path: String)    = new FinatraHttpRequest(client).method(HttpMethod.HEAD).path(path)
  def patch(path: String)   = new FinatraHttpRequest(client).method(HttpMethod.PATCH).path(path)

}
