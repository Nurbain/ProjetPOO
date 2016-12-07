import java.awt.*;
import java.io.*;
import java.awt.image.*;

public class rob extends nuanceGris implements usage {

    private nuanceGris img;
    private nuanceGris imgCopy;

    public rob(File image) throws IOException {
        img = new nuanceGris(image);
        imgCopy = new nuanceGris(image);
    }


    public void filtrerobert() throws IOException {
        img.niveauGris();
        imgCopy.niveauGris();

        //Parcours tous les pixels
        for (int i = 1; i < img.img.getWidth()-1; i++) {
            for (int j = 1; j < img.img.getHeight()-1; j++) {
                //couleur red du pixel

                double pixelrx = (new Color(img.img.getRGB(i+1, j)).getRed()) - ( new Color(img.img.getRGB(i, j)).getRed());
                double pixelry = (new Color(img.img.getRGB(i, j+1)).getRed()) - ( new Color(img.img.getRGB(i, j)).getRed());

                double valr = Math.sqrt((pixelrx * pixelrx) + (pixelry * pixelry));

                if (valr<=0){valr=0;}
                else if (valr>=255){valr=255;}

                double pixelgx = ( new Color(img.img.getRGB(i+1, j)).getGreen()) - ( new Color(img.img.getRGB(i, j)).getGreen());
                double pixelgy = ( new Color(img.img.getRGB(i, j+1)).getGreen()) - ( new Color(img.img.getRGB(i, j)).getGreen()) ;

                double valg = Math.sqrt((pixelgx * pixelgx) + (pixelgy * pixelgy));
                if (valg<=0){valg=0;}
                else if (valg>=255){valg=255;}

                double pixelbx = ( new Color(img.img.getRGB(i+1, j)).getBlue()) - ( new Color(img.img.getRGB(i, j)).getBlue());

                double pixelby = ( new Color(img.img.getRGB(i, j+1)).getBlue()) - ( new Color(img.img.getRGB(i, j)).getBlue());


                double valb = Math.sqrt((pixelbx * pixelbx) + (pixelby * pixelby));

                if (valb<=0){valb=0;}
                else if (valb>=255){valb=255;}


                int couleur = new Color((int)valr,(int)valg,(int)valb).getRGB();
                imgCopy.img.setRGB(i,j,couleur);
            }
        }
        //ImageIO.write(imgCopy, "png",new File("roberts.jpg"));
    }

    //Useless but in interface
    public double pixelX(double[][] x, double[][] y, BufferedImage img, int i, int j) {
        return 0;
    }

    //Useless but in interface
    public double pixelY(double[][] x, double[][] y, BufferedImage img, int i, int j) {
        return 0;
    }

    public BufferedImage getImg() {
        return this.img.img;
    }

    public BufferedImage getImgCopy() {
        return this.imgCopy.img;
    }


}
