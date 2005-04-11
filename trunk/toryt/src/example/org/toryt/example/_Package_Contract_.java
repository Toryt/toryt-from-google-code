package org.toryt.example;
import org.toryt_II.TorytException;
import org.toryt_II.packache.HardPackageContract;

public class _Package_Contract_ extends HardPackageContract {

  public _Package_Contract_() throws TorytException {
    super(_Package_Contract_.class.getPackage());
    addClassContract(new _Contract_Node());
    addClassContract(new _Contract_Group());
//    addClassContract(new _Contract_Bookmark());
    close();
  }
  
}
    
    
 
