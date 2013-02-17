package org.toryt_II.testcase;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Reserved labels in test cases.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestCaseLabels {

  /**
   * Prefix for the labels.
   *
   * <strong>= {@value}</strong>
   */
  final static String PREFIX = TestCaseLabels.class.getCanonicalName();

  /**
   * Key for the implicit argument of a method call
   * in a context.
   *
   * <strong>= {@value}</strong>
   */
  final static String THIS = PREFIX + "." + "THIS";

  /**
   * Key for storing the result of a method call in
   * a context.
   *
   * <strong>= {@value}</strong>
   */
  final static String RESULT = PREFIX + "." + "RESULT";

  /**
   * Key for the exception that might be thrown by
   * a method call.
   *
   * <strong>= {@value}</strong>
   */
  final static String EXCEPTION = PREFIX + "." + "THROWN";

  /**
   * Call in <code>new</code> state.
   *
   * <strong>= {@value}</strong>
   */
  final static String NEW_CALL = ".";

  /**
   * Call in <code>new</code> state.
   *
   * <strong>= {@value}</strong>
   */
  final static String OLD_CALL = "'";

}