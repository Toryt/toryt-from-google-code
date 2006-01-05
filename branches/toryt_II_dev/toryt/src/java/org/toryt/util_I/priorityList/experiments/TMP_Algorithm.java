package org.toryt.util_I.priorityList.experiments;


/**
 * @author    Jan Dockx
 * @author    PeopleWare n.v.
 */
public abstract class TMP_Algorithm {

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

  public static void main(String[] args) {
//    doStuffWithProduct();
//    System.out.println("\n\n\n\n"); //$NON-NLS-1$
    doStuffWithMultiProduct();
//    buildLists();
//    buildCartesianProduct();
//    System.out.println("" + _cp.size() //$NON-NLS-1$
//                       + " elements (expected size = " //$NON-NLS-1$
//                       + expectedSize()
//                       + ")");  //$NON-NLS-1$
//    System.out.println("max priority: "); //$NON-NLS-1$
//    for (int i = 0; i < LIST_LENGTHS.length; i++) {
//      System.out.println("    level " //$NON-NLS-1$
//                         + i
//                         + ": " //$NON-NLS-1$
//                         + getMaximumPriority(i));
//    }
//    System.out.println();
//    printCp();
//    System.out.println("Combination candidates 1:"); //$NON-NLS-1$
//    printCombinationCandidates(getCombinationCandidates());
  }
  
//  public static interface Item extends Comparable {
//    
//    public int getPriority();
//    
//    public int getPosition();
//    
//  }
//  
//  public static abstract class AbstractItem implements Item {
//    
//    public final int compareTo(Object o) {
//      if (o == null) {
//        throw new ClassCastException();
//      }
//      return getPriority() - ((Item)o).getPriority();
//    }
//    
//  }
//  
//  public static class SimpleItem extends AbstractItem {
//    
//    public SimpleItem(int position) {
//      $position = position;
//    }
//    
//    public int getPosition() {
//      return $position;
//    }
//    
//    private int $position;
//    
//    public int getPriority() {
//      return getPosition();
//    }
//    
//    public String toString() {
//      return getPriority() + " (" + getPosition() + ")";  //$NON-NLS-1$//$NON-NLS-2$
//    }
//
//  }
//  
//  public static class CompoundItem extends AbstractItem {
//
//    public CompoundItem(Item[] elements) {
//      $elements = new Item[elements.length];
//      System.arraycopy(elements, 0, $elements, 0, elements.length);
//    }
//    
//    private Item[] $elements;
//    
//    public int getPriority() {
//      int result = 0;
//      for (int i = 0; i < $elements.length; i++) {
//        result += $elements[i].getPriority();
//      }
//      return result;
//    }
//    
//    public int getPosition() {
//      // NOT IMPLEMENTED
//      return 0;
//    }
//    
//    public String toString() {
//      String result = getPriority() + " ("; //$NON-NLS-1$
//      for (int i = 0; i < $elements.length; i++) {
//        result = result + $elements[i].getPosition();
//        if (i < $elements.length - 1) {
//          result = result + " "; //$NON-NLS-1$
//        }
//      }
//      return result + ")"; //$NON-NLS-1$
//    }
//    
//  }
//  
//  private static final int[] LIST_LENGTHS = {4, 2, 8, 5};
//  
//  private static int expectedSize() {
//    int result = 1;
//    for (int i = 0; i < LIST_LENGTHS.length; i++) {
//      result *= LIST_LENGTHS[i];
//    }
//    return result;
//  }
//  
//  private static void buildLists() {
//    _lists = new SimpleItem[LIST_LENGTHS.length][];
//    for (int i = 0; i < LIST_LENGTHS.length; i++) {
//      buildList(i);      
//    }
//  }
//  
//  private static void buildList(int i) {
//    _lists[i] = new SimpleItem[LIST_LENGTHS[i]];
//    for (int j = 0; j < LIST_LENGTHS[i]; j++) {
//      _lists[i][j] = new SimpleItem(j);
//    }
//  }
//
//  private static int getMaximumPriority(int level) {
//    int result = 0;
//    for (int i = level; i < LIST_LENGTHS.length; i++) {
//      result += LIST_LENGTHS[i] - 1;
//    }
//    return result;
//  }
//  
//  private static SimpleItem[][] getCombinationCandidates() {
//    SimpleItem[][] result = new SimpleItem[getMaximumPriority(0) + 1][];
//                                // ++, because we need 0 too
//    for (int i = 0; i < result.length; i++) {
//      result[i] = getCombinationCandidates(0, i);
//    }
//    return result;    
//  }
//  
//  private static void printCombinationCandidates(SimpleItem[][] cc) {
//    for (int i = 0; i < cc.length; i++) {
//      System.out.print("candidates for priority " //$NON-NLS-1$
//                       + i
//                       + ": "); //$NON-NLS-1$
//      for (int j = 0; j < cc[i].length; j++) {
//        System.out.print(cc[i][j]);
//        if (j < cc[i].length - 1) {
//          System.out.print(", "); //$NON-NLS-1$
//        }
//      }
//      System.out.println();
//    }
//  }
//  
//  private static SimpleItem[] getCombinationCandidates(int level, int priority) {
//    SimpleItem[] result = new SimpleItem[0];
//    int top = Math.min(priority + 1, _lists[level].length);
//    int minimumNeeded =  Math.max(priority - getMaximumPriority(level + 1), 0);
//    int bottom = Math.min(top, minimumNeeded);
//    return (SimpleItem[])Arrays.asList(_lists[level]).subList(bottom, top).toArray(result);
//  }
//  
//  private static SimpleItem[][] _lists;
//  
//  private static void buildCartesianProduct() {
//    buildCartesionProductDeep(new Item[LIST_LENGTHS.length], 0);
//  }
//  
//  private static void buildCartesionProductDeep(Item[] elementsAcc, int level) {
//    if (level >= elementsAcc.length) { // array is filled
//      CompoundItem cp = new CompoundItem(elementsAcc);
//      PriorityGroup pg = (PriorityGroup)_cp.get(new Integer(cp.getPriority()));
//      if (pg == null) {
//        pg = new PriorityGroup(cp.getPriority());
//        _cp.put(new Integer(pg.getPriority()), pg);
//      }
//      pg.$elements.add(new CompoundItem(elementsAcc));
//      // we are done, go level up
//    }
//    else { // fill in next element and go deep
//      for (int i = 0; i < _lists[level].length; i++) {
//        elementsAcc[level] = _lists[level][i];
//        buildCartesionProductDeep(elementsAcc, level + 1);
//      }
//    }
//  }
//  
//  /**
//   * A collection of CompoundItems with the same priority.
//   * 
//   * @invar (forall CompoundItem cp; $elements.contains(cp); cp.getPriority() == getPriority());
//   */
//  public static class PriorityGroup {
//    
//    public PriorityGroup(int priority) {
//      $priority = priority;
//    }
//    
//    public int getPriority() {
//      return $priority;
//    }
//    
//    private int $priority;
//    
//    public HashSet $elements = new HashSet();
//    
//    public String toString() {
//      return "" //$NON-NLS-1$
//              + getPriority()
//              + "[#" //$NON-NLS-1$
//              + $elements.size()
//              + "]"; //$NON-NLS-1$
////              + ": " //$NON-NLS-1$
////              + $elements.toString();
//    }
//    
//  }
//
//  /**
//   * @invar (forall Object o; _cp.contains(o); o instanceof PriorityGroup);
//   */
//  private static final SortedMap _cp = new TreeMap();
//
//  private static void printCp() {
//    Iterator iter = _cp.values().iterator();
//    while (iter.hasNext()) {
//      System.out.println(iter.next());
//    }
//  }
//
//  
  private static void doStuffWithProduct() {
    doStuffWithProduct(8, 5);
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithProduct(5, 8);
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithProduct(5, 5);
  }

