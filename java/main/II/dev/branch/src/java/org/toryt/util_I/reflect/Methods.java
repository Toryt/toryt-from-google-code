/*<license>
Copyright 2006 - $Date$ by Jan Dockx.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt.util_I.reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Utility methods for reflection. Use these methods if you are
 *   interested in the result of reflection, and not in a particular
 *   reason why some reflective inspection might have failed.</p>
 *
 * @idea (jand) most methods are also in ppw-bean; consolidate
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Methods {

  /**
   * @deprecated use ppwcode util reflection
   */
  private Methods() {
    // NOP
  }

  /**
   * @pre method != null;
   * @result ((result == CLASS_MUTATOR) || (result == CLASS_INSPECTOR)) ??
   *            Modifier.isStatic(method.getModifiers());
   * @result ((result == INSTANCE_MUTATOR) || (result == INSTANCE_INSPECTOR)) ??
   *            (! Modifier.isStatic(method.getModifiers()));
   * @result ((result == CLASS_MUTATOR) || (result == INSTANCE_MUTATOR)) ??
   *            method.getReturnType().equals(Void.TYPE);
   * @result ((result == CLASS_INSPECTOR) || (result == INSTANCE_INSPECTOR)) ??
   *            (! method.getReturnType().equals(Void.TYPE));
   *
   * @todo move to ppwcode util reflection
   */
  public static MethodKind methodKind(Method method) {
    assert method != null;
    if (Modifier.isStatic(method.getModifiers())) { // class
      if (method.getReturnType().equals(Void.TYPE)) { // mutator
        return MethodKind.CLASS_MUTATOR;
      }
      else { // inspector
        return MethodKind.CLASS_INSPECTOR;
      }
    }
    else { // instance
      if (method.getReturnType().equals(Void.TYPE)) { // mutator
        return MethodKind.INSTANCE_MUTATOR;
      }
      else { // inspector
        return MethodKind.INSTANCE_INSPECTOR;
      }
    }
  }

  /**
   * <p>Return the method of class {@code type} with signature {@code signature}.
   *   If something goes wrong, throw a {@link CannotGetMethodException}. In contrast
   *   to {@link Class#getDeclaredMethod(String, Class...)}, {@code findMethod}
   *   throws a simple exception when anything goes wrong (with more detailed reasons as
   *   the {@link Throwable#getCause() cause}). {@code findMethod} returns any method
   *   (not only {@code public} methods as {@link Class#getMethod(String, Class...)}
   *   does), but only methods declared exactly in {@code type}, like
   *   {@link Class#getDeclaredMethod(String, Class...)}, and unlike
   *   {@link Class#getMethod(String, Class...)}.</p>
   *
   * @param type
   *        The class to look for the method in.
   * @param signature
   *        The signature of the method to look for. This is the name of the
   *        method, with the FQCN of the arguments in parenthesis appended, comma
   *        separated. For classes of the package {@code java.lang}, the short class
   *        name may be used.
   *        The return type is not a part of the signature, nor are potential
   *        exception types the method can throw.
   *        Example: {@code "findMethod(java.lang.Class,java.lang.String)"},
   *        which is equivalent to {@code "findMethod ( Class, String )"}.
   * @result result != null
   * @result result.getDeclaringClass() == type;
   * @result new MethodSignature(signature).getMethodName().equals(result.getName());
   * @result Arrays.deepEquals(result.getParameterTypes(), new MethodSignature(signature).getParameterTypes());
   * @throws CannotGetMethodException
   *         type == null;
   * @throws CannotGetMethodException
   *         signature == null;
   * @throws CannotGetMethodException
   *         new MethodSignature(signature) throws CannotParseSignatureException;
   * @throws CannotGetMethodException
   *         type.getDeclaredMethod(new MethodSignature(signature).getMethodName(),
   *                                new MethodSignature(signature).getParameterTypes())
   *           throws SecurityException;
   * @throws CannotGetMethodException
   *         type.getDeclaredMethod(new MethodSignature(signature).getMethodName(),
   *                                new MethodSignature(signature).getParameterTypes())
   *           throws NoSuchMethodException);
   *
   * @deprecated use ppwcode util reflection
   */
  public static Method findMethod(Class<?> type, String signature) throws CannotGetMethodException {
    if (signature == null) {
      throw new CannotGetMethodException(type, signature,
                                         new NullPointerException("signature was null"));
    }
    try {
      MethodSignature sig = new MethodSignature(signature);
      return type.getDeclaredMethod(sig.getMethodName(), sig.getParameterTypes());
    }
    catch (NullPointerException npExc) {
      throw new CannotGetMethodException(type, signature, npExc);
    }
    catch (CannotParseSignatureException cpsExc) {
      throw new CannotGetMethodException(type, signature, cpsExc);
    }
    catch (SecurityException sExc) {
      throw new CannotGetMethodException(type, signature, sExc);
    }
    catch (NoSuchMethodException nsmExc) {
      throw new CannotGetMethodException(type, signature, nsmExc);
    }
  }

  /**
   * @deprecated use ppwcode util reflection
   */
  public static <_T_> Constructor<_T_> findConstructor(Class<_T_> type, String signature)
      throws CannotGetMethodException {
    try {
      @SuppressWarnings("unchecked") Constructor<_T_>[] constructors = type.getConstructors();
      // unchecked cast because Class.getConstructors return Constructor[] instead of Constructor<T>[]
      for (int i = 0; i < constructors.length; i++) {
        if (constructors[i].toString().indexOf(signature) > -1) {
          return constructors[i];
        }
      }
      throw new CannotGetMethodException(type, signature, null);
    }
    catch (NullPointerException npExc) {
      throw new CannotGetMethodException(type, signature, npExc);
    }
  }

}
