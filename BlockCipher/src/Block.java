import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.*;

public class Block {
    public static void ecbEncrypt(String path) throws IOException {
        int[] key;
        Random rnd = new Random();
        BufferedImage bImage = ImageIO.read(new File(path));
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int blockPixelWidth = 4;
        int blockPixelHeight = 4;
        int blockWidthCount = (width / blockPixelWidth);
        int blockHeightCount = (height / blockPixelHeight);
        key= new int[blockPixelHeight * blockPixelWidth];
        for(int i=0;i < blockPixelWidth * blockPixelHeight;i++)
            key[i] = rnd.nextInt(2);
        for (int xblock = 0; xblock < blockHeightCount; xblock++) {
            for (int yblock = 0; yblock < blockWidthCount; yblock++) {
                int count = 0;
                for (int x = blockPixelHeight * xblock; x < blockPixelHeight * (xblock + 1); x++)
                    for (int y = blockPixelWidth * yblock; y < blockPixelWidth * (yblock + 1); y++) {
                        if (key[count] == 1) {
                            if (bImage.getRGB(y, x) == -1)
                                bImage.setRGB(y, x, -16777216);
                            else
                                bImage.setRGB(y, x, -1);
                        }
                        count++;
                    }
            }
        }
        ImageIO.write(bImage,"bmp",new File("files/ecb_crypto.bmp"));
    }

    public static void cbcEncryption(String path) throws IOException {
        int[] vector,key;
        Random rnd = new Random();
        BufferedImage bImage = ImageIO.read(new File(path));
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int blockPixelWidth = 4;
        int blockPixelHeight = 4;
        int blockWidthCount = (width / blockPixelWidth);
        int blockHeightCount = (height / blockPixelHeight);
        vector = new int[blockPixelWidth * blockPixelHeight];
        key = new int[blockPixelWidth * blockPixelHeight];
        for(int i=0;i < blockPixelWidth * blockPixelHeight;i++ ) {
            vector[i] = rnd.nextInt(2);
            key[i] = rnd.nextInt(2);
        }
        for (int xblock = 0; xblock < blockHeightCount; xblock++) {
            for (int yblock = 0; yblock < blockWidthCount; yblock++) {
                int count = 0;
                for (int x = blockPixelHeight * xblock; x < blockPixelHeight * (xblock + 1); x++) {
                    for (int y = blockPixelWidth * yblock; y < blockPixelWidth * (yblock + 1); y++) {
                        if (bImage.getRGB(y, x) == -16777216)
                            vector[count] = 1 ^ vector[count];
                        if (key[(count * x * y) % (count + 1)] == 1) {
                            bImage.setRGB(y, x, -16777216);
                            vector[count] = 1;
                        } else {
                            bImage.setRGB(y, x, -1);
                            vector[count] = 0;
                        }
                        count++;
                    }
                }
            }
        }
        ImageIO.write(bImage,"bmp",new File("files/cbc_crypto.bmp"));
    }

    public static void main(String[] args) throws IOException {
        ecbEncrypt("files/plain24bit.bmp");
        cbcEncryption("files/plain24bit.bmp");
    }
}
