package org.toryt.example;


import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;
import org.toryt.hard.MutatorContract;


public class _Contract_Bookmark extends ClassContract {

  public _Contract_Bookmark() throws TorytException {
    super(Bookmark.class);
    setSuperClassContract("org.toryt.example.Node");
    addClassMethodContract(new ConstructorContract(Bookmark.class, "Bookmark()") {
      
                                });
    addClassMethodContract(new ConstructorContract(Bookmark.class, "Bookmark(String, String, String, int, Group)") {
      
                                });
    addInstanceMethodContract(new MutatorContract(Bookmark.class, "setUrl(String)") {
      
                                  });
    addInstanceMethodContract(new MutatorContract(Bookmark.class, "clicked()") {
      
                                  });
    addInstanceMethodContract(new MutatorContract(Bookmark.class, "setRating(int)") {
      
                                  });
    addBasicInspector("getUrl()");
    addBasicInspector("getNumberOfClicks()");
    addBasicInspector("getDateAdded()");
    close();
  }
  
}