package example

import menu.KeyPress._
import menu.Menus._
import org.scalatest._

class MenuTest extends FlatSpec with Matchers {

  "A SingleChoiceMenu" should "have a well formatted string representation" in {

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

  "changeState" should "handle Up and Down keys" in {

    val s0 = SingleChoiceMenu("Title", List("Option1", "Option2"), selected = 0)

    val s1 = changeState(Up, s0)
    s1.selected shouldEqual 0

    val s2 = changeState(Down, s1)
    s2.selected shouldEqual 1

    val s3 = changeState(Down, s2)
    s3.selected shouldEqual 1
  }

}
