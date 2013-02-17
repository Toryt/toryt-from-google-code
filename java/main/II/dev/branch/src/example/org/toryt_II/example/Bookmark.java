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


import java.util.Date;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Bookmarks have a URL, the date they were added, and the number of times
 * they were clicked, and a rating.
 *
 * @author    Bart Van Den Poel
 * @author    Jan Dockx
 * @author    Ren&eacute; Clerckx
 * @author    Wim Lambrechts
 * @author    Abdul Shoudouev
 * @author    Peopleware n.v.
 *
 * @invar getUrl() != null;
 * @invar getDateAdded() != null;
 * @invar ! getDateAdded().after(new Date());
 * @invar getNumberOfClicks() >= 0;
 * @invar new.getDateAdded().equals(getDateAdded());
 *
 * @protected
 * @invar getNumberOfBookmarks() == 1;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Bookmark extends Node {

  /*<constructors>*/
  //------------------------------------------------------------------

  /**
   * @post new.getUrl().equals(EMPTY);
   * @post new.getTitle().equals(EMPTY);
   * @post new.getDescription().equals(EMPTY);
   * @post new.getDate().equals(new Date());
   * @post new.getRating() == 0;
   * @post new.getNumberOfClicks() == 0;
   * @post new.getGroup() == null;
   */
  public Bookmark() {
    // NOP
  }

  /**
   * @post new.getUrl().equals(url == null ? EMPTY : url);
   * @post new.getTitle().equals(title == null ? EMPTY : title);
   * @post new.getDescription().equals(description == null ? EMPTY : description);
   * @post new.getDate().equals(new Date());
   * @post new.getRating() == rating;
   * @post new.getNumberOfClicks() == 0;
   * @post new.getGroup() == group;
   * @throws IllegalArgumentException
   *         (rating < 0) || (rating > 10);
   */
  Bookmark(String url, String title, String description, int rating, Group group) {
    setUrl(url);
    setTitle(title);
    setDescription(description);
    setRating(rating);
    setGroup(group);
  }

  /*</constructors>*/



  /*<property name="url">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public String getUrl() {
    return $url;
  }

  /**
   * @post new.getUrl().equals(url == null ? EMPTY : url);
   */
  public void setUrl(String url) {
    $url = (url == null ? EMPTY : url);
  }

  /**
   * @invar $url != null;
   */
  private String $url =  EMPTY;

  /*</property> */



  /*<property name="numberOfClicks">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public int getNumberOfClicks() {
    return $numberOfClicks;
  }

  /**
   * @post new.getNumberOfClicks() == getNumberOfClicks() + 1;
   * @throws ArithmeticException
   *         getNumberOfClicks() == Integer.MAX_VALUE;
   */
  public void clicked() throws ArithmeticException {
    if ($numberOfClicks == Integer.MAX_VALUE) {
      throw new ArithmeticException("Overflow of " //$NON-NLS-1$
                              + "$numberOfClicks; " //$NON-NLS-1$
                              + "this bookmark is too popular."); //$NON-NLS-1$
    }
    $numberOfClicks++;
  }

  /**
   * @invar $numberOfClicks >= 0;
   */
  private int $numberOfClicks;

  /*</property> */



  /*<property name="dateAdded">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public Date getDateAdded() {
    return (Date)$dateAdded.clone();
  }

  /**
   * @invar $dateAdded != null;
   */
  private final Date $dateAdded = new Date();

  /*</property> */



  /*<property name="rating">*/
  //------------------------------------------------------------------

  /**
   * @post new.getRating() == rating();
   * @throws IllegalArgumentException
   *         (rating < 0) || (rating > 10);
   */
  public void setRating(int rating) throws IllegalArgumentException {
    if ((rating < 0) || (rating > 10)) {
      throw new IllegalArgumentException(
                             "Rating out of bounds."); //$NON-NLS-1$
    }
    $rating = rating;
  }

  /**
   * @return getRating();
   */
  @Override
  protected int getTotalOfRatings() {
    return $rating;
  }

  @Override
  protected int getNumberOfBookmarks() {
    return 1;
  }

  /**
   * @invar $rating >= 0;
   */
  private int $rating;

  /*</property> */

}



