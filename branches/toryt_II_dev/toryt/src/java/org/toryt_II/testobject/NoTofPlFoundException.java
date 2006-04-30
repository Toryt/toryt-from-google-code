package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that no <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * could be instantiated for class {@link #getForClass()}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class NoTofPlFoundException extends TofPlFactoryException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   */
  public NoTofPlFoundException(TofPlFactory tofPlFactory, Class<?> forClass) {
    super(tofPlFactory, forClass);
  }

}