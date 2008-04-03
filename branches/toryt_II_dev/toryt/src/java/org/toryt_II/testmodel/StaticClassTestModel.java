package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Classes.TypeKind;


/**
 * Test model for top level and static nested classes.
 * Apart from the child test models offered by
 * {@link ClassTestModel}, static classes also can have
 * {@link StaticClassTestModel static nested classes}.
 *
 * @author Jan Dockx
 *
 * @invar getTypeKind() == TypeKind.STATIC;
 * @invar staticNestedClassTestModels != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class StaticClassTestModel<_Subject_>  extends ClassTestModel<_Subject_>  {

  public StaticClassTestModel() {
    super(TypeKind.STATIC);
  }

  public final TestModelCollectionDelegate<StaticClassTestModel<?>> staticNestedClassTestModels =
    new TestModelCollectionDelegate<StaticClassTestModel<?>>(this);

  {
    addTestModelCollectionDelegate("static nested classes", staticNestedClassTestModels);
  }

}