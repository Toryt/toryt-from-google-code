package org.toryt.example;


import org.toryt.TorytException;
import org.toryt.hard.ClassContract;
import org.toryt.hard.ConstructorContract;
import org.toryt.hard.MutatorContract;


public class _Contract_Bookmark extends ClassContract {

  public _Contract_Bookmark() throws TorytException {
    super(Bookmark.class);
    setSuperClassContract("org.toryt.example.Node");
    addClassMethodContract(new ConstructorContract(his, Bookmark.class, "Bookmark()") {
      
                                });
    addClassMethodContract(new ConstructorContract(this, Bookmark.class, "Bookmark(String, String, String, int, Group)") {
      
                                });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "setUrl(String)") {
      
                                  });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "clicked()") {
      
                                  });
    addInstanceMethodContract(new MutatorContract(this, Bookmark.class, "setRating(int)") {
      
                                  });
    addBasicInspector("getUrl()");
    addBasicInspector("getNumberOfClicks()");
    addBasicInspector("getDateAdded()");
    close();
  }
  
}