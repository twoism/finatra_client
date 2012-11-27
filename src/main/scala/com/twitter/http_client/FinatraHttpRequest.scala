package com.twitter.http_client

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.http.{Response, Request}
import com.twitter.finagle.Service
import com.twitter.util.Future

class FinatraHttpRequest(val client: Service[Request, Response]) {
  var headers:  Map[String, String]   = Map.empty
  var params:   Map[String, String]   = Map.empty
  var path:     String                = "/"
  var method:   HttpMethod            = HttpMethod.GET
  var httpVersion: HttpVersion        = HttpVersion.HTTP_1_1

  def get(path: String): FinatraHttpRequest = {
    this.path   = path
    this.method = HttpMethod.GET
    this
  }

  def fetch[T](callback: Response => T):Future[T] = {
    client(build) map { r =>
      callback(r)
    }
  }

  def build = {
    val f = Request(httpVersion, method, path)
    f
  }
}
