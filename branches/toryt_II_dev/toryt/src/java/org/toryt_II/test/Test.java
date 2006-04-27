package org.toryt_II.test;

import java.io.PrintStream;

import org.toryt_II.OLDTorytException;



/**
 * An actual instance of a test.
 * After {@link #test()} has been called, {@link #isSuccessful()}
 * is <code>true</true> when the text succeeded, and <code>false</code>
 * if it failed. A test can only be run once.
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
   * @throws OLDTorytException
   *         hasRun();
   */
  void test() throws OLDTorytException;

  boolean hasRun();

  /**
   * @throws OLDTorytException
   *         ! hasRun();
   */
  boolean isSuccessful() throws OLDTorytException;

  /**
   * Structured output on an output stream.
   *
   * ATdeprecated
   * @mudo not sure about deprecation
   */
  void report(PrintStream out);

}