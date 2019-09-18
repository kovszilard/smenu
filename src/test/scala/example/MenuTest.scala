package example

import menu.Menus._
import org.scalatest._

class MenuTest extends FlatSpec with Matchers {

  "A SingleChoiceMenu" should "have a well formed string representation" in {

    val menu1 = SingleChoiceMenu("What is your favourite fruit?", List("Apple", "Banana"))

    val expected1 =
      """|What is your favourite fruit?
         |> Apple
         |  Banana""".stripMargin

    val menu2 = SingleChoiceMenu("What is your favourite fruit?", List("Apple", "Banana"), selected = 1)

    val expected2 =
      """|What is your favourite fruit?
         |  Apple
         |> Banana""".stripMargin

    menuString(menu1) shouldEqual expected1
    menuString(menu2) shouldEqual expected2
  }

}
