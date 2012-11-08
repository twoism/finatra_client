package com.twitter.http_client

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import com.twitter.util.Future
import com.codahale.jerkson.Json._

case class Tweet(status: String, screen_name: String)

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

  def parseCollection(response: Future[Response]) = {
    response map { resp =>
      parse[List[Tweet]](resp.contentString)
    }
  }

  def get(path: String) = {
    parseCollection(request(HttpMethod.GET, path))
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

class LocalClient extends HttpClient {
  val baseURI = "localhost:4567"

  def tweets = { get("/tweets") }
}

object App {
  def main(args: Array[String]) {
    val client = new LocalClient

    val response = client.tweets

    val body = response.get()
    println("**************************************")
    println(body)
    println("**************************************")
  }
}
