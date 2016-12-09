package kz.pvnhome.pvnt;

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
}
