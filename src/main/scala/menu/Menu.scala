package menu

import cats.Show
import cats.instances.all._
import cats.syntax.all._
import cats.effect._
import menu.KeyPress._
import org.jline.reader.LineReaderBuilder
import org.jline.terminal.{Terminal, TerminalBuilder}

object Menus {
  
  case class SingleChoiceMenu[A](title: String, options: Seq[A], selected: Int = 0)


  def singleChoiceMenu[A : Show](title: String, options: Seq[A]): IO[A] = IO(runSingleChoiceMenu(SingleChoiceMenu(title, options)))

  def runSingleChoiceMenu[A : Show](menu: SingleChoiceMenu[A]): A = {

    def loop(lastKey: Option[KeyPress], menu: SingleChoiceMenu[A], terminal: Terminal): A = lastKey match {
      case Some(Enter) => menu.options(menu.selected)
      case key =>
        // bring cursor back to beginning of menu
        menu.show.lines.foreach(_ => terminal.writer().print("\u001b[1A"))

        // print new menu
        val currentMenuState = changeState(key, menu)
        terminal.writer().println(currentMenuState.show)
        terminal.writer().flush()

        val newKey = ReadArrow.readKey(terminal.reader())
        loop(newKey, currentMenuState, terminal)
    }

    val terminal: Terminal = TerminalBuilder.builder()
      .system(true)
      .build()
    terminal.enterRawMode()

    terminal.writer().println(menu.show)
    terminal.writer().flush()
    val result = loop(ReadArrow.readKey(terminal.reader()), menu, terminal)

    terminal.close()

    result
  }

//  def singleChoiceMenu[F[_], A : Show](title: String, options: Seq[A]): F[A] = ???
//  def singleChoiceMenu[F[_]](title: String, options: Seq[String], selected: Option[Int] = None): F[Int] = ???
//  def singleChoiceMenu[F[_], A](title: String, options: Map[String, A], selected: Option[String] = None): F[A] = ???

  implicit def singleChoiceMenuShow[A : Show]: Show[SingleChoiceMenu[A]] = new Show[SingleChoiceMenu[A]] {
    def show(m: SingleChoiceMenu[A]): String = {
      val options = m.options.zipWithIndex.map{ case (s, i) =>
        if (i == m.selected) show"> $s"
        else show"  $s"}

      s"${m.title}\n${options.mkString("\n")}"
    }
  }

  def changeState[A](input: Option[KeyPress], m: SingleChoiceMenu[A]): SingleChoiceMenu[A] = input match {
    case Some(Up) if m.selected > 0 => m.copy(selected = m.selected - 1)
    case Some(Down) if m.selected < m.options.size - 1 => m.copy(selected = m.selected + 1)
    case _ => m
  }
}

trait KeyPress
object KeyPress {

  case object Up extends KeyPress
  case object Down extends KeyPress
  case object Escape extends KeyPress
  case object Enter extends KeyPress
  case object Space extends KeyPress
  case object Tab extends KeyPress
}
