package org.toryt_II.cases;


import org.toryt_I.support.straightlist.NullFirstStraightList;
import org.toryt_I.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;


/**
 * Implementations provide sets of test cases.
 *
 * @author    Jan Dockx
 *
 * @deprecated
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
   * @throws OLDTorytException
   */
  NullFirstStraightList getCasesWithNull() throws OLDTorytException;

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws OLDTorytException
   */
  StraightList getCases() throws OLDTorytException;

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws OLDTorytException
   */
  NullFirstStraightList getSomeCasesWithNull() throws OLDTorytException;

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws OLDTorytException
   */
  StraightList getSomeCases() throws OLDTorytException;

}
