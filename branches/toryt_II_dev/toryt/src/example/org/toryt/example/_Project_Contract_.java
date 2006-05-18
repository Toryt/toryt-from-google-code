package org.toryt.example;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.OLDTorytException;
import org.toryt_II.OLDcontract.hard.HardProjectContract;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _Project_Contract_ extends HardProjectContract {

  public _Project_Contract_() throws OLDTorytException {
    super("Toryt");
    addPackageContract(new org.toryt.example._Package_Contract_());
    close();
  }

}



