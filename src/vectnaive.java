import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Fichier contenant les fonctions nécéssaires à la vectorisation d'une image.
 *
 * @author Matthieu LEON et Nathan URBAIN
 *
 * @see Sobel
 * @see usage
 */

public class vectnaive extends Sobel implements usage {

    public Sobel img;
    public ArrayList<segment> listvect = new ArrayList();;

    /**
     * Constructeur vect
     */
    public vectnaive() {}

    /**
     * Constructeur vect à partir d'un FILE correspondant à l'image cible.
     *
     * @param image
     *              File image correspondant à l'image cible.
     * @throws IOException Pour l'ouverture de l'image
     */
    public vectnaive(File image) throws IOException{
        img = new Sobel(image);
    }

    /**
     * FOnction qui calcule la pente entre 2 points x y et i j
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


    public void suitvec (segment s , BufferedImage img)
    {
        double coef = getCoef(s.p1,s.p2);
        double testcoef;
        int x,y;


    }

    //Direction 1- 2\ 3| 4/

    /**
     * Fonction qui regarde dans une directions autour du pixel en argument pour essayer de trouver des segments.
     *
     * @param p
     *             point representant le pixel
     * @param dir
     *              Direction dans laquelle la fonction doit regarder s'il y a un segment.
     * @param imgmodif
     *              Copie de l'image cible.
     * @return
     *              Renvoie l'image modifiée avec un nouveau segment.
     */
    public void  parcours (point p ,int dir, BufferedImage imgmodif)
    {
        point p2 = new point(p.x,p.y);
        switch(dir)
        {
            case 1 :
                while(p2.x<imgmodif.getWidth()-1 && imgmodif.getRGB(p2.x+1, p.y) == Color.white.getRGB() )
                {
                    if(p2.x!=p.x){
                        imgmodif.setRGB(p2.x,p.y,Color.BLACK.getRGB());}
                    p2.x++;
                }
                if(p2.x!=p.x) {
                    segment v1 = new segment(p,p2);
                    this.listvect.add(v1);
                }

                //this.segment[this.max]=p1;
                //this.segment[this.max][1]= new point(i,y);
                //this.max++;
                break;

            case 2 :
                while(p2.x<imgmodif.getWidth()-1 && p2.y<imgmodif.getHeight()-1 && imgmodif.getRGB(p2.x+1, p2.y+1) == Color.white.getRGB() )
                {
                    if(p2.x!=p.x && p2.y!=p.y){
                        imgmodif.setRGB(p2.x,p2.y,Color.BLACK.getRGB());}
                    p2.x++;
                    p2.y++;

                }
                if(p2.x!=p.x && p2.y!=p.y){
                    segment v2 = new segment(p,p2);
                    this.listvect.add(v2);
                }
                //ajoute le segment x,y i,j si i!=x
                break;

            case 3 :
                while(p2.y<imgmodif.getHeight()-1 && imgmodif.getRGB(p.x, p2.y+1) == Color.white.getRGB() )
                {
                    if(p2.y!=p.y){
                        imgmodif.setRGB(p2.x,p2.y,Color.BLACK.getRGB());}
                    p2.y++;
                }
                if(p2.y!=p.y) {
                    segment v3 = new segment(p,p2);
                    this.listvect.add(v3);
                }
                //ajoute le segment x,y x,j si j!=y
                break;

            case 4 :
                while(p2.x>=1 && p2.y<imgmodif.getHeight()-1 && imgmodif.getRGB(p2.x-1, p2.y+1) == Color.white.getRGB() )
                {
                    if(p2.x!=p.x && p2.y!=p.y){
                        imgmodif.setRGB(p2.x,p2.y,Color.BLACK.getRGB());}
                    p2.x--;
                    p2.y++;
                }
                if(p2.x!=p.x && p2.y!=p.y) {
                    segment v4 = new segment(p,p2);
                    this.listvect.add(v4);
                }
                //ajoute le segment x,y i,j si i!=x
                break;
            default: break;
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
     *              Renvoie l'image vectorisée
     * @throws IOException Pour l'ouverture de l'image
     */
    public BufferedImage vectoristation() throws IOException {

        img.filtreSobel();
        BufferedImage copyvect = img.getImgCopy();
        copyvect = seuil(copyvect);

        for (int i = 1; i < img.getImg().getWidth() - 1; i++) {
            for (int j = 1; j < img.getImg().getHeight() - 1; j++) {
                if (copyvect.getRGB(i,j)== Color.white.getRGB())
                {
                    if (copyvect.getRGB(i + 1, j) == Color.white.getRGB()) {
                        this.parcours(new point(i,j), 1, copyvect);
                    }
                    if (copyvect.getRGB(i + 1, j + 1) == Color.white.getRGB()) {
                        this.parcours(new point(i,j), 2, copyvect);
                    }
                    if (copyvect.getRGB(i, j + 1) == Color.white.getRGB()) {
                        this.parcours(new point(i,j),3, copyvect);
                    }
                    if (copyvect.getRGB(i - 1, j + 1) == Color.white.getRGB()) {
                        this.parcours(new point(i,j), 4, copyvect);
                    }
                }
            }
        }


        File image = new File("test.svg");

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



        /*vg xmlns="http://www.w3.org/2000/svg" version="1.1">
  <rect x="25" y="25" width="200" height="200" fill="lime" stroke-width="4" stroke="pink" />
  <circle cx="125" cy="125" r="75" fill="orange" />
  <polyline points="50,150 50,200 200,200 200,100" stroke="red" stroke-width="4" fill="none" />
  <line x1="50" y1="50" x2="200" y2="200" stroke="blue" stroke-width="4" />*/

        bw.write("</svg>");
        bw.close();

        return copyvect;
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