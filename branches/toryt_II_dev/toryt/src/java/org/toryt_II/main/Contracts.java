package org.toryt_II.main;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.toryt.util_I.reflect.CouldNotInstantiateBeanException;
import org.toryt.util_I.reflect.CouldNotLoadClassException;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.ClassContract;
import org.toryt_II.contract.PackageContract;
import org.toryt_II.contract.ProjectContract;
import org.toryt_II.contract.TypeContract;
import org.toryt_II.contract.hard.HardPackageContract;


/**
 * This class offers a number of static methods that will retrieve specific
 * contracts through reflection, based on naming conventions.
 *
 * @author Jan Dockx
 */
public class Contracts {

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



  private Contracts() {
    // NOP
  }



  public static final Map CONTRACT_CACHE = new HashMap();



  /**
   * {@value}
   */
  public static final String TYPE_CONTRACT_NAME_PREFIX = "_Contract_";

  /**
   * Return an instance of the class that implements the contract of
   * <code>type</code>. This class should be in the same package
   * as <code>type</code>, and have the same name as <code>type</code>,
   * except that is has a prefix {@link #TYPE_CONTRACT_NAME_PREFIX}.
   * This is implemented as a singleton-like pattern, in that the same instance
   * will be returned each time this method is run with the same argument, in the same
   * VM.
   *
   * @pre type != null;
   * @result result != null;
   * @result result.getClass().getName()
   *            .equals(Beans.prefixedFqcn(TYPE_CONTRACT_NAME_PREFIX, type.getName());
   *
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws CouldNotInstantiateBeanException
   */
  public static TypeContract typeContractInstance(Class type)
      throws CouldNotInstantiateBeanException {
    assert type != null;
    TypeContract tc = (TypeContract)CONTRACT_CACHE.get(type);
    if (tc == null) {
      ClassLoader cl = type.getClassLoader();
      String cn = type.getName();
      tc = (TypeContract)Reflection.instantiatePrefixed(cl, TYPE_CONTRACT_NAME_PREFIX, cn);
      CONTRACT_CACHE.put(type, tc);
    }
    return tc;
  }

  /**
   * Return an instance of the class that implements the contract of
   * the class with <code>typeName</code>. <code>null</code> is returned if this type is
   * an interface, and not a class. The contract class should be in the same package
   * as <code>type</code>, and have the same name as <code>type</code>,
   * except that is has a prefix {@link #TYPE_CONTRACT_NAME_PREFIX}.
   * This is implemented as a singleton-like pattern, in that the same instance
   * will be returned each time this method is run with the same argument, in the same
   * VM.
   *
   * @pre typeName != null;
   * @result Class.forName(typeName).isInterface()
   *          ? null
   *          : typeContractInstance(Class.forName(typeName));
   *
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws CouldNotInstantiateBeanException
   * @throws CouldNotLoadClassException
   */
  public static ClassContract classContractInstance(String className)
      throws CouldNotInstantiateBeanException, CouldNotLoadClassException {
    assert className != null;
    Class clazz = Reflection.loadForName(className);
    return classContractInstance(clazz);
  }

  /**
   * Return an instance of the class that implements the contract of
   * the class <code>clazz</code>. <code>null</code> is returned if this type is
   * an interface, and not a class. The contract class should be in the same package
   * as <code>clazz</code>, and have the same name as <code>clazz</code>,
   * except that is has a prefix {@link #TYPE_CONTRACT_NAME_PREFIX}.
   * This is implemented as a singleton-like pattern, in that the same instance
   * will be returned each time this method is run with the same argument, in the same
   * VM.
   *
   * @pre clazz != null;
   * @result clazz.isInterface()
   *          ? null
   *          : typeContractInstance(clazz);
   *
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws CouldNotInstantiateBeanException
   */
  public static ClassContract classContractInstance(Class clazz)
      throws CouldNotInstantiateBeanException {
    assert clazz != null;
    if (clazz.isInterface()) {
      return null;
    }
    else {
      return (ClassContract)typeContractInstance(clazz);
    }
  }



//  /**
//   * {@value}
//   */
//  public static final String PACKAGE_CONTRACT_CLASS_NAME = "_Package_Contract_";

