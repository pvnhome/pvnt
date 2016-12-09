package kz.pvnhome.pvnt;

import java.io.IOException;
import java.io.Writer;

/**
 * Created: 08.12.2016 20:27:41
 * @author victor
 */
public class ImplPart extends CompositePart {

   //==============================================================
   // Constructors.
   //==============================================================

   public ImplPart(String id) {
      super(id);
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
      writer.write("<!--pvnImplEnd-->");
   }
}
