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
public class ConstantTestObjectFactory<_TestObjectType_>
    extends AbstractTestObjectFactory<_TestObjectType_> {

  /**
   * @pre immutableInstance != null;
   * @post new.generate() == immutableInstance;
   */
  public ConstantTestObjectFactory(_TestObjectType_ immutableInstance) {
    super();
    assert immutableInstance != null;
    $immutableInstance = immutableInstance;
  }

  public final _TestObjectType_ generate() {
    return $immutableInstance;
  }

  private final _TestObjectType_ $immutableInstance;

}