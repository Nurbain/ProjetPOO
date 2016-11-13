/**
 * Created by Stery on 11/11/2016.
 */

import java.awt.*;
import javax.swing.*;
import java.lang.Object;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

public class main {

    public static BufferedImage niveaugris(String image) throws IOException {
        BufferedImage img=ImageIO.read(new File(image));
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {

                // recuperer couleur de chaque pixel
                Color pixelcolor= new Color(img.getRGB(i, j));

                // recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
                int r=pixelcolor.getRed();
                int g=pixelcolor.getGreen();
                int b=pixelcolor.getBlue();

                int intensite=(r+g+b)/3;
                int couleur = new Color(intensite,intensite,intensite).getRGB();

                img.setRGB(i,j,couleur);
            }
        }
        //ImageIO.write(img, "png",new File("nuance.jpg"));
        return img;
    }


    public static double pixelX(double x[][], double y[][], BufferedImage img, int i, int j) {
        double pixel_x = (x[0][0] * new Color(img.getRGB(i-1, j-1)).getRed()) + (x[0][1] * new Color(img.getRGB(i, j-1)).getRed()) + (x[0][2] * new Color(img.getRGB(i+1, j-1)).getRed()) +
                (x[1][0] * new Color(img.getRGB(i-1, j)).getRed())   + (x[1][1] * new Color(img.getRGB(i, j)).getRed())   + (x[1][2] * new Color(img.getRGB(i+1, j)).getRed()) +
                (x[2][0] * new Color(img.getRGB(i-1, j+1)).getRed()) + (x[2][1] * new Color(img.getRGB(i, j+1)).getRed()) + (x[2][2] * new Color(img.getRGB(i+1, j+1)).getRed());

        return pixel_x;
    }

    public static double pixelY(double x[][], double y[][], BufferedImage img, int i, int j) {
        double pixel_y = (y[0][0] * new Color(img.getRGB(i-1, j-1)).getRed()) + (y[0][1] * new Color(img.getRGB(i, j-1)).getRed()) + (y[0][2] * new Color(img.getRGB(i+1, j-1)).getRed()) +
                (y[1][0] * new Color(img.getRGB(i-1, j)).getRed())   + (y[1][1] * new Color(img.getRGB(i, j)).getRed())   + (y[1][2] * new Color(img.getRGB(i+1, j)).getRed()) +
                (y[2][0] * new Color(img.getRGB(i-1, j+1)).getRed()) + (y[2][1] * new Color(img.getRGB(i, j+1)).getRed()) + (y[2][2] * new Color(img.getRGB(i+1, j+1)).getRed());

        return pixel_y;
    }

    public static String new_name(String name_file, String filtre) {
        String[] tmp = name_file.split("[.]");
        String newName = tmp[0] + "_" + filtre + "." + tmp[1];
        return newName;
    }

    public static void filtresobel(String image) throws IOException {
		/* BufferedImage img=ImageIO.read(new File(image));
		BufferedImage imgcopy=ImageIO.read(new File(image)); */

        BufferedImage img = niveaugris(image);
        BufferedImage imgcopy = niveaugris(image);

        double sobel_x[][]={	{-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}};

        double sobel_y[][]={	{-1, -2, -1},
                {0,  0,  0},
                {1,  2,  1}};
        //Parcours tous les pixels
        for (int i = 1; i < img.getWidth()-1; i++) {
            for (int j = 1; j < img.getHeight()-1; j++) {

                //couleur red du pixel
                double pixelrx = pixelX(sobel_x, sobel_y, img, i, j);
                double pixelry = pixelY(sobel_x, sobel_y, img, i, j);
                double valr = Math.sqrt((pixelrx * pixelrx) + (pixelry * pixelry));

                if (valr<=0)
                {valr=0;}
                else
                if (valr>=255)
                {valr=255;}
                //Pixel vert
                double pixelgx = pixelX(sobel_x, sobel_y, img, i, j);
                double pixelgy = pixelY(sobel_x, sobel_y, img, i, j);

                double valg = Math.sqrt((pixelgx * pixelgx) + (pixelgy * pixelgy));

                if (valg<=0)
                {valg=0;}
                else
                if (valg>=255)
                {valg=255;}
                //Pixel bleu
                double pixelbx = pixelX(sobel_x, sobel_y, img, i, j);
                double pixelby = pixelY(sobel_x, sobel_y, img, i, j);
                double valb = Math.sqrt((pixelbx * pixelbx) + (pixelby * pixelby));

                if (valb<=0)
                {valb=0;}
                else
                if (valb>=255)
                {valb=255;}


                int couleur = new Color((int)valr,(int)valg,(int)valb).getRGB();
                imgcopy.setRGB(i,j,couleur);
            }
        }
        ImageIO.write(imgcopy, "png",new File(new_name(image,"sobel")));
    }



    public static void main(String[] args) throws IOException{

        JFileChooser chooser = new JFileChooser();

        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();
        String file = curFile.getAbsolutePath();
        System.out.println(file);
        filtresobel(file);
    }
}
