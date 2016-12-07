import java.awt.image.BufferedImage;

/**
 * Interface contenant certaines fonctions communes à différents filtres.
 *
 * @author Matthieu LEON et Nathan URBAIN
 *
 */

public interface usage {

    /**
     * Permet de donner une approximation des gradients horizontaux pour chaque pixel.
     *
     * @param x
     *  Matrice de convolution en x
     * @param y
     *  Matrice de convolution en y
     * @param img
     *  Image dont les couleurs des pixels vont être récupérées.
     * @param i
     *  Pixel qui a comme position x i
     * @param j
     *  Pixel qui a comme position y j
     * @return Approximation des gradients horizontaux
     */
    double pixelX(double x[][], double y[][], BufferedImage img, int i, int j);

    /**
     * Permet de donner une approximation des gradients verticaux pour chaque pixel.
     *
     * @param x
     *  Matrice de convolution en x
     * @param y
     *  Matrice de convolution en y
     * @param img
     *  Image dont les couleurs des pixels vont être récupérées.
     * @param i
     *  Pixel qui a comme position x i
     * @param j
     *  Pixel qui a comme position y j
     * @return Approximation des gradients verticaux
     */
    double pixelY(double x[][], double y[][], BufferedImage img, int i, int j);

    /**
     * Renvoie l'image source.
     * @return Image source.
     */
    BufferedImage getImg();

    /**
     * Renvoie l'image Copie.
     * @return Image Copie.
     */
    BufferedImage getImgCopy();
}
