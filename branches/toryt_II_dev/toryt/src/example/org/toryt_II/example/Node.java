/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt_II.example;


import java.text.NumberFormat;

import org.toryt.util_I.annotations.vcs.CvsInfo;


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
 * @invar (! Double.isNanN(getRating()) ==> (getRating() >= 0);
 * @invar (! Double.isNaN(getRating()) ==> (getRating() <= 10);
 * @invar (getGroup() != null)
 *          ? getGroup().getNodes().get(getTitle()) == this
 *          : true;
 *
 * @protected
 * @invar getTotalOfRatings() >= 0;
 * @invar getNumberOfBookmarks() > 0;
 * @invar getTotalOfRatings() <= 10 * getNumberOfBookmarks();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class Node implements java.io.Serializable {


  public static final String EMPTY = "";



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
   * Since the title is used as the key in the group we are in, we also
   * need to change that entry to keep type invariants!
   *
   * @param title
   *        The title to set.
   * @post new.getTitle().equals(title == null ? EMPTY : title);
   */
  public void setTitle(String title) {
    Group ourGroup = getGroup();
    setGroup(null);
    $title = (title == null ? EMPTY : title);
    setGroup(ourGroup);
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
   *        ==> ! getGroup()@post.getNodes().values().contains(this);
   * @post (group != null) ==> group@post.getNodes().values().contains(this);
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