  /**
   * Return an instance of the class that implements the
   * {@link HardPackageContract contract of the package} with name <code>packageName</code>.
   * First, we look in the {@link #CONTRACT_CACHE}. If we cannot find it there,
   * we will create a {@link HardPackageContract} dynamically, by looking in the sourcePath,
   * and adding contracts for all subpackages and types in the package.
   *
   * @pre packageName != null;
   * @pre sourcePath != null;
   * @pre sourcePath.isDirectory();
   * @result result != null;
   * @result result.getClass().getName()
   *            .equals(packageName + "." + PACKAGE_CONTRACT_CLASS_NAME);
   */
  public static PackageContract packageContractInstance(String packageName,
                                                        File sourcePath) {
    assert packageName != null;
    PackageContract pc = (PackageContract)CONTRACT_CACHE.get(packageName);
    if (pc == null) {
//    pc = (HardPackageContract)java.beans.Beans.instantiate(Contracts.class.getClassLoader(),
//    packageName + "." + PACKAGE_CONTRACT_CLASS_NAME);
      org.toryt_II.contract.hard.HardPackageContract hPc
          = new org.toryt_II.contract.hard.HardPackageContract(Package.getPackage(packageName));
      assert hPc != null : "Package " + packageName + " could not be loaded.";
      String relativePackageDirName = packageName.replace('.', File.separatorChar);
      File pDir = new File(sourcePath, relativePackageDirName);
      File[] pDirContents
          = pDir.listFiles(new FileFilter() {
                public boolean accept(File el) {
                  return (el.isDirectory() && (! el.getName().equals("CVS")))
                          || (el.getName().endsWith(".java")
                              && (! el.getName().startsWith(TYPE_CONTRACT_NAME_PREFIX))
                              && (! el.getName().startsWith(PROJECT_CONTRACT_CLASS_NAME)));
                  }
                });
      assert pDirContents != null : "sourcepath is wrong";
      for (int i = 0; i < pDirContents.length; i++) {
        if (pDirContents[i].isDirectory()) {
          String subPackageName = packageName + "." + pDirContents[i].getName();
          try {
            hPc.addSubPackageContract(packageContractInstance(subPackageName, sourcePath));
          }
          catch (OLDTorytException e) {
            assert false : "could not be closed";
          }
        }
        else if (pDirContents[i].isFile()) {
          String fileName = pDirContents[i].getName();
          // remove .java suffix, which is 5 characters
          String className = fileName.substring(0, fileName.length() - 5);
          String fqcn = packageName + "." + className;
          try {
            hPc.addClassContract(classContractInstance(fqcn));
          }
          catch (CouldNotInstantiateBeanException e) {
            // MUDO log this as a test failure or a warning or something
            System.out.println("No contract class found for class " + fqcn);
          }
          catch (CouldNotLoadClassException e) {
            // MUDO log this as a test failure or a warning or something
            System.out.println("No contract class found for class " + fqcn);
          }
          catch (OLDTorytException e) {
            // TODO Auto-generated catch block
            assert false : "OLDTorytExceptionshould not happen: " + e;
          }
        }
      }
      hPc.close();
      pc = hPc;
      CONTRACT_CACHE.put(packageName, pc);
    }
    return pc;
  }



  /**
   * {@value}
   */
  public static final String PROJECT_CONTRACT_CLASS_NAME = "_Project_Contract_";

  /**
   * Return an instance of the class that implements the contract of
   * a project with name <code>projectName</code>. This class should be
   * in the package with name <code>packageName</code>, and have the simple
   * name {@link #PROJECT_CONTRACT_CLASS_NAME}.
   *
   * @pre packageName != null;
   * @result result != null;
   * @result result.getClass().getName()
   *            .equals(packageName + "." + PROJECT_CONTRACT_CLASS_NAME);
   *
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public static ProjectContract projectContractInstance(String projectName,
                                                        String packageName)
      throws IOException, ClassNotFoundException {
    assert packageName != null;
    ProjectContract pc = (ProjectContract)CONTRACT_CACHE.get(projectName);
    if (pc == null) {
      pc = (ProjectContract)java.beans.Beans.instantiate(Contracts.class.getClassLoader(),
                                        packageName + "." + PROJECT_CONTRACT_CLASS_NAME);
      CONTRACT_CACHE.put(projectName, pc);
    }
    return pc;
  }

//  public static void main(String[] args) throws IOException, ClassNotFoundException {
//    Contract c = typeContractInstance(Node.class);
//    System.out.println(c);
//    c = projectContractInstance("Toryt", "org.toryt_II.example");
//    System.out.println(c);
//    c = packageContractInstance("org.toryt_II.example",
//                                new File("/Users/jand/Documents/eclipse/workspace/toryt/src/example"));
//    ((HardPackageContract)c).report(System.out, 0);
//  }

}