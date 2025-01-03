package frc.robot.Utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize {

   /**
   * Serialize any object to a file
   *
   * @param <T> Any object class
   * @param data The object itself
   * @param filepath Filepath for the output file
   */
   public static <T> void serialize(T data, String filepath) {
      try {
         FileOutputStream fileOutputStream = new FileOutputStream(filepath);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
         objectOutputStream.writeObject(data);
         fileOutputStream.flush();
         fileOutputStream.close();
      } catch (Exception e) {
         System.err.println("Could not serialize an object ...");
         e.printStackTrace();
      }
   }

   /**
   * Deserialize from a file to an object
   *
   * @param <T> Any object class
   * @param filepath Filepath for the input file
   * @return The loaded class instance
   */
   @SuppressWarnings("unchecked")
   public static <T> T deserialize(String filepath) {
      File file = new File(filepath);
      if (!file.exists()) return null; // file does not exist

      T loadedObject = null;
      try {
         FileInputStream fileInputStream = new FileInputStream(filepath);
         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
         // If the loaded object is not of the same type as the variable assigned to it
         // (T), this line will throw an error
         loadedObject = (T) objectInputStream.readObject();
         objectInputStream.close();
         fileInputStream.close();
         return loadedObject;
      } catch (Exception e) {
         System.err.println("File could not be deserialized ...");
         e.printStackTrace();
         return null;
      }
   }

   /**
   * Serialize any object to a list of byte
   *
   * @param <T> Any object class
   * @param obj The object itself
   * @return List of byte containing the data of the object
   */
   public static <T> byte[] serializeToByte(T obj) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try {
         ObjectOutputStream outputStream = new ObjectOutputStream(out);
         outputStream.writeObject(obj);
         outputStream.flush();
         return out.toByteArray();
      } catch (Exception e) {
         System.err.println("Could not serialize an object to bytes ...");
         e.printStackTrace();
         return null;
      }
   }

   /**
   * Deserialize from a list of byte to an object
   *
   * @param <T> Any object class
   * @param data The list of byte
   * @return The object contained in the list of byte
   */
   @SuppressWarnings("unchecked")
   public static <T> T deserializeFromByte(byte[] data) {
      try {
         ByteArrayInputStream in = new ByteArrayInputStream(data);
         ObjectInputStream inputStream = new ObjectInputStream(in);
         // If the constructed object is not of the same type as the variable assigned to
         // it (T) this line will throw an error
         return (T) inputStream.readObject();
      } catch (Exception e) {
         System.err.println("Could not deserialize a list of byte to an object ...");
         e.printStackTrace();
         return null;
      }
   }

   /**
   * This function creates a exact deep copy of the object's memory
   *
   * @param origin The object to clone
   * @return Return the same information of the origin but in a different memory address
   */
   public static <T> T clone(T origin) {
      T copied = null;
      if (copied == null) {
         byte[] bytes = Serialize.serializeToByte(origin);
         copied = Serialize.deserializeFromByte(bytes);
      }
      return copied;
   }
}
