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

    public static int ifLowerUpperCase(Character c) {
        if(Character.isUpperCase(c))
            return (int)(c) - 65;
        else
            return (int)(c) - 97;
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

    public static boolean aAffineValue(String str) {
        int[] a ={1,3,5,7,9,11,15,17,19,21,23,25};
        for(int i:a)
            if(i == Integer.parseInt(str) && gcd(Integer.parseInt(str),26) == 1)
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
                System.out.println("Key = " + getK());
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
                System.out.println("Key = (" + getA() + "," + getB() + ")");
            }else {
                System.out.println("a and b  doesn't match the expected value");
                System.exit(0);
            }

        }else{
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static boolean ifFileExists(String path) {
        File myObj = new File(path);
        return myObj.exists();
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
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static boolean ifLetter(char c) {
        return (int) c >= 65 && (int) c <= 90 || (int) c >= 97 && (int) c <= 122;
    }

    public static String caesarEncrypt(String text, int k) {
        StringBuffer result= new StringBuffer();
        for (int i=0; i<text.length(); i++)
        {
            if(ifLetter(text.charAt(i))) {
                if(Character.isUpperCase(text.charAt(i))) {
                    char ch = (char)(((int)text.charAt(i) + k - 65) % 26 + 65);
                    result.append(ch);
                }else {
                    char ch = (char)(((int)text.charAt(i) + k - 97) % 26 + 97);
                    result.append(ch);
                }
            }else {
                result.append(text.charAt(i));
             }
        }
        System.out.println("Encrypt text = " + result.toString());
        return result.toString();
    }

    public static String caesarDecrypt(String text ,int k) {
            StringBuffer result= new StringBuffer();
            for (int i=0; i<text.length(); i++)
            {
                if(ifLetter(text.charAt(i))){
                    if(Character.isUpperCase(text.charAt(i))){
                        char ch = (char)(Math.floorMod((int)text.charAt(i) - k - 65,26) + 65);
                        result.append(ch);
                    }else {
                        char ch = (char)(Math.floorMod((int)text.charAt(i) - k - 97,26) + 97);
                        result.append(ch);
                    }
                }else {
                    result.append(text.charAt(i));
                }
            }
            return result.toString();
    }

    public static void caesarCrypto() {
        char a,b;
        readFile("files/crypto.txt");
        a = getData().charAt(0);
        readFile("files/extra.txt");
        System.out.println("Known-plaintext = " + getData());
        b = getData().charAt(0);
        setK(Math.floorMod((int)a - (int)b,26));
        if(getK() >= 1 && 25 >= getK()){
            System.out.println("Calculated key = " + getK());
            writeFile("files/key-found.txt",String.valueOf(getK()),false);
            readFile("files/crypto.txt");
            System.out.println("Encrypt text = " + getData());
            System.out.println("Decrypt text = " + caesarDecrypt(getData(),getK()));
            writeFile("files/decrypt.txt",caesarDecrypt(getData(),getK()),false);
        }
        else {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static void caesarCryptogram() {
        readFile("files/crypto.txt");
        for(int k = 1; k < 26; k++) {
            writeFile("files/decrypt.txt",caesarDecrypt(getData(),k) + " k = " + k,true);
        }
    }

    public static String affineEncrypt(String text, int a,int b) {
            StringBuffer result= new StringBuffer();
            for(int i=0; i<text.length(); i++) {
                if(ifLetter(text.charAt(i))) {
                    if(Character.isUpperCase(text.charAt(i))) {
                        char ch = (char) ((((a * (text.charAt(i) - 65)) + b) % 26) + 65);
                        result.append(ch);
                    } else {
                        char ch = (char) ((((a * (text.charAt(i) - 97)) + b) % 26) + 97);
                        result.append(ch);
                    }
                } else {
                    result.append(text.charAt(i));
                }
            }
            System.out.println("Encrypt text = " + result.toString());
            return result.toString();
    }

    public static String affineDecrypt(String text, int a,int b) {
            StringBuffer result= new StringBuffer();
            for (int i=0; i<text.length(); i++) {
                if (ifLetter(text.charAt(i))) {
                    if (Character.isUpperCase(text.charAt(i))) {
                        char ch = (char) ((Math.floorMod(modularInverse(a, 26) * ((text.charAt(i) - 65 - b)), 26)) + 65);
                        result.append(ch);
                    } else {
                        char ch = (char) ((Math.floorMod(modularInverse(a, 26) * ((text.charAt(i) - 97 - b)), 26)) + 97);
                        result.append(ch);
                    }
                } else {
                    result.append(text.charAt(i));
                }
            }
            return result.toString();
        }

    public static void affineCrypto() {
        int a = 0, b = 0, x1 = 0, fx1 = 0, x2 = 0, fx2 = 0, q = 0, p = 0;
        readFile("files/extra.txt");
        System.out.println("Known-plaintext = " + getData());
        x1 = ifLowerUpperCase(getData().charAt(0));
        x2 = ifLowerUpperCase(getData().charAt(1));
        readFile("files/crypto.txt");
        fx1 = ifLowerUpperCase(getData().charAt(0));
        fx2 = ifLowerUpperCase(getData().charAt(1));
        q = Math.floorMod(fx1 - fx2, 26);
        p = Math.floorMod(x1 - x2, 26);
        if (q % p == 0) {
            q = q / p;
            a = q;
        } else {
            q = q * modularInverse(p, 26);
            q = Math.floorMod(q, 26);
            a = q;
        }
        b = Math.floorMod(fx1 - a * x1, 26);
        if (aAffineValue(String.valueOf(a)) && rightValue(String.valueOf(b),false)) {
            writeFile("files/key-found.txt", a + " " + b, false);
            System.out.println("Calculated key = (" + a +"," + b + ")");
            readFile("files/crypto.txt");
            System.out.println("Encrypt text = " + getData());
            System.out.println("Decrypt text = " + affineDecrypt(getData(),a,b));
            writeFile("files/decrypt.txt",affineDecrypt(getData(),a,b),false);
        } else {
            System.out.println("Error");
            System.exit(0);
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
            if(cipher == 'c') {
                if(operation == 'e') {
                    System.out.println("---Caesar Encrypt---");
                    getKeyCaesar("files/key.txt");
                    readFile("files/plain.txt");
                    System.out.println("Text = " + getData());
                    writeFile("files/crypto.txt",caesarEncrypt(getData(),getK()),false);
                } else if(operation == 'd') {
                    System.out.println("---Caesar Decrypt---");
                    getKeyCaesar("files/key.txt");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Efifsnj12 e 12pwduytlwfknn",false);
                    readFile("files/crypto.txt");
                    System.out.println("Encrypt text = " + getData());
                    System.out.println("Decrypt text = " + caesarDecrypt(getData(),getK()));
                    writeFile("files/decrypt.txt",caesarDecrypt(getData(),getK()),false);
                } else if(operation == 'j') {
                    System.out.println("---Caesar Known-plaintext attack---");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Efifsnj12 e 12pwduytlwfknn",false);
                    caesarCrypto();
                } else {
                    System.out.println("---Caesar Brute-force attack--- ");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Efifsnj12 e 12pwduytlwfknn",false);
                    caesarCryptogram();
                    System.out.println("Attack completed");
                }
            } else {
                if(operation == 'e') {
                    System.out.println("---Affine Encrypt---");
                    getKeyAffine("files/key.txt");
                    readFile("files/plain.txt");
                    System.out.println("Text = " + getData());
                    writeFile("files/crypto.txt",affineEncrypt(getData(),getA(),getB()),false);
                } else if(operation == 'd') {
                    System.out.println("---Affine Decrypt---");
                    getKeyAffine("files/key.txt");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Dixivwc98 d 98gpyfzampihww",false);
                    readFile("files/crypto.txt");
                    System.out.println("Encrypt text = " + getData());
                    System.out.println("Decrypt text = " + affineDecrypt(getData(),getA(),getB()));
                    writeFile("files/decrypt.txt",affineDecrypt(getData(),getA(),getB()),false);

                } else if(operation == 'j') {
                    System.out.println("---Affine Known-plaintext attack---");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Dixivwc98 d 98gpyfzampihww",false);
                    affineCrypto();
                } else {
                    System.out.println("---Affine Brute-force attack--- ");
                    if(!ifFileExists("files/crypto.txt"))
                        writeFile("files/crypto.txt","Dixivwc98 d 98gpyfzampihww",false);
                    affineCryptogram();
                    System.out.println("Attack completed");
                }
            }
        }else {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        core(args[0].charAt(0),args[1].charAt(0));
    }
}