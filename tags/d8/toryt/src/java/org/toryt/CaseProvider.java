package org.toryt;


import org.toryt.support.straightlist.NullFirstStraightList;
import org.toryt.support.straightlist.StraightList;


/**
 * Implementations provide sets of test cases.
 *
 * @author    Jan Dockx
 */
public interface CaseProvider {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

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
   * All possible relevant cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  NullFirstStraightList getCasesWithNull() throws TorytException;

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  StraightList getCases() throws TorytException;

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  NullFirstStraightList getSomeCasesWithNull() throws TorytException;

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  StraightList getSomeCases() throws TorytException;

}
