package org.toryt_II.testobject;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A <code>ConstantTestObjectFactory</code>'s {@link #generate()} method
 *   always returns the same instance. This can be used (and may only be used)
 *   for test objects that are guaranteed to be immutable.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ConstantTestObjectFactory extends AbstractTestObjectFactory {

  /**
   * @pre immutableInstance != null;
   * @post new.generate() == immutableInstance;
   * @post getTestObjectClass() == immutableInstance.getClass();
   */
  public ConstantTestObjectFactory(Object immutableInstance) {
    super(immutableInstance.getClass());
    assert immutableInstance != null;
    $immutableInstance = immutableInstance;
  }

  public final Object generate() {
    return $immutableInstance;
  }

  private final Object $immutableInstance;

}