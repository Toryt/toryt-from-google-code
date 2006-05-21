package org.toryt_II.contract.condition;


import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testcase.TestCaseLabels;


/**
 * An ExceptionCondition is used to validate boolean expressions on a
 * method execution context (a {@link Map} that contains the
 * test case, old values and the execution result) during a method test,
 * to validate a thrown exception. The class of the exception for which this
 * condition applies is given as a generic parameter. The exception is
 * stored in the <code>context</code> with key {@link TestCaseLabels#EXCEPTION}.
// * ,
// * and can be retrieved with the correct static type using
// * @link #exception(Map)}.
 *
 * @author Jan Dockx
 *
 * @invar getExceptionType() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ExceptionCondition<_Exception_ extends Throwable>
    extends Condition {


  /**
   * @basic
   */
  public abstract Class<_Exception_> getExceptionType();

  /**
   * Validate a context.
   *
   * @pre context != null;
   * @pre context.containsKey(org.toryt_II.testcase.Labels.THIS);
   * @pre context.get(org.toryt_II.testcase.Labels.THIS) != null;
   * @pre context.get(org.toryt_II.testcase.Labels.THIS) instanceof _ImplicitArgument_;
   * @pre context.containsKey(org.toryt_II.testcase.Labels.EXCEPTION);
   * @pre context.get(org.toryt_II.testcase.Labels.EXCEPTION) != null;
   * @pre context.get(org.toryt_II.testcase.Labels.EXCEPTION) instanceof _Exception_;
   */
  boolean validate(Map<String, Object> context);

//  /**
//   * Retrieve the exception from the context.
//   *
//   * @pre context != null;
//   * @pre context.containsKey(org.toryt_II.testcase.Labels.THIS);
//   * @pre context.get(org.toryt_II.testcase.Labels.THIS) != null;
//   * @pre context.get(org.toryt_II.testcase.Labels.THIS) instanceof _ImplicitArgument_;
//   * @pre context.containsKey(org.toryt_II.testcase.Labels.EXCEPTION);
//   * @pre context.get(org.toryt_II.testcase.Labels.EXCEPTION) != null;
//   * @pre context.get(org.toryt_II.testcase.Labels.EXCEPTION) instanceof _Exception_;
//   * @todo This might be a good reason for declaring a generic subtype of Map: to
//   *       have generic type control on the type of the implicit argument and the type
//   *       of the result. This isn't possible for the type of the exception, because
//   *       there can be many possible exceptions.
//   * @return context.get(org.toryt_II.testcase.Labels.EXCEPTION);
//   * @result result != null;
//   */
//  protected final _Exception_ exception(Map<String, Object> context) {
//    @SuppressWarnings("unchecked") _Exception_ exception_ =
//        (_Exception_)context.get(EXCEPTION);
//    return exception_;
//  }

}