package org.toryt.example;


import java.util.Map;

import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.StraightList;


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
  
  public StraightList getCasesMaps() throws TorytException {
    return _C_N.getCasesMaps();
  }
  
  public static _Contract_Node _C_N;
  
  static {
    try {
      _C_N = new _Contract_Node();
    }
    catch (TorytException e) {
      assert false;
    }
  }
  
  public LazyMappingStraightList.Mapping getCaseMapping() {
    return CASE_MAPPING;
  }
  
  public final static LazyMappingStraightList.Mapping CASE_MAPPING
      = new LazyMappingStraightList.Mapping() {
          public Object map(Object o) {
            Map m = (Map)o;
            Node subject = new Group();
            subject.setDescription((String)m.get("description"));
            subject.setTitle((String)m.get("title"));
            subject.setGroup((Group)m.get("group"));
            return subject;
          }
        };

  
}