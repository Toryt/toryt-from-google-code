package org.toryt_II.contract;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.ListWrapperStraightList;
import org.toryt.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.test.Test;


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
    Set subcontracts = getSubContracts();
    if (subcontracts.isEmpty()) {
      return new ListWrapperStraightList($extraTests);
    }
    else {
      StraightList[] result =  new StraightList[subcontracts.size() + 1];
      result[0] = new ListWrapperStraightList($extraTests);
      int i = 1;
      Iterator iter = subcontracts.iterator();
      while (iter.hasNext()) {
        Contract c = (Contract)iter.next();
        result[i] = c.getExtraTests();
        i++;
      }
      return new ConcatStraightList(result);
    }
  }
  
  /**
   * @pre    t != null;
   * @throws OLDTorytException
   *         isClosed();
   */
  public final void addExtraTests(Test t) throws OLDTorytException {
    assert t != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $extraTests.add(t);
  }
  
  private List $extraTests = new ArrayList();

  public final StraightList getTests() throws OLDTorytException {
    return new ConcatStraightList(new StraightList[] {getExtraTests(), getMethodTests()});
  }

  public final boolean isClosed() {
    return $closed;
  }
  
  public final void close() {
    $closed = true;
  }
  
  private boolean $closed;

}