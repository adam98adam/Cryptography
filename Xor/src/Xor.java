import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Xor {

    public static void prepare() {
        try {
            File orig = new File("files/orig.txt");
            File plain = new File("files/plain.txt");
            StringBuilder sb = new StringBuilder();
            String text="";
            Reader reader = new InputStreamReader(new FileInputStream(orig), StandardCharsets.UTF_8);
            BufferedReader fin = new BufferedReader(reader);
            Writer writer = new OutputStreamWriter(new FileOutputStream(plain), StandardCharsets.UTF_8);
            BufferedWriter fout = new BufferedWriter(writer);
            text = fin.readLine();
            while (text != null) {
                text = text.replaceAll("[^a-zA-Z ]", "");
                text = text.toLowerCase();
                sb.append(text, 0, 25);
                sb.append("\n");
                text = fin.readLine();
            }
            fout.write(sb.toString());
            fin.close();
            fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] key()  {
        try {
            File key = new File("files/key.txt");
            Reader reader = new InputStreamReader(new FileInputStream(key), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().getBytes(StandardCharsets.UTF_8);

        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public static void encrypt(byte[] key) {
        try {
            File plain = new File("files/plain.txt");
            File crypto = new File("files/crypto.txt");
            StringBuilder sb = new StringBuilder();
            String text="";
            Reader reader = new InputStreamReader(new FileInputStream(plain), StandardCharsets.UTF_8);
            BufferedReader brplain = new BufferedReader(reader);
            Writer writer = new OutputStreamWriter(new FileOutputStream(crypto), StandardCharsets.UTF_8);
            BufferedWriter bwcrypto = new BufferedWriter(writer);
            text = brplain.readLine();
            byte[] bplain = text.getBytes(StandardCharsets.UTF_8);
            while(text != null) {
                for(int i=0; i<text.length();i++) {
                    sb.append((char)(bplain[i] ^ key[i]));
                }
                sb.append("\n");
                text = brplain.readLine();
                if(text != null)
                    bplain = text.getBytes(StandardCharsets.UTF_8);
            }
            bwcrypto.write(sb.toString());
            brplain.close();
            bwcrypto.close();
        } catch (IOException e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
    }

    public static void cryptanalysis() {
        try{
            File crypto = new File("files/crypto.txt");
            File decrypt = new File("files/decrypt.txt");
            StringBuilder sb = new StringBuilder();
            int length = 25;
            byte[] key = new byte[length];
            Arrays.fill(key,(byte)0);
            String text1="";
            String text2="";
            Reader reader = new InputStreamReader(new FileInputStream(crypto), StandardCharsets.UTF_8);
            BufferedReader brcrypto = new BufferedReader(reader);
            Reader read = new InputStreamReader(new FileInputStream(crypto), StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(read);
            Writer writer = new OutputStreamWriter(new FileOutputStream(decrypt), StandardCharsets.UTF_8);
            BufferedWriter bwdecrypt = new BufferedWriter(writer);
            text1 = brcrypto.readLine();
            text2 = brcrypto.readLine();
            byte[] btext1 = text1.getBytes(StandardCharsets.UTF_8);
            byte[] btext2 = text2.getBytes(StandardCharsets.UTF_8);
            while(text2 != null) {
                for(int i=0;i<length;i++) {
                    if(key[i] == (byte)0) {
                        if(btext1[i] >= (byte)65 || btext2[i] >= (byte)65)
                            if(btext1[i] < (byte)65)
                                key[i] = (byte) (btext2[i] ^ (byte)32);
                            else if(btext2[i] < (byte)65)
                                key[i] = (byte) (btext1[i] ^ (byte)32);
                            else if(btext1[i] != btext2[i])
                                key[i] = (byte)32;
                    }
                }
                text1 = text2;
                btext1 = text1.getBytes(StandardCharsets.UTF_8);
                text2 = brcrypto.readLine();
                if(text2 != null)
                    btext2 = text2.getBytes(StandardCharsets.UTF_8);
            }
            brcrypto.close();
            text1 = buffer.readLine();
            btext1 = text1.getBytes(StandardCharsets.UTF_8);
            while(text1 != null) {
                for(int i=0; i<text1.length();i++) {
                    sb.append((char)(btext1[i] ^ key[i]));
                }
                sb.append("\n");
                text1 = buffer.readLine();
                if(text1 != null)
                    btext1 = text1.getBytes(StandardCharsets.UTF_8);
            }
            bwdecrypt.write(sb.toString());
            bwdecrypt.close();
            buffer.close();
        } catch (IOException e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        switch(args[0]) {
            case "-p": prepare(); break;
            case "-e": encrypt(key()); break;
            case "-k": cryptanalysis();
        }
    }
}
