package kz.pvnhome.pvnt;

/**
 * Created: 08.12.2016 9:11:32
 * @author victor
 */
public class PVNTemplateRunner {
   public static void main(String[] args) {
      System.out.println("PVN Template engine");

      String path = ".";
      String ext = "html";
      boolean debugMode = true; // TODO Switch to false after debugging

      if (args.length == 1) {
         path = args[0];
      } else if (args.length == 2) {
         path = args[0];
         ext = args[1];
      } else {
         System.out.println("WARN: Bad argument count");
         System.out.println("Use:");
         System.out.println("   pvnt [path/to/site [extension]]");
      }

      System.out.println("Process all " + ext + " files in " + path);

      try {
         Site site = new Site(path, ext);

         site.setDebugMode(debugMode);

         site.load();

         if (site.isDebugMode()) {
            site.printPartsTree();
         }

         site.process();
         
         site.save();

      } catch (Exception e) {
         if (debugMode) {
            e.printStackTrace();
         } else {
            System.out.println("ERROR: " + e.getMessage());
         }
      }
   }
}
