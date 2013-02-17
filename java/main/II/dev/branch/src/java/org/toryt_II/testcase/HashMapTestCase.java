package org.toryt_II.testcase;


import static org.toryt_II.testcase.TestCaseLabels.EXCEPTION;
import static org.toryt_II.testcase.TestCaseLabels.THIS;
import static org.toryt_II.testcase.TestCaseLabels.RESULT;

import java.util.HashMap;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * {@link HashMap}-based implementation of {@link TestCase}.
 *
 * @author Jan Dockx
 *
 * @invar containsKey(THIS) ? get(THIS) instanceof _ImplicitArgument_;
 * @invar containsKey(RESULT) ? get(RESULT) instanceof _Result_;
 * @invar containsKey(EXCEPTION) ? get(EXCEPTION) instanceof Throwable;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class HashMapTestCase<_ImplicitArgument_, _Result_>
    extends HashMap<String, Object>
    implements TestCase<_ImplicitArgument_, _Result_> {

  @Override
  public final Object put(String key, Object value) throws IllegalArgumentException {
    if (THIS.equals(key) || RESULT.equals(key) || EXCEPTION.equals(key)) {
      throw new IllegalArgumentException(key);
    }
    return super.put(key, value);
  }

  @Override
  public final void putAll(Map<? extends String, ? extends Object> map)  throws IllegalArgumentException {
    if ((map != null) &&
         (map.keySet().contains(THIS) ||
          map.keySet().contains(RESULT) ||
          map.keySet().contains(EXCEPTION))) {
      throw new IllegalArgumentException();
    }
    super.putAll(map);
  }

  public final _ImplicitArgument_ getImplicitArgument() {
    @SuppressWarnings("unchecked") _ImplicitArgument_ implicitArgument =
        (_ImplicitArgument_)get(THIS);
    return implicitArgument;
  }

  public final void setImplicitArgument(_ImplicitArgument_ ia) {
    super.put(THIS, ia);
  }

  /**
   * @mudo not implemented yet
   */
  public final void storeState() {
    // Not implemented yet
  }

  public final _Result_ getResult() {
    @SuppressWarnings("unchecked") _Result_ result = (_Result_)get(RESULT);
    return result;
  }

  public final void setResult(_Result_ result) {
    super.put(RESULT, result);
  }

  public final Throwable getException() {
    @SuppressWarnings("unchecked") Throwable exception = (Throwable)get(EXCEPTION);
    return exception;
  }

  public final void setException(Throwable exception) {
    super.put(EXCEPTION, exception);
  }

}