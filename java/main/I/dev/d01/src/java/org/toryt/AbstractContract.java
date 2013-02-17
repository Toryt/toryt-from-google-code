package org.toryt;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Jan Dockx
 */
public abstract class AbstractContract implements Contract {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/
  

  
  public final List getExtraTests() {
    return Collections.unmodifiableList($extraTests);
  }
  
  /**
   * @pre    t != null;
   * @throws TorytException
   *         isClosed();
   */
  public final void addExtraTests(Test t) throws TorytException {
    assert t != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $extraTests.add(t);
  }
  
  private List $extraTests = new ArrayList();

  public final List getTests() throws TorytException {
    // MUDO lazy
    ArrayList result = new ArrayList(getExtraTests());
    result.addAll(getMethodTests());
    return result;
  }

  public final boolean isClosed() {
    return $closed;
  }
  
  protected final void close() {
    $closed = true;
  }
  
  private boolean $closed;

}