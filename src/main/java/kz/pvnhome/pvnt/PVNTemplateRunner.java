/*
 PVN Template utility 
 Copyright (C) 2016,  Victor Pyankov

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package kz.pvnhome.pvnt;

/**
 * PVN Template utility.
 * Created: 08.12.2016 9:11:32
 * 
 * History:
 * 09.12.2016 - 0.90 - (BETA) Basic functionality. Ready for testing. (victor)
 * 12.12.2016 - 0.91 - (BETA) Added command line switch "-d". (victor)
 * 12.12.2016 - 0.92 - (BETA) REM was deleted from pvnt.bat. (victor)
 * 
 * @author victor
 */
public class PVNTemplateRunner {
   public static final String VERSION = "0.92 beta";

   public static void main(String[] args) {
      String path = ".";
      String ext = "html";
      boolean debugMode = false;

      int ind = 0;
      if (args.length > 0) {
         if ("-h".equals(args[0]) || "-help".equals(args[0])) {
            printUsage();
            System.exit(0);
         } else if ("-version".equals(args[0])) {
            printVersion();
            System.exit(0);
         } else if ("-d".equals(args[0]) || "-debug".equals(args[0])) {
            debugMode = true;
            ind++;
         }
      }

      if (args.length == ind + 1) {
         path = args[ind];
      } else if (args.length == ind + 2) {
         path = args[ind];
         ext = args[ind + 1];
      } else if (args.length == ind) {
      } else {
         System.out.println("WARN: Bad argument count");
         printUsage();
         System.exit(0);
      }

      printVersion();

      System.out.println("INFO: Process all " + ext + " files in " + path + " (debug " + (debugMode ? "on" : "off") + ")");

      try {
         Site site = new Site(path, ext);

         site.setDebugMode(debugMode);

         site.load();

         System.out.println("INFO: files loaded");

         if (site.isDebugMode()) {
            site.printPartsTree();
         }

         site.process();

         System.out.println("INFO: files processed");

         site.save();

         System.out.println("INFO: files saved");

      } catch (Exception e) {
         if (debugMode) {
            e.printStackTrace();
         } else {
            System.out.println("ERROR: " + e.getMessage());
         }
      }
   }

   private static void printUsage() {
      printVersion();
      System.out.println("usage: pvnt [options] [path/to/site [extension]]");
      System.out.println("Options:");
      System.out.println("   -help, -h              print this message");
      System.out.println("   -version               print the version information and exit");
      System.out.println("   -debug, -d             print debugging information");
   }

   private static void printVersion() {
      System.out.println("PVN Template engine (version " + VERSION + ")");
   }
}
