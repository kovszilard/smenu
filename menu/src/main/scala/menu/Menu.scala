package menu

import cats.Show
import cats.data.NonEmptyList
import cats.syntax.all._
import cats.effect._
import menu.KeyPress._
import org.jline.terminal.{Terminal, TerminalBuilder}

object Menu {

  def singleChoiceMenu[F[_] : Sync, A : Show](title: String, options: NonEmptyList[A]): F[A] = runMenu(SingleChoiceMenu(title, options)).map(_.head)
  def multipleChoiceMenu[F[_] : Sync, A : Show](title: String, options: NonEmptyList[A]): F[List[A]] = runMenu(MultipleChoiceMenu(title, options))

  private[menu] trait Menu[A] {
    def getResult: List[A]
  }
  private[menu] final case class SingleChoiceMenu[A](title: String, options: NonEmptyList[A], selected: Int = 0) extends Menu[A] {
    def getResult: List[A] = options.get(selected).toList
  }
  private[menu] final case class MultipleChoiceMenu[A](title: String, options: NonEmptyList[A], cursorPos: Int = 0, selected: List[Int] = List.empty) extends Menu[A] {
    def getResult: List[A] = selected.map(x => options.get(x).get)
  }

  private[menu] def runMenu[F[_] : Sync, A : Show](menu: Menu[A]): F[List[A]] = {

    def loop(lastKey: Option[KeyPress], menu: Menu[A], terminal: Terminal): List[A] = lastKey match {
      case Some(Enter) => menu.getResult
      case key =>
        // bring cursor back to beginning of menu
        menu.show.linesIterator.foreach(_ => terminal.writer().print("\u001b[1A"))

        // print new menu
        val currentMenuState = changeState(key, menu)
        terminal.writer().println(currentMenuState.show)
        terminal.writer().flush()

        val newKey = Utils.readKey(terminal.reader())
        loop(newKey, currentMenuState, terminal)
    }

    Sync[F].bracket{
      Sync[F].delay(
        TerminalBuilder.builder().system(true).build()
      )
    }{ terminal =>
      terminal.enterRawMode()

      terminal.writer().println(menu.show)
      terminal.writer().flush()
      Sync[F].delay(loop(Utils.readKey(terminal.reader()), menu, terminal))
    }{ terminal =>
      terminal.close()
      Sync[F].unit
    }
  }

  private[menu] implicit def menuShow[A : Show]: Show[Menu[A]] = new Show[Menu[A]] {
    def show(m: Menu[A]): String = m match {
      case m: SingleChoiceMenu[A] =>
        val options = m.options.zipWithIndex.map{ case (s, i) =>
          if (i == m.selected) show"> $s"
          else show"  $s"}

        s"${m.title}\n${options.toList.mkString("\n")}"
      case m: MultipleChoiceMenu[A] =>
        val options = m.options.zipWithIndex.map{ case (s, i) =>
          val cursorPosStr = if (i == m.cursorPos) "> " else "  "
          val rest = if (m.selected.contains(i)) show"[*] $s" else show"[ ] $s"
          cursorPosStr + rest
        }

        s"${m.title}\n${options.toList.mkString("\n")}"
    }
  }

  private[menu] def changeState[A](input: Option[KeyPress], m: Menu[A]): Menu[A] = m match {
    case m: SingleChoiceMenu[A] => changeState(input, m)
    case m: MultipleChoiceMenu[A] => changeState(input, m)
  }

  private[menu] def changeState[A](input: Option[KeyPress], m: SingleChoiceMenu[A]): SingleChoiceMenu[A] = input match {
    case Some(Up) if m.selected > 0 => m.copy(selected = m.selected - 1)
    case Some(Down) if m.selected < m.options.size - 1 => m.copy(selected = m.selected + 1)
    case _ => m
  }

  private[menu] def changeState[A](input: Option[KeyPress], m: MultipleChoiceMenu[A]): MultipleChoiceMenu[A] = input match {
    case Some(Up) if m.cursorPos > 0 => m.copy(cursorPos = m.cursorPos - 1)
    case Some(Down) if m.cursorPos < m.options.size - 1 => m.copy(cursorPos = m.cursorPos + 1)
    case Some(Space) if !m.selected.contains(m.cursorPos) => m.copy(selected = (m.selected ++ List(m.cursorPos)).distinct)
    case Some(Space) if m.selected.contains(m.cursorPos) => m.copy(selected = m.selected.filter(x => x != m.cursorPos))
    case _ => m
  }
}

private[menu] trait KeyPress
private[menu] object KeyPress {

  case object Up extends KeyPress
  case object Down extends KeyPress
  case object Escape extends KeyPress
  case object Enter extends KeyPress
  case object Space extends KeyPress
  case object Tab extends KeyPress
}
