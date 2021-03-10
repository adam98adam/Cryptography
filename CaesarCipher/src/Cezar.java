import java.io.*;
import java.util.Scanner;

public class Cezar {

    private static int k=0;
    private static String data="";

    public static int getK() {
        return k;
    }

    public static void setK(int k) {
        Cezar.k = k;
    }

    public static String getData() {
        return data;
    }

    public static void setData(String data) {
        Cezar.data = data;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean rightValue(String str) {
        return Integer.parseInt(str) >= 1 && Integer.parseInt(str) <= 25;
    }

    public static void getKey(String path) {
        //String path = "files/key.txt";
        readFile(path);
        setData(getData().substring(0, getData().indexOf(" ")));
        if(isNumeric(getData())) {
            if(rightValue(getData())){
                setK(Integer.parseInt(getData()));
            }else{
                System.out.println("k doesn't match the expected value");
                System.exit(0);
            }
        }else{
            System.out.println("k isn't integer");
            System.exit(0);
        }
    }

    public static void readFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                setData( myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeFile(String path,String text,boolean append) {
        try {
            File myObj = new File(path);
            if (!myObj.exists()) {
                myObj.createNewFile();
            }
            FileWriter fw = new FileWriter(myObj,append);
            fw.write(text + "\n");
            fw.close();
            //PrintWriter pw = new PrintWriter(myObj);
            //pw.println(text);
            //pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String encrypt(String text, int k)
    {
        StringBuffer result= new StringBuffer();

        for (int i=0; i<text.length(); i++)
        {
            if(text.charAt(i) == ' ')
                result.append(' ');
            else if (Character.isUpperCase(text.charAt(i)))
            {
                char ch = (char)(((int)text.charAt(i) +
                        k - 65) % 26 + 65);
                result.append(ch);
            }
            else
            {
                char ch = (char)(((int)text.charAt(i) +
                        k - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text ,int k) {
        {
            StringBuffer result= new StringBuffer();

            for (int i=0; i<text.length(); i++)
            {
                if(text.charAt(i) == ' ')
                    result.append(' ');
                else if (Character.isUpperCase(text.charAt(i)))
                {
                    char ch = (char)(Math.floorMod((int)text.charAt(i) -
                            k - 65,26) + 65);
                    result.append(ch);
                }
                else
                {
                    char ch = (char)(Math.floorMod((int)text.charAt(i) -
                            k - 97,26) + 97);
                    result.append(ch);
                }
            }
            return result.toString();
        }
    }

    public static void caesarCrypto() {
        char a,b;
        readFile("files/crypto.txt");
        a = getData().charAt(0);
        readFile("files/extra.txt");
        b = getData().charAt(0);
        setK(Math.floorMod((int)a - (int)b,26));
        System.out.println(getK());
        if(getK() >= 1 && 25 >= getK()){
            writeFile("files/key-found.txt",String.valueOf(getK()),false);
        }
        else {
            System.exit(0);
        }
    }

    public static void caesarCryptogram() {
        readFile("files/crypto.txt");
        for(int k = 1; k < 26; k++) {
            writeFile("files/decrypt.txt",decrypt(getData(),k),true);
        }
    }



    public static void core(char cipher,char operation) {
        if((cipher == 'a' || cipher == 'c') && (operation == 'e' || operation == 'd' || operation == 'j') || operation == 'k') {
            if(cipher == 'a') {
                System.out.println("Hello");
            }else {
                System.out.println("World");
            }
        }else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        caesarCryptogram();
        //etKey();
        //readFile("files/plain.txt");
        //writeFile("files/crypto.txt",encrypt(getData(),getK()));
        //readFile("files/crypto.txt");
        //writeFile("files/decrypt.txt",decrypt(getData(),getK()));
    }
}


