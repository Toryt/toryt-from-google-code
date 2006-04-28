package org.toryt.example;


import org.toryt.support.straightlist.StraightList;
import org.toryt.support.straightlist.LazyMappingStraightList.Mapping;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.hard.HardClassContract;
import org.toryt_II.contract.hard.HardConstructorContract;
import org.toryt_II.contract.hard.HardMutatorContract;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _Contract_Bookmark extends HardClassContract {

  public _Contract_Bookmark() throws OLDTorytException {
    super(Bookmark.class);
    addConstructorContract(new HardConstructorContract(this, Bookmark.class, "Bookmark()") {

      public StraightList getTestCases() throws OLDTorytException {
        // TODO Auto-generated method stub
        return null;
      }

    });

    addConstructorContract(new HardConstructorContract(this, Bookmark.class, "Bookmark(String, String, String, int, Group)") {

      public StraightList getTestCases() throws OLDTorytException {
        // TODO Auto-generated method stub
        return null;
      }

                                });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "setUrl(String)") {

      public StraightList getTestCases() throws OLDTorytException {
        // TODO Auto-generated method stub
        return null;
      }

                                  });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "clicked()") {

      public StraightList getTestCases() throws OLDTorytException {
        // TODO Auto-generated method stub
        return null;
      }

                                  });
    addInstanceMethodContract(new HardMutatorContract(this, Bookmark.class, "setRating(int)") {

      public StraightList getTestCases() throws OLDTorytException {
        // TODO Auto-generated method stub
        return null;
      }

                                  });
    addBasicInspector("getUrl()");
    addBasicInspector("getNumberOfClicks()");
    addBasicInspector("getDateAdded()");
    close();
  }

  public StraightList getCasesMaps() throws OLDTorytException {
    // TODO Auto-generated method stub
    return null;
  }

  public Mapping getCaseMapping() {
    // TODO Auto-generated method stub
    return null;
  }

}