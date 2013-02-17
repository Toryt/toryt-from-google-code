package org.toryt.example.hard;


import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;
import org.toryt.hard.MutatorContract;
import org.toryt.support.straightlist.StraightList;
import org.toryt.support.straightlist.LazyMappingStraightList.Mapping;


public class _Contract_Bookmark extends ClassContract {

  public _Contract_Bookmark() throws TorytException {
    super(Bookmark.class);
    addConstructorContract(new ConstructorContract(this, Bookmark.class, "Bookmark()") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
    });

    addConstructorContract(new ConstructorContract(this, Bookmark.class, "Bookmark(String, String, String, int, Group)") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "setUrl(String)") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                  });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "clicked()") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                  });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "setRating(int)") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                  });
    addBasicInspector("getUrl()");
    addBasicInspector("getNumberOfClicks()");
    addBasicInspector("getDateAdded()");
    close();
  }

  public StraightList getCasesMaps() throws TorytException {
    // TODO Auto-generated method stub
    return null;
  }

  public Mapping getCaseMapping() {
    // TODO Auto-generated method stub
    return null;
  }
  
}