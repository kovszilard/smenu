package menu

import cats.Show
import cats.data.NonEmptyList
import cats.instances.string._
import cats.syntax.all._
import menu.KeyPress._
import menu.Menus._
import org.scalatest._

class MenuTest extends FlatSpec with Matchers {

  "A SingleChoiceMenu" should "have a well formatted string representation" in {

    val menu1 = SingleChoiceMenu("What is your favourite fruit?", NonEmptyList.of("Apple", "Banana"))

    val expected1 =
      """|What is your favourite fruit?
         |> Apple
         |  Banana""".stripMargin

    val menu2 = SingleChoiceMenu("What is your favourite fruit?", NonEmptyList.of("Apple", "Banana"), selected = 1)

    val expected2 =
      """|What is your favourite fruit?
         |  Apple
         |> Banana""".stripMargin

    implicitly[Show[Menu[String]]].show(menu1) shouldEqual expected1
    implicitly[Show[Menu[String]]].show(menu2) shouldEqual expected2
  }

  "changeState" should "handle Up and Down keys" in {

    val s0 = SingleChoiceMenu("Title", NonEmptyList.of("Option1", "Option2"), selected = 0)

    val s1 = changeState(Up.some, s0)
    s1.selected shouldEqual 0

    val s2 = changeState(Down.some, s1)
    s2.selected shouldEqual 1

    val s3 = changeState(Down.some, s2)
    s3.selected shouldEqual 1
  }

}
