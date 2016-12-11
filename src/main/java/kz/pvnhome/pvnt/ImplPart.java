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
 * Created: 08.12.2016 20:27:41
 * @author victor
 */
public class ImplPart extends CompositePart {
   boolean processed = false;

   //==============================================================
   // Constructors.
   //==============================================================

   public ImplPart(String id) {
      super(id);
   }

   public ImplPart(Part part) {
      super(part.getId());
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getDescription() {
      return "IMPL: id = " + getId();
   }

   @Override
   public void write(Writer writer) throws IOException {
      writer.write("<!--pvnImplBeg " + getId() + "-->");
      super.write(writer);
      writer.write("<!--pvnImplEnd " + getId() + "-->");
   }

   //==============================================================
   // GET/SET-methods.
   //==============================================================

   public boolean isProcessed() {
      return processed;
   }

   public void setProcessed(boolean processed) {
      this.processed = processed;
   }
}
