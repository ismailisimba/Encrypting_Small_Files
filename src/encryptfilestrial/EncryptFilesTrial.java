
package encryptfilestrial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptFilesTrial {

 public  String FILE_TO_SEND = "E:\\cod\\server\\www.pdf";
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    static String IV  = "AAAAAAAAAAAAAAAa";
    static String plaintext = "1234567891234561"; /*Note null padding*/
    static String encryptionKey = "0123456789abcdef";
     int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    public final static String FILE_TO_RECEIVED = "E:\\cod\\client\\received.pdf";
    public final static String FILE_TO_RECEIVED2 = "E:\\cod\\client\\received2.pdf";
    public final static   int FILE_SIZE =1000000;
    public static byte [] mybytearrayread;
    public static byte [] mybytearraywrite;
    public static byte [] mybytearraywrite2;
    Key randomKey;
   
   
   public void fileRead() throws FileNotFoundException, IOException{
       
             File myFile = new File (FILE_TO_SEND);
             mybytearrayread = new byte [(int)myFile.length()];
             fis = new FileInputStream(myFile);
             bis = new BufferedInputStream(fis);
             bis.read(mybytearrayread,0,mybytearrayread.length);     
   }
   
   public void fileWrite() throws FileNotFoundException, IOException{
                 
          fos = new FileOutputStream(FILE_TO_RECEIVED);
          bos = new BufferedOutputStream(fos);
          bos.write(mybytearraywrite,0,mybytearraywrite.length);
          bos.flush();
   }
      public void fileWriteEncrypted() throws FileNotFoundException, IOException{
                 
          fos = new FileOutputStream(FILE_TO_RECEIVED2);
          bos = new BufferedOutputStream(fos);
          bos.write(mybytearraywrite2,0,mybytearraywrite2.length);
          bos.flush();
   }
   
   public  Key generateKey() throws Exception {
       KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
       keyGen.init(128); // for example
       SecretKey encryptionKey2 = keyGen.generateKey();
       return encryptionKey2;
}  
   
    public  byte[] encrypt(byte[] mybytearrayread,Key encryptionKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES", "BC");
    cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
    byte[] encVal = cipher.doFinal(mybytearrayread);
    return encVal;
  }
    
    public byte[] decrypt(byte[] encVal, Key encryptionKey) throws Exception{
    Cipher cipher = Cipher.getInstance("AES", "BC");
    cipher.init(Cipher.DECRYPT_MODE, encryptionKey);
    byte[] mybytearraywrite21 = cipher.doFinal(encVal);
    return mybytearraywrite21;
      } 
    
    
    
    
    
   
   
   
    public static void main(String[] args) throws IOException, Exception {
       
      EncryptFilesTrial object = new EncryptFilesTrial ();
      object.fileRead();
      Key encryptionkey =object.generateKey();
      byte[]encryptedfile= object.encrypt(mybytearrayread, encryptionkey);
      
      byte[]decryptedfile=object.decrypt(encryptedfile, encryptionkey);
      mybytearraywrite=decryptedfile;
      mybytearraywrite2=encryptedfile;
      object.fileWriteEncrypted();
      object.fileWrite();
      String encodedKey = Base64.getEncoder().encodeToString(encryptionkey.getEncoded());
      System.out.println("Good new is all lines executed\n and key is "+encodedKey);
      
        
           
    }
    
}
