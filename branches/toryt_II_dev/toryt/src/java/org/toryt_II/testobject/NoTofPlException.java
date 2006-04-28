package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Signals that no <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * could be instantiated for class {@link #getForClass()}.
 *
 * @author Jan Dockx
 *
 * @invar getForClass() != null;
 * @invar getMessage().equals(forClass.getName());
 * @invar getCause() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class NoTofPlException extends TorytException {

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