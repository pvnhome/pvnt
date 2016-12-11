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
