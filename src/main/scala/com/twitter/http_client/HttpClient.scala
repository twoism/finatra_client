package com.twitter.http_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import com.twitter.util.Future

abstract class HttpClient {
  val baseURI: String;

  lazy val client: Service[Request, Response] = ClientBuilder()
      .codec(new RichHttp[Request](Http()))
      .hosts(baseURI)
      .hostConnectionLimit(1)
      .build()

  def request(method: HttpMethod, path: String) = {
    client(Request(HttpVersion.HTTP_1_1, method, path))
  }

  def get(path: String) = {
    request(HttpMethod.GET, path)
  }

  def post(path: String) = {
    request(HttpMethod.POST, path)
  }

  def put(path: String) = {
    request(HttpMethod.PUT, path)
  }

  def delete(path: String) = {
    request(HttpMethod.DELETE, path)
  }
}