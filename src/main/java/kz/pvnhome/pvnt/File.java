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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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

   private TmplPart tmpl;
   private Map<String, ImplPart> implMap;

   //==============================================================
   // Constructors.
   //==============================================================

   public File(Site site, Path path) {
      super("");
      this.site = site;
      this.path = path;

      children = new ArrayList<>();
      implMap = new HashMap<>();
   }

   //==============================================================
   // Public methods.
   //==============================================================

   public String load() throws IOException {
      return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
   }

   public void save() throws IOException {
      try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
         for (Part part : getChildren()) {
            part.write(writer);
         }
      }
   }

   public void addImpl(ImplPart part) {
      implMap.put(part.getId(), part);
   }

   public Part findImpl(String id) {
      return implMap.get(id);
   }

   public String[] getImplIds() {
      Set<String> keySet = implMap.keySet();
      return keySet.toArray(new String[keySet.size()]);
   }

   public boolean isRoot() {
      return parent == null;
   }

   public void addChild(File child) {
      child.setParent(this);
      children.add(child);
   }

   public void process() throws Exception {
      if (!isRoot()) {
         site.printDebugMessage("process file: %s", getName());
         setChildren(new ArrayList<>());
         process(parent.getChildren(), this);

         if (implMap.values().stream().anyMatch(it -> !it.isProcessed())) {
            throw new Exception("We have IMPL tags " + Arrays.toString(implMap.values().stream().filter(it -> !it.isProcessed()).map(it -> it.getId()).toArray()) + " without EDIT tags in template in " + getName());
         }
      }

      for (File file : children) {
         file.process();
      }
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

   private void process(List<Part> templateParts, Part currentPart) {
      for (Part part : templateParts) {
         if (part instanceof TextPart) {
            currentPart.addPart(part);
         } else {
            if (part instanceof EditPart && implMap.containsKey(part.getId())) {
               ImplPart implPart = implMap.get(part.getId());
               implPart.setProcessed(true);
               currentPart.addPart(implPart);
            } else {
               if (part instanceof ImplPart) {
                  ImplPart implPart = implMap.get(part.getId());
                  if (implPart != null) {
                     implPart.setProcessed(true);
                     // TODO We need to compare part with implPart and we need to print message if the difference will be detected  
                  }
               }

               Part clonedPart = clone(part);
               currentPart.addPart(clonedPart);
               if (!part.getChildren().isEmpty()) {
                  process(part.getChildren(), clonedPart);
               }
            }
         }
      }
   }

   private Part clone(Part part) {
      Part newPart = part;
      if (part instanceof EditPart) {
         newPart = new EditPart(part);
      } else if (part instanceof TmplPart) {
         newPart = new TmplPart(getTmpl().getId());
      } else if (part instanceof ImplPart) {
         newPart = new ImplPart(part);
      }
      return newPart;
   }

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

   public File getParent() {
      return parent;
   }

   public void setParent(File parent) {
      this.parent = parent;
   }

   public TmplPart getTmpl() {
      return tmpl;
   }

   public void setTmpl(TmplPart tmpl) {
      this.tmpl = tmpl;
   }
}
