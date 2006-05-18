package org.toryt_II.OLDcontract;


import java.util.Set;

import org.toryt_I.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.test.Test;



/**
 * A contract can be tested. The {@link #getTests() tests} of type {@link Test}
 * are run by a {@link Test} in order.
 * 
 * Contracts are gathered in a tree of {@link TypeContract type contracts},
 * and {@link MethodContract method contracts}.
 * 
 * @author Jan Dockx
 * 
 * @invar getTests() != null;
 * @invar ! getTests().contains(null);
 * @invar (forall Object o; getTests().contains(o); o instanceof Test);
 * @invar getMethodContracts() != null;
 * @invar ! getMethodContracts().contains(null);
 * @invar (forall Object o; getMethodContracts().contains(o);
 *            o instanceof OLDMethodContract);
 * @invar getExtraTests() != null;
 * @invar ! getExtraTests().contains(null);
 * @invar (forall Object o; getExtraTests().contains(o);
 *            o instanceof Test);
 */
public interface Contract {

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


  
  /**
   * @return getExtraTests() + getMethodTests();
   * @throws OLDTorytException
   * 
   * @deprecated
   */
  StraightList getTests() throws OLDTorytException;

  /**
   * @throws OLDTorytException
   * @basic
   * @deprecated
   */
  StraightList getMethodTests() throws OLDTorytException;

  /**
   * @basic
   * @deprecated
   */
  StraightList getExtraTests();

  /**
   * Contracts are often built at runtime. When the building process
   * has finished, they are closed. The tests can only be retrieved
   * when the contract is closed.
   * 
   * @basic
   * @init false;
   */
  boolean isClosed();
  
  Set getSubContracts();
  
}