package org.toryt.example.jexl;


import java.util.Iterator;
import java.util.Map;

import org.toryt.Cases;
import org.toryt.Condition;
import org.toryt.MethodContract;
import org.toryt.TorytException;
import org.toryt.contract.Collections;
import org.toryt.contract.Strings;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;
import org.toryt.jexl.JexlCondition;
import org.toryt.support.straightlist.ArrayStraightList;
import org.toryt.support.straightlist.EmptyStraightList;
import org.toryt.support.straightlist.LazyCombinationStraightList;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.StraightList;


public class _Contract_Group extends ClassContract {

  public _Contract_Group() throws TorytException {
    super(Group.class);
    
    // basic inspectors
    addBasicInspector("getNodes()");

    // type invariants
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        return subject.getNodes() != null;
      }      
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        return Collections.noNull(subject.getNodes().values());
      }      
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        return Collections.noNull(subject.getNodes().keySet());
      }      
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        return Collections.instanceOf(subject.getNodes().values(), Node.class);
      }      
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        return Collections.instanceOf(subject.getNodes().keySet(), String.class);
      }      
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Group subject = (Group)context.get(MethodContract.SUBJECT_KEY);
        Iterator iter = subject.getNodes().values().iterator();
        while (iter.hasNext()) {
          Node n = (Node)iter.next();
          if (n.getGroup() != subject) {
            return false;
          }
        }
        return true;
      }      
    });
    
    // constructors
    addConstructorContract(new ConstructorContract(this, Group.class, "Group()") {

      public StraightList getTestCases() throws TorytException {
        return EmptyStraightList.INSTANCE;
      }

      {
        addPostcondition(new JexlCondition("this.getDescription().equals(EMPTY);"));
        addPostcondition(new JexlCondition("this.getTitle().equals(EMPTY);"));
        addPostcondition(new JexlCondition("java.lang.Double.isNaN(this.getRating() == 0);"));
        addPostcondition(new JexlCondition("this.getGroup() == null;"));
        addPostcondition(new JexlCondition("this.getNodes().isEmpty();"));
        close();
      }

    });
    addConstructorContract(new ConstructorContract(this, Group.class, "Group(java.lang.String,java.lang.String,org.toryt.example.jexl.Group)") {

      public String[] getFormalParameters() {
        return new String[] {"title", "description", "parent"};
      }
      
      public StraightList getTestCases() throws TorytException {
        return new LazyCombinationStraightList(
           new String[] {"title", "description", "parent"},
           new StraightList[] {Cases.findTestObjectList(String.class),
                               Cases.findTestObjectList(String.class),
                               getSomeCasesWithNull()});
      }

      {
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            String description = (String)context.get("description");
            return subject.getDescription()
                .equals(description == null ? Strings.EMPTY : description);
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            String title = (String)context.get("title");
            return subject.getTitle().equals(title == null ? Strings.EMPTY : title);
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return Double.isNaN(subject.getRating());
          }
        });
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            Group parent = (Group)context.get("parent");
            return subject.getGroup() == parent;
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return subject.getNodes().isEmpty();
          }});
        close();
      }

    });
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
      assert false : "TorytExceptionshould not happen: " + e;
    }
  }
  
  public LazyMappingStraightList.Mapping getCaseMapping() {
    return CASE_MAPPING;
  }
  
  public final static LazyMappingStraightList.Mapping CASE_MAPPING
      = new LazyMappingStraightList.AllValidMapping() {
          public Object map(Object o) {
            Map m = (Map)o;
            Node subject = new Group();
            subject.setDescription((String)m.get("description"));
            subject.setTitle((String)m.get("title"));
            subject.setGroup((Group)m.get("group"));
            return subject;
          }
        };

  public StraightList getSomeCases() throws TorytException {
    ArrayStraightList groups // this must become a factory, since now we share the groups
    = new ArrayStraightList(new Group[] {new Group(),
                                         new Group("title","description",null),
                                         new Group("title","description",new Group())});
    return groups;
  }

}