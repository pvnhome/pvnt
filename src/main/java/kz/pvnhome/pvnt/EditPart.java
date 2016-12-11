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

/**
 * Created: 08.12.2016 20:27:17
 * @author victor
 */
public class EditPart extends CompositePart {

   //==============================================================
   // Constructors.
   //==============================================================

   public EditPart(String id) {
      super(id);
   }

   public EditPart(Part part) {
      super(part.getId());
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getDescription() {
      return "EDIT: id = " + getId();
   }

   @Override
   public void write(Writer writer) throws IOException {
      writer.write("<!--pvnEditBeg " + getId() + "-->");
      super.write(writer);
      writer.write("<!--pvnEditEnd " + getId() + "-->");
   }
}
