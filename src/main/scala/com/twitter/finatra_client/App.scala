package com.twitter.finatra_client

import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future
import com.codahale.jerkson.Json

case class Tweet(status: String, screen_name: String)

class TweetClient extends FinatraClient {
  val baseURI = "localhost:4567"

  def tweets(params: Tuple2[String, String]*) = {
    post("/tweets").params(params:_*).headers("X-Foo" -> "bar").fetch { resp =>
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
