package org.toryt.example;


import org.toryt.support.straightlist.StraightList;
import org.toryt.support.straightlist.LazyMappingStraightList.Mapping;
import org.toryt_II.TorytException;
import org.toryt_II.contract.hard.HardClassContract;
import org.toryt_II.contract.hard.HardConstructorContract;
import org.toryt_II.contract.hard.HardMutatorContract;


public class _Contract_Bookmark extends HardClassContract {

  public _Contract_Bookmark() throws TorytException {
    super(Bookmark.class);
    addConstructorContract(new HardConstructorContract(this, Bookmark.class, "Bookmark()") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
    });

    addConstructorContract(new HardConstructorContract(this, Bookmark.class, "Bookmark(String, String, String, int, Group)") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "setUrl(String)") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                  });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "clicked()") {

      public StraightList getTestCases() throws TorytException {
        // TODO Auto-generated method stub
        return null;
      }
      
                                  });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "setRating(int)") {

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