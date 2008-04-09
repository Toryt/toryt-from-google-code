package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Classes;


/**
 * Test model for top level and static nested classes.
 * Apart from the child test models offered by
 * {@link ClassTestModel}, static classes also can have
 * {@link StaticClassTestModel static nested classes}.
 *
 * @author Jan Dockx
 *
 * @invar ! Classes.isInnerClass(subject);
 * @invar staticNestedClassTestModels != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class StaticClassTestModel<_Subject_> extends ClassTestModel<_Subject_>  {

//  public StaticClassTestModel() {
//    super(TypeKind.STATIC);
//  }

  @Override
  public void setSubject(Class<_Subject_> subject) {
    assert ! Classes.isInnerClass(subject);
    super.setSubject(subject);
  }


  public final TestModelCollectionDelegate<StaticClassTestModel<?>> staticNestedClassTestModels =
    new TestModelCollectionDelegate<StaticClassTestModel<?>>(this);

  {
    addTestModelCollectionDelegate("static nested classes", staticNestedClassTestModels);
  }

}