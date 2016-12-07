import java.awt.image.BufferedImage;

/**
 * Created by Stery on 14/11/2016.
 */

public interface usage {

    double pixelX(double x[][], double y[][], BufferedImage img, int i, int j);

    double pixelY(double x[][], double y[][], BufferedImage img, int i, int j);

    String new_name(String name_file, String filtre);

    BufferedImage getImg();

    BufferedImage getImgCopy();
}
