/**
 * Created by Stery on 15/11/2016.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;



public class ZFenetre extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu test1 = new JMenu("Fichier");
    private JMenu test2_1 = new JMenu("Filtre");
    private JMenu test2_2 = new JMenu("Intensité");
    private JMenu test2 = new JMenu("Paramètres");

    private JMenuItem item1 = new JMenuItem("Ouvrir...");
    private JMenuItem item2 = new JMenuItem("Fermer");
    private JMenuItem item3 = new JMenuItem("Enregistrer sous...");

    private JCheckBoxMenuItem filtreS = new JCheckBoxMenuItem("Sobel");
    private JCheckBoxMenuItem filtreP = new JCheckBoxMenuItem("Prewitt");

    private JRadioButtonMenuItem int1 = new JRadioButtonMenuItem("100");
    private JRadioButtonMenuItem int2 = new JRadioButtonMenuItem("90");
    private JRadioButtonMenuItem int3 = new JRadioButtonMenuItem("80");
    private JRadioButtonMenuItem int4 = new JRadioButtonMenuItem("70");
    private JRadioButtonMenuItem int5 = new JRadioButtonMenuItem("60");
    private JRadioButtonMenuItem int6 = new JRadioButtonMenuItem("50");
    private JRadioButtonMenuItem int7 = new JRadioButtonMenuItem("40");
    private JRadioButtonMenuItem int8 = new JRadioButtonMenuItem("30");
    private JRadioButtonMenuItem int9 = new JRadioButtonMenuItem("20");
    private JRadioButtonMenuItem int10 = new JRadioButtonMenuItem("10");
    private JRadioButtonMenuItem int11 = new JRadioButtonMenuItem("0");

    private JLabel img;
    private JPanel pan;

    //Permet de retenir le filtre utilisé actuellement
    private String filtreActuel;
    private File curFile;

    public void setIntensite(JRadioButtonMenuItem Jb) {
        int1.setSelected(false);
        int2.setSelected(false);
        int3.setSelected(false);
        int4.setSelected(false);
        int5.setSelected(false);
        int6.setSelected(false);
        int7.setSelected(false);
        int8.setSelected(false);
        int9.setSelected(false);
        int10.setSelected(false);
        int11.setSelected(false);
        Jb.setSelected(true);
    }

    public void setFiltre(JCheckBoxMenuItem Jb) {
        filtreS.setSelected(false);
        filtreP.setSelected(false);
        Jb.setSelected(true);
    }

    public ZFenetre(File curFile, BufferedImage imgFiltre) throws IOException{

        this.curFile = curFile;
        filtreActuel = "Sobel";
        setTitle("Projet BOUYA");
        pan = new JPanel();
        ImageIcon icon = new ImageIcon(imgFiltre);

        img = new JLabel(icon);
        pan.add(img);

        setContentPane(pan);
        setSize(icon.getIconWidth() + 20, icon.getIconHeight() + 65);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //On initialise nos menus
        test1.add(item1);
        test1.add(item3);

        //On ajoute les éléments dans notre sous-menu

        filtreS.setSelected(true);
        test2_1.add(filtreS);
        test2_1.add(filtreP);

        int1.setSelected(true);
        test2_2.add(int1);
        test2_2.add(int2);
        test2_2.add(int3);
        test2_2.add(int4);
        test2_2.add(int5);
        test2_2.add(int6);
        test2_2.add(int7);
        test2_2.add(int8);
        test2_2.add(int9);
        test2_2.add(int10);
        test2_2.add(int11);

        //Ajout du sous-menu dans notre menu
        test2.add(this.test2_1);
        test2.add(this.test2_2);
        //Ajout d'un séparateur
        test1.addSeparator();

        //Gestion "fermer"
        item2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        //Gestion "Enregistrer sous..."
        item3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0){
                JOptionPane jop = new JOptionPane();
                String nom = jop.showInputDialog(null, "Nom de l'image", "Enregistrer sous", JOptionPane.QUESTION_MESSAGE);
                System.out.println("Nom: <" + nom + ">");
                if (nom != null && nom != "") {
                    nom = nom + ".png";
                    try {
                        ImageIO.write(imgFiltre, "png", new File(nom));
                    } catch (IOException e) {
                        System.out.println("Erreur sauvegarde image.");
                    }

                    JOptionPane jop1 = new JOptionPane();
                    jop1.showMessageDialog(null, "Enregistrement de l'image réussi.", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        //Gestion "ouvrir"
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                File curFile;
                int rec = chooser.showOpenDialog(null);
                if ( rec != JFileChooser.APPROVE_OPTION ) {
                    System.out.println("Ouverture fichier annulée.");
                    return;
                }

                curFile = chooser.getSelectedFile();
                String file = curFile.getAbsolutePath();

                if (filtreActuel == "Sobel") {
                    try {
                        Sobel t = new Sobel(curFile);
                        t.filtreSobel();
                        pan.remove(img);
                        img = new JLabel(new ImageIcon(t.getImgCopy()));
                        pan.add(img);
                    } catch (IOException ex) {}
                    System.out.println("Image chargée.");
                }
                else  if (filtreActuel == "Prewitt") {
                    try {
                        Prewitt t = new Prewitt(curFile);
                        t.filtrePrewitt();
                        pan.remove(img);
                        img = new JLabel(new ImageIcon(t.getImgCopy()));
                        pan.add(img);
                    } catch (IOException ex) {}
                    System.out.println("Image chargée.");
                }


            }
        });

        //Gestion des filtres
        filtreS.addActionListener(e -> {
            System.out.println("Modification du filtre...");
            try {
                Sobel t = new Sobel(this.curFile);
                t.filtreSobel();
                pan.remove(img);
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                this.filtreActuel = "Sobel";
                setFiltre(filtreS);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });

        filtreP.addActionListener(e -> {
            System.out.println("Modification du filtre...");
            try {
                Prewitt t = new Prewitt(this.curFile);
                t.filtrePrewitt();
                pan.remove(img);
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                this.filtreActuel = "Prewitt";
                setFiltre(filtreP);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });


        // Gestion de l'intensité
        int1.addActionListener(e -> {
            System.out.println("int1");
            setIntensite(int1);

        });

        int2.addActionListener(e -> {
            System.out.println("int2");
            setIntensite(int2);
        });

        int3.addActionListener(e -> {
            System.out.println("int3");
            setIntensite(int3);
        });

        int4.addActionListener(e -> {
            System.out.println("int4");
            setIntensite(int4);
        });

        int5.addActionListener(e -> {
            System.out.println("int5");
            setIntensite(int5);
        });

        int6.addActionListener(e -> {
            System.out.println("int6");
            setIntensite(int6);
        });

        int7.addActionListener(e -> {
            System.out.println("int7");
            setIntensite(int7);
        });

        int8.addActionListener(e -> {
            System.out.println("int8");
            setIntensite(int8);
        });

        int9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("int9");
                setIntensite(int9);
            }
        });

        int10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("int10");
                setIntensite(int10);
            }
        });

        int11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("int11");
                setIntensite(int11);
            }
        });

        test1.add(item2);

        //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
        //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
        menuBar.add(test1);
        menuBar.add(test2);
        setJMenuBar(menuBar);
        setVisible(true);
    }

}

