package com.twitter.http_client

import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future
import com.codahale.jerkson.Json

class TweetClient extends HttpClient {

  case class Tweet(status: String, screen_name: String)

  val baseURI = "localhost:4567"

  def tweets(params: Map[String, String]=Map.empty) = {
    get("/tweets").fetch { resp =>
      println(resp.contentString)
      //Json.parse[List[Tweet]](resp.contentString)
    }
  }
}

object App {
  def main(args: Array[String]) {
    val client = new TweetClient

    client.tweets().onSuccess { tweets =>
      println(tweets)
    } onFailure { e =>
      e.printStackTrace()
    }

  }
}
