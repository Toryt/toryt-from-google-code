package org.toryt_II.testobject;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Supporting code for implementations of {@link TestObjectFactory}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractTestObjectFactory implements TestObjectFactory {

  /**
   * @pre testObjectClass != null;
   * @post new.getTestObjectClass() == testObjectClass;
   */
  protected AbstractTestObjectFactory(Class testObjectClass) {
    assert testObjectClass != null;
    $testObjectClass = testObjectClass;
  }


  public final Class getTestObjectClass() {
    return $testObjectClass;
  }

  /**
   * @invar $testObjectClass != null;
   */
  private final Class $testObjectClass;

}