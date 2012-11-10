package com.twitter.http_client

import com.twitter.util.Future
import com.twitter.finagle.http.{Response, Request}

abstract class Parser[T] {
  def parseCollection(response: Future[Response]): Future[List[T]]
}