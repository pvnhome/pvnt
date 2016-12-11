/*
 PVN Template utility 
 Copyright (C) 2016,  Victor Pyankov

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package kz.pvnhome.pvnt;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created: 08.12.2016 20:30:53
 * @author victor
 */
public abstract class CompositePart implements Part {
   private String id;
   private List<Part> children;

   //==============================================================
   // Constructors.
   //==============================================================

   public CompositePart(String id) {
      this.id = id;
      children = new ArrayList<>();
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getId() {
      return id;
   }

   @Override
   public void addPart(Part part) {
      children.add(part);
   }

   @Override
   public List<Part> getChildren() {
      return children;
   }

   @Override
   public void setChildren(List<Part> children) {
      this.children = children;
   }

   @Override
   public void write(Writer writer) throws IOException {
      for (Part part : children) {
         part.write(writer);
      }
   }
}
