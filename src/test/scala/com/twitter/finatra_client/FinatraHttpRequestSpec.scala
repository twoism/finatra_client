package com.twitter.finatra_client

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Http, RichHttp, Response, Request}
import com.twitter.finagle.builder.ClientBuilder
import org.jboss.netty.handler.codec.http.HttpMethod

class FinatraHttpRequestSpec extends FlatSpec with ShouldMatchers {
  val client: Service[Request, Response] = ClientBuilder()
          .codec(new RichHttp[Request](Http()))
          .hosts("localhost:7070")
          .hostConnectionLimit(1)
          .build()

  val request = new FinatraHttpRequest(client)

  ".method()" should "set the http method" in {
    val r = request.method(HttpMethod.PUT).build

    r.method should equal(HttpMethod.PUT)
  }

  ".params()" should "set the GET params" in {
    val r = request.params("screen_name" -> "twoism").build

    r.uri should equal("/?screen_name=twoism")
  }

  ".params()" should "set the POST body params" in {
    val r = request.method(HttpMethod.POST).params("screen_name" -> "twoism").build

    r.getContentString() should equal("?screen_name=twoism")
  }

  ".headers()" should "set HTTP Headers" in {
    val r = request.headers("X-RateLimit" -> "100").build

    r.getHeader("X-RateLimit") should equal("100")
  }
}
