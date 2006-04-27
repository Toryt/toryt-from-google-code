package org.toryt_II.contract.hard;


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.AbstractTypeContract;
import org.toryt_II.contract.ClassMethodContract;
import org.toryt_II.contract.InstanceMethodContract;
import org.toryt_II.contract.InterfaceContract;


/**
 * @author Jan Dockx
 */
public class HardTypeContract
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

  
  
  public HardTypeContract(Class type) {
    super(type);
  }

  public HardTypeContract(String fqn) throws OLDTorytException {
    super(fqn);

  }

  
  
  public final Set getDirectSuperInterfaceContracts() {
    return Collections.unmodifiableSet($directSuperInterfaceContracts);
  }
  
  /**
   * @pre    ic != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! ic.isClosed();
   */
  public final void addDirectSuperInterfaceContract(InterfaceContract ic) throws OLDTorytException {
    assert ic != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! ic.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $directSuperInterfaceContracts.add(ic);
  }
  
  /**
   * @pre fqn != null;
   */
  public final void addDirectSuperInterfaceContract(String fqn) throws OLDTorytException {
    assert fqn != null;
//    Class si = Class.forName(fqn with prefix contract; look in specific package);
    
  }
  
  private Set $directSuperInterfaceContracts = new HashSet();
    
  public final Set getInstanceMethodContracts() {
    return Collections.unmodifiableSet($instanceMethodContracts);
  }
  
  /**
   * @pre    imc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! imc.isClosed();
   */
  public final void addInstanceMethodContract(InstanceMethodContract imc) throws OLDTorytException {
    assert imc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! imc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $instanceMethodContracts.add(imc);
  }
  
  private Set $instanceMethodContracts = new HashSet();
    
  public final Set getClassMethodContracts() {
    return Collections.unmodifiableSet($classMethodContracts);
  }
  
  /**
   * @pre    cmc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! cmc.isClosed();
   */
  public final void addClassMethodContract(ClassMethodContract cmc) throws OLDTorytException {
    assert cmc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! cmc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $classMethodContracts.add(cmc);
  }
  
  private Set $classMethodContracts = new HashSet();

  public final Set getNestedClassContracts() {
    return Collections.unmodifiableSet($nestedClassContracts);
  }
  
  /**
   * @pre    ncc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! ncc.isClosed();
   */
  public final void addNestedClassContract(HardTypeContract ncc) throws OLDTorytException {
    assert ncc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! ncc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $nestedClassContracts.add(ncc);
  }
  
  private Set $nestedClassContracts = new HashSet();

  public final Set getBasicInspectors() {
    return Collections.unmodifiableSet($basicInspectors);
  }
  
  /**
   * @pre    m != null;
   * @throws OLDTorytException
   *         isClosed();
   */
  public final void addBasicInspector(Method m) throws OLDTorytException {
    assert m != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $basicInspectors.add(m);
  }
  
  public final void addBasicInspector(String signature) throws OLDTorytException {
    addBasicInspector(Reflection.findMethod(getType(), signature, this));
  }
  
  private Set $basicInspectors = new HashSet();
  
}