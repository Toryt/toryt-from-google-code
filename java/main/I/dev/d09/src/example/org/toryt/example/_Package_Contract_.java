package org.toryt.example;
import org.toryt.TorytException;
import org.toryt.hard.PackageContract;

public class _Package_Contract_ extends PackageContract {

  public _Package_Contract_() throws TorytException {
    super(_Package_Contract_.class.getPackage());
    addClassContract(new _Contract_Bookmark());
    addClassContract(new _Contract_Group());
    addClassContract(new _Contract_Node());
    close();
  }
  
}
    
    
 
