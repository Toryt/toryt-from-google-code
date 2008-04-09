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


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * A collection of bookmark tree nodes.
 *
 * @author    Bart Van Den Poel
 * @author    Jan Dockx
 * @author    Ren&eacute; Clerckx
 * @author    Wim Lambrechts
 * @author    Abdul Shoudouev
 * @author    Peopleware n.v.
 *
 * @invar getNodes() != null;
 * @invar cC:noNull(getNodes().values());
 * @invar cC:noNull(getNodes().keySet());
 * @invar cC:instanceOf(getNodes().values(), Node.class);
 * @invar cC:instanceOf(getNodes().keySet(), String.class);
 * @invar (foreach Node n; getNodes().values().contains(n);
 *            n.getGroup() == this);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Group extends Node {

  /*<constructors>*/
  //------------------------------------------------------------------

  /**
   * @post new.getDescription().equals(EMPTY);
   * @post new.getTitle().equals(EMPTY);
   * @post new.getRating() == 0;
   * @post new.getGroup() == null;
   * @post new.getNodes().isEmpty();
   */
  public Group() {
    //NOP
  }

  /**
   * @post new.getDescription()
   *        .equals(description == null ? EMPTY : description);
   * @post new.getTitle().equals(title == null ? EMPTY : title);
   * @post Double.isNaN(new.getRating());
   * @post new.getGroup() == parent;
   * @post new.getNodes().isEmpty();
   */
  public Group(String title, String description, Group parent) {
    setTitle(title);
    setDescription(description);
    setGroup(parent);
  }

  /*<constructors>*/



  /*<property name="nodes">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public Map<String, Node> getNodes() {
    return Collections.unmodifiableMap($nodes);
  }

  /**
   * @post getNodes().get(node.getTitle()) == node;
   */
  void addNode(Node node) {
    $nodes.put(node.getTitle(), node);
  }

  /**
   * @post getNodes().get(node.getTitle()) == null;
   */
  void removeNode(Node node) {
    $nodes.remove(node.getTitle());
  }

  /**
   * @invar $nodes != null
   * @invar Collections.noNull($nodes);
   * @invar (foreach Node n; $nodes.values().contains(n);
   *          n.getGroup() == this);
   */
  private final HashMap<String, Node> $nodes = new HashMap<String, Node>();

  /* </property> */



  /* <property name="rating"> */
  //------------------------------------------------------------------

  @Override
  protected final int getTotalOfRatings() {
    int totalOfRatings = 0;
    Iterator<Node> iterator = $nodes.values().iterator();
    while (iterator.hasNext()) {
      Node node = iterator.next();
      totalOfRatings += node.getTotalOfRatings();
    }
    return totalOfRatings;
  }

  @Override
  protected final int getNumberOfBookmarks() {
    int numberOfBookmarks = 0;
    Iterator<Node> iterator = $nodes.values().iterator();
    while (iterator.hasNext()) {
      Node node = iterator.next();
      numberOfBookmarks += node.getNumberOfBookmarks();
    }
    return numberOfBookmarks;
  }

  /* </property> */

}
