package org.toryt.support.straightlist;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;


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
    setSizeGuess($l.getBigSize());
  }
  
  /**
   * @invar $l != null;
   */
  private StraightList $l;
  
  private Mapping $mapping;
  
  public final Iterator iterator() {
    return new AbstractUnmodifiableIterator() {
      /**
       * Since the mapping can fail for the last element in the list,
       * we need to prefetch the next element, because otherwise
       * hasNext might have been a lie.
       */

      private Iterator $iter = $l.iterator();
      
      /**
       * <code>null</code> can be a valid element
       */
      private Object $nextElement;
      
      /**
       * $nextElement contains the real next element;
       * if this is <code>false</code>, there is no next element.
       */
      private boolean $nextElementOk = false;
      
      {
        proceed();
      }
      
      private void proceed() {
        $nextElementOk = false;
        BigInteger invalidMappings = ZERO;
        while ((! $nextElementOk) && $iter.hasNext()) {
          Object iterNext = $iter.next();
              // the size of the source list may have shrunk
          if ($mapping.isValid(iterNext)) {
            $nextElementOk = true;
            $nextElement = $mapping.map(iterNext);
          }
          else {
            invalidMappings = invalidMappings.add(ONE);
          }
        }
        if (! isSizeFixed()) {
          updateListSize($l.getBigSize().subtract(invalidMappings), $iter.hasNext());
        }
      }
      
      public final boolean hasNext() {
        return $nextElementOk;
      }

      public final Object next() {
        if (! $nextElementOk) {
          throw new NoSuchElementException();          
        }
        Object result = $nextElement;
        proceed();
        return result;
      }
      
    };
  }

  /**
   * Mapping of objects to other objects.
   * The mapping can be be not applicable to certain
   * object. In that case, {@link #isValid(Object)}
   * is <code>false</code>, and {@link #map(Object)}
   * will not be called. This means that the algorithm
   * of {@link #map(Object)} does not need to produce
   * a meaningful result, or even has to end, if the object
   * is flagged as not-valid.
   */
  static public interface Mapping {
    
    boolean isValid(Object o);
    
    /**
     * @pre isValid(o);
     */
    Object map(Object o);

  }

  /**
   * Utility class that says that all source
   * object are valid for the mapping.
   */
  static public abstract class AllValidMapping implements  Mapping {
    
    public final boolean isValid(Object o) {
      return true;
    }

  }

}
