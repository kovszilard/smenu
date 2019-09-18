package menu

object Menus {

  case class SingleChoiceMenu(title: String, options: List[String], selected: Int = 0)

  def menuString(m: SingleChoiceMenu): String = {

    val options = m.options.zipWithIndex.map{ case (s, i) =>
      if (i == m.selected) s"> $s"
      else s"  $s"}

    s"${m.title}\n${options.mkString("\n")}"
  }
}

object Test extends App {

  import Menus._

  val menu = SingleChoiceMenu("What is your favourite fruit?", List("Apple", "Banana"))

  println(menuString(menu))
}
