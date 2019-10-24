package menu

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import cats.instances.all._

object Example extends IOApp {

  def run(args: List[String]): IO[ExitCode] = for {
    result <- Menus.singleChoiceMenu("What is your fruit?", List("Apple", "Banana", "Grapes"))
    _ <- IO(println(result))
  } yield (ExitCode.Success)
}
