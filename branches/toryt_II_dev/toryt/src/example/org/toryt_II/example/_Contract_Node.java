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

package org.toryt_II.example;


import static org.toryt_II.testcase.TestCaseLabels.THIS;

import java.util.HashSet;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.CannotGetMethodException;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.contract.ContractIsClosedException;
import org.toryt_II.contract.InstanceMutatorContract;
import org.toryt_II.contract.bean.ClassContractBean;
import org.toryt_II.contract.bean.InstanceMutatorContractBean;
import org.toryt_II.contract.condition.Condition;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _Contract_Node extends ClassContractBean<Node> {

  public _Contract_Node() throws CannotGetMethodException {
    super(Node.class);

    try {
      // basic inspectors
      addBasicClassInspector(Reflection.findMethod(Node.class, "getDescription()"));
      addBasicClassInspector(Reflection.findMethod(Node.class, "getTitle()"));
      addBasicClassInspector(Reflection.findMethod(Node.class, "getRating()"));
      addBasicClassInspector(Reflection.findMethod(Node.class, "getGroup()"));

      // type invariants
      addInstanceInvariantCondition(new Condition() {
        public boolean validate(Map context) {
          Node subject = (Node)context.get(THIS);
          return subject.getTitle() != null;
        }
      });
      addInstanceInvariantCondition(new Condition() {
        public boolean validate(Map context) {
          Node subject = (Node)context.get(THIS);
          return subject.getDescription() != null;
        }
      });
      addInstanceInvariantCondition(new Condition() {
        public boolean validate(Map context) {
          Node subject = (Node)context.get(THIS);
          return ! Double.isNaN(subject.getRating()) ? (subject.getRating() >= 0) : true;
        }
      });
      addInstanceInvariantCondition(new Condition() {
        public boolean validate(Map context) {
          Node subject = (Node)context.get(THIS);
          return ! Double.isNaN(subject.getRating()) ? (subject.getRating() <= 10) : true;
        }
      });
      addInstanceInvariantCondition(new Condition() {
        public boolean validate(Map context) {
          Node subject = (Node)context.get(THIS);
          return (subject.getGroup() != null)
                  ? subject.getGroup().getNodes().get(subject.getTitle()) == subject
                  : true;
        }
      });

      // instance methods
      InstanceMutatorContractBean<Node> mc1 =
      new InstanceMutatorContractBean<Node>(Reflection.findMethod(Node.class, "setDescription(java.lang.String)"),
                                            this,
                                            new HashSet<InstanceMutatorContract<?>>()) {

        @Override
        public String[] getFormalParameters() {
          return new String[] {"description"};
        }

        {
          addPostcondition(new Condition() {
            public boolean validate(Map context) {
              Node subject = (Node)context.get(THIS);
              String description = (String)context.get("description");
              boolean result = subject.getDescription().equals(description == null ? "" : description);
              return result;
            }});
          close();
        }

      };

      InstanceMutatorContractBean<Node> mc2 =
      new InstanceMutatorContractBean<Node>(Reflection.findMethod(Node.class, "setTitle(java.lang.String)"),
                                           this,
                                           new HashSet<InstanceMutatorContract<?>>()) {

        @Override
        public String[] getFormalParameters() {
          return new String[] {"title"};
        }

        {
          addPostcondition(new Condition() {
            public boolean validate(Map context) {
              Node subject = (Node)context.get(THIS);
              String title = (String)context.get("title");
              return subject.getTitle().equals(title == null ? "" : title);
            }});
          close();
        }

      };

      InstanceMutatorContractBean<Node> mc3 =
      new InstanceMutatorContractBean<Node>(Reflection.findMethod(Node.class, "setGroup(org.toryt_II.example.Group)"),
                                            this,
                                            new HashSet<InstanceMutatorContract<?>>()) {

        @Override
        public String[] getFormalParameters() {
          return new String[] {"group"};
        }

        {
          addPostcondition(new Condition() {
            public boolean validate(Map context) {
              Node subject = (Node)context.get(THIS);
              Group group = (Group)context.get("group");
              return subject.getGroup() == group;
            }});
          addPostcondition(new Condition() {
            public boolean validate(Map context) {
              Node subject = (Node)context.get(THIS);
              Group oldGroup = (Group)context.get("getGroup()");
              Group oldGroupATpost = (Group)context.get("getGroup()@post");
              return oldGroup != null ? ! oldGroupATpost.getNodes().values().contains(subject) : true;
            }});
          addPostcondition(new Condition() {
            public boolean validate(Map context) {
              Node subject = (Node)context.get(THIS);
              Group group = (Group)context.get("group");
              Group groupATpost = (Group)context.get("group@post");
              return group != null ? groupATpost.getNodes().values().contains(subject) : true;
            }});
          close();
        }

        @Override
        public void recordState(Map<String, Object> context) {
          Node subject = (Node)context.get(THIS);
          context.put("group@post", context.get("group"));
          context.put("getGroup()", subject.getGroup());
          context.put("getGroup()@post", subject.getGroup());
        }

      };
    }
    catch (ContractIsClosedException e) {
      assert false : "ContractIsClosedException should not happen: " + e;
    }

    close();
  }

}