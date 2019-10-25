package menu

import org.jline.utils.NonBlockingReader

object Utils {

  def readKey(reader: NonBlockingReader): Option[KeyPress] = {

    reader.read() match {
      // check for escape sequence (blocks for 100ms)
      case 27 => reader.peek(100L) match {
        // there are more characters to read so it is an escape sequence
        case a if a > 0 => reader.read() match {
          // possible arrow keys
          case 91 => reader.read() match {
            case 65 => Option(KeyPress.Up)
            case 66 => Option(KeyPress.Down)
            case _ => Option.empty
          }
          // not arrow key
          case _ => Option.empty
        }
        // no more characters to read, so it is an escape key
        case _ => Option(KeyPress.Escape)
      }
      case 13 => Option(KeyPress.Enter)
      case 32 => Option(KeyPress.Space)
      case 9 => Option(KeyPress.Tab)
      case _ => Option.empty
    }
  }
}
