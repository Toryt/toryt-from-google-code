package org.toryt;


import java.io.PrintStream;
import java.util.Set;

import org.toryt.support.straightlist.StraightList;


/**
 * The contract of a type. This features type invariants, method contracts
 * for all the methods defined in the type, nested class contracts, and extra tests.
 * Furthermore, there are references to all contracts for all direct supertypes.
 * 
 * @invar getType() != null;
 * @invar getDirectSuperInterfaceContracts() != null;
 * @invar ! getDirectSuperInterfaceContracts().contains(null);
 * @invar (forall Object o; getDirectSuperInterfaceContracts().contains(o);
 *            o instanceof InterfaceContract);
 * @invar (forall InterfaceContract ic; getDirectSuperInterfaceContracts().contains(ic);
 *            getType().superInterfaces contains ic.getType()); MUDO
 * @invar getInstanceMethodContracts() != null;
 * @invar ! getInstanceMethodContracts().contains(null);
 * @invar (forall Object o; getInstanceMethodContracts().contains(o);
 *            o instanceof InstanceMethodContract);
 * @invar (forall MethodContract mc; getInstanceMethodContracts().contains(mc);
 *            mc.getMethod().getDeclaringClass() == getType());
 * @invar getClassMethodContracts() != null;
 * @invar ! getClassMethodContracts().contains(null);
 * @invar (forall Object o; getClassMethodContracts().contains(o);
 *            o instanceof ClassMethodContract);
 * @invar (forall OLDMethodContract mc; getClassMethodContracts().contains(mc);
 *            mc.getMethod().getDeclaringClass() == getType());
 * @invar getNestedClassContracts() != null;
 * @invar ! getNestedClassContracts().contains(null);
 * @invar (forall Object o; getNestedClassContracts().contains(o);
 *            o instanceof ClassContract);
 * @invar (forall ClassContract cc; getNestedClassContracts().contains(cc);
 *            cc.getType().getDeclaringClass() == getType());
 * @invar getBasicInspectors() != null;
 * @invar ! getBasicInspectors().contains(null);
 * @invar (forall Object o; getBasicInspectors().contains(o);
 *            o instanceof Method);
 * @invar (forall Method m; getBasicInspectors().contains(m);
 *            m.getDeclaringClass() == getType());
 * @invar (exists AllMembersCoveredTest amct; getExtraTests().contains(amct);
 *            amct.getTypeContract() == this);
 */
public interface TypeContract extends Contract {

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
  
  /*<property name="type">*/
  //------------------------------------------------------------------
  
  /**
   * @basic
   */
  Class getType();
  
  /*</property>*/
  
  
  Set getDirectSuperInterfaceContracts();

  /**
   * Contracts for all instance (non-static) methods, except
   * basic inspectors.
   * 
   * @basic
   */
  Set getInstanceMethodContracts();

  /**
   * Contracts for all class (static) methods.
   * 
   * @basic
   */
  Set getClassMethodContracts();

  /**
   * Contracts for all nested classes.
   * 
   * @basic
   */
  Set getNestedClassContracts();

  /**
   * @basic
   */
  Set getBasicInspectors();
  
  /**
   * The union of the method tests of all instance method contracts,
   * all class method contracts and all nested classes contracts.
   */
  StraightList getMethodTests() throws TorytException;


  /*<property name="type invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  Set getTypeInvariantConditions();
  
  /*</property>*/  

  
  
  public class AllMembersCoveredTest extends AbstractTest {

    public void test() throws TorytException {
      // MUDO
      // TypeContract.this.getType, get all methods
      // check that instance methods are in the contracts, except basic inspectors
      // same for class methods are in the contracts, except basic inspectors
      // log members for which a contract is missing
      // set successful status
      setRun();
    }
//
//    public final TypeContract getTypeContract() {
//      return TypeContract.this;
//    }
    
    public boolean isSuccessful() {
      // MUDO
      return false;
    }

    public final void report(PrintStream out) {
      out.println((isSuccessful() ? "success" : "FAILURE")
                  + ": "
//                  + TypeContract.this.getType().toString()
                  + " all members covered");
      out.println("-------------------------------------------------------------");
    }
    
  }

}