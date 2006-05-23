/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt_II.example;

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
    Group computerCompanyGroup = new Group("Computer Companies",
          "This group contains different Computer Companies", $root);
    Group newspaperGroup = new Group("Newspapers",
          "This group contains newspapers like New York Times en De Standaard", $root);
    Group meteoGroup = new Group("Meteo",
          "This group contains weather site and pictures of Sabine Hagedoren", $root);
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

  @Override
  public String toString() {
    return $root.toString();
  }

}
