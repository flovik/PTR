package org.victor
import org.scalatest.funsuite
import classes.HelloWorder

import org.scalatest.funsuite.AnyFunSuite

class HelloWorderTests extends AnyFunSuite {
  test("HelloWorder.helloPtr") {
    val helloWorder = new HelloWorder;
    assert(helloWorder.helloPtr() == "Hello PTR!")
  }
}