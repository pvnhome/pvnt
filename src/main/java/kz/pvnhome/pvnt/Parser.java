package kz.pvnhome.pvnt;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Created: 08.12.2016 17:33:39
 * @author victor
 */
public class Parser {
   private static final char[] TAG_PREFIX = new char[]{'<', '!', '-', '-', 'p', 'v', 'n'};
   private static final int TAG_BEG_LEN = TAG_PREFIX.length + 7;
   private static final char[] TAG_BEG_END = new char[]{'-', '-', '>'};
   private static final char[] TAG_END_TMPL = new char[]{'<', '!', '-', '-', 'p', 'v', 'n', 'T', 'm', 'p', 'l', 'E', 'n', 'd', '-', '-', '>'};
   private static final char[] TAG_END_EDIT = new char[]{'<', '!', '-', '-', 'p', 'v', 'n', 'E', 'd', 'i', 't', 'E', 'n', 'd', '-', '-', '>'};
   private static final char[] TAG_END_IMPL = new char[]{'<', '!', '-', '-', 'p', 'v', 'n', 'I', 'm', 'p', 'l', 'E', 'n', 'd', '-', '-', '>'};

   private enum State {
      TEXT,
      TMPL_ID,
      TMPL,
      EDIT_ID,
      EDIT,
      IMPL_ID,
      IMPL
   }

   //==============================================================
   // Public methods.
   //==============================================================

