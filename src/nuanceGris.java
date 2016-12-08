/**
 * Fichier comportant toutes les fonctions pour effectuer une image en nuance de gris à partir de l'image source.
 * @author Matthieu LEON et Nathan URBAIN
 *
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class nuanceGris {
    public BufferedImage img;

    public nuanceGris() {
    }

    /**
     * Constructeur à partir d'un fichier image.
     * @param image
     *  File contenant l'image source.
     * @throws IOException Pour l'ouverture de l'image
     */
    public nuanceGris(File image) throws IOException {
        this.img = ImageIO.read(image);
    }


    /**
     * Fonction effectuant la nuance de Gris sur 'img'
     * @throws IOException Pour l'ouverture de l'image
     */
    public void niveauGris() throws IOException {
        for (int i = 0; i < this.img.getWidth(); i++) {
            for (int j = 0; j < this.img.getHeight(); j++) {

                // recuperer couleur de chaque pixel
                Color pixelcolor = new Color(this.img.getRGB(i, j));

                // recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
                int r = pixelcolor.getRed();
                int g = pixelcolor.getGreen();
                int b = pixelcolor.getBlue();

                int intensite = (r + g + b) / 3;
                int couleur = new Color(intensite, intensite, intensite).getRGB();

                this.img.setRGB(i, j, couleur);
            }
        }
    }
}
