package kz.pvnhome.pvnt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created: 08.12.2016 14:07:55
 * @author victor
 */
public class File extends CompositePart {
   private Site site;
   private Path path;

   private File parent;
   private List<File> children;

   private Map<String, Part> implMap;

   //==============================================================
   // Constructors.
   //==============================================================

   public File(Site site, Path path) {
      super("");
      this.site = site;
      this.path = path;

      implMap = new HashMap<>();
   }

   //==============================================================
   // Public methods.
   //==============================================================

   public String load() throws IOException {
      return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
   }

   public void addImpl(Part part) {
      implMap.put(part.getId(), part);
   }

   public Part findImpl(String id) {
      return implMap.get(id);
   }

   public String[] getImplIds() {
      Set<String> keySet = implMap.keySet();
      return keySet.toArray(new String[keySet.size()]);
   }

   //==============================================================
   // Part interface methods.
   //==============================================================

   @Override
   public String getDescription() {
      return path.toString();
   }

   //==============================================================
   // Private methods.
   //==============================================================

   //TODO

   //==============================================================
   // GET/SET-methods.
   //==============================================================

   public String getName() {
      return path.toString();
   }

   public Path getPath() {
      return path;
   }

   public Site getSite() {
      return site;
   }

}
