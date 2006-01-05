package org.toryt.util_I.priorityList.experiments;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * <p>The cartesian product of several {@link PriorityList}s, which is itself
 *   a priority list.</p>
 * <p>This implementation is lazy. The user should traverse through this
 *   result using the {@link #priorityListIterator()}. The elements retrieved
 *   from the list are arrays of length
 *   <code>{@link #getPriorityLists()}.length</code>:
 *   <code>Object[{@link #getPriorityLists()}.length]</code>.</p>
 * <p>This is implemented through nesting and flattening:
 *   <code>P1 x P2 x P3 x P4 = flatten(((P1 x P2) x P3) x P4)</code></p>
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 * 
 * @invar getPriorityLists() != null;
 * @invar getPriorityLists().length >= 2;
 */
public class MultiProductPriorityList2
    extends AbstractSequentialPriorityList {

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
   * @param priorityLists
   *        An array of PriorityList to combine in the cartesian product.
   * @pre priorityLists != null;
   * @pre priorityLists.length >= 2;
   * @post Arrays.equals(priorityLists, getPriorityLists());
   */
  public MultiProductPriorityList2(PriorityList[] priorityLists) {
    super(multiProduct(PriorityLists.extractSize(priorityLists)));
    assert priorityLists != null;
    assert priorityLists.length >= 2;
    $priorityLists = (PriorityList[])priorityLists.clone();
    $ppl = new ProductPriorityList(priorityLists[0],
                                   priorityLists[1]);
    for (int i = 2; i < priorityLists.length; i++) {
      $ppl = new ProductPriorityList($ppl, priorityLists[i]);
    }
  }

  /**
   * Return the product of all {@link BigInteger} instance in <code>bis</code>.
   */
  private static BigInteger multiProduct(BigInteger[] bis) {
    BigInteger result = BigInteger.ONE;
    for (int i = 0; i < bis.length; i++) {
      result = result.multiply(bis[i]);
    }
    return result;
  }
  
  private static List flatten(List nested) {
    LinkedList result = new LinkedList();
    ListIterator iter = nested.listIterator();
    while (iter.hasNext()) {
      Object next = iter.next();
      if ((next != null)
          && ((next instanceof List) || (next instanceof Object[]))) {
        List nextAsList;
        if (next instanceof List) {
          nextAsList = (List)next;
        }
        else { // array
          nextAsList = Arrays.asList((Object[])next);
        }
        result.addAll(flatten(nextAsList));
      }
      else {
        result.add(next);
      }
    }
    return result;
  }
  
  private static Object[] flatten(Object[] nested) {
    List nestedAsList = Arrays.asList(nested);
    return flatten(nestedAsList).toArray();
  }
  
  /**
   * @basic
   */
  public PriorityList[] getPriorityLists() {
    return (PriorityList[])$priorityLists.clone();
  }
  
  /**
   * @invar $priorityLists != null;
   */
  private PriorityList[] $priorityLists;

  private ProductPriorityList $ppl;
  
  public final PriorityListIterator priorityListIterator() {
    return new MX_PLI();
  }
  
  public final PriorityListIterator priorityListIterator(BigInteger index)
      throws IndexOutOfBoundsException {
    assert index != null;
    PriorityListIterator pli = priorityListIterator();
    while (pli.bigIntegerNextIndex().compareTo(index) < 0) {
      try {
        pli.next();
      }
      catch (NoSuchElementException nseExc) {
        throw new IndexOutOfBoundsException();
      }
    }
    return pli;
  }
  
  private class MX_PLI extends AbstractPriorityListIterator {
    
    public MX_PLI() {
      super(MultiProductPriorityList2.this);
      init($ppl.priorityListIterator());
    }

    /**
     * The algorithm requires a deep clone.
     */
    public Object clone() {
      MX_PLI result = (MX_PLI)super.clone();
      result.init((PriorityListIterator)$pplIter.clone());
      return result;
    }
    
    private void init(PriorityListIterator pplIter) {
      $pplIter = pplIter;
    }
    
    private PriorityListIterator $pplIter;

    public Object next() throws NoSuchElementException {
      return flatten((Object[])$pplIter.next());
    }

    public Object previous() throws NoSuchElementException {
      return flatten((Object[])$pplIter.previous());
    }

    public final boolean hasNext() {
      return $pplIter.hasNext();
    }

    public final boolean hasPrevious() {
      return $pplIter.hasPrevious();
    }

    public final int nextIndex() {
      return $pplIter.nextIndex();
    }

    public final BigInteger bigIntegerNextIndex() {
      return $pplIter.bigIntegerNextIndex();
    }

    public final int previousIndex() {
      return $pplIter.previousIndex();
    }

    public final BigInteger bigIntegerPreviousIndex() {
      return $pplIter.bigIntegerPreviousIndex();
    }
    
  }

}