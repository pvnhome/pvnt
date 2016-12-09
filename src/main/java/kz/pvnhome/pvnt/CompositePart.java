package kz.pvnhome.pvnt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 08.12.2016 20:30:53
 * @author victor
 */
public abstract class CompositePart implements Part {
   private String id;
   private List<Part> children;

   //==============================================================
   // Constructors.
   //==============================================================

   public CompositePart(String id) {
      this.id = id;
      children = new ArrayList<>();
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getId() {
      return id;
   }

   @Override
   public void addPart(Part part) {
      children.add(part);
   }

   @Override
   public List<Part> getChildren() {
      return children;
   }

   //==============================================================
   // Public methods.
   //==============================================================

   //TODO

   //==============================================================
   // Private methods.
   //==============================================================

   //TODO

   //==============================================================
   // GET/SET-methods.
   //==============================================================

}
