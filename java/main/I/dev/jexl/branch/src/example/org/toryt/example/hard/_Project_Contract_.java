package org.toryt.example.hard;


import org.toryt.TorytException;
import org.toryt.hard.ProjectContract;


public class _Project_Contract_ extends ProjectContract {

  public _Project_Contract_() throws TorytException {
    super("Toryt");
    addPackageContract(new org.toryt.example.hard._Package_Contract_());
    close();
  }
  
}
    
    
 