   public static void parse(File file, Part part, String text) throws Exception {
      char ch;
      int pos = 0;
      int tagLen;
      int len = text.length();

      StringBuilder sb = new StringBuilder();

      Part currentPart = null;

      State state = State.TEXT;

      while (pos < len) {
         ch = text.charAt(pos);

         switch (state) {
            case TEXT :
               state = findBegTag(text, len, pos);
               if (state == State.TEXT) {
                  sb.append(ch);
                  pos++;
               } else {
                  System.out.println("  add text"); // TODO DELETE DEBUG
                  String newText = sb.toString();
                  if (!newText.isEmpty()) {
                     part.addPart(new TextPart(newText));
                  }
                  sb.setLength(0);
                  pos += TAG_BEG_LEN;
               }
               break;

            case TMPL_ID :
               tagLen = findBegTagEnd(text, len, pos);
               if (tagLen > 0) {
                  if (file.getTmpl() == null) {
                     file.setTmpl(new TmplPart(sb.toString().trim()));
                     currentPart = file.getTmpl();
                  } else {
                     StringBuilder err = new StringBuilder();
                     err.append("Multiple TMPL tag in ").append(file.getPath().toString()).append(" (TMPL: ");
                     if (sb.toString().trim().isEmpty()) {
                        err.append(" root");
                     } else {
                        err.append(" file=").append(sb.toString().trim());
                     }
                     err.append(")");
                     throw new Exception(err.toString());
                  }

                  sb.setLength(0);
                  pos += tagLen;
                  state = State.TMPL;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;

            case TMPL :
               tagLen = findTmplEndTag(text, len, pos);
               if (tagLen > 0) {
                  System.out.println("  add tmpl"); // TODO DELETE DEBUG

                  parse(file, currentPart, sb.toString());

                  part.addPart(currentPart);

                  String templateFileName = currentPart.getId();
                  if (templateFileName != null && !templateFileName.isEmpty()) {
                     // Результат getParent() не проверяется на null так как file.getPath() 
                     // гарантированно возвращает absolute path.   
                     Path templatePath = file.getPath().getParent().resolve(templateFileName);
                     if (Files.exists(templatePath, LinkOption.NOFOLLOW_LINKS)) {
                        System.out.println("  add tmpl file"); // TODO DELETE DEBUG
                        File parentFile = file.getSite().addFile(templatePath);
                        parentFile.addChild(file);
                     } else {
                        throw new Exception("Template \"" + templateFileName + "\" is not exists");
                     }
                  }

                  currentPart = null;
                  sb.setLength(0);
                  pos += tagLen;
                  state = State.TEXT;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;

            case EDIT_ID :
               tagLen = findBegTagEnd(text, len, pos);
               if (tagLen > 0) {
                  currentPart = new EditPart(sb.toString().trim());
                  sb.setLength(0);
                  pos += tagLen;
                  state = State.EDIT;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;

            case EDIT :
               tagLen = findEditEndTag(text, len, pos);
               if (tagLen > 0) {
                  System.out.println("  add edit"); // TODO DELETE DEBUG

                  parse(file, currentPart, sb.toString());

                  part.addPart(currentPart);

                  currentPart = null;
                  sb.setLength(0);
                  pos += tagLen;
                  state = State.TEXT;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;

            case IMPL_ID :
               tagLen = findBegTagEnd(text, len, pos);
               if (tagLen > 0) {
                  currentPart = new ImplPart(sb.toString().trim());
                  sb.setLength(0);
                  pos += tagLen;
                  state = State.IMPL;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;

            case IMPL :
               tagLen = findImplEndTag(text, len, pos);
               if (tagLen > 0) {
                  System.out.println("  add impl"); // TODO DELETE DEBUG

                  parse(file, currentPart, sb.toString());

                  part.addPart(currentPart);
                  file.addImpl((ImplPart) currentPart);

                  currentPart = null;
                  sb.setLength(0);
                  pos += tagLen;
                  state = State.TEXT;
               } else {
                  sb.append(ch);
                  pos++;
               }
               break;
         }
      }

      if (state == State.TEXT) {
         String newText = sb.toString();
         if (!newText.isEmpty()) {
            part.addPart(new TextPart(newText));
         }
      } else {
         StringBuilder err = new StringBuilder();
         err.append("End tag not found in ").append(file.getPath().toString()).append(" (").append(part.getDescription());
         if (currentPart != null) {
            err.append(" -> ").append(currentPart.getDescription());
         }
         err.append(")");
         throw new Exception(err.toString());
      }
   }

   //==============================================================
   // Private methods.
   //==============================================================

   private static State findBegTag(String t, int l, int p) {
      if (p + TAG_BEG_LEN < l) {
         for (int i = 0; i < TAG_PREFIX.length; i++) {
            if (t.charAt(p + i) != TAG_PREFIX[i]) {
               return State.TEXT;
            }
         }

         State s = null;
         int j = p + TAG_PREFIX.length;

         if (t.charAt(j) == 'T' && t.charAt(j + 1) == 'm' && t.charAt(j + 2) == 'p' && t.charAt(j + 3) == 'l') {
            s = State.TMPL_ID;
         } else if (t.charAt(j) == 'I' && t.charAt(j + 1) == 'm' && t.charAt(j + 2) == 'p' && t.charAt(j + 3) == 'l') {
            s = State.IMPL_ID;
         } else if (t.charAt(j) == 'E' && t.charAt(j + 1) == 'd' && t.charAt(j + 2) == 'i' && t.charAt(j + 3) == 't') {
            s = State.EDIT_ID;
         } else {
            return State.TEXT;
         }

         if (t.charAt(j + 4) == 'B' && t.charAt(j + 5) == 'e' && t.charAt(j + 6) == 'g') {
            return s;
         } else {
            return State.TEXT;
         }
      } else {
         return State.TEXT;
      }
   }

   private static int findBegTagEnd(String t, int l, int p) {
      return findEndTag(t, l, p, TAG_BEG_END);
   }

   private static int findTmplEndTag(String t, int l, int p) {
      return findEndTag(t, l, p, TAG_END_TMPL);
   }

   private static int findEditEndTag(String t, int l, int p) {
      return findEndTag(t, l, p, TAG_END_EDIT);
   }

   private static int findImplEndTag(String t, int l, int p) {
      return findEndTag(t, l, p, TAG_END_IMPL);
   }

   private static int findEndTag(String t, int l, int p, char[] c) {
      if (p + c.length <= l) {
         for (int i = 0; i < c.length; i++) {
            if (t.charAt(p + i) != c[i]) {
               return 0;
            }
         }
         return c.length;
      } else {
         return 0;
      }
   }

   //==============================================================
   // GET/SET-methods.
   //==============================================================

   //TODO

   //==============================================================
   // Private classes.
   //==============================================================

   private static class Tag {
      public State state;
      public int len;
      public String id;

      public Tag(State state, int len, String id) {
         this.state = state;
         this.len = len;
         this.id = id;
      }
   }
}
