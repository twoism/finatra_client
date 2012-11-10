package com.twitter.http_client

import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future
import com.codahale.jerkson.Json._

case class Tweet(status: String, screen_name: String)

class TweetClient extends HttpClient {
  val baseURI = "localhost:4567"

  def tweets = {
    get("/tweets") map { resp =>
      parse[List[Tweet]](resp.contentString)
    }
  }
}

object App {
  def main(args: Array[String]) {
    val client = new TweetClient
    client.tweets.onSuccess { tweets =>

      println("**************************************")
      println(tweets)
      println("**************************************")

    } onFailure { _ =>
      println("Failed!")
    }

  }
}
