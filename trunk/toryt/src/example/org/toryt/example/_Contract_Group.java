package org.toryt.example;


import java.util.List;

import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;


public class _Contract_Group extends ClassContract {

  public _Contract_Group() throws TorytException {
    super(Group.class);
    setSuperClassContract("org.toryt.example.Node");
//    addClassMethodContract(new ConstructorContract(Group.class, "Group()") {
//      
//                                });
//    addClassMethodContract(new ConstructorContract(Group.class, "Group(java.lang.String, java.lang.String, org.toryt.example.Group)") {
//      
//                                });
    addBasicInspector("getNodes()");
    close();
  }
  
  public List getCases() throws TorytException {
    return getCases(new GroupFactory());
  }

  public static List getCases(GroupFactory gf) throws TorytException {
    return _Contract_Node.getCases(gf);
  }
  
  public static class GroupFactory extends _Contract_Node.NodeFactory {
    
    public Node createNode() {
      return new Group();
    }
    
  }
  
  
}