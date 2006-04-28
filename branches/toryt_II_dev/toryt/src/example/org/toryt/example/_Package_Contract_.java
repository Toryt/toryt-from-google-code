package org.toryt.example;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.hard.HardPackageContract;

@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _Package_Contract_ extends HardPackageContract {

  public _Package_Contract_() throws OLDTorytException {
    super(_Package_Contract_.class.getPackage());
    addClassContract(new _Contract_Node());
    addClassContract(new _Contract_Group());
//    addClassContract(new _Contract_Bookmark());
    close();
  }

}



