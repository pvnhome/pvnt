package kz.pvnhome.pvnt;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created: 08.12.2016 12:30:52
 * @author victor
 */
public class Site {
   private String path;
   private String ext;

   private boolean debugMode = false;

   private Map<String, File> files = new LinkedHashMap<>();

   //==============================================================
   // Constructors.
   //==============================================================

   public Site() {
      this(".", "html");
   }

   public Site(String ext) {
      this(".", ext);
   }

   public Site(String path, String ext) {
      this.path = path;
      this.ext = ext;
   }

   //==============================================================
   // Public methods.
   //==============================================================

   public void load() throws Exception {
      Path dir = Paths.get(path);

      try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir, "*." + ext)) {
         for (Path p : dirStream) {
            if (Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS)) {
               addFile(p);
            }
         }
      }
   }

   public void process() {

   }

   public void save() {
      for (File file : files.values()) {
         System.out.println("   " + file.getName());
         String[] implIds = file.getImplIds();
         if (implIds.length > 0) {
            System.out.println("      IMPL IDS: " + Arrays.toString(implIds));
         }
         printFilePartsTree(file);
      }
      /*
      Path path = FileSystems.getDefault().getPath("logs", "access.log");
      BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
      
      try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("example.txt"), StandardCharsets.UTF_8)) {
         writer.write("");
      } catch (IOException e) {
         // TODO 
      }
      */
   }

   public void addFile(Path path) throws Exception {
      File file = new File(this, path.toAbsolutePath());
      if (files.containsKey(file.getName())) {
         System.out.println("added before: " + file.getName());
      } else {
         System.out.println("add file:     " + file.getName());
         Parser.parse(file, file, file.load());
         files.put(file.getName(), file);
      }
   }

   public void printPartsTree() {
      System.out.println("PARTS TREE:");

      for (File file : files.values()) {
         System.out.println("   " + file.getName());
         String[] implIds = file.getImplIds();
         if (implIds.length > 0) {
            System.out.println("      IMPL IDS: " + Arrays.toString(implIds));
         }
         printFilePartsTree(file);
      }
   }

   public void printDebugMessage(String message, Object... params) {
      if (debugMode) {
         System.out.println(String.format(message, params));
      }
   }

   //==============================================================
   // Private methods.
   //==============================================================

   private void printFilePartsTree(File file) {
      for (Part part : file.getChildren()) {
         printFilePartTree(part, 6);
      }
   }

   private void printFilePartTree(Part part, int depth) {
      char[] identChars = new char[depth];
      Arrays.fill(identChars, ' ');
      String ident = new String(identChars);

      if (part.getChildren().isEmpty()) {
         System.out.println(ident + part.getDescription());
      } else {
         System.out.println(ident + part.getDescription() + " {");
         for (Part child : part.getChildren()) {
            printFilePartTree(child, depth + 3);
         }
         System.out.println(ident + "}");
      }
   }

   //==============================================================
   // GET/SET-methods.
   //==============================================================

   public boolean isDebugMode() {
      return debugMode;
   }

   public void setDebugMode(boolean debugMode) {
      this.debugMode = debugMode;
   }
}
