package frc.robot.Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/** Useful functions for manipulating directories and files. */
public class DirFiles {

   private DirFiles(){}
   public static void createDirectoryAndDeleteExistingFile(String filename) {
      if (filename == null || filename.length() <= 0) return;

      File f = new File(filename);
      File fDir = f.getParentFile();
      boolean fDirExist = fDir.exists();
      if (!fDirExist) fDir.mkdir();

      if(f.exists() && !f.isDirectory()) f.delete();
   }

   public static boolean doesFileExist(String path) {
      if (path == null || path.length() <= 0) return false;

      File f = new File(path);
      File fDir = f.getParentFile();
      boolean fDirExist = fDir.exists();
      if (!fDirExist) return false;

      if (f.exists() && !f.isDirectory()) return true;

      return false;

   }

   public static void DeleteFileIfExist(String path) {
      if (null == path || path.isEmpty()) return;
      Path p = Paths.get(path);
      try {
         Files.deleteIfExists(p);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static String getDirectoryPath(String path) {
      if (path == null || path.isEmpty()) return null;
      File f = new File(path);
      File fDir = f.getParentFile();
      return fDir.toString();
   }
   public static String changeExtension(String filename, String ext) {
      if(filename == null || filename.isEmpty())
         return null;
         int idx = filename.lastIndexOf(".");
         if (idx <= 0) return null;
         String nameWithoutExt = filename.substring(0, idx);
         return nameWithoutExt + ext;
      }

   public static String removeExtension(String filename, String ext) {
      if(filename == null || filename.isEmpty())
         return null;
      int idx = filename.lastIndexOf(ext);
      if (0 <= idx)
         filename = filename.substring(0, idx);
      return filename;
   }
   public static ArrayList<String> ListAllFilesInDirectory(String path) {
      final ArrayList<String> files = new ArrayList<>();
      Path p = Paths.get(path);
      try {
         Stream<Path> ps = Files.list(p); // not recursive
         //Stream<Path> ps = Files.walk(p); // recursive
         ps.forEach(a -> {
            if (!Files.isDirectory(a))
               files.add(a.toString());
         });
         ps.close();
      } catch (IOException e) {e.printStackTrace();}

      return files;
   }

   public static ArrayList<String> ListFilesInDirectoryWithExtension(String path, String ext) {
      ArrayList<String> files   = ListAllFilesInDirectory(path);
      ArrayList<String> outputs = new ArrayList<>();
      for (int i = 0; i < files.size(); i++) {
         String s = files.get(i);
         boolean endsWith = (ext == null || ext.isEmpty())? true: s.endsWith(ext);
         if (endsWith) outputs.add(s);
      }
      return outputs;
   }
   public static ArrayList<String> DeleteAllFilesInDirectoryWithExtension(String path, String ext) {
      ArrayList<String> files = ListFilesInDirectoryWithExtension(path, ext);

      for (int i = 0; i < files.size(); i++) {
         DeleteFileIfExist(files.get(i));
      }
      return files;
   }
   public static void CopyFileToDirectory(String path, String dirToCopyResultTo) {
      if (null == path              || path             .isEmpty()) return;
      if (null == dirToCopyResultTo || dirToCopyResultTo.isEmpty()) return;
      if (!doesFileExist(path)) return;

      try {
         Path p1 = Paths.get(path);
         Path pf = p1.getFileName();
         Path p2 = Paths.get(dirToCopyResultTo, pf.toString());
         if (!Files.exists(p2.getParent()))
            Files.createDirectory(p2.getParent());
         Files.deleteIfExists(p2);
         Files.copy(p1, p2);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static boolean createDirIfDoesntExist(String dirPath){
      if (null == dirPath || dirPath.isBlank()) return false;
      // Check if the log directory exists
      var directory = new File(dirPath);
      if (!directory.exists()) {
         directory.mkdir();
      }
      return directory.exists();
   }
}
