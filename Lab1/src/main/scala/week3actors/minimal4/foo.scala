package org.victor
package week3actors.minimal4

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.mutable.ListBuffer

object foo {
  sealed trait mathCommand

  final case class CalculateAverage(number: Double) extends mathCommand

  // setup is like the constructor of the actor and some initialization
  // receiveMessage is like when we forward that message to other actors
  def apply(): Behavior[Double] = Behaviors
    .setup({ context =>
      val averageCalculator = context.spawn(averager(ListBuffer(0)), "averageCalculator")
      context.watch(averageCalculator)
      averageCalculator ! averager.CalculateAverage(0)

      Behaviors
        .receiveMessage({ message =>
          averageCalculator ! averager.CalculateAverage(message)
          Behaviors.same
        })
  })
}
