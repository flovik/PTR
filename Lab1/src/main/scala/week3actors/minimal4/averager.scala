package org.victor
package week3actors.minimal4

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.mutable.ListBuffer

object averager {

  def apply(numbers: ListBuffer[Double]): Behavior[mathCommand] = Behaviors
    .receive[mathCommand]((context, message) => {
      message match {
        case CalculateAverage(number) =>
          val average = (numbers.sum + number) / 2
          context.log.info(s"Current average is $average")
          numbers.update(0, average)
          Behaviors.same
      }
    })

  sealed trait mathCommand

  final case class CalculateAverage(number: Double) extends mathCommand
}
