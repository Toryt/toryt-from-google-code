package org.toryt_II.testmodel;


import org.toryt_II.TorytException;


/**
 * Signals that creation of a {@link TestModel} by a {@link TestModelFactory}
 * failed. The {@link #getMessage() message} and {@link #getCause() cause}
 * will express why the creation failed. {@link #getSubject()} returns
 * the object for which we tried to create a {@link TestModel}.
 *
 * @author Jan Dockx
 *
 * @invar getSubject() != null;
 */
public class TestModelFactoryException/*<SubjectType>*/ extends TorytException {

  // TODO why can't we not make this class generic?

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
   * @pre subject != null;
   * @post new.getSubject() == subject;
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TestModelFactoryException(Object /*SubjectType*/ subject, String message, Throwable cause) {
    super(message, cause);
    assert subject != null;
    $subject = subject;
  }

  /**
   * @basic
   */
  public Object /*SubjectType*/ getSubject() {
    return $subject;
  }

  /**
   * @invar $subject != null;
   */
  private final Object /*SubjectType*/ $subject;

}