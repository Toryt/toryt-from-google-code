package org.toryt.example;

import org.toryt.util_I.annotations.vcs.CvsInfo;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Demo {

  private Group $root;

  public Demo() {
    initializeGroupsAndBookmarks();
  }

  private void initializeGroupsAndBookmarks() {
    $root = new Group("Bookmarks", "The root", null);
    Group computerCompanyGroup = new Group("Computer Companies",  //$NON-NLS-1$
          "This group contains different Computer Companies", $root);  //$NON-NLS-1$
    Group newspaperGroup = new Group("Newspapers",  //$NON-NLS-1$
          "This group contains newspapers like New York Times en De Standaard", $root); //$NON-NLS-1$
    Group meteoGroup = new Group("Meteo",  //$NON-NLS-1$
          "This group contains weather site and pictures of Sabine Hagedoren", $root); //$NON-NLS-1$
    // Computer Companies
    new Bookmark("http://www.apple.com","Apple Inc","Apple Computer, maker of the iPod", 10, computerCompanyGroup);
    new Bookmark("http://www.dell.com","Dell Inc","Maker of very boring computers and very bad software", 8, computerCompanyGroup);
    new Bookmark("http://www.hp.com","Hewlett Packard Inc","Guys who alo begun in a garage", 6, computerCompanyGroup);
    // Newspapers
    new Bookmark("http://www.gva.be","De Frut","Gazet van Antwerpen", 2, newspaperGroup);
    new Bookmark("http://www.destandaard.be","De Standaard","De Standaard, niet toevallig de Standaard", 7, newspaperGroup);
    new Bookmark("http://www.nytimes.com","New York Times","The New York Times", 9, newspaperGroup);
    new Bookmark("http://www.tv-vrouwen.net/weer/sabine7/", "Sabine Hagedoren", "Always take the weather with you", 1, meteoGroup);
    new Bookmark("http://www.cote.azur.fr/meteo.htm","Weather at the Cote d'azure","Cote Azure weather information", 6, meteoGroup);
    new Bookmark("http://www.gismeteo.ru","Weather in Russia","Weather in Russia...", 1, meteoGroup);
  }

  public static void main(String[] args) {
    System.out.println(new Demo());
  }

  /**
   * @return Returns the groups.
   */
  public Group getRoot() {
    return $root;
  }

//	public Group getGroupByTitle(String title) {
//		Group result = null;
//		for (int lcv = 0; lcv < groups.size(); lcv++) {
//		  Group grp = (Group)groups.get(lcv);
//		  if (grp.getTitle().toUpperCase().equals(title.toUpperCase())) {
//		  	result = grp;
//		  	break;
//		  }
//		}
//		return (result);
//	}

  public String toString() {
    return $root.toString();
  }

}
