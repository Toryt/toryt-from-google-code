package org.toryt_II;

import java.io.PrintStream;



/**
 * An actual instance of a test.
 * After {@link #test()} has been called, {@link #isSuccessful()}
 * is <code>true</true> when the text succeeded, and <code>false</code>
 * if it failed.
 */
public interface Test {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  /**
   * @post   ! hasRun();
   * @post   new.hasRun();
   * @throws TorytException
   *         hasRun();
   */
  void test() throws TorytException;

  boolean hasRun();

  /**
   * @throws TorytException
   *         ! hasRun();
   */
  boolean isSuccessful() throws TorytException;

  /**
   * Structured output on an output stream.
   *
   * ATdeprecated
   * @mudo not sure about deprecation
   */
  void report(PrintStream out);

}