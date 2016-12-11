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
