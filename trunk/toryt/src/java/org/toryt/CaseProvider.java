/*<license>
Copyright 2004, PeopleWare n.v.
NO RIGHTS ARE GRANTED FOR THE USE OF THIS SOFTWARE, EXCEPT, IN WRITING,
TO SELECTED PARTIES.
</license>*/


package org.toryt;


import java.util.List;


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
  List getCasesWithNull() throws TorytException;

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  List getCases() throws TorytException;

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  List getSomeCasesWithNull() throws TorytException;

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  List getSomeCases() throws TorytException;

}
