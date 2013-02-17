package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Generates combinations (set product) of a number of {@link StraightList source lists}
 * lazily. A combination is a map with an entry of each given source list.
 * The labels in the map are given in a String array. The combination maps
 * are generated when they are requested by the {@link Iterator#next()} method.
 *
 * @author    Jan Dockx
 * 
 * @invar ! contains(null);
 * @invar (forall Object o, contains(o), o instanceof Map);
 * 
 * @mudo better contract
 */
public class LazyCombinationStraightList extends AbstractStraightList {

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
   * @pre sources != null;
   * @pre ! Arrays.asList(sources).contains(null);
   * @pre labels != null;
   * @pre labels.length = sources.length;
   * @post (forall int i; (0 <= i) && (i < l.length); containsAll(l[i]));
   * @post (forall Object o; contains(o);
   *          (exists int i; (0 <= i) && (i < l.length); l[i].contains(o)));
   * 
   * @mudo better contract
   */
  public LazyCombinationStraightList(StraightList[] sources, String[] labels) {
    assert sources != null;
    assert labels != null;
    assert labels.length == sources.length;
    $sources = sources;
    $labels = labels;
  }
  
  /**
   * @invar $sources != null;
   */
  private StraightList[] $sources;
  
  /**
   * @invar $labels != null;
   */
  private String[] $labels;

  /**
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations
   * until a match failure is found.
   */
  public final boolean contains(Object o) {
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      if (! entry.equals(o)) {
        return false;
      }
    }
    return true;
  }

  public final boolean isEmpty() {
    return size() == 0;
  }

  public final Iterator iterator() {
    return new Iterator() {

      /**
       * $iter will always be ready for the next element.
       */
      private Iterator[] $iter = new Iterator[$sources.length];
      
      private Object[] $entries = new Object[$sources.length];

      {
        nextElement(true);
      }
      
      private void nextElement(boolean init) {
        int i = $iter.length - 1;
        while (i >= 0) {
          if (($iter[i] == null) || (! $iter[i].hasNext())) {
            // reset entry i
            $iter[i] = $sources[i].iterator();
            // remember that we must also now take the next element of i - 1
            $entries[i] = $iter[i].next();
            i--;
          }
          else { // just take the next of level i, and we are done
            $entries[i] = $iter[i].next();
            i = -2;
          }
        }
        /* if we end with -1 instead of -2, we have just reset the 0th
         * iterator, which means we are done
         */
        if ((! init) && (i == -1)) {
          $entries = null;
        }
      }
      
      public final boolean hasNext() {
        return $entries != null;
      }

      public final Object next() throws NoSuchElementException {
        if ($entries == null) {
          throw new NoSuchElementException();
        }
        Map result = new HashMap();
        for (int i = 0; i < $iter.length; i++) {
          result.put($labels[i], $entries[i]);
        }
        nextElement(false);
        return result;
      }
  
      public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
      }
      
    };
  }

  private int $size = -1;
  
  public final int size() {
    if ($size < 0) {
      $size = 1;
      for (int i = 0; i < $sources.length; i++) {
        $size = ($size < Integer.MAX_VALUE / $sources[i].size())
                    ? ($size * $sources[i].size())
                    : Integer.MAX_VALUE;
      }
    }
    return $size;
  }

  /**
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations.
   */
  public final Object[] toArray(Class clazz, int size) {
    if (size < size()) {
      throw new ArrayIndexOutOfBoundsException();
    }
    Object[] result = (Object[])Array.newInstance(clazz, size); 
    int i = 0;
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      result[i] = entry;
    }
    return result;
  }

}
