package org.toryt.example.hard;
 
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
 * @toryt:cC toryt.contract.Collections;
 * 
 * @invar getNodes() != null;
 * @invar cC:noNull(getNodes().values());
 * @invar cC:noNull(getNodes().keySet());
 * @invar cC:instanceOf(getNodes().values(), Node.class);
 * @invar cC:instanceOf(getNodes().keySet(), String.class);
 * @invar (foreach Node n; getNodes().values().contains(n);
 *            n.getGroup() == this);
 */
public class Group extends Node {

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
  public Map getNodes() {
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
   * @invar (foreach Object o; $nodes.values().contains(o);
   *          o != null);
   * @invar (foreach Object o; $nodes.values().contains(o);
   *          o instanceof Node);
   * @invar (foreach Object o; $nodes.keySet().contains(o);
   *          o != null);
   * @invar (foreach Object o; $nodes.keySet().contains(o);
   *          o instanceof String);
   * @invar (foreach Node n; $nodes.values().contains(n);
   *          n.getGroup() == this);
   */
  private final HashMap $nodes = new HashMap();

  /* </property> */



  /* <property name="rating"> */
  //------------------------------------------------------------------

  protected final int getTotalOfRatings() {
    int totalOfRatings = 0;
    Iterator iterator = $nodes.values().iterator();
    while (iterator.hasNext()) {
      Node node = (Node)iterator.next();
      totalOfRatings += node.getTotalOfRatings();
    }
    return totalOfRatings;
  }

  protected final int getNumberOfBookmarks() {
    int numberOfBookmarks = 0;
    Iterator iterator = $nodes.values().iterator();
    while (iterator.hasNext()) {
      Node node = (Node)iterator.next();
      numberOfBookmarks += node.getNumberOfBookmarks();
    }
    return numberOfBookmarks;
  }

  /* </property> */
  
}
