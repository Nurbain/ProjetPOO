/**
 * Created by Nathan on 28/11/2016.
 */

import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class nuanceGris {
    public BufferedImage img;

    public nuanceGris() {}

    public nuanceGris(File image) throws IOException{
        this.img = ImageIO.read(image);
    }

    public nuanceGris(BufferedImage image) throws IOException{
        this.img = image;
    }

    public void niveauGris() throws IOException {
        for (int i = 0; i < this.img.getWidth(); i++) {
            for (int j = 0; j < this.img.getHeight(); j++) {

                // recuperer couleur de chaque pixel
                Color pixelcolor = new Color(this.img.getRGB(i, j));

                // recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
                int r = pixelcolor.getRed();
                int g  =pixelcolor.getGreen();
                int b = pixelcolor.getBlue();

                int intensite=(r+g+b)/3;
                int couleur = new Color(intensite,intensite,intensite).getRGB();

                this.img.setRGB(i,j,couleur);
            }
        }
        //ImageIO.write(img, "png",new File("nuance.jpg"));
        //return img;
    }





}
