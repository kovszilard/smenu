package menu

import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO, IOApp}
import cats.instances.all._

object Example extends IOApp {

  def run(args: List[String]): IO[ExitCode] = for {
    result1 <- Menus.singleChoiceMenu("What is your fruit?", NonEmptyList.of("Apple", "Banana", "Grapes"))
    _ <- IO(println(result1))
    result2 <- Menus.multipleChoiceMenu("What is your fruit?", NonEmptyList.of("Apple", "Banana", "Grapes"))
    _ <- IO(println(result2))
  } yield (ExitCode.Success)
}
