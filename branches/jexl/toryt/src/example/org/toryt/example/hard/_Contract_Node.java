package org.toryt.example.hard;


import java.util.Map;

import org.toryt.Cases;
import org.toryt.Condition;
import org.toryt.MethodContract;
import org.toryt.MethodTest;
import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.MutatorContract;
import org.toryt.support.straightlist.LazyCombinationStraightList;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.StraightList;


public class _Contract_Node extends ClassContract {

  private static _Contract_Group _C_G;
  
  static {
    try {
      _C_G = new _Contract_Group();
    }
    catch (TorytException e) {
      assert false;
    }
  }

  public _Contract_Node() throws TorytException {
    super(Node.class);

    // basic inspectors
    addBasicInspector("getDescription()");
    addBasicInspector("getTitle()");
    addBasicInspector("getRating()");
    addBasicInspector("getGroup()");
    
    // type invariants
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Node subject = (Node)context.get(MethodContract.SUBJECT_KEY);
        return subject.getTitle() != null;
      }
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Node subject = (Node)context.get(MethodContract.SUBJECT_KEY);
        return subject.getDescription() != null;
      }
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Node subject = (Node)context.get(MethodContract.SUBJECT_KEY);
        return ! Double.isNaN(subject.getRating()) ? (subject.getRating() >= 0) : true;
      }
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Node subject = (Node)context.get(MethodContract.SUBJECT_KEY);
        return ! Double.isNaN(subject.getRating()) ? (subject.getRating() <= 10) : true;
      }
    });
    addTypeInvariantCondition(new Condition() {
      public boolean validate(Map context) {
        Node subject = (Node)context.get(MethodContract.SUBJECT_KEY);
        return (subject.getGroup() != null)
                ? subject.getGroup().getNodes().get(subject.getTitle()) == subject
                : true;
      }
    });
    
    // instance methods
    addInstanceMethodContract(new MutatorContract(this, Node.class, "setDescription(java.lang.String)") {

      public String[] getFormalParameters() {
        return new String[] {"description"};
      }

      {
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Node subject = (Node)context.get(SUBJECT_KEY); 
            String description = (String)context.get("description");
            boolean result = subject.getDescription().equals(description == null ? "" : description);
            return result;
          }});
        close();
      }
      
      public StraightList getTestCases() throws TorytException {
        return new LazyCombinationStraightList(
             new String[] {SUBJECT_KEY, "description"},
             new StraightList[] {getCases(),
                                 Cases.findTestObjectList(String.class)});
      }


    });
    addInstanceMethodContract(new MutatorContract(this, Node.class, "setTitle(java.lang.String)") {

      public String[] getFormalParameters() {
        return new String[] {"title"};
      }
      
      {
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Node subject = (Node)context.get(SUBJECT_KEY); 
            String title = (String)context.get("title");
            return subject.getTitle().equals(title == null ? "" : title);
          }});
        close();
      }

      public StraightList getTestCases() throws TorytException {
        return new LazyCombinationStraightList(
             new String[] {SUBJECT_KEY, "title"},
             new StraightList[] {getCases(),
                                 Cases.findTestObjectList(String.class)});
      }
      
    });
    addInstanceMethodContract(new MutatorContract(this, Node.class, "setGroup(org.toryt.example.Group)") {

      public String[] getFormalParameters() {
        return new String[] {"group"};
      }
      
      {
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Node subject = (Node)context.get(SUBJECT_KEY); 
            Group group = (Group)context.get("group");
            return subject.getGroup() == group;
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Node subject = (Node)context.get(SUBJECT_KEY); 
            Group oldGroup = (Group)context.get("getGroup()"); 
            Group oldGroupATpost = (Group)context.get("getGroup()@post"); 
            return oldGroup != null ? ! oldGroupATpost.getNodes().values().contains(subject) : true;
          }});
        addPostcondition(new Condition() {
          public boolean validate(Map context) {
            Node subject = (Node)context.get(SUBJECT_KEY); 
            Group group = (Group)context.get("group");
            Group groupATpost = (Group)context.get("group@post");
            return group != null ? groupATpost.getNodes().values().contains(subject) : true;
          }});
        close();
      }

      public StraightList getTestCases() throws TorytException {
        return new LazyCombinationStraightList(
              new String[] {SUBJECT_KEY, "group"},
              new StraightList[] {getCases(),
                                  _C_G.getCasesWithNull()});
      }
      
      public void recordState(MethodTest test) {
        Map state = test.getContext();
        Node subject = (Node)state.get(SUBJECT_KEY);
        state.put("group@post", state.get("group"));
        state.put("getGroup()", subject.getGroup());
        state.put("getGroup()@post", subject.getGroup());
      }
      
    });

    close();
  }
    
  public StraightList getCasesMaps() throws TorytException {
    return new LazyCombinationStraightList(
                new String[] {"description", "title", "group"},
                new StraightList[] {Cases.findTestObjectList(String.class),
                                    Cases.findTestObjectList(String.class),
                                    _C_G.getSomeCasesWithNull()});
  }

  public LazyMappingStraightList.Mapping getCaseMapping() {
    return CASE_MAPPING;
  }
  
  public final static LazyMappingStraightList.Mapping CASE_MAPPING
      = new LazyMappingStraightList.AllValidMapping() {
          public Object map(Object o) {
            Map m = (Map)o;
            Node subject = new NodeStub();
            subject.setDescription((String)m.get("description"));
            subject.setTitle((String)m.get("title"));
            subject.setGroup((Group)m.get("group"));
            return subject;
          }
        };
  

  public static class NodeStub extends Node {

    protected int getTotalOfRatings() {
      return 0;
    }

    protected int getNumberOfBookmarks() {
      return 0;
    }
    
  }

}