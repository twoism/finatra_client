package com.twitter.finatra_client

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.http.{HeaderMap, ParamMap, Response, Request}
import com.twitter.finagle.Service
import com.twitter.util.Future

class FinatraHttpRequest(val client: Service[Request, Response]) {
  var _headers:     Seq[Tuple2[String, String]]    = Seq.empty
  var _params:      Seq[Tuple2[String, String]]    = Seq.empty
  var _path:        String                         = "/"
  var _method:      HttpMethod                     = HttpMethod.GET
  var httpVersion:  HttpVersion                    = HttpVersion.HTTP_1_1

  def method(theMethod: HttpMethod) = {
    this._method = theMethod
    this
  }

  def params(theParams: Tuple2[String,String]*) = {
    this._params = theParams
    this
  }

  def path(thePath: String) = {
    this._path = thePath
    this
  }

  def headers(theHeaders: Tuple2[String, String]*) = {
    this._headers = theHeaders
    this
  }

  def fetch[T](callback: Response => T):Future[T] = {
    client(build) map { r =>
      callback(r)
    }
  }

  def build = {
    val request     = Request(_path, _params:_*)
    request.method  = this._method

    if (request.method != HttpMethod.GET || request.method != HttpMethod.HEAD) {
      val encoder = new QueryStringEncoder("")

      this._params.foreach { case (key, value) =>
        encoder.addParam(key, value)
      }

      request.setContentString(encoder.toString)
    }

    this._headers.foreach(h => request.httpMessage.addHeader(h._1, h._2))

    request
  }
}
