package org.toryt.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.toryt.Cases;
import org.toryt.MethodTest;
import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;
import org.toryt.hard.MutatorContract;


public class _Contract_Node extends ClassContract {

  public _Contract_Node() throws TorytException {
    super(Node.class);
    setSuperClassContract("java.lang.Object");
    addClassMethodContract(new ConstructorContract(Node.class, "Node()") {

      public List getTestCases() {
        List result = new ArrayList();
        Map testCase = new HashMap();
        result.add(testCase);
        return result;
      }

      public void validatePostConditions(MethodTest test) {
        Node subject = (Node)test.getTestCase().get(SUBJECT_KEY); 
        test.validate(subject.getDescription().equals(""));
        test.validate(subject.getTitle().equals(""));
        test.validate(subject.getRating() == 0);
        test.validate(subject.getGroup() == null);
      }

    });
    addInstanceMethodContract(new MutatorContract(Node.class, "setDescription(String)") {

      public String[] getFormalParameters() {
        return new String[] {"description"};
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

      public void validatePostConditions(MethodTest test) {
        Node subject = (Node)test.getTestCase().get(SUBJECT_KEY); 
        String description = (String)test.getTestCase().get("description");
        test.validate(subject.getDescription()
                 .equals(description == null ? "" : description));
      }
      
    });
    addInstanceMethodContract(new MutatorContract(Node.class, "setTitle(String)") {
      public String[] getFormalParameters() {
        return new String[] {"title"};
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
      
      public void validatePostConditions(MethodTest test) {
        Node subject = (Node)test.getTestCase().get(SUBJECT_KEY); 
        String title = (String)test.getTestCase().get("title");
        test.validate(subject.getDescription()
                 .equals(title == null ? "" : title));
      }
      
    });
    addInstanceMethodContract(new MutatorContract(Node.class, "setGroup(Group)") {
      public String[] getFormalParameters() {
        return new String[] {"group"};
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
      
      public void recordOldState(MethodTest test, Map state) {
        Node subject = (Node)test.getTestCase().get(SUBJECT_KEY); 
        state.put("getGroup()@pre",subject.getGroup());
      }
      
      public void validatePostConditions(MethodTest test) {
        Node subject = (Node)test.getTestCase().get(SUBJECT_KEY); 
        Group group = (Group)test.getTestCase().get("group");
        Group oldGroup = (Group)test.getOldState().get("getGroup()@pre"); 
        test.validate(subject.getGroup() == group);
        test.validate(oldGroup != null ?
                      subject.getGroup().getNodes().values().contains(subject) : true);
        test.validate(group != null ?
                      group.getNodes().values().contains(subject) : true);
      }

    });
    addBasicInspector("getDescription()");
    addBasicInspector("getTitle()");
    addBasicInspector("getRating()");
    addBasicInspector("getGroup()");
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
        Iterator groups = Cases.findTestObjectList(Group.class).iterator();
        while (groups.hasNext()) {
          Group group = (Group)groups.next();
          result.add(nf.createNode(description, title, group));
        }
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