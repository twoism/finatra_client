package com.twitter.http_client

import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future
import com.codahale.jerkson.Json
import javax.management.remote.rmi._RMIConnection_Stub

case class Tweet(status: String, screen_name: String)

class TweetClient extends HttpClient {
  val baseURI = "localhost:4567"

  def tweets(params: Tuple2[String, String]*) = {
    get("/tweets").params(params:_*).fetch { resp =>
      Json.parse[List[Tweet]](resp.contentString)
    }
  }
}

object App {
  def main(args: Array[String]) {
    val client = new TweetClient

    client.tweets("screen_name" -> "foobar").onSuccess { tweets =>
      println(tweets)
    } onFailure { e =>
      e.printStackTrace()
    }

  }
}
