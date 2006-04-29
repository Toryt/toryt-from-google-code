package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Exceptions thrown by {@link TofPlFactory} instances.
 *
 * @author Jan Dockx
 *
 * @invar getTofPlFactory() != null;
 * @invar getForClass() != null;
 * @invar getMessage() == null;
 * @invar getCause() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TofPlFactoryException extends TorytException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   */
  public TofPlFactoryException(TofPlFactory tofPlFactory, Class forClass) {
    super();
    assert tofPlFactory != null;
    assert forClass != null;
    $tofPlFactory = tofPlFactory;
    $forClass = forClass;
  }

  /**
   * @basic
   */
  public TofPlFactory getTofPlFactory() {
    return $tofPlFactory;
  }

  /**
   * @invar $tofPlFactory != null;
   */
  private final TofPlFactory $tofPlFactory;

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