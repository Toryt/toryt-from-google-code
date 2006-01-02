/*<license>
Copyright 2004, PeopleWare n.v.
NO RIGHTS ARE GRANTED FOR THE USE OF THIS SOFTWARE, EXCEPT, IN WRITING,
TO SELECTED PARTIES.
</license>*/


package org.toryt_II.cases;


import java.util.List;


/**
 * Implementations provide sets of test cases.
 *
 * @author    Jan Dockx
 */
public abstract class AbstractCaseProvider {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /*</section>*/


  /**
   * All possible relevant cases for this type,
   * with <code>null</code>.
   */
  public final List getCasesWithNull() {
    List result = getCases();
    result.add(0, null);
    return result;
  }

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   */
  public abstract List getCases();

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   */
  public final List getSomeCasesWithNull() {
    List result = getSomeCases();
    result.add(0, null);
    return result;
  }

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   */
  public List getSomeCases() {
    return getCases();
  }

}
