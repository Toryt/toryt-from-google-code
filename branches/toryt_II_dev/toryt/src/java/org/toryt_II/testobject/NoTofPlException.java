package org.toryt_II.testobject;


/**
 * Signals that no <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * could be instantiated for class {@link #getForClass()}.
 *
 * @author Jan Dockx
 *
 * @invar getForClass() != null;
 * @invar getMessage().equals(forClass.getName());
 * @invar getCause() == null;
 *
 * @mudo extend general TorytException
 */
public class NoTofPlException extends Exception {

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
   * @pre forClass != null;
   * @post new.getForClass() == forClass;
   */
  public NoTofPlException(Class forClass) {
    super(forClass.getName());
    assert forClass != null;
    $forClass = forClass;
  }

  /**
   * @basic
   */
  public Class getForClass() {
    return $forClass;
  }

  /**
   * @invar $forClass != null;
   */
  private final Class $forClass;

}