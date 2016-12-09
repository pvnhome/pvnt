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
