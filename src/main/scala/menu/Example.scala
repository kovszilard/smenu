package menu

import cats.Show
import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO, IOApp}
import cats.instances.all._
import cats.syntax.all._

object Example extends IOApp {

  trait Pet
  case object Cat extends Pet
  case object Dog extends Pet

  implicit def showPet: Show[Pet] = implicitly[Show[String]].contramap(_.toString)

  def run(args: List[String]): IO[ExitCode] = for {
    result1 <- Menus.singleChoiceMenu[IO, String]("What is your fruit?", NonEmptyList.of("Apple", "Banana", "Grapes"))
    _ <- IO(println(result1))
    result2 <- Menus.multipleChoiceMenu[IO, Pet]("Cats or dogs?", NonEmptyList.of(Cat, Dog))
    _ <- IO(println(result2))
  } yield (ExitCode.Success)
}
