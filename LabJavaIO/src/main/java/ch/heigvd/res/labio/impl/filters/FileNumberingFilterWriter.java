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

  private int lineCount = 0;
  private boolean previousCarriage;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    if (off < 0 || off + len > str.length()) throw new IllegalArgumentException();

    for (int i = off; i < off + len && i < str.length(); ++i) {
      write(str.charAt(i));
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
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    if (off < 0 || off + len > cbuf.length) throw new IllegalArgumentException();


    for (int i = off; i < off + len && i < toString().length(); ++i) {

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
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    // TODO: 15.03.19 make it beautiful

    if (lineCount == 0) {
      super.write('1');
      super.write('\t');
      lineCount += 2;

      super.write(c);
    } else if (c == '\n') {

      previousCarriage = false;
      super.write(c);

      for (char cara : Integer.toString(lineCount++).toCharArray()) {
        super.write(cara);
      }

      super.write('\t');


    } else if (c == '\r') {

      previousCarriage = true;
      super.write(c);

    } else if (previousCarriage) {

      previousCarriage = false;

      for (char cara : Integer.toString(lineCount++).toCharArray()) {
        super.write(cara);
      }

      super.write('\t');
      super.write(c);
    } else {
      super.write(c);
    }

  }
}


