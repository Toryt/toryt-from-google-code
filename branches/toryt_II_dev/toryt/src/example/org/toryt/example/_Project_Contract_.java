package org.toryt.example;


import org.toryt_II.TorytException;
import org.toryt_II.contract.hard.HardProjectContract;


public class _Project_Contract_ extends HardProjectContract {

  public _Project_Contract_() throws TorytException {
    super("Toryt");
    addPackageContract(new org.toryt.example._Package_Contract_());
    close();
  }
  
}
    
    
 
