package org.toryt;


import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


/**
 * @author Jan Dockx
 */
public abstract class AbstractContract implements Contract {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/
  

  
  public final List getExtraTests() {
    return Collections.unmodifiableList($extraTests);
  }
  
  /**
   * @pre    t != null;
   * @throws TorytException
   *         isClosed();
   */
  public final void addExtraTests(Test t) throws TorytException {
    assert t != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $extraTests.add(t);
  }
  
  private List $extraTests = new ArrayList();

  public final List getTests() throws TorytException {
    return new AbstractSequentialList() {
                  List $l1 = getExtraTests();
                  List $l2 = getMethodTests();

        /* MUDO this implementation is very ad hoc and
         * probably not correct; look for an implementation
         * in jakarta commons collections */

        public ListIterator listIterator(int index) {
          return new ListIterator() {
            
            ListIterator $i1 = $l1.listIterator();
            ListIterator $i2 = $l2.listIterator();
            
            public boolean hasNext() {
              return $i2.hasNext();
            }

            public Object next() {
              if ($i1.hasNext()) {
                return $i1.next();
              }
              else {
                return $i2.next();
              }
            }

            public boolean hasPrevious() {
              return $i1.hasNext();
            }

            public Object previous() {
              if ($i2.hasPrevious()) {
                return $i2.previous();
              }
              else {
                return $i1.previous();
              }
            }

            public int nextIndex() {
              if ($i1.hasNext()) {
                return $i1.nextIndex();
              }
              else {
                return $i2.nextIndex() + $l1.size();
              }
            }

            public int previousIndex() {
              if ($i2.hasPrevious()) {
                return $i2.previousIndex() + $l1.size();
              }
              else {
                return $i1.previousIndex();
              }
            }

            public void remove() {
              if ($i2.hasPrevious()) {
                $i2.remove();
              }
              else {
                $i1.remove();
              }
            }

            public void set(Object o) {
              if ($i2.hasPrevious()) {
                $i2.set(o);
              }
              else {
                $i1.set(o);
              }
            }

            public void add(Object o) {
              if ($i2.hasPrevious()) {
                $i2.add(o);
              }
              else {
                $i1.add(o);
              }
            }
            
          };
        }

        public int size() {
          return $l1.size() + $l2.size();
        }
      };
  }

  public final boolean isClosed() {
    return $closed;
  }
  
  protected final void close() {
    $closed = true;
  }
  
  private boolean $closed;

}