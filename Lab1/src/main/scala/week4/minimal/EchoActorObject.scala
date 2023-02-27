package org.victor
package week4.minimal

import akka.actor.Props

object EchoActorObject {
  def props(name: String): Props = Props(new EchoActorClass(name))

  case class Echo(message: String)

  case object Kill
}