  private static void doStuffWithProduct(int i, int j) {
    ConstantPL list1 = new ConstantPL(i);
    System.out.println("list1:"); //$NON-NLS-1$
    printPl(list1, Integer.MAX_VALUE, -1);
    ConstantPL list2 = new ConstantPL(j);
    System.out.println("list2:"); //$NON-NLS-1$
    printPl(list2, Integer.MAX_VALUE, -1);
    ProductPriorityList x = new ProductPriorityList(list1, list2);
    System.out.println("product:"); //$NON-NLS-1$
    printPl(x, Integer.MAX_VALUE, -1);
    printPl(x, 22, 7);
  }
  
  private static void doStuffWithMultiProduct() {
//    doStuffWithMultiProduct(new int[] {8, 5});
//    System.out.println("\n"); //$NON-NLS-1$
//    doStuffWithMultiProduct(new int[] {5, 8});
//    System.out.println("\n"); //$NON-NLS-1$
//    doStuffWithMultiProduct(new int[] {5, 5});
//  System.out.println("\n"); //$NON-NLS-1$
    doStuffWithMultiProduct(new int[] {3, 3, 2});
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithMultiProduct2(new int[] {3, 3, 2});
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithMultiProduct(new int[] {5, 8, 4});
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithMultiProduct2(new int[] {5, 8, 4});
    System.out.println("\n"); //$NON-NLS-1$
    doStuffWithMultiProduct(new int[] {5, 8, 4, 2, 9});
  }

  private static void doStuffWithMultiProduct(int[] is) {
    ConstantPL[] lists = new ConstantPL[is.length];
    for (int i = 0; i < is.length; i++) {
      lists[i] = new ConstantPL(is[i]);
      System.out.println("list " + i); //$NON-NLS-1$
      printPl(lists[i], Integer.MAX_VALUE, -1);
    }
    MultiProductPriorityList mx = new MultiProductPriorityList(lists);
    System.out.println("product:"); //$NON-NLS-1$
    printPl(mx, Integer.MAX_VALUE, -1);
    printPl(mx, 22, 7);
  }
  
  private static void doStuffWithMultiProduct2(int[] is) {
    ConstantPL[] lists = new ConstantPL[is.length];
    for (int i = 0; i < is.length; i++) {
      lists[i] = new ConstantPL(is[i]);
      System.out.println("list " + i); //$NON-NLS-1$
      printPl(lists[i], Integer.MAX_VALUE, -1);
    }
    MultiProductPriorityList2 mx = new MultiProductPriorityList2(lists);
    System.out.println("product:"); //$NON-NLS-1$
    printPl(mx, Integer.MAX_VALUE, -1);
//    printPl(mx, 22, 7);
  }
  
  private static void printPl(PriorityList pl, int upperLimit, int lowerLimit) {
    System.out.print("#" + pl.bigIntegerSize()); //$NON-NLS-1$
    System.out.print(" {"); //$NON-NLS-1$
    PriorityListIterator i = pl.priorityListIterator();
    int counter = 0;
    int bucketPriority = 0;
    System.out.print("\n<" + bucketPriority + ": ");  //$NON-NLS-1$//$NON-NLS-2$
    while (i.hasNext() && (counter <= upperLimit)) {
      Object item = i.next();
      counter++;
      if (priorityOfItem(item) > bucketPriority) {
        bucketPriority++;
        System.out.print("\n<" + bucketPriority + ">: ");  //$NON-NLS-1$//$NON-NLS-2$
      }
      else if (priorityOfItem(item) < bucketPriority) {
        System.out.print("ERROR");  //$NON-NLS-1$
      }
      printItem(item);
      if (i.hasNext() && (counter <= upperLimit)) {
        System.out.print(", "); //$NON-NLS-1$
      }
      System.out.flush();
    }
    System.out.println("}"); //$NON-NLS-1$
    System.out.println("counted: " + counter); //$NON-NLS-1$
//    System.out.print("R {"); //$NON-NLS-1$
//    while (i.hasPrevious() && (counter >= lowerLimit)) {
//      counter--;
//      printItem(i.previous());
//      if (i.hasPrevious() && (counter >= lowerLimit)) {
//        System.out.print(", "); //$NON-NLS-1$
//      }
//      System.out.flush();
//    }
//    System.out.println("}"); //$NON-NLS-1$
//    System.out.println("counted: " + counter); //$NON-NLS-1$
//    System.out.print("#" + pl.bigIntegerSize()); //$NON-NLS-1$
//    System.out.print(" {"); //$NON-NLS-1$
//    while (i.hasNext()) {
//      counter++;
//      printItem(i.next());
//      if (i.hasNext()) {
//        System.out.print(", "); //$NON-NLS-1$
//      }
//      System.out.flush();
//    }
//    System.out.println("}"); //$NON-NLS-1$
//    System.out.println("counted: " + counter); //$NON-NLS-1$
  }
  
  private static void printItem(Object item) {
    try {
      Object[] array = (Object[])item;
      System.out.print("("); //$NON-NLS-1$
      for (int k = 0; k < array.length; k++) {
        System.out.print(array[k]);
        if (k < array.length - 1) {
          System.out.print(","); //$NON-NLS-1$
        }
      }
      System.out.print(")"); //$NON-NLS-1$
    }
    catch (ClassCastException ccExc) {
      System.out.print(item);
    }
    System.out.print("<" + priorityOfItem(item) + ">"); //$NON-NLS-1$//$NON-NLS-2$
  }

  private static int priorityOfItem(Object item) {
    try {
      Object[] array = (Object[])item;
      int priority = 0;
      for (int k = 0; k < array.length; k++) {
        priority += ((Integer)array[k]).intValue();
      }
      return priority;
    }
    catch (ClassCastException ccExc) {
      return ((Integer)item).intValue();
    }
  }
  
  private static class ConstantPL extends AbstractPriorityList {
    
    public ConstantPL(int size) {
      super(size);
    }

    public Object get(int index) {
      if ((index < 0) || (index >= size())) {
        throw new IndexOutOfBoundsException();
      }
      return new Integer(index);
    }
    
  }


}
