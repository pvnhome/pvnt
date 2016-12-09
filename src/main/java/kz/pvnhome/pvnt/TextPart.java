package kz.pvnhome.pvnt;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

/**
 * Created: 08.12.2016 19:53:30
 * @author victor
 */
public class TextPart implements Part {
   private String text;

   //==============================================================
   // Constructors.
   //==============================================================

   public TextPart(String text) {
      this.text = text;
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getId() {
      return "";
   }

   @Override
   public void addPart(Part part) {
   }

   @Override
   public List<Part> getChildren() {
      return Collections.emptyList();
   }

   @Override
   public String getDescription() {
      return "TEXT";
   }

   @Override
   public void write(Writer writer) throws IOException {
      writer.write(text);
   }
}
