package org.toryt.util_I.collections.priorityList.algebra;


import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A {@link PriorityList}, that is the concatenation union of all component
 *   PriorityLists. This means that the priority of buckets in later components
 *   is shifted.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ConcatPriorityList extends AbstractComponentPriorityList {

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
   */
  public ConcatPriorityList(Class priorityElementType, PriorityList[] components) {
    super(priorityElementType,
          calculateSize(components),
          components);
    assert Collections.forAll(components,
                              new Assertion() {
                                    public boolean isTrueFor(Object o) {
                                      return getPriorityElementType().
                                              isAssignableFrom(((PriorityList)o).
                                                               getPriorityElementType());
                                    }
                                  });
  }

  private static int calculateSize(PriorityList[] components) {
    assert components != null;
    int acc = 0;
    for (int i = 0; i < components.length; i++) {
      acc += (components[i] == null) ? 0 : components[i].size();
    }
    return acc;
  }

  public final boolean contains(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion() {
                                    public boolean isTrueFor(Object s) {
                                      return (s == null) && ((PriorityList)s).contains(o);
                                    }
                                  });
  }

  public final List subList(int fromIndex, int toIndex) {
    if ((fromIndex < 0) || (toIndex < 0) || (toIndex < fromIndex)) {
      throw new IndexOutOfBoundsException();
    }
    PriorityList[] components = getComponents();
    int relativeFromIndex = fromIndex;
    int relativeToIndex = toIndex;
    // find from-component
    int fromComponent = 0;
    while ((fromComponent < components.length) &&
           (relativeFromIndex >= components[fromComponent].size())) {
      if (components[fromComponent] != null) {
        relativeFromIndex -= components[fromComponent].size();
        relativeToIndex -= components[fromComponent].size();
      }
      assert relativeFromIndex >= 0;
      assert relativeToIndex >= 0;
      fromComponent++;
    }
    // result starts in component[fromComponent]
    if (fromComponent >= components.length) {
      throw new IndexOutOfBoundsException();
    }
    // find to-component
    int toComponent = fromComponent;
    while ((toComponent < components.length) &&
           (relativeToIndex > components[toComponent].size())) {
      if (components[toComponent] != null) {
        relativeToIndex -= components[toComponent].size();
      }
      assert relativeToIndex >= 0;
      toComponent++;
    }
    // result ends in component[toComponent]
    if (toComponent >= components.length) {
      throw new IndexOutOfBoundsException();
    }
    // create and return result
    assert components[fromComponent] != null;
    assert components[toComponent] != null;
    if (toComponent == fromComponent) {
      return components[fromComponent].subList(relativeFromIndex, relativeToIndex);
    }
    else {
      int nrOfSubcomponents = toComponent - fromComponent + 1;
      PriorityList[] subcomponents = new PriorityList[nrOfSubcomponents];
      subcomponents[0] = (PriorityList)components[fromComponent].
                            subList(relativeFromIndex, components[fromComponent].size());
      for (int i = 1; i < nrOfSubcomponents - 1; i++) {
        subcomponents[i] = components[fromComponent + i];
      }
      subcomponents[nrOfSubcomponents - 1] = (PriorityList)components[toComponent].
                                                subList(0, relativeToIndex);
      return new ConcatPriorityList(getPriorityElementType(), subcomponents);
    }
  }

  private class RelativePosition {

    public RelativePosition(int ai) throws IndexOutOfBoundsException {
      assert ai >= 0;
      this.absoluteIndex = ai;
      this.relativeIndex = ai;
      while ((componentIndex < components.length) &&
             ((components[componentIndex] == null) ||
               (relativeIndex >= components[componentIndex].size()))) {
        relativeIndex -= (components[componentIndex] == null) ?
                           0 :
                           components[componentIndex].size();
        componentIndex++;
      }
      if (componentIndex >= components.length) {
        throw new IndexOutOfBoundsException();
      }
    }

    public final PriorityList[] components = getComponents();

    public int absoluteIndex;

    public int componentIndex = 0;

    public int relativeIndex;

  }

  public final Object get(int index) {
    RelativePosition rp = new RelativePosition(index);
    return rp.components[rp.componentIndex].get(rp.relativeIndex);
  }

  public final int indexOf(Object o) {
    PriorityList[] components = getComponents();
    int accIndex = 0;
    for (int i = 0; i < components.length; i++) {
      if (components[i] != null) {
        int componentIndex = components[i].indexOf(o);
        if (componentIndex >= 0) {
          return componentIndex + accIndex;
        }
        else {
          accIndex += components[i].size();
        }
      }
    }
    return -1;
  }

  public final int lastIndexOf(Object o) {
    PriorityList[] components = getComponents();
    int accIndex = size() - components[components.length].size();
    for (int i = components.length; i >= 0; i--) {
      if (components[i] != null) {
        int componentIndex = components[i].lastIndexOf(o);
        if (componentIndex >= 0) {
          return componentIndex + accIndex;
        }
        else {
          accIndex -= components[i].size();
        }
      }
    }
    return -1;
  }

  public final ListIterator listIterator(final int index) {
    return new AbstractLockedListIterator() {

                  private RelativePosition $rp = new RelativePosition(index);

                  private ListIterator $componentIterator =
                    $rp.components[$rp.componentIndex].listIterator($rp.relativeIndex);

                  public boolean hasNext() {
                    return ($componentIterator != null) && $componentIterator.hasNext();
                  }

                  public Object next() {
                    Object result = $componentIterator.next();
                    if (! $componentIterator.hasNext()) {
                      do {
                        $rp.componentIndex++;
                      } while (($rp.componentIndex < $rp.components.length) &&
                               (($rp.components[$rp.componentIndex] == null) ||
                                  $rp.components[$rp.componentIndex].isEmpty()));
                      if ($rp.componentIndex < $rp.components.length) {
                        assert $rp.components[$rp.componentIndex] != null;
                        assert ! $rp.components[$rp.componentIndex].isEmpty();
                        $componentIterator =
                          $rp.components[$rp.componentIndex].listIterator();
                      }
                      // else, leave it
                    }
                    $rp.absoluteIndex--;
                    return result;
                  }

                  public boolean hasPrevious() {
                    return ($componentIterator != null) && $componentIterator.hasPrevious();
                  }

                  public Object previous() {
                    Object result = $componentIterator.previous();
                    if (! $componentIterator.hasPrevious()) {
                      do {
                        $rp.componentIndex--;
                      } while (($rp.componentIndex >= 0) &&
                               (($rp.components[$rp.componentIndex] == null) ||
                                  $rp.components[$rp.componentIndex].isEmpty()));
                      if ($rp.componentIndex >= 0) {
                        assert $rp.components[$rp.componentIndex] != null;
                        assert ! $rp.components[$rp.componentIndex].isEmpty();
                        $componentIterator =
                          $rp.components[$rp.componentIndex].
                            listIterator($rp.components[$rp.componentIndex].size());
                      }
                      // else, leave it
                    }
                    $rp.absoluteIndex--;
                    return result;
                  }

                  public int nextIndex() {
                    return $rp.absoluteIndex;
                  }

                  public int previousIndex() {
                    return $rp.absoluteIndex - 1;
                  }

                };
  }

  public Iterator priorityElementIterator() {
    return new AbstractLockedCollectionIterator() {

                  private Iterator $listIter = iterator();

                  private Iterator $bucketIter;

                  {
                    nextBucketIterator();
                  }

                  private void nextBucketIterator() {
                    $bucketIter = null;
                    while ($listIter.hasNext() && ($bucketIter == null)) {
                      LockableBigSet bucket = (LockableBigSet)$listIter.next();
                      if (! bucket.isEmpty()) {
                        $bucketIter = bucket.iterator();
                      }
                    }
                  }

                  public boolean hasNext() {
                    return $bucketIter != null;
                  }

                  public Object next() {
                    Object result = $bucketIter.next();
                    if (! $bucketIter.hasNext()) {
                      nextBucketIterator();
                    }
                    return result;
                  }

                };
  }

}