package org.toryt.example;

import java.text.NumberFormat;

/**
 * Elements in a tree of bookmarks. All elements in this tree have
 * a {@link #getTitle() title}, a {@link #getDescription() description},
 * and a {@link #getRating() rating}.
 * 
 * @author    Bart Van Den Poel
 * @author    Jan Dockx
 * @author    Ren&eacute; Clerckx
 * @author    Wim Lambrechts
 * @author    Abdul Shoudouev
 * @author    Peopleware n.v.
 * 
 * @invar getTitle() != null;
 * @invar getDescription() != null;
 * @invar getRating() >= 0;
 * @invar getRating() <= 10;
 * @invar (getGroup() != null)
 *          ? getGroup().getNodes().get(getTitle()) == this
 *          : true;
 * 
 * @protected
 * @invar getTotalOfRatings() >= 0;
 * @invar getNumberOfBookmarks() > 0;
 * @invar getTotalOfRatings() <= 10 * getNumberOfBookmarks();
 */
public abstract class Node implements java.io.Serializable {

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

  
  
  public static final String EMPTY = ""; //$NON-NLS-1$

  
  
  /*<constructors>*/
  //------------------------------------------------------------------

  /**
   * @post new.getDescription().equals(EMPTY);
   * @post new.getTitle().equals(EMPTY);
   * @post new.getRating() == 0;
   * @post new.getGroup() == null;
   */
  protected Node() {
    // NOP
  }
  
  /*<constructors>*/

  

  /*<property name="description">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public String getDescription() {
    return $description;
  }

  /**
   * @param description The description to set.
   * @post new.getDescription()
   *          .equals(description == null ? EMPTY : description);
   */
  public void setDescription(String description) {
    $description = (description == null ? EMPTY : description);
  }

  /**
   * @invar $description != null;
   */
  private String $description = EMPTY;

  /*</property>*/



  /*<property name="title">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public String getTitle() {
    return $title;
  }

  /**
   * @param title
   *        The title to set.
   * @post new.getTitle().equals(title == null ? EMPTY : title);
   */
  public void setTitle(String title) {
    $title = (title == null ? EMPTY : title);
  }

  /**
   * @invar $title != null;
   */
  private String $title = EMPTY;

  /*</property> */

  
  
  /*<property name="rating">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @protected return ((double)getTotalOfRatings()) / getNumberOfBookmarks();
   */
  public final double getRating() {
    return ((double)getTotalOfRatings()) / getNumberOfBookmarks();
  }

  /**
   * @basic
   */
  protected abstract int getTotalOfRatings();
  
  /**
   * @basic
   */
  protected abstract int getNumberOfBookmarks();
  
  /*</property> */

  
  
  /*<property name="group">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public Group getGroup() {
    return $group;
  }
  
  /**
   * @post new.getGroup() == group;
   * @post (getGroup() != null)
   *        ==> ! (new getGroup()).getNodes().values().contains(this);
   * @post (group != null) ==> (new group).getNodes().values().contains(this);
   */
  public void setGroup(Group group) {
    if ($group != null) {
      $group.removeNode(this);
    }
    $group = group;
    if ($group != null) {
      $group.addNode(this);
    }
  }
  
  private Group $group;
  
  /*</property> */

  
  private static int NF_DIGITS = 3;
  
  private static NumberFormat NF = NumberFormat.getNumberInstance();
  
  static {
    NF.setMinimumFractionDigits(NF_DIGITS);
    NF.setMaximumFractionDigits(NF_DIGITS);
  }
  
}
