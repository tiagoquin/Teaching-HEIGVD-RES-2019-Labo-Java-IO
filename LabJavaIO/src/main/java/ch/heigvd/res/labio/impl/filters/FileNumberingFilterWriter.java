package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineCount = 1;
  private boolean previousCarriage;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    write(str.toCharArray(),off,len); //Calls write with CharArray
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    if (off < 0 || off + len > cbuf.length) {
      throw new IllegalArgumentException();
    }

    for (int i = off; i < off + len; ++i) {

      write(cbuf[i]);
    }

    if (previousCarriage) {
      for (char cara : Integer.toString(lineCount++).toCharArray()) {
        super.write(cara);
      }

      super.write('\t');
      previousCarriage = false;
    }
  }

  @Override
  public void write(int c) throws IOException {

    if (lineCount == 1) {
      newLine();

      super.write(c);
    } else if (c == '\n') {

      previousCarriage = false;
      super.write(c);

      newLine();


    } else if (c == '\r') {

      previousCarriage = true;
      super.write(c);

    } else if (previousCarriage) {

      previousCarriage = false;

      newLine();

      super.write(c);
    } else {
      super.write(c);
    }

  }

  /**
   * Write "n\t" where n is the lineCount
   * @throws IOException
   */
  public void newLine() throws IOException {
    for (char cara : Integer.toString(lineCount++).toCharArray()) {
      write(cara);
    }
    write('\t');
  }
}


