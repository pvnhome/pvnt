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
      if (getId() == null || getId().isEmpty()) {
         return "TMPL: root";
      } else {
         return "TMPL: file = " + getId();
      }
   }

   @Override
   public void write(Writer writer) throws IOException {
      writer.write("<!--pvnTmplBeg " + getId() + "-->");
      super.write(writer);
      writer.write("<!--pvnTmplEnd-->");
   }
}
