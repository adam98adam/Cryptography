import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class Block {


    public static void ecbEncrypt1d() throws IOException {
        int[] key = {1,0,1,1,1,1,1,0,1,0,1,1,1,0,0,1};
        BufferedImage bImage = ImageIO.read(new File("files/plain24bit.bmp"));
        System.out.println("Type = " + bImage.getType());
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int[] bmpPixels = bImage.getRGB(0, 0, width, height, null, 0, width);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "bmp", bos);
        for(int i=0;i < 9000;i++) {
            for(int k=0;k < 4*4;k++) {
                if(key[k] == 1) {
                    if(bmpPixels[k + i*16] == -1)
                        bmpPixels[k + i*16] = -16777216;
                    else
                        bmpPixels[k + i*16] = -1;
                }
            }
        }
        bImage.setRGB(0,0,width,height,bmpPixels,0,width);
        ImageIO.write(bImage,"bmp",new File("files/output1.bmp"));
        System.out.println("Done");


    }

    public static void ecbEncrypt() throws IOException {
        int[] key = {1,1,0,1,0,0,1,1,0,1,1,1,0,1,1,1};
        BufferedImage bImage = ImageIO.read(new File("files/plain24bit.bmp"));
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int blockWidthCount = (width / 4);
        int blockHeightCount = (height / 4);
        int blockPixelWidth = 4;
        int blockPixelHeight = 4;
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
        ImageIO.write(bImage,"bmp",new File("files/output2.bmp"));
    }





        //int[] bmpPixels = bImage.getRGB(0, 0, width, height, null, 0, width);
        //System.out.println("Size = " + bmpPixels.length);
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //ImageIO.write(bImage, "bmp", bos);
        //for(int i=0;i < 9000;i++) {
            //for(int k=0;k < 4*4;k++) {
                //if(key[k] == 1) {
                   // if(bmpPixels[k + i*16] == -1)
                     //   bmpPixels[k + i*16] = -16777216;
                    //else
                     //   bmpPixels[k + i*16] = -1;
                //}
           // }

        //bImage.setRGB(0,0,width,height,bmpPixels,0,width);
       // ImageIO.write(bImage,"bmp",new File("files/ecboutput.bmp"));
        //System.out.println("Done ebc");
        //Color c = new Color(-16777216,false);


    public static void cbcEncryption() throws IOException {
        int[] key = {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1};
        BufferedImage bImage = ImageIO.read(new File("files/plain24bit.bmp"));
        int width = bImage.getWidth();
        int height = bImage.getHeight();

    }


    public static void main(String[] args) throws IOException {
        //ecbEncrypt1d();
        ecbEncrypt();
        //cbcEncryption();
    }
}
