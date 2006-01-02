package org.toryt.example;


import java.util.Iterator;
import java.util.Map;

import org.toryt.patterns_I.Collections;
import org.toryt.patterns_I.Strings;
import org.toryt.support.straightlist.ArrayStraightList;
import org.toryt.support.straightlist.EmptyStraightList;
import org.toryt.support.straightlist.LazyCombinationStraightList;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.StraightList;
import org.toryt_II.TorytException;
import org.toryt_II.cases.Cases;
import org.toryt_II.contract.MethodContract;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.hard.HardClassContract;
import org.toryt_II.contract.hard.HardConstructorContract;


public class _Contract_Group extends HardClassContract {

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
    addConstructorContract(new HardConstructorContract(this, Group.class, "Group()") {

      public StraightList getTestCases() throws TorytException {
        return EmptyStraightList.INSTANCE;
      }

      {
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return subject.getDescription().equals(Strings.EMPTY);
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return subject.getTitle().equals(Strings.EMPTY);
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return Double.isNaN(subject.getRating());
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return subject.getGroup() == null;
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Group subject = (Group)context.get(SUBJECT_KEY);
            return subject.getNodes().isEmpty();
          }});
        close();
      }

    });
    addConstructorContract(new HardConstructorContract(this, Group.class, "Group(java.lang.String,java.lang.String,org.toryt_II.example.Group)") {

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
      assert false;
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