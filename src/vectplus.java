import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe vectplus qui permet de faire une vectorisation moins naive d'une image
 *
 * @author Matthieu LEON et Nathan URBAIN
 */

public class vectplus extends segment implements usage{
    public Sobel img;
    public ArrayList<segment> listvect = new ArrayList();;
    private int min = 3;
    private point ptsmodif;
    /**
     * Constructeur vect
     */
    public vectplus() {}

    /**
     * Constructeur vect à partir d'un FILE correspondant à l'image cible.
     *
     * @param image
     *              File image correspondant à l'image cible.
     * @throws IOException Pour l'ouverture de l'image
     */
    public vectplus(File image) throws IOException{
        img = new Sobel(image);
    }

    /**
     * Fonction qui calcule la pente entre 2 points x y et i j
     * @param p
     *          point representant le pixel p
     * @param p2
     *          point representant le pixel q
     * @return
     *          Renvoie le coef de la pente entre c'est point
     */
    public double getCoef(point p , point p2)
    {
        return ((double)p2.y-(double)p.y/(double)p2.x-(double)p.x);
    }



    /**
     * Fonction qui regarde si le nouveau point potentiellement ajouté au segment ne devie pas trop le segement initial
     * cette deviation est calculé grace au coefficiant de droite
     * @param s
     *          point representant le pixel p
     * @param img
     *          Image a modifié
     * @return
     *         Modifie le segement initiale
     */
    public void suitvec (segment s , BufferedImage img) {
        double coef = getCoef(s.p1, s.p2);
        double ptsmodifcoef;
        double mini = 3. / 4.;
        double max = 5. / 4.;
        double res;
        //direction ----------
        if (s.p2.x + 1<img.getWidth() && img.getRGB(s.p2.x + 1, s.p2.y) == Color.white.getRGB()) {
            point t = new point(s.p2.x + 1, s.p2.y);
            ptsmodifcoef = getCoef(s.p1, t);
            res = ptsmodifcoef / coef;
            if (res > mini && res < max) {
                img.setRGB(s.p2.x + 1, s.p2.y, Color.BLACK.getRGB());
                s.p2 = t;
                suitvec(s, img);
            }
        }
        //Direction \
        else if (s.p2.x + 1<img.getWidth() && s.p2.y + 1<img.getHeight() && img.getRGB(s.p2.x + 1, s.p2.y + 1) == Color.white.getRGB()) {
            point t = new point(s.p2.x + 1, s.p2.y + 1);
            ptsmodifcoef = getCoef(s.p1, t);
            res = ptsmodifcoef / coef;
            if (res > mini && res < max) {
                img.setRGB(s.p2.x + 1, s.p2.y + 1, Color.BLACK.getRGB());
                s.p2 = t;
                suitvec(s, img);
            }
        }
        //Direction ||
        else if (s.p2.y + 1<img.getHeight() && img.getRGB(s.p2.x, s.p2.y + 1) == Color.white.getRGB()) {
            point t = new point(s.p2.x, s.p2.y + 1);
            ptsmodifcoef = getCoef(s.p1, t);
            res = ptsmodifcoef / coef;
            if (res > mini && res < max) {
                img.setRGB(s.p2.x, s.p2.y + 1, Color.BLACK.getRGB());
                s.p2 = t;
                suitvec(s, img);
            }
        }
        //Direction /
        else if (s.p2.y + 1<img.getHeight() && s.p2.x - 1>=0 && img.getRGB(s.p2.x - 1, s.p2.y + 1) == Color.white.getRGB()) {
            point t = new point(s.p2.x - 1, s.p2.y + 1);
            ptsmodifcoef = getCoef(s.p1, t);
            res = ptsmodifcoef / coef;
            if (res > mini && res < max) {
                img.setRGB(s.p2.x - 1, s.p2.y + 1, Color.BLACK.getRGB());
                s.p2 = t;
                suitvec(s, img);
            }
        }
    }

    //Direction 1- 2\ 3| 4/

    /**
     * Fonction allant trouver tous les segments de minimum taille depuis le point donné et une direction
     *
     * @param p
     *             point representant le pixel
     * @param dir
     *              Direction dans laquelle la fonction doit regarder s'il y a un segment.
     * @param imgmodif
     *              Copie de l'image cible.
     * @param it
     *              la longueur du segment actuelle
     * @return
     *              Retourne le point blanc a minimum point du point d'origine
     */

    public void parcours (point p, int dir, BufferedImage imgmodif , int it)
    {
        if(p.x>=imgmodif.getWidth() || p.y>=imgmodif.getHeight() || p.x<0 ||p.y < 0 )
        {}
        else {
            point p2 = new point(p.x, p.y);
            if (it <= this.min) {
                switch (dir) {
                    case 1:
                        if (imgmodif.getRGB(p2.x, p.y) == Color.white.getRGB()) {
                            imgmodif.setRGB(p2.x, p.y, Color.BLACK.getRGB());
                            parcours(new point(p2.x + 1, p.y), dir, imgmodif, it + 1);
                        }
                        else {}
                        break;

                    case 2:
                        if (imgmodif.getRGB(p2.x, p2.y) == Color.white.getRGB()) {
                            imgmodif.setRGB(p2.x, p.y, Color.BLACK.getRGB());
                            parcours(new point(p2.x + 1, p.y + 1), dir, imgmodif, it + 1);
                        }
                        else {}
                        break;

                    case 3:
                        if (imgmodif.getRGB(p.x, p2.y) == Color.white.getRGB()) {
                            imgmodif.setRGB(p2.x, p.y, Color.BLACK.getRGB());
                            parcours(new point(p2.x, p.y + 1), dir, imgmodif, it + 1);
                        }
                        else {}
                        break;

                    case 4:
                        if (imgmodif.getRGB(p2.x, p2.y) == Color.white.getRGB()) {
                            imgmodif.setRGB(p2.x, p.y, Color.BLACK.getRGB());
                            parcours(new point(p2.x - 1, p.y + 1), dir, imgmodif, it + 1);
                        }
                        else {}
                        break;

                    default:
                        break;
                }
            }
            else this.ptsmodif = p2;
        }
    }


    /**
     * Fonction permettant de modifier le seuil d'une image.
     * @param image
     *              Image à modifier.
     * @return
     *              Image dont le seuil à été modifiée.
     * @throws IOException Pour l'ouverture de l'image
     */
    public BufferedImage seuil(BufferedImage image)throws IOException {

        for (int i = 0; i < image.getWidth() - 1; i++) {
            for (int j = 0; j < image.getHeight() - 1; j++) {

                Color pixelcolor = new Color(image.getRGB(i, j));
                // recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
                int r = pixelcolor.getRed();
                int g = pixelcolor.getGreen();
                int b = pixelcolor.getBlue();

                if ((r + g + b) / 2 < 127) {
                    image.setRGB(i, j,Color.BLACK.getRGB() );
                }
                else image.setRGB(i, j,Color.WHITE.getRGB() );
            }
        }
        return image;
    }

    /**
     * Fonction principale de la vectorisation.
     * @return
     *              Renvoie l'image vectorisée par calcule de coefficient de courbe a chaque point
     * @throws IOException Pour l'ouverture de l'image
     */
    public BufferedImage vectoristation() throws IOException {

        img.filtreSobel();
        BufferedImage copyvect = img.getImgCopy();
        copyvect = seuil(copyvect);

        for (int i = 1; i < img.getImg().getWidth()-1 ; i++) {
            for (int j = 0; j < img.getImg().getHeight()-1; j++) {
                if (copyvect.getRGB(i,j)== Color.white.getRGB()) {
                    point dep = new point(i,j);
                    segment s;
                    if (copyvect.getRGB(i + 1, j) == Color.white.getRGB()) {
                        this.ptsmodif=null;
                        this.parcours(dep, 1, copyvect ,1 );
                        //Permet de verifié si le segement n'est pas trop petit
                        if(this.ptsmodif!=null)
                        {
                            s=new segment(dep,this.ptsmodif);
                            //Calcule un segement moins naif
                            suitvec(s,copyvect);
                            this.listvect.add(s);
                            copyvect.setRGB(s.p2.x, s.p2.y, Color.WHITE.getRGB());
                        }
                    }
                    if (copyvect.getRGB(i + 1, j + 1) == Color.white.getRGB()) {
                        this.ptsmodif=null;
                        this.parcours(new point(i, j), 2, copyvect,1);
                        if(this.ptsmodif!=null)
                        {
                            s=new segment(dep,this.ptsmodif);
                            suitvec(s,copyvect);
                            this.listvect.add(s);
                            copyvect.setRGB(s.p2.x, s.p2.y, Color.WHITE.getRGB());
                        }
                    }
                    if (copyvect.getRGB(i, j + 1) == Color.white.getRGB()) {
                        this.ptsmodif=null;
                        this.parcours(new point(i, j), 3, copyvect,1);
                        if(this.ptsmodif!=null)
                        {
                            s=new segment(dep,this.ptsmodif);
                            suitvec(s,copyvect);
                            this.listvect.add(s);
                            copyvect.setRGB(s.p2.x, s.p2.y, Color.WHITE.getRGB());
                        }
                    }
                    if (copyvect.getRGB(i - 1, j + 1) == Color.white.getRGB()) {
                        this.ptsmodif=null;
                        this.parcours(new point(i, j), 4, copyvect,1);
                        if(this.ptsmodif!=null)
                        {
                            s=new segment(dep,this.ptsmodif);
                            suitvec(s,copyvect);
                            this.listvect.add(s);
                            copyvect.setRGB(s.p2.x, s.p2.y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
        }


        //Ecriture fichier .svg

        File image = new File("vecto_plus.svg");

        if(!image.exists())
        {
            image.createNewFile();
        }

        FileWriter imw= new FileWriter(image.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(imw);
        bw.write("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");

        for(int k=0;k<this.listvect.size();k++)
        {
            bw.write("<line x1=\""+this.listvect.get(k).p1.x+"\" y1=\""+this.listvect.get(k).p1.y+"\" x2=\""+this.listvect.get(k).p2.x+"\" y2=\""+this.listvect.get(k).p2.y+"\" stroke=\"black\" stroke-width=\"1\" />");
        }



        bw.write("</svg>");
        bw.close();

        return copyvect;
    }

    /**
     * Fonction inutile ici.
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
     * @return
     */
    public double pixelX(double[][] x, double[][] y, BufferedImage img, int i, int j) {
        return 0;
    }

    /**
     *  Fonction inutile ici.
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
     * @return
     */
    public double pixelY(double[][] x, double[][] y, BufferedImage img, int i, int j) {
        return 0;
    }

    /***
     * Permet de renvoyer l'image source.
     * @return
     *              Renvoie l'image source.
     */
    public BufferedImage getImg() {return this.img.getImg();}

    /**
     * Permet de renvoyer l'image modifiée.
     * @return
     *  Renvoie l'image modifiée.
     */
    public BufferedImage getImgCopy() {return this.img.getImgCopy();}
}
