package kz.pvnhome.pvnt;

import java.util.List;

import kz.pvnhome.pvnt.javacc.ExtListParser;
import kz.pvnhome.pvnt.javacc.ParseException;

/**
 * Created: 08.12.2016 9:11:32
 * @author victor
 */
public class PVNTemplateRunner {
   //==============================================================
   // Constructors.
   //==============================================================

   //TODO

   //==============================================================
   // Public methods.
   //==============================================================

   public static void main(String[] args) {
      System.out.println("PVN Template engine");
      try {
         List<String> list = ExtListParser.parse("1,2,3");
         for (String item : list) {
            System.out.println(item);
         }
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   //==============================================================
   // Private methods.
   //==============================================================

   //TODO

   //==============================================================
   // GET/SET-methods.
   //==============================================================

   //TODO

}
