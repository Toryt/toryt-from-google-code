package org.toryt_II.testcase;


import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * <p>A test case for a method test.</p>
 * <p>This is a map, that goes through a number of states.
 *   After construction (state {@link TestCaseState#OPEN),
 *   the implicit argument and
 *   actual arguments for the method call can be added.
 *   The state can be set to PRE if the arguments are valid
 *   for the method call. In this state, {@link #storeState()}
 *   can be called, to fill the map with the transitive closure
 *   of all the values. When this is done, the state is
 *   READY. After the method is called, the result or an
 *   exception can be added to the map. Once this is done, the state
 *   becomes CLOSED.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked() ? containsKey(THIS);
 * @invar isLocked() ? implicitArgument() != null;
 * @invar containsKey(THIS) ? get(THIS) instanceof _ImplicitArgument_;
 * @invar containsKey(EXCEPTION) ? get(EXCEPTION) instanceof Throwable;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestCase<_ImplicitArgument_, _Result_>
    extends Map<String, Object> {

  /**
   * The <code>put</code>  method works as expected, except that it
   * doesn't allow the keys THIS, RESULT or EXCEPTION.
   * This method throws an {@link IllegalArgumentException}
   * when exercised with one of those keys.
   * You should use {@link #setImplicitArgument(Object)},
   * {@link #setResult(Object)} and {@link #setException(Throwable)}
   * to add these values instead.
   *
   * @post   ! THIS.equals(key) && ! RESULT.equals(key) && ! EXCEPTION.equals(key);
   * @throws IllegalArgumentException
   *         THIS.equals(key) || RESULT.equals(key) || EXCEPTION.equals(key);
   */
  Object put(String key, Object value) throws IllegalArgumentException;

  /**
   * The <code>putAll</code> method works as expected, except that it
   * doesn't allow the keys THIS, RESULT or EXCEPTION.
   * This method throws an {@link IllegalArgumentException}
   * when exercised with one of those keys.
   * You should use {@link #setImplicitArgument(Object)},
   * {@link #setResult(Object)} and {@link #setException(Throwable)}
   * to add these values instead.
   *
   * @post   (map == null) ||
   *         (! map.keySet().contains(THIS) &&
   *          ! map.keySet().contains(RESULT) &&
   *          ! map.keySet().contains(EXCEPTION));
   * @throws IllegalArgumentException
   *         (map != null) &&
   *         (map.keySet().contains(THIS) ||
   *          map.keySet().contains(RESULT) ||
   *          map.keySet().contains(EXCEPTION));
   *         We can't throw a {@link TorytException}, because the {@link Map}
   *         contract limits us.
   */
  void putAll(Map<? extends String, ? extends Object> map)  throws IllegalArgumentException;

  /**
   * Retrieve the implicit argument from this test case.
   *
   * @return get(THIS);
   */
  _ImplicitArgument_ getImplicitArgument();

  /**
   * Put the implicit argument in this test case.
   *
   * @post new.getImplicitArgument == ia;
   */
  void setImplicitArgument(_ImplicitArgument_ ia);

  /**
   * Visit all entries in the map, and put the transitive closure
   * of all properties in the map to, with appropriate keys.
   *
   * @mudo not implemented yet
   */
  void storeState();

  /**
   * Retrieve the result from this test case.
   * This might return <code>null</code>
   * (before the test, or after the test if the
   * tested method is a mutator, or if the tested
   * method is an inspector and the result is
   * <code>null</code> effectively).
   *
   * @return get(RESULT);
   */
  _Result_ getResult();

  /**
   * Retrieve the exception from this test case.
   * This might return <code>null</code>.
   *
   * @post new.getResult() == result;
   */
  void setResult(_Result_ result) throws IllegalStateException;

  /**
   * Retrieve the exception from this test case.
   * This might return <code>null</code> (before the
   * test, or after the test if the tested method
   * did complete nominally).
   *
   * @return get(EXCEPTION);
   */
  Throwable getException();

  /**
   * Retrieve the exception from this test case.
   * This might return <code>null</code>.
   *
   * @post new.getException() == exception;
   */
  void setException(Throwable exception);

}