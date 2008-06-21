package org.toryt.support.straightlist;


import java.util.Iterator;


/**
 * Lazy mapping of the elements of one {@link StraightList}.
 *
 * @author    Jan Dockx
 */
public class LazyMappingStraightList extends AbstractLazyStraightList {

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
   * @pre l != null;
   * @post containsAll(l);
   * @post l.containsAll(this) except null;
   * @post ; the order of <code>l</code> and <code>this</code> is the same
   */
  public LazyMappingStraightList(StraightList l, Mapping mapping) {
    assert l != null;
    assert mapping != null;
    $l = l;
    $mapping = mapping;
  }
  
  /**
   * @invar $l != null;
   */
  private StraightList $l;
  
  private Mapping $mapping;
  
  public final Iterator iterator() {
    return new Iterator() {

      Iterator $iter = $l.iterator();
      
      public boolean hasNext() {
        return $iter.hasNext();
      }

      public Object next() {
        return $mapping.map($iter.next());
      }

      public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
      }
      
    };
  }

  public final int size() {
    return $l.size();
  }
  
  static public interface Mapping {
    Object map(Object o);
  }
  
}
