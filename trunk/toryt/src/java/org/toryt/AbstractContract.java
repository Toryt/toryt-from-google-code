package org.toryt;


import java.util.ArrayList;
import java.util.List;

import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.ListWrapperStraightList;
import org.toryt.support.straightlist.StraightList;


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
  

  
  public final StraightList getExtraTests() {
    return new ListWrapperStraightList($extraTests);
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

  public final StraightList getTests() throws TorytException {
    return new ConcatStraightList(new StraightList[] {getExtraTests(), getMethodTests()});
  }

  public final boolean isClosed() {
    return $closed;
  }
  
  protected final void close() {
    $closed = true;
  }
  
  private boolean $closed;

}