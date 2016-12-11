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
 * Created: 08.12.2016 20:26:59
 * @author victor
 */
public class TmplPart extends CompositePart {

   //==============================================================
   // Constructors.
   //==============================================================

   public TmplPart(String id) {
      super(id);
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getDescription() {
      if (isRoot()) {
         return "TMPL: root";
      } else {
         return "TMPL: file = " + getId();
      }
   }

   @Override
   public void write(Writer writer) throws IOException {
      if (isRoot()) {
         writer.write("<!--pvnTmplBeg-->");
      } else {
         writer.write("<!--pvnTmplBeg " + getId() + "-->");
      }
      super.write(writer);
      writer.write("<!--pvnTmplEnd-->");
   }

   //==============================================================
   // Public methods.
   //==============================================================

   public boolean isRoot() {
      return getId() == null || getId().isEmpty();
   }
}
