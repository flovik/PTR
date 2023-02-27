package org.victor
package week4.minimal

import akka.actor.Props

object SupervisorActor {
  def props(): Props = Props(new SupervisorClass())

  case class SendMessage(message: String)

  case object SendWorkers
}
