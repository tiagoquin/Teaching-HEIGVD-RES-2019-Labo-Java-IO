package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    if (off < 0 || off + len > cbuf.length) throw new IllegalArgumentException();


    for (int i = off; i < off + len; ++i) {

      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    super.write(Character.toUpperCase(c));
  }

}
