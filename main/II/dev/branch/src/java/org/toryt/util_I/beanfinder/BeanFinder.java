/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

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

package org.toryt.util_I.beanfinder;


import java.lang.reflect.Type;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Instances of this type find (or create) an instance of
 *   {@link #getBeanType()} for a given argument. Implementations
 *   should not log above the debug level.</p>
 *
 * <h3>Type Safety</h3>
 * <p>In an early version of this class, this class had a second generic
 *   parameter {@code _Bean_}, used as the return type for the method
 *   {@link #findFor(Object)}. {@link #getBeanType()} then
 *   had an argument {@code getBeanType(_Argument_ argument)}.
 *   Yet, implementations sadly cannot guarantee
 *   type safety in general. Therefor, this version was discarded, and
 *   the method now has the non-generic return type {@link Object},
 *   and a classic type-defining method {@link #getBeanType()}.
 *   The method {@link #findFor(Object)} only needs to guarantee
 *   erased type safety.</p>
 * <p>The intention of the find-method is
 *   to either return a correct bean, or throw a {@link NoBeanFoundException}
 *   if no bean could be found for the given argument, or throw
 *   a {@link BeanFinderConfigurationException} if anything goes wrong
 *   while retrieving a bean for the given argument. In particular,
 *   many implementations will use bean instantiation of a class whose name is
 *   defined in some way by the argument (aka &quot;discovery&quot; of an
 *   implementation of the service defined by the argument). The class
 *   to instantiate should obviously be a subtype of {@code _Bean_},
 *   but often has more requirements, based on the actual {@code argument}
 *   instance, beyond merely the {@code _Argument_} type. If the class
 *   whose name is defined by the argument does not uphold these requirements,
 *   we consider this an error in configuration on the users side,
 *   and simply want to throw a general {@link BeanFinderConfigurationException}.
 *   In particular, we want to avoid the need for the caller of this method
 *   to deal with {@link ClassCastException ClassCastExceptions}.
 *   <strong>Sadly, we cannot count on the generics mechanism to do this for
 *   us.</strong></p>
 * <p>First of all, since the resulting instance will often be created using
 *   reflection, generics cannot be used, since this is only a <em>compile
 *   time</em> mechanism. We need to use RTTI to check the type.</p>
 * <p>Second, because of erasure, a cast to {@code _Bean_} will happen
 *   after the method ends, just before control is returned to the calling method.
 *   If the returned instance is not of type {@code _Bean_}, it is the caller
 *   that will get a {@link ClassCastException}; there is no way we can catch
 *   this exception and wrap it in a {@link BeanFinderConfigurationException}
 *   for her. To avoid this, we need to check the type of the instance ourselfs
 *   before returning the instance (and throw the {@link BeanFinderConfigurationException}
 *   if the type is not {@code _Bean_}. Sadly, we cannot write
 *   {@code if (result instanceof _Bean_) ...}: we cannot use
 *   generic parameters like that. We need a {@link Class} object for that,
 *   so we can use {@code if (beanClass.isInstance(result)) ...}. This
 *   can be solved by providing such a {@link Class} property (see
 *   {@code getBeanType(_Argument_ argument)}).</p>
 * <p>Third, however, the type {@code _Bean_} is often generic, but with
 *   actual generic (possibly bound) parameters. We cannot check whether the
 *   returned instance upholds this type constraint, because we are working
 *   at runtime, and the necessary information is not available due to erasure.
 *   On the one hand, we cannot get the full type information from the resulting
 *   object: using {@link Object#getClass()} we can get the raw type, and from there
 *   we can get the possible formal generic parameters, but not the actual
 *   generic parameters. There is no way to retrieve the complete type information from
 *   an instance, so there is nothing to check. But if we could get this information,
 *   it would still be impossible (or very difficult) to validate, since
 *   also the {@code getBeanType(_Argument_ argument)} is erased. It would require a much
 *   more complex data structure to express what we actually want to return than
 *   a simple {@link Class}. Also, a {@link Type} cannot help, since we have
 *   no way to create generic {@link Type} instances.</p>
 * <p>We must say that we can only guarantee non-generic type
 *   safety, in the traditional pre-generics way. Even when the return type
 *   of {@link #findFor(Object)} is a generic type, this would not protect
 *   against deeper generic type problems (e.g., {@code _Bean_} would be
 *   {@code Map<Female, List<? extends Person>>}, and we would return an instance
 *   of {@code Map<Beverage, Integer>}). Some user code, maybe many steps
 *   away, would get a {@code ClassCastException}.</p>
 * <p>This problem is not limited to this particular case. This problem will
 *   occur often when we use reflection, and certainly when we use
 *   {@link Class#forName(String)}.</p>
 * <h4>Contract paradigm</h4>
 * <p>This is clear when we look at it from the contract point of view.
 *   {@code _Bean_ findFor(_Argument_ argument)} is a contract that says
 *   that the implementor guarantees that he will return an instance of type
 *   {@code _Bean_}. Normally, we don't care for this postcondition, since the
 *   compiler takes care of this for us. When we use reflect, e.g.,
 *   {@link Class#forName(String)} however, we need at some time to add
 *   the compiler annotation to disregard type information warnings. This means
 *   that the responsibility for this part of the contract returns to the
 *   implementor: the compiler has told us with the warning that it cannot
 *   guarantee type safety. Since {@link Class#forName(String)} is typically
 *   used in situations where the implementor of this method has no control
 *   over that particular class (that class might be written on another continent,
 *   years later), the implementor should check for the postcondition to be
 *   upheld at runtime, and we have seen above that this is not possible, due
 *   to erasure. So, this method cannot be implemented in a meaningful way
 *   in a number of interesting cases. When a particular implementation
 *   requires the use of {@link Class#forName(String)}, a correct implementation
 *   is impossible, since the implementer cannot guarantee that the contract
 *   is upheld in all cases. In fact, doing such an implementation would
 *   lul the user of the method into a false sense of security: the generic
 *   return type gives the impression of very precise and tight type safety,
 *   while in fact a {@link ClassCastException} might happen at any time,
 *   in remote code.</p>
 * <p>A solution might be to implement strong preconditions, but that is difficult
 *   since the strongest preconditions would only apply in specific subtypes
 *   (and preconditions cannot be strengthened). Furthermore, since the name
 *   of the class to load and create an instance would be given often by some
 *   sort of configuration, by the end user, and not by a user-programmer,
 *   the precondition propagates to the end user. This would be highly difficult
 *   to debug in case of error.</p>
 * <h4>Conclusion</h4>
 * <p>The {@link ClassCastException ClassCastExceptions} in user code may
 *   happen as a result from configuration error, and cannot be avoided. This is
 *   a clear lack in encapsulation in this class, but there is no alternative.
 *   It is then better to make the user aware of this problem, then to pose
 *   as if there would be tight type safety. Therefor, the final definition
 *   of this class does not have a generic return type for {@link #findFor(Object)}.</p>
 *
 * @author Jan Dockx
 *
 * @invar getBeanType() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface BeanFinder<_Argument_> {

  /**
   * Return an instance of {@link #getBeanType()} for {@code argument}.
   * If this finder is not capable of doing so, it should throw a
   * {@link NoBeanFoundException}. This method never returns {@code null}.
   *
   * @pre    argument != null;
   * @result result != null;
   * @result getBeanType().isInstance(result);
   * @throws NoBeanFoundException
   *         ; Could not return a a bean of type {@link #getBeanType()} for
   *         {@code argument}.
   * @throws BeanFinderConfigurationException
   *         ; We found a bean with this strategy alright, but it did not fullfil
   *         the requirements. So the user provided a bean to be found, that
   *         did not fullfil the requirements: this is an error in configuration
   *         on the users side.
   */
  Object findFor(_Argument_ argument)
      throws BeanFinderConfigurationException, NoBeanFoundException;

  /**
   * Sadly, to be able to validate whether the result of {@link #findFor(Object)}
   * is of the expected type, we need to be able to check the type
   * via RTTI, since we cannot perform an instanceof check against a type parameter,
   * since generic type information will be erased at runtime.
   *
   * @basic
   */
  Class<?> getBeanType();

}