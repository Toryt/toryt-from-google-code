package org.toryt.support.straightlist;


/**
 * Implementation of a number of methods of {@link StraightList}
 *
 * @author    Jan Dockx
 */
public abstract class AbstractAllValidStraightList extends AbstractStraightList {

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
   * @result true;
   */
  public boolean isSizeFixed() {
    return true;
  }

}
