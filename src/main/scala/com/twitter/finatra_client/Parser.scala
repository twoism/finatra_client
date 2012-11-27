package com.twitter.finatra_client

import com.twitter.util.Future
import com.twitter.finagle.http.{Response, Request}

abstract trait Parser[T] {
  def parseCollection(response: Future[Response]): Future[List[T]]
}