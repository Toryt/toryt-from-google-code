package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.bigSet.algebra.UnionBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A {@link PriorityList}, that is the union of all component PriorityLists.</p>
 * <p>Because big sets can be so large (and <code>contains(Object)</code>
 *   and <code>containsAll(Collection)</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   priority elements from different component priority lists in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component priority lists to be <em>disjunct</em> on the level of
 *   the priority elements.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          getPriorityElementType().isAssignableFrom(getComponents()[i].getPriorityElementType()));
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          (forall int j; (j >= 0) && (j < getComponents().length) && (j != i);
 *            (forall Object o; getComponents()[i].containsPriorityElement(o);
 *              ! getComponents()[j].containsPriorityElement(o)));
 *        component priority lists are disjunct on the level of the priority elements
 */
public class UnionPriorityList extends AbstractComponentPriorityList {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  /**
   * @pre priorityElementType != null;
   * @pre $components != null;
   * @pre cC:noNull($components);
   * @pre (forall int i; (i >= 0) && (i < $components.length);
   *        $components[i].isLocked());
   * @pre (forall int i; (i >= 0) && (i < $components.length);
   *        getPriorityElementType().isAssignableFrom($components[i].getPriorityElementType()));
   * @pre (forall int i; (i >= 0) && (i < $components.length);
   *        (forall int j; (j >= 0) && (j < $components.length) && (j != i);
   *          (forall Object o; $components[i].containsPriorityElement(o);
   *             ! $components[j].containsPriorityElement(o)));
   *      component priority lists must be disjunct on the level of the priority
   *      elements (not checked with an assertion because too expensive)
   * @post Collections.containsAll($components, new.getComponents());
   */
  public UnionPriorityList(Class priorityElementType, PriorityList[] components) {
    super(priorityElementType, calculateCardinality(components), components);
    assert Collections.forAll(components,
                              new Assertion() {
                                    public boolean isTrueFor(Object o) {
                                      return getPriorityElementType().
                                              isAssignableFrom(((PriorityList)o).
                                                               getPriorityElementType());
                                    }
                                  });
    $size = calculateSize(components);
  }

  private static int calculateSize(PriorityList[] components) {
    assert components != null;
    int max = 0;
    for (int i = 0; i < components.length; i++) {
      max = Math.max(components[i].size(), max);
    }
    return max;
  }

  private static BigInteger calculateCardinality(PriorityList[] components) {
    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < components.length; i++) {
      result = result.add(components[i].getCardinality());
    }
    return result;
  }

  /**
   * @return (sum int i; (i >=0 ) && (i < getComponents().length);
   *            getComponents()[i].getCardinality());
   *
  public final BigInteger getCardinality();
   */

  public final boolean contains(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion() {
                                    public boolean isTrueFor(Object s) {
                                      return (s == null) && ((PriorityList)s).contains(o);
                                    }
                                  });
  }

  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion() {
                                    public boolean isTrueFor(Object s) {
                                      return (s == null) && ((PriorityList)s).containsPriorityElement(o);
                                    }
                                  });
  }

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.forAll(getComponents(),
                              new Assertion() {
                                    public boolean isTrueFor(Object o) {
                                      return (o == null) || ((PriorityList)o).isEmpty();
                                    }
                                  });
  }

  public final int size() {
    return $size;
  }

  /**
   * @invar $size >= 0;
   */
  private final int $size;

  private class UnionListIterator extends AbstractLockedListIterator {

    public UnionListIterator(int index) {
      initIterators(index);
    }

    private final PriorityList[] $components = getComponents();

    private final ListIterator[] $iterators = new ListIterator[$components.length];

    private void initIterators(int index) {
      for (int i = 0; i < $components.length; i++) {
        $iterators[i] = $components[i].listIterator(index);
      }
    }

    public boolean hasNext() {
      // TODO Auto-generated method stub
      return false;
    }

    public Object next() {
      LockableBigSet[] result = new LockableBigSet[$iterators.length];
      for (int i = 0; i < $iterators.length; i++) {
        if ($iterators[i].hasNext()) {
          result[i] = (LockableBigSet)$iterators[i].next();
        }
      }
      return new UnionBigSet(getPriorityElementType(), result);
CHANGE UnionBigSet to accept nulls in components !!!
      // MUDO either cache this, or override BigSet.equals()!!!!
    }

    public boolean hasPrevious() {
      // TODO Auto-generated method stub
      return false;
    }

    public Object previous() {
      // TODO Auto-generated method stub
      return null;
    }

    public int nextIndex() {
      // TODO Auto-generated method stub
      return 0;
    }

    public int previousIndex() {
      // TODO Auto-generated method stub
      return 0;
    }

    public void set(Object o) {
      // TODO Auto-generated method stub

    }

    public void add(Object o) {
      // TODO Auto-generated method stub

    }

  }

  public List subList(int fromIndex, int toIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  public final Object get(int index) {
    PriorityList[] components = getComponents();
    LockableBigSet[] result = new LockableBigSet[components.length];
    for (int i = 0; i < components.length; i++) {
      result[i] = (LockableBigSet)components[i].get(index);
    }
    return new UnionBigSet(getPriorityElementType(), result);
    // MUDO either cache this, or override BigSet.equals()!!!!
  }

  public int indexOf(Object o) {
    // TODO Auto-generated method stub
    return 0;
  }

  public int lastIndexOf(Object o) {
    // TODO Auto-generated method stub
    return 0;
  }

  public ListIterator listIterator() {
    // TODO Auto-generated method stub
    return null;
  }

  public ListIterator listIterator(int index) {
    // TODO Auto-generated method stub
    return null;
  }

}