package org.toryt.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.toryt.Cases;
import org.toryt.Condition;
import org.toryt.MethodContract;
import org.toryt.MethodTest;
import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.MutatorContract;


public class _Contract_Node extends ClassContract {

  public _Contract_Node() throws TorytException {
    super(Node.class);
    setSuperClassContract("java.lang.Object");
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
      }
      
      public List getTestCases() throws TorytException {
        List result = new ArrayList();
        Iterator subjects = getCases().iterator();
        while (subjects.hasNext()) {
          Node subject = (Node)subjects.next();
          Iterator descriptions = Cases.findTestObjectList(String.class).iterator();
          while (descriptions.hasNext()) {
            String description = (String)descriptions.next();
            Map testCase = new HashMap();
            testCase.put(SUBJECT_KEY, subject);
            testCase.put("description", description);
            result.add(testCase);
          }
        }
        return result;
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
      }

      public List getTestCases() throws TorytException {
        List result = new ArrayList();
        Iterator subjects = getCases().iterator();
        while (subjects.hasNext()) {
          Node subject = (Node)subjects.next();
          Iterator titles = Cases.findTestObjectList(String.class).iterator();
          while (titles.hasNext()) {
            String title = (String)titles.next();
            Map testCase = new HashMap();
            testCase.put(SUBJECT_KEY, subject);
            testCase.put("title", title);
            result.add(testCase);
          }
        }
        return result;
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
      }

      public List getTestCases() throws TorytException {
        List result = new ArrayList();
        Iterator subjects = getCases().iterator();
        while (subjects.hasNext()) {
          Node subject = (Node)subjects.next();
          Iterator groups = new _Contract_Group().getCasesWithNull().iterator();
          while (groups.hasNext()) {
            Group group = (Group)groups.next();
            Map testCase = new HashMap();
            testCase.put(SUBJECT_KEY, subject);
            testCase.put("group", group);
            result.add(testCase);
          }
        }
        return result;
      }
      
      public void recordState(MethodTest test) {
        Map state = test.getContext();
        Node subject = (Node)state.get(SUBJECT_KEY);
        state.put("group@post", state.get("group"));
        state.put("getGroup()", subject.getGroup());
        state.put("getGroup()@post", subject.getGroup());
      }
      
    });
    addBasicInspector("getDescription()");
    addBasicInspector("getTitle()");
    addBasicInspector("getRating()");
    addBasicInspector("getGroup()");
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
    close();
  }
  
  public List getCases() throws TorytException {
    return getCases(new NodeFactory());
  }

  public static List getCases(NodeFactory nf) throws TorytException {
    List result = new ArrayList();
    Iterator descriptions = Cases.findTestObjectList(String.class).iterator();
    while (descriptions.hasNext()) {
      String description = (String)descriptions.next();
      Iterator titles = Cases.findTestObjectList(String.class).iterator();
      while (titles.hasNext()) {
        String title = (String)titles.next();
        // If we just use the group cases, we get an infinite loop
        result.add(nf.createNode(description, title, null));
        result.add(nf.createNode(description, title, new Group()));
        result.add(nf.createNode(description, title, new Group("title","description",null)));
        result.add(nf.createNode(description, title, new Group("title","description",new Group())));
      }
    }
    return result;
  }
  
  public static class NodeFactory {
    
    public Node createNode() {
      return new NodeStub();
    }

    public Node createNode(String description, String title, Group group) {
      Node subject = createNode();
      subject.setDescription(description);
      subject.setTitle(title);
      subject.setGroup(group);
      return subject;
    }
    
  }
  
  private static class NodeStub extends Node {

    protected int getTotalOfRatings() {
      return 0;
    }

    protected int getNumberOfBookmarks() {
      return 0;
    }
    
  }
  
}