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

public class vect extends Sobel implements usage {

    public Sobel img;
    public ArrayList<segment> listvect = new ArrayList();;

    /**
     * Constructeur vect
     */
    public vect() {}

    /**
     * Constructeur vect à partir d'un FILE correspondant à l'image cible.
     *
     * @param image
     *              File image correspondant à l'image cible.
     * @throws IOException Pour l'ouverture de l'image
     */
    public vect(File image) throws IOException{
        img = new Sobel(image);
    }

    //Direction 1- 2\ 3| 4/

    /**
     * Fonction qui regarde dans une directions autour du pixel en argument pour essayer de trouver des segments.
     *
     * @param x
     *             Position X du pixel
     * @param y
     *             Position Y du pixel
     * @param dir
     *              Direction dans laquelle la fonction doit regarder s'il y a un segment.
     * @param imgmodif
     *              Copie de l'image cible.
     * @return
     *              Renvoie l'image modifiée avec un nouveau segment.
     */
    public BufferedImage  parcours (int x , int y ,int dir, BufferedImage imgmodif)
    {
        int i=x;
        int j=y;
        switch(dir)
        {
            case 1 :
                while(i<imgmodif.getWidth()-1 && imgmodif.getRGB(i+1, y) == Color.white.getRGB() )
                {
                    if(i!=x){
                        imgmodif.setRGB(i,y,Color.BLACK.getRGB());}
                    i++;
                }
                if(i!=x) {
                    segment v1 = new segment(x, y, i, y);
                    this.listvect.add(v1);
                }

                //this.segment[this.max]=p1;
                //this.segment[this.max][1]= new point(i,y);
                //this.max++;
                break;

            case 2 :
                while(i<imgmodif.getWidth()-1 && j<imgmodif.getHeight()-1 && imgmodif.getRGB(i+1, j+1) == Color.white.getRGB() )
                {
                    if(i!=x && j!=y){
                        imgmodif.setRGB(i,j,Color.BLACK.getRGB());}
                    i++;
                    j++;

                }
                if(i!=x && j!=y){
                    segment v2 = new segment(x,y,i,j);
                    this.listvect.add(v2);
                }
                //ajoute le segment x,y i,j si i!=x
                break;

            case 3 :
                while(j<imgmodif.getHeight()-1 && imgmodif.getRGB(x, j+1) == Color.white.getRGB() )
                {
                    if(j!=y){
                        imgmodif.setRGB(x,j,Color.BLACK.getRGB());}
                    j++;
                }
                if(j!=y) {
                    segment v3 = new segment(x, y, x, j);
                    this.listvect.add(v3);
                }
                //ajoute le segment x,y x,j si j!=y
                break;

            case 4 :
                while(i>=1 && j<imgmodif.getHeight()-1 && imgmodif.getRGB(i-1, j+1) == Color.white.getRGB() )
                {
                    if(i!=x && j!=y){
                        imgmodif.setRGB(i,j,Color.BLACK.getRGB());}
                    i--;
                    j++;
                }
                if(i!=x && j!=y) {
                    segment v4 = new segment(x, y, i, j);
                    this.listvect.add(v4);
                }
                //ajoute le segment x,y i,j si i!=x
                break;
            default: break;
        }
        return imgmodif;
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
                        copyvect=this.parcours(i, j, 1, copyvect);
                    }
                    if (copyvect.getRGB(i + 1, j + 1) == Color.white.getRGB()) {
                        copyvect=this.parcours(i, j, 2, copyvect);
                    }
                    if (copyvect.getRGB(i, j + 1) == Color.white.getRGB()) {
                        copyvect=this.parcours(i, j,3, copyvect);
                    }
                    if (copyvect.getRGB(i - 1, j + 1) == Color.white.getRGB()) {
                        copyvect=this.parcours(i, j, 4, copyvect);
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
            bw.write("<line x1=\""+this.listvect.get(k).x1+"\" y1=\""+this.listvect.get(k).y1+"\" x2=\""+this.listvect.get(k).x2+"\" y2=\""+this.listvect.get(k).y2+"\" stroke=\"black\" stroke-width=\"1\" />");
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