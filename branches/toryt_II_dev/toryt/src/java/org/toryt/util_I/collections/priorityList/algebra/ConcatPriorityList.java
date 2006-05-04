package org.toryt.util_I.collections.priorityList.algebra;


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
public class ConcatPriorityList<_PriorityElement_>
    extends AbstractComponentPriorityList<_PriorityElement_> {

  /**
   * @pre component != null;
   * @pre cC:noNull(component);
   * @pre (forall int i; (i >= 0) && (i < component.length);
   *        component[i].isLocked());
   * @pre (! nullAllowed) ? (forall int i; (i >= 0) && (i < components.length);
   *          ! components[i].containsPriorityElement(null));
   * @post new.isNullPriorityElementAllowed() == nullPriorityElementAllowed;
   * @post Collections.containsAll(components, new.getComponents());
   */
  public ConcatPriorityList(boolean nullPriorityElementAllowed,
                            PriorityList<? extends _PriorityElement_>... component) {
    super(calculateSize(component), nullPriorityElementAllowed, component);
  }

  private static int calculateSize(PriorityList<?>[] components) {
    assert components != null;
    int acc = 0;
    for (PriorityList<?> pl : components) {
      acc += (pl == null) ? 0 : pl.size();
    }
    return acc;
  }

  @Override
  public final boolean contains(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion<PriorityList<? extends _PriorityElement_>>() {
                                    public boolean isTrueFor(PriorityList<? extends _PriorityElement_> s) {
                                      return s.contains(o);
                                    }
                                  });
  }

  public final ConcatPriorityList<_PriorityElement_> subList(int fromIndex, int toIndex) {
    if ((fromIndex < 0) || (toIndex < 0) || (fromIndex > toIndex) || (toIndex > size())) {
      throw new IndexOutOfBoundsException();
    }
    PriorityList<? extends _PriorityElement_>[] components = getComponents();
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
    int nrOfSubcomponents = toComponent - fromComponent + 1;
    assert nrOfSubcomponents > 0;
    @SuppressWarnings("unchecked") PriorityList<? extends _PriorityElement_>[] subcomponents =
          (PriorityList<? extends _PriorityElement_>[])new PriorityList<?>[nrOfSubcomponents];
    subcomponents[0] = components[fromComponent].subList(relativeFromIndex, components[fromComponent].size());
    for (int i = 1; i < nrOfSubcomponents - 1; i++) {
      subcomponents[i] = components[fromComponent + i];
    }
    if (toComponent > fromComponent) {
      subcomponents[nrOfSubcomponents - 1] = components[toComponent].subList(0, relativeToIndex);
    }
    return new ConcatPriorityList<_PriorityElement_>(isNullAllowed(), subcomponents);
  }

  private class RelativePosition {

    public RelativePosition(int ai) throws IndexOutOfBoundsException {
      assert ai >= 0;
      this.absoluteIndex = ai;
      this.relativeIndex = ai;
      while ((componentIndex < getComponents().length) &&
             ((getComponents()[componentIndex] == null) ||
               (relativeIndex >= getComponents()[componentIndex].size()))) {
        relativeIndex -= (getComponents()[componentIndex] == null) ?
                           0 :
                             getComponents()[componentIndex].size();
        componentIndex++;
      }
      if (componentIndex >= getComponents().length) {
        throw new IndexOutOfBoundsException();
      }
    }

    public int absoluteIndex;

    public int componentIndex = 0;

    public int relativeIndex;

  }

  public final LockableBigSet<? extends _PriorityElement_> get(int index) {
    RelativePosition rp = new RelativePosition(index);
    return getComponents()[rp.componentIndex].get(rp.relativeIndex);
  }

  @Override
  public final int indexOf(Object o) {
    PriorityList<? extends _PriorityElement_>[] components = getComponents();
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

  @Override
  public final int lastIndexOf(Object o) {
    PriorityList<? extends _PriorityElement_>[] components = getComponents();
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

  public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(final int index)
      throws IndexOutOfBoundsException {
    return new AbstractLockedListIterator() {

                  private RelativePosition $rp = new RelativePosition(index);

                  private ListIterator<? extends LockableBigSet<? extends _PriorityElement_>> $componentIterator =
                      getComponents()[$rp.componentIndex].listIterator($rp.relativeIndex);

                  public boolean hasNext() {
                    return ($componentIterator != null) && $componentIterator.hasNext();
                  }

                  public LockableBigSet<? extends _PriorityElement_> next() {
                    PriorityList<? extends _PriorityElement_>[] components = getComponents();
                    LockableBigSet<? extends _PriorityElement_> result = $componentIterator.next();
                    if (! $componentIterator.hasNext()) {
                      do {
                        $rp.componentIndex++;
                      } while (($rp.componentIndex < components.length) &&
                               ((components[$rp.componentIndex] == null) ||
                                  components[$rp.componentIndex].isEmpty()));
                      if ($rp.componentIndex < components.length) {
                        assert components[$rp.componentIndex] != null;
                        assert ! components[$rp.componentIndex].isEmpty();
                        $componentIterator =
                          components[$rp.componentIndex].listIterator();
                      }
                      // else, leave it
                    }
                    $rp.absoluteIndex++;
                    return result;
                  }

                  public boolean hasPrevious() {
                    return ($componentIterator != null) && $componentIterator.hasPrevious();
                  }

                  public LockableBigSet<? extends _PriorityElement_> previous() {
                    PriorityList<? extends _PriorityElement_>[] components = getComponents();
                    LockableBigSet<? extends _PriorityElement_> result = $componentIterator.previous();
                    if (! $componentIterator.hasPrevious()) {
                      do {
                        $rp.componentIndex--;
                      } while (($rp.componentIndex >= 0) &&
                               ((components[$rp.componentIndex] == null) ||
                                  components[$rp.componentIndex].isEmpty()));
                      if ($rp.componentIndex >= 0) {
                        assert components[$rp.componentIndex] != null;
                        assert ! components[$rp.componentIndex].isEmpty();
                        $componentIterator =
                          components[$rp.componentIndex].listIterator(components[$rp.componentIndex].size());
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

}