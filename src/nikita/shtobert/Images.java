package nikita.shtobert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Images
{
    BufferedImage bi1;
    BufferedImage bi2;
    int[][] im1;
    int[][] im2;

    int width1;
    int width2;

    int height1;
    int height2;

    int widthDif;
    int heightDif;

    int normalSearchX = 0;
    int normalSearchY = 0;
    int normalMinSum = Integer.MAX_VALUE;

    public Images(BufferedImage b1, BufferedImage b2)
    {
        bi1 = clone(b1);
        bi2 = clone(b2);

        width1 = b1.getWidth();
        height1 = b1.getHeight();

        width2 = b2.getWidth();
        height2 = b2.getHeight();

        widthDif = width1 - width2;
        heightDif = height1 - height2;

        im1 = new int[height1][width1];
        im2 = new int[height2][width2];

        int p;
        byte r;

        for(int y = 0; y < height1; y++)
        {
            for(int x = 0; x < width1; x++)
            {
                p = b1.getRGB(x, y);
                r = (byte) ((p >> 16) & 0xff);
                im1[y][x] = r;
            }
        }

        for(int y = 0; y < height2; y++)
        {
            for(int x = 0; x < width2; x++)
            {
                p = b2.getRGB(x, y);
                r = (byte) ((p >> 16) & 0xff);
                im2[y][x] = r;
            }
        }
    }

    public void saveGrayScale(String s1, String s2)
    {
        BufferedImage result1 = new BufferedImage(width1, height1, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage result2 = new BufferedImage(width2, height2, BufferedImage.TYPE_BYTE_GRAY);

        for(int y = 0; y < height1; y++)
        {
            for(int x = 0; x < width1; x++)
            {
                int value = im1[y][x] << 16 | im1[y][x] << 8 | im1[y][x];
                result1.setRGB(x, y, value);
            }
        }

        for(int y = 0; y < height2; y++)
        {
            for(int x = 0; x < width2; x++)
            {
                int value = im2[y][x] << 16 | im2[y][x] << 8 | im2[y][x];
                result2.setRGB(x, y, value);
            }
        }

        saveImage(result1, s1);
        saveImage(result2, s2);
    }

    public int[][] normalSearch()
    {
        int[][] result_table = new int[height1][width1];

        for(int i = 0; i < height1; i++)
        {
            for(int j = 0; j < width1; j++)
            {
                result_table[i][j] = Integer.MAX_VALUE;
            }
        }

        int tempSum = 0;
        int maxX = 0;
        int maxY = 0;
        int minSum = Integer.MAX_VALUE;

        for(int y = 0; y < height1; y++)
        {
            for(int x = 0; x < width1; x++)
            {
                tempSum = 0;
                if((y < heightDif) && (x < widthDif))
                {
                    for (int y1 = 0; y1 < height2; y1++)
                    {
                        for (int x1 = 0; x1 < width2; x1++)
                        {
                            tempSum += Math.abs(im2[y1][x1] - im1[y + y1][x + x1]);
                        }
                    }

                    result_table[y][x] = tempSum;
                    if(tempSum < minSum)
                    {
                        maxX = x;
                        maxY = y;
                        minSum = tempSum;
                    }
                }
                else
                {

                }
            }
        }

        normalSearchX = maxX;
        normalSearchY = maxY;
        normalMinSum = minSum;

        return result_table;
    }

    public static final BufferedImage clone(BufferedImage image)
    {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }

    public static void saveImage(BufferedImage bi, String s)
    {
        File f;
        try
        {
            f = new File(s);
            ImageIO.write(bi, "png", f);
            System.out.println("SAVED IMAGE");
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public static BufferedImage getImage(String s)
    {
        BufferedImage img = null;
        File f = null;


        try
        {
            f = new File(s);
            img = ImageIO.read(f);

        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return img;
    }

    public static void toCSV(int[][] ar, int width, int height, String s) throws IOException
    {

    }
}