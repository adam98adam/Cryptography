import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Cezar {

    private static int a=0,b=0,k=0;
    private static String data="";

    public static int getA() {
        return a;
    }

    public static void setA(int a) {
        Cezar.a = a;
    }

    public static int getB() {
        return b;
    }

    public static void setB(int b) {
        Cezar.b = b;
    }

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

    public static boolean aAffineValue(String str) {
        int[] a ={1,3,5,7,9,11,15,17,19,21,23,25};
        for(int i:a)
            if(i == Integer.parseInt(str))
                return true;
        return false;
    }

    public static boolean rightValue(String str,boolean caesar) {
        if(caesar)
            return Integer.parseInt(str) >= 1 && Integer.parseInt(str) <= 25;
        else
            return Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 25;
    }

    public static void getKeyCaesar(String path) {
        readFile(path);
        String[] vals = getData().split(" ");
        if(isNumeric(vals[0])) {
            if(rightValue(vals[0],true)){
                setK(Integer.parseInt(vals[0]));
            }else{
                System.out.println("k doesn't match the expected value");
                System.exit(0);
            }
        }else{
            System.out.println("k isn't integer");
            System.exit(0);
        }
    }


    public static void getKeyAffine(String path) {
        readFile(path);
        String[] vals = getData().split(" ");
        if(isNumeric((vals[0])) && isNumeric((vals[1]))) {
            if(aAffineValue(vals[0]) && rightValue(vals[1],false)) {
                setA(Integer.parseInt(vals[0]));
                setB(Integer.parseInt(vals[1]));
            }else {
                System.out.println("a and b  doesn't match the expected value");
                System.exit(0);
            }

        }else{
            System.out.println("Error");
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

    public static int gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static int modularInverse(int a,int m)
    {
        BigInteger bi1 = new BigInteger(String.valueOf(a));
        BigInteger bi2 = new BigInteger(String.valueOf(m));
        return bi1.modInverse(bi2).intValue();
    }

    public static String caesarEncrypt(String text, int k)
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

    public static String caesarDecrypt(String text ,int k) {
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
            readFile("files/crypto.txt");
            writeFile("files/decrypt.txt",caesarDecrypt(getData(),getK()),false);
        }
        else {
            System.exit(0);
        }
    }

    public static void caesarCryptogram() {
        readFile("files/crypto.txt");
        for(int k = 1; k < 26; k++) {
            writeFile("files/decrypt.txt",caesarDecrypt(getData(),getK()),true);
        }
    }

    public static String affineEncrypt(String text, int a,int b) {

        if(gcd(a,26) == 1) {
            StringBuffer result= new StringBuffer();
            for (int i=0; i<text.length(); i++)
            {
                if(text.charAt(i) == ' ')
                    result.append(' ');
                else if (Character.isUpperCase(text.charAt(i)))
                {
                    char ch = (char)((((a * (text.charAt(i) - 65)) + b) % 26) + 65);
                    result.append(ch);
                }
                else
                {
                    char ch = (char)((((a * (text.charAt(i) - 97)) + b) % 26) + 97);
                    result.append(ch);
                }
            }
            return result.toString();

        }
        else{
            System.out.println("gcd(" + a +",26) is diffrent than 1");
            return "Error";
        }
    }

    public static String affineDecrypt(String text, int a,int b) {

        if(gcd(a,26) == 1) {
            StringBuffer result= new StringBuffer();
            for (int i=0; i<text.length(); i++)
            {
                if(text.charAt(i) == ' ')
                    result.append(' ');
                else if (Character.isUpperCase(text.charAt(i)))
                {
                    char ch = (char)((Math.floorMod(modularInverse(a,26) * ((text.charAt(i)  - 65 - b)),26)) + 65);
                    result.append(ch);
                }
                else
                {
                    char ch = (char)((Math.floorMod(modularInverse(a,26) * ((text.charAt(i)  - 97 - b)),26)) + 97);
                    result.append(ch);
                }
            }
            return result.toString();

        }
        else{
            System.out.println("gcd(" + a +",26) is diffrent than 1");
            return "Error";
        }
    }

    public static void affineCryptogram() {
        int[] a ={1,3,5,7,9,11,15,17,19,21,23,25};
        readFile("files/crypto.txt");
        for(int i:a)
            for(int b=0;b<26;b++)
                writeFile("files/decrypt.txt",affineDecrypt(getData(),i,b) + " a = " + i + " b = " + b,true);

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
        getKeyAffine("files/key.txt");
        //readFile("files/plain.txt");
        //writeFile("files/crypto.txt",affineEncrypt(getData(),getA(),getB()),false);
        //affineCryptogram();
        //getKeyAffine("files/key.txt");
        //readFile("files/plain.txt");
        //System.out.println(affineEncrypt(getData(),getA(),getB()));
        //setData(affineEncrypt(getData(),getA(),getB()));
        //System.out.println(affineDecrypt(getData(),getA(),getB()));
        /*
        if(modularInverse(1,26) == 1){
            System.out.println("Hello");
        }else{
            System.out.println("Adam");
        }
        //caesarCryptogram();
        //getKey();
        //readFile("files/plain.txt");
        //writeFile("files/crypto.txt",encrypt(getData(),getK()));
        //readFile("files/crypto.txt");
        //writeFile("files/decrypt.txt",decrypt(getData(),getK()));
        */

    }
}


