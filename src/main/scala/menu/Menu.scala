package menu

import menu.KeyPress._

object Menus {

  case class SingleChoiceMenu(title: String, options: List[String], selected: Int = 0)

  def menuString(m: SingleChoiceMenu): String = {

    val options = m.options.zipWithIndex.map{ case (s, i) =>
      if (i == m.selected) s"> $s"
      else s"  $s"}

    s"${m.title}\n${options.mkString("\n")}"
  }

  def changeState(input: KeyPress, m: SingleChoiceMenu): SingleChoiceMenu = input match {
    case Up if m.selected > 0 => m.copy(selected = m.selected - 1)
    case Down if m.selected < m.options.size - 1 => m.copy(selected = m.selected + 1)
    case _ => m
  }
}

trait KeyPress
object KeyPress {

  case object Up extends KeyPress
  case object Down extends KeyPress
}
