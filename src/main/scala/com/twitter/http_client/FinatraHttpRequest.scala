package com.twitter.http_client

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.http.{ParamMap, Response, Request}
import com.twitter.finagle.Service
import com.twitter.util.Future

class FinatraHttpRequest(val client: Service[Request, Response]) {
  //var _headers:  Tuple2[String, String]   = Map.empty
  var _params:   Seq[Tuple2[String, String]]   = List.empty
  var path:     String                = "/"
  var method:   HttpMethod            = HttpMethod.GET
  var httpVersion: HttpVersion        = HttpVersion.HTTP_1_1

  def get(path: String): FinatraHttpRequest = {
    this.path   = path
    this.method = HttpMethod.GET
    this
  }

  def params(theParams: Tuple2[String,String]*) = {
    this._params = theParams
    this
  }

//  def headers(theHeaders: Map[String, String]) = {
//    this._headers = theHeaders
//    this
//  }

  def fetch[T](callback: Response => T):Future[T] = {
    client(build) map { r =>
      callback(r)
    }
  }

  def build = {
    val request = Request(path, _params:_*)
    request
  }
}
