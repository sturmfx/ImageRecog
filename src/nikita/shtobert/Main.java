package nikita.shtobert;

import java.awt.image.BufferedImage;

public class Main
{

    public static void main(String[] args)
    {
        BufferedImage b1 = Images.getImage("I:\\sky_test1.png"); // 1000 * 1000 main image
        BufferedImage b2 = Images.getImage("I:\\sky_test2.png"); // 200 * 100 sub-image

        Images obj = new Images(b1, b2);
        int [][] test = obj.normalSearch(); // poluchenije tablichi pixelnoj razniji izobrazenij esli verhnij levij ugol menshei kartinki prinjat za (x, y)
        System.out.println("X = " + obj.normalSearchX + "  Y = " + obj.normalSearchY + "  SUM of PIXEl DIFFERENCE= " + obj.normalMinSum); //vivod koordinat verhnego levogo pikselja naibolee podhodjashei zoni
        //obj.saveGrayScale("I:\\sky_test1g.png", "I:\\sky_test2g.png");
    }
}
