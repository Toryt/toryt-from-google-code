package org.toryt.hard;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.toryt.CaseProvider;
import org.toryt.InstanceMethodContract;
import org.toryt.TorytException;




/**
 * @author Jan Dockx
 */
public abstract class ClassContract
    extends TypeContract implements org.toryt.ClassContract, CaseProvider {

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

  /**
   * @pre ! type.isInterface();
   */
  public ClassContract(Class type) {
    super(type);
    assert ! type.isInterface();
  }

  /**
   * @pre ! type.isInterface();
   */
  public ClassContract(String fqn) throws TorytException {
    super(fqn);
  }

  public final ClassContract getSuperClassContract() {
    return $superClassContract;
  }
  
  /**
   * @pre cc != null;
   * @post new.getSuperClassContract() != null;
   */
  protected final void setSuperClassContract(ClassContract cc) {
    assert cc != null;
    $superClassContract = cc;
  }
  
  /**
   * @pre fqn != null;
   */
  protected final void setSuperClassContract(String fqn) throws TorytException {
    assert fqn != null;
//  Class si = Class.forName(fqn with prefix contract; look in specific package);
  }
  
  
  
  private ClassContract $superClassContract;
  
  public final Set getConstructorContracts() {
    return Collections.unmodifiableSet($constructorContracts);
  }
  
  /**
   * @pre    cc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         imc.isClosed();
   */
  public final void addConstructorContract(ConstructorContract cc) throws TorytException {
    assert cc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (cc.isClosed()) {
      throw new TorytException(this, null);
    }
    $constructorContracts.add(cc);
  }
  
  private Set $constructorContracts = new HashSet();
    
  /**
   * All possible relevant cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  public final List getCasesWithNull() throws TorytException {
    List result = getCases();
    result.add(0, null);
    return result;
  }

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  public abstract List getCases() throws TorytException;

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  public final List getSomeCasesWithNull() throws TorytException {
    List result = getSomeCases();
    result.add(0, null);
    return result;
  }

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  public List getSomeCases() throws TorytException {
    return getCases();
  }

}