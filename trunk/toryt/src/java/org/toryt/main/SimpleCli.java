package org.toryt.main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.toryt.AbstractTest;
import org.toryt.Contract;
import org.toryt.Test;
import org.toryt.TorytException;


/**
 * @author Jan Dockx
 */
public class SimpleCli extends AbstractTest {

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
  


  public SimpleCli(Contract contract) {
    assert contract != null;
    $contract = contract;
  }
  
  /*<property name="failed tests">*/
  //------------------------------------------------------------------
  
  public final List getFailedTests() {
    return Collections.unmodifiableList($failedTests);
  }
  
  private List $failedTests;

  /*</property>*/

  
  public final Contract getContract() {
    return $contract;
  }
  
  private Contract $contract;
  
  public final static int MAXIMUM_NR_FAILURES = 20;
  
  private final boolean hasEnough() {
    return (getFailedTests() != null) && (getFailedTests().size() > MAXIMUM_NR_FAILURES);
  }
  
  public final void test() throws TorytException {
    if (hasRun()) {
      throw new TorytException(null, null);
    }
    else {
      setRun();
    }
    $failedTests = new ArrayList();
    List tests = getContract().getTests();
// DEBUG
System.out.println(tests.size());
// DEBUG
    ListIterator iter = tests.listIterator();
    while (iter.hasNext() && ! hasEnough()) {
      Test t = (Test)iter.next();
      t.test();
      if (! t.isSuccessful()) {
        $failedTests.add(t);
        System.out.println(t);
      }
    }
  }

  public final boolean isSuccessful() {
    return (getFailedTests() != null) && (getFailedTests().isEmpty());
  }

  public static void main(String[] args) throws TorytException {
    System.out.println("Starting tests.");
    System.out.println(new Date());
    System.out.println("\n");
    for (int i = 0; i < args.length; i++) {
      System.out.println("Starting test for \""
                         + args[i]
                         + "\".");
      Class contractClass = instantiateClass(args[i]);
      if (contractClass != null) {
        Contract contractInstance = getContractInstance(contractClass);
        if (contractInstance != null) {
          SimpleCli test = new SimpleCli(contractInstance);
          test.test();
        }
      }
    }
    System.out.println("Tests done.");
  }
  
  private static Class instantiateClass(final String clazz) {
    Class result = null;
    try {
      result = Class.forName(clazz);
    }
    catch (ClassNotFoundException cnfExc) {
      System.out.println("ERROR: class \""
                         + clazz
                         + "\" not found.\n");
    }
    catch (ExceptionInInitializerError eiiErr) {
      System.out.println("ERROR: \"" + clazz
          + "\" threw an exception during "
          + "static initialisation.\n");
      eiiErr.printStackTrace();
      System.out.println();
    }
    catch (LinkageError lErr) {
      System.out.println("ERROR: a linkage error occured "
                         + "when loading class\""
                         + clazz + "\". This is extreme.\n");
      lErr.printStackTrace();
      System.out.println();
    }
    return result;
  }

  private static Contract getContractInstance(final Class contractClass) {
    Contract contract = null;
    try {
      contract = (Contract)contractClass.newInstance();
        // throws ClassCastException if this was not a test class
    }
    catch (ClassCastException ccExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" does not extend"
          + "\""
          + Contract.class.getName()
          + "\".\n");
    }
    catch (IllegalAccessException iaExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" no-args constructor is not public.\n");
    }
    catch (InstantiationException iExc) {
      System.out.println("ERROR: \""
          + contractClass.getName() + "\" cannot be instantiated "
          + "(class is abstract, interface, array, primitive "
          + "type, void, or does not have a no-args "
          + "constructor).\n");
    }
    catch (ExceptionInInitializerError eiiExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" no-args constructor threw an exception:");
      eiiExc.getCause().printStackTrace();
      System.out.println();
    }
    catch (SecurityException sExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" security exception during construction.");
      sExc.getCause().printStackTrace();
      System.out.println();
    }
    return contract;
  }

}