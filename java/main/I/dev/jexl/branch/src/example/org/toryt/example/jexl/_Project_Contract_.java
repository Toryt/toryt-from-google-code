package org.toryt.example.jexl;


import org.toryt.TorytException;
import org.toryt.hard.ProjectContract;


public class _Project_Contract_ extends ProjectContract {

  public _Project_Contract_() throws TorytException {
    super("Toryt");
    addPackageContract(new _Package_Contract_());
    close();
  }
  
}
    
    
 
