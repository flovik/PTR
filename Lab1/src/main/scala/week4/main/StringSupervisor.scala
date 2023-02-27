package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.event.Level

class StringSupervisor extends Actor with ActorLogging {
  override def receive: Receive = ???


  private def workerName(): String = {
    s"worker-${java.util.UUID.randomUUID().toString}"
  }
}

object StringSupervisora {
  def apply(): Behavior[CleanString] = Behaviors
    .supervise[CleanString] {
      Behaviors.setup { ctx =>
        ctx.log.info(ctx.self.path.name)
        val strPrinter = ctx.spawn(stringPrinter, String.format("printer-%s", uuid))
        val strJoiner = ctx.spawn(stringJoiner(strPrinter, ctx.self), String.format("joiner-%s", uuid))
        val strLowercaserAndSwapper = ctx.spawn(stringLowercaserAndSwapper(strJoiner, ctx.self), String.format("lowercaserAndSwapper-%s", uuid))
        val strSplitter = ctx.spawn(stringSplitter(strLowercaserAndSwapper, ctx.self), String.format("splitter-%s", uuid))
        ctx.watch(strPrinter)
        ctx.watch(strJoiner)
        ctx.watch(strLowercaserAndSwapper)
        ctx.watch(strSplitter)

        Behaviors
          .receiveMessage[CleanString] {
            case CleanMessage(message) =>
              strSplitter ! SplitMessage(message)
              Behaviors.same
            case ChildActorRestarted(actorRef) =>
              throw new IllegalArgumentException("Child actor restarted")
              Behaviors.same
            case _ =>
              Behaviors.same
          }
          .receiveSignal {
            case (context, signal) if signal == PreRestart =>
              context.log.warn("Restarting supervisor")
              context.log.warn(context.self.path.name)
              Behaviors.same
          }
      }
    }
    .onFailure(SupervisorStrategy.restart.withLogLevel(level = Level.WARN))


  //actors
  private def stringSplitter(nextActor: ActorRef[CleanString], parent: ActorRef[CleanString]): Behavior[CleanString] =
    Behaviors
      .supervise[CleanString] {
        Behaviors
          .receive[CleanString] { (ctx, message) =>
            ctx.log.info(ctx.self.path.name)
            message match {
              case SplitMessage(message) =>
                try {
                  if (message.contains('@')) {
                    throw new IllegalArgumentException("Message is empty")
                  }
                  val words = message.split("\\s+")
                  nextActor ! LowercaseAndSwapMessage(words)
                  Behaviors.same
                }
                catch {
                  case e: IllegalArgumentException =>
                    ctx.log.warn("Splitter actor failed")
                    parent ! ChildActorRestarted(ctx.self)
                    Behaviors.same
                }
            }
          }
          .receiveSignal {
            case (context, signal) if signal == PreRestart =>
              context.log.warn("Splitter restarting...")
              context.log.warn(context.self.path.name)
              Behaviors.same
          }
      }
      .onFailure[IllegalArgumentException](SupervisorStrategy.restart.withLogLevel(level = Level.WARN))

  private def stringLowercaserAndSwapper(nextActor: ActorRef[CleanString], parent: ActorRef[CleanString]): Behavior[CleanString] =
    Behaviors
      .supervise[CleanString] {
        Behaviors
          .receive[CleanString] { (ctx, message) =>
            ctx.log.info(ctx.self.path.name)
            message match {
              case LowercaseAndSwapMessage(words) =>
                val lowercasedWords = words.map(_.toLowerCase)
                val swappedMsWithNs = lowercasedWords.map(str => str.map {
                  case 'm' => 'n'
                  case 'n' => 'm'
                  case c => c
                })
                nextActor ! JoinMessage(swappedMsWithNs)
                Behaviors.same
            }
          }
          .receiveSignal {
            case (context, signal) if signal == PreRestart =>
              context.log.warn("Lowercaser and swapper restarting...")
              context.log.warn(context.self.path.name)
              Behaviors.same
          }
      }
      .onFailure[IllegalArgumentException](SupervisorStrategy.restart.withLogLevel(level = Level.WARN))

  private def stringJoiner(nextActor: ActorRef[CleanString], parent: ActorRef[CleanString]): Behavior[CleanString] =
    Behaviors
      .supervise[CleanString] {
        Behaviors
          .receive[CleanString] { (ctx, message) =>
            ctx.log.info(ctx.self.path.name)
            message match {
              case JoinMessage(words) =>
                val joinedWords = words.mkString(" ")
                nextActor ! PrintMessage(joinedWords)
                Behaviors.same
            }
          }
          .receiveSignal {
            case (context, signal) if signal == PreRestart =>
              context.log.warn("Joiner restarting...")
              context.log.warn(context.self.path.name)
              Behaviors.same
          }
      }
      .onFailure[IllegalArgumentException](SupervisorStrategy.restart.withLogLevel(level = Level.WARN))

  private def stringPrinter(): Behavior[CleanString] =
    Behaviors
      .supervise[CleanString] {
        Behaviors
          .receive[CleanString] { (ctx, message) =>
            ctx.log.info(ctx.self.path.name)
            message match {
              case PrintMessage(message) =>
                ctx.log.info(message)
                Behaviors.same
            }
          }
          .receiveSignal {
            case (context, signal) if signal == PreRestart =>
              // Stopping all actors
              context.log.warn("String printer restarting...")
              context.log.warn(context.self.path.name)
              Behaviors.same
          }
      }
      .onFailure[IllegalArgumentException](SupervisorStrategy.restart.withLogLevel(level = Level.WARN))

  sealed trait CleanString

  final case class CleanMessage(message: String) extends CleanString

  final case class SplitMessage(message: String) extends CleanString

  final case class LowercaseAndSwapMessage(words: Array[String]) extends CleanString

  final case class JoinMessage(words: Array[String]) extends CleanString

  final case class PrintMessage(message: String) extends CleanString

  case class ChildActorRestarted(actorRef: ActorRef[CleanString]) extends CleanString
}


object StringSplitter {
  def props(): Props = Props(new StringSplitterClass())

  case class SendMessage(message: String)
}