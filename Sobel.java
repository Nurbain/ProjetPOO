import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stery on 14/11/2016.
 */
public class Sobel extends nuanceGris implements usage{

    private nuanceGris img;
    private nuanceGris imgCopy;
    private double sobel_x[][]={{-1, 0, 1},
                                {-2, 0, 2},
                                {-1, 0, 1}};
    private double sobel_y[][]={{-1, -2, -1},
                                {0,  0,  0},
                                {1,  2,  1}};

    public Sobel() {}

    public Sobel(File image, String Path) throws IOException{
        img = new nuanceGris(image);
        imgCopy = new nuanceGris(image);
        //path = Path;
    }

    public Sobel(File image) throws IOException{
        img = new nuanceGris(image);
        imgCopy = new nuanceGris(image);
    }

    public void filtreSobel() throws IOException {
		/* BufferedImage img=ImageIO.read(new File(image));
		BufferedImage imgCopy=ImageIO.read(new File(image)); */

        img.niveauGris();
        imgCopy.niveauGris();

        //Parcours tous les pixels
        for (int i = 1; i < img.img.getWidth()-1; i++) {
            for (int j = 1; j < img.img.getHeight()-1; j++) {

                //couleur red du pixel
                double pixelrx = pixelX(sobel_x, sobel_y, img.img, i, j);
                double pixelry = pixelY(sobel_x, sobel_y, img.img, i, j);
                double valr = Math.sqrt((pixelrx * pixelrx) + (pixelry * pixelry));

                if (valr<=0)
                    {valr=0;}
                else
                    if (valr>=255)
                        {valr=255;}
                //Pixel vert
                double pixelgx = pixelX(sobel_x, sobel_y, img.img, i, j);
                double pixelgy = pixelY(sobel_x, sobel_y, img.img, i, j);

                double valg = Math.sqrt((pixelgx * pixelgx) + (pixelgy * pixelgy));

                if (valg<=0)
                    {valg=0;}
                else
                    if (valg>=255)
                        {valg=255;}
                //Pixel bleu
                double pixelbx = pixelX(sobel_x, sobel_y, img.img, i, j);
                double pixelby = pixelY(sobel_x, sobel_y, img.img, i, j);
                double valb = Math.sqrt((pixelbx * pixelbx) + (pixelby * pixelby));

                if (valb<=0)
                    {valb=0;}
                else
                    if (valb>=255)
                        {valb=255;}


                int couleur = new Color((int)valr,(int)valg,(int)valb).getRGB();
                this.imgCopy.img.setRGB(i,j,couleur);
            }
        }
        
    }

    public double pixelX(double x[][], double y[][], BufferedImage img, int i, int j) {
        double pixel_x = (x[0][0] * new Color(img.getRGB(i-1, j-1)).getRed()) + (x[0][1] * new Color(img.getRGB(i, j-1)).getRed()) + (x[0][2] * new Color(img.getRGB(i+1, j-1)).getRed()) +
                (x[1][0] * new Color(img.getRGB(i-1, j)).getRed())   + (x[1][1] * new Color(img.getRGB(i, j)).getRed())   + (x[1][2] * new Color(img.getRGB(i+1, j)).getRed()) +
                (x[2][0] * new Color(img.getRGB(i-1, j+1)).getRed()) + (x[2][1] * new Color(img.getRGB(i, j+1)).getRed()) + (x[2][2] * new Color(img.getRGB(i+1, j+1)).getRed());

        return pixel_x;
    }

    public double pixelY(double x[][], double y[][], BufferedImage img, int i, int j) {
        double pixel_y = (y[0][0] * new Color(img.getRGB(i-1, j-1)).getRed()) + (y[0][1] * new Color(img.getRGB(i, j-1)).getRed()) + (y[0][2] * new Color(img.getRGB(i+1, j-1)).getRed()) +
                (y[1][0] * new Color(img.getRGB(i-1, j)).getRed())   + (y[1][1] * new Color(img.getRGB(i, j)).getRed())   + (y[1][2] * new Color(img.getRGB(i+1, j)).getRed()) +
                (y[2][0] * new Color(img.getRGB(i-1, j+1)).getRed()) + (y[2][1] * new Color(img.getRGB(i, j+1)).getRed()) + (y[2][2] * new Color(img.getRGB(i+1, j+1)).getRed());

        return pixel_y;
    }

    public String new_name(String name_file, String filtre) {
        String[] tmp = name_file.split("[.]");
        String newName = tmp[0] + "_" + filtre + "." + tmp[1];
        return newName;
    }

    public BufferedImage getImg() {
        return this.img.img;
    }
    public BufferedImage getImgCopy() {
        return this.imgCopy.img;
    }
}
