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

    public static void ecbEncrypt() throws IOException {
        byte[] header = new byte[54];
        int[] key = {1,0,1,0,1,1,1,0,1,0,1,0,1,0,0,1};
        BufferedImage bImage = ImageIO.read(new File("files/plain24bit.bmp"));
        System.out.println("Type = " + bImage.getType());
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int[] bmpPixels = bImage.getRGB(0, 0, width, height, null, 0, width);
        System.out.println("Size = " + bmpPixels.length);
        for (int a = 0; a < 100; a++)
            System.out.println(bmpPixels[a]);
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
        ImageIO.write(bImage,"bmp",new File("files/output.bmp"));
        System.out.println("Done");


    }


    public static void main(String[] args) throws IOException {
        ecbEncrypt();

    }
}
