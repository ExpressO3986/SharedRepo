package frc.robot.Utilities;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConvertToCsv {
   static String seperator = ",";

   public static void ConvertRecordedDataToCsv(String dir)
   {
      // Specifier juste le repertoire, le rest de la conversion est automatique!
      String recExt = ".data", csvExt = ".csv";

      ArrayList<String> recFiles = DirFiles.ListFilesInDirectoryWithExtension(dir, recExt);
      ArrayList<String> csvFiles = DirFiles.ListFilesInDirectoryWithExtension(dir, csvExt);

      for (String recPath : recFiles) {
         String expectedCsvPath = DirFiles.changeExtension(recPath, csvExt);
         if (csvFiles.contains(expectedCsvPath)) // don't convert if file already was converted to CSV before
               continue;
         // WriteToCsv(Serialize.deserialize(recPath), expectedCsvPath);
         System.out.println("  Exported: " + expectedCsvPath);
      }
      System.out.println("All done!");
   }

   /** This is a generic .CSV file writer of numeric data only, where you specify the file path,
    * the column header, then provide every row data. You are responsible for having the exact
    * same number of elements in the header as well as in each row.
    * @param csvOutpoutFilePath
    * @param header
    * @param vals
    */
   public static void WriteToCsv(String csvOutpoutFilePath, String header, ArrayList<Double[]> vals) {
      if (null == vals) return;

      try (PrintWriter writer = new PrintWriter(new File(csvOutpoutFilePath))) {
         String colums = null;

         //--------------------------------------------
         colums = header;
         writer.println(colums);
         for (int i = 0; i < vals.size(); i++) {
            var row = vals.get(i);
            for (int j = 0; j < row.length; j++) {
               if (j == 0)
                  writer.print(row[j]);
               else
                  writer.print(seperator + row[j]);
            }
            writer.println();
         }
         writer.flush();

         //--------------------------------------------
         writer.close();

      } catch (Exception e) {
         System.out.println("   ERROR: "+ csvOutpoutFilePath);
         System.out.println(e.getStackTrace());
      }
   }
}
