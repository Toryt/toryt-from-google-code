package org.toryt.hard;


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt.AbstractTypeContract;
import org.toryt.ClassMethodContract;
import org.toryt.InstanceMethodContract;
import org.toryt.InterfaceContract;
import org.toryt.MethodTest;
import org.toryt.ReflectionSupport;
import org.toryt.TorytException;


/**
 * @author Jan Dockx
 */
public class TypeContract
    extends AbstractTypeContract {

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

  
  
  public TypeContract(Class type) {
    super(type);
  }

  public TypeContract(String fqn) throws TorytException {
    super(fqn);

  }

  
  
  public final Set getDirectSuperInterfaceContracts() {
    return Collections.unmodifiableSet($directSuperInterfaceContracts);
  }
  
  /**
   * @pre    ic != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! ic.isClosed();
   */
  public final void addDirectSuperInterfaceContract(InterfaceContract ic) throws TorytException {
    assert ic != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! ic.isClosed()) {
      throw new TorytException(this, null);
    }
    $directSuperInterfaceContracts.add(ic);
  }
  
  /**
   * @pre fqn != null;
   */
  public final void addDirectSuperInterfaceContract(String fqn) throws TorytException {
    assert fqn != null;
//    Class si = Class.forName(fqn with prefix contract; look in specific package);
    
  }
  
  private Set $directSuperInterfaceContracts = new HashSet();
    
  public final Set getInstanceMethodContracts() {
    return Collections.unmodifiableSet($instanceMethodContracts);
  }
  
  /**
   * @pre    imc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! imc.isClosed();
   */
  public final void addInstanceMethodContract(InstanceMethodContract imc) throws TorytException {
    assert imc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! imc.isClosed()) {
      throw new TorytException(this, null);
    }
    $instanceMethodContracts.add(imc);
  }
  
  private Set $instanceMethodContracts = new HashSet();
    
  public final Set getClassMethodContracts() {
    return Collections.unmodifiableSet($classMethodContracts);
  }
  
  /**
   * @pre    cmc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! cmc.isClosed();
   */
  public final void addClassMethodContract(ClassMethodContract cmc) throws TorytException {
    assert cmc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! cmc.isClosed()) {
      throw new TorytException(this, null);
    }
    $classMethodContracts.add(cmc);
  }
  
  private Set $classMethodContracts = new HashSet();

  public final Set getNestedClassContracts() {
    return Collections.unmodifiableSet($nestedClassContracts);
  }
  
  /**
   * @pre    ncc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! ncc.isClosed();
   */
  public final void addNestedClassContract(TypeContract ncc) throws TorytException {
    assert ncc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! ncc.isClosed()) {
      throw new TorytException(this, null);
    }
    $nestedClassContracts.add(ncc);
  }
  
  private Set $nestedClassContracts = new HashSet();

  public final Set getBasicInspectors() {
    return Collections.unmodifiableSet($basicInspectors);
  }
  
  /**
   * @pre    m != null;
   * @throws TorytException
   *         isClosed();
   */
  public final void addBasicInspector(Method m) throws TorytException {
    assert m != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $basicInspectors.add(m);
  }
  
  public final void addBasicInspector(String signature) throws TorytException {
    addBasicInspector(ReflectionSupport.findMethod(getType(), signature, this));
  }
  
  private Set $basicInspectors = new HashSet();
  
  /**
   * {@inheritDoc}
   * The default implementation does nothing.
   */
  public void validateTypeInvariants(Object subject, MethodTest test) {
    // NOP
  }
  
}