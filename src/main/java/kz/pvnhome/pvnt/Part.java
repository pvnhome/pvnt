package kz.pvnhome.pvnt;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created: 08.12.2016 17:36:52
 * @author victor
 */
public interface Part {
   public String getId();

   public void addPart(Part part);

   public List<Part> getChildren();

   public String getDescription();

   public void write(Writer writer) throws IOException;
}
