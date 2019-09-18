package example

import menu.Menus._
import org.scalatest._

class MenuTest extends FlatSpec with Matchers {

  "A SingleChoiceMenu" should "have a well formed string representation" in {

    val menu = SingleChoiceMenu("What is your favourite fruit?", List("Apple", "Banana"))

    val expected =
      """|What is your favourite fruit?
         |> Apple
         |  Banana""".stripMargin

    menuString(menu) shouldEqual expected
  }
}
