package org.toryt;


import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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

    public AllMembersCoveredTest(TypeContract tc) {
      assert tc != null;
      $tc = tc;
    }

    public final TypeContract getTypeContract() {
      return $tc;
    }

    private TypeContract $tc;

    public final void test() throws TorytException {
      Set allMethodContracts = new HashSet();
      allMethodContracts.addAll($tc.getClassMethodContracts());
      allMethodContracts.addAll($tc.getInstanceMethodContracts());
      Method[] methods = $tc.getType().getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        if (Modifier.isPublic(methods[i].getModifiers())
            && (! $tc.getBasicInspectors().contains(methods[i]))) {
          boolean found = false;
          Iterator iter = allMethodContracts.iterator();
          while ((! found) && iter.hasNext()) {
            MethodContract mc = (MethodContract)iter.next();
            if (methods[i].equals(mc.getMember())) {
              found = true;
            }
          }
          if (! found) {
            $membersWithoutContracts.add(methods[i]);
          }
        }
      }
      Class[] nestedClasses = $tc.getType().getDeclaredClasses();
      for (int i = 0; i < nestedClasses.length; i++) {
        if (Modifier.isPublic(nestedClasses[i].getModifiers())) {
          boolean found = false;
          Iterator iter = $tc.getNestedClassContracts().iterator();
          while ((! found) && iter.hasNext()) {
            TypeContract tc = (TypeContract)iter.next();
            if (nestedClasses[i] == tc.getType()) {
              found = true;
            }
          }
          if (! found) {
            $membersWithoutContracts.add(nestedClasses[i]);
          }
        }
      }
      setRun();
    }

    public final Set getMethodWithoutContracts() {
      return Collections.unmodifiableSet($membersWithoutContracts);
    }

    private Set $membersWithoutContracts = new HashSet();

    public final boolean isSuccessful() {
      return $membersWithoutContracts.isEmpty();
    }

    public final void report(PrintStream out) {
      out.println((isSuccessful() ? "success" : "FAILURE")
                  + ": "
//                  + TypeContract.this.getType().toString()
                  + " all members covered");
      out.println("-------------------------------------------------------------");
      if (! isSuccessful()) {
        out.println("Members missing contract:");
        Iterator iter = $membersWithoutContracts.iterator();
        while (iter.hasNext()) {
          Object member = iter.next();
          out.println("  " + member.toString());
        }
      }
    }

  }

}