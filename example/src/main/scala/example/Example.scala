package example

import cats.Show
import cats.instances.all._
import cats.syntax.all._
import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO, IOApp}
import smenu.Menu

object Example extends IOApp {

  trait Pet
  case object Cat extends Pet
  case object Dog extends Pet

  implicit def showPet: Show[Pet] = Show[String].contramap(_.toString)

  def run(args: List[String]): IO[ExitCode] = for {
    result1 <- Menu.singleChoiceMenu[IO, String]("What is your favourite fruit?", NonEmptyList.of("Apple", "Banana", "Orange"))
    _ <- IO(println(s"You choose: $result1\n"))
    result2 <- Menu.multipleChoiceMenu[IO, Pet]("Cats or dogs or both?", NonEmptyList.of(Cat, Dog))
    _ <- IO(println(s"You choose: $result2"))
  } yield (ExitCode.Success)
}
