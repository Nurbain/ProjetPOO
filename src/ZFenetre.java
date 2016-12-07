/**
 * Created by Stery on 15/11/2016.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
    private JCheckBoxMenuItem filtreR = new JCheckBoxMenuItem("Roberts");
    private JCheckBoxMenuItem filtreV = new JCheckBoxMenuItem("Vectorisation");

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
    private BufferedImage imgFiltre;

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
        filtreR.setSelected(false);
        filtreV.setSelected(false);
        Jb.setSelected(true);
    }

    public void switch_img() throws IOException{
        switch (filtreActuel) {
            case "Sobel":
                Sobel t = new Sobel(this.curFile);
                t.filtreSobel();
                filtreActuel = "Roberts";
                this.imgFiltre = t.getImgCopy();
                setFiltre(filtreS);
                break;

            case "Prewitt":
                Prewitt p = new Prewitt(this.curFile);
                p.filtrePrewitt();
                filtreActuel = "Roberts";
                this.imgFiltre = p.getImgCopy();
                setFiltre(filtreP);
                break;

            case "Roberts":
                rob r = new rob(this.curFile);
                r.filtrerobert();
                filtreActuel = "Roberts";
                this.imgFiltre = r.getImgCopy();
                setFiltre(filtreR);
                break;

            case "Vectorisation":
                vect v = new vect(this.curFile);
                v.vectoristation();
                filtreActuel = "Roberts";
                this.imgFiltre = v.getImgCopy();
                setFiltre(filtreV);
                break;

            default:
                break;
        }
    }

    public ZFenetre(File file) throws IOException {

        curFile = file;
        setTitle("Projet BOUYA");

        //Ajout d'une favicon
        File tmp = new File("favicon.jpg");
        Image favicon = ImageIO.read(tmp);
        this.setIconImage((Image) favicon);
        pan = new JPanel();

        //Gestion fenetre choix filtre au lancement
        String[] filtre = {"Sobel", "Prewitt", "Roberts", "Vectorisation"};

        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();

        String nom = (String)jop.showInputDialog(null,
                "Veuillez indiquer le filtre à utiliser.",
                "Choix du filtre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                filtre,
                filtre[0]);
        filtreActuel = nom;

        switch(filtreActuel) {
            case "Sobel": Sobel t = new Sobel(this.curFile);
                            t.filtreSobel();
                            filtreActuel = "Roberts";
                            this.imgFiltre = t.getImgCopy();
                            setFiltre(filtreS);
                            break;

            case "Prewitt": Prewitt p = new Prewitt(this.curFile);
                            p.filtrePrewitt();
                            filtreActuel = "Roberts";
                            this.imgFiltre = p.getImgCopy();
                            setFiltre(filtreP);
                            break;

            case "Roberts": rob r = new rob(this.curFile);
                            r.filtrerobert();
                            filtreActuel = "Roberts";
                            this.imgFiltre = r.getImgCopy();
                            setFiltre(filtreR);
                            break;

            case "Vectorisation": vect v = new vect(this.curFile);
                                    v.vectoristation();
                                    filtreActuel = "Roberts";
                                    this.imgFiltre = v.getImgCopy();
                                    setFiltre(filtreV);
                                    item3.setEnabled(false);
                                    break;

            default: break;
        }

        ImageIcon icon = new ImageIcon(imgFiltre);

        Image img_tmp = icon.getImage();
        Graphics2D g = (Graphics2D) img_tmp.getGraphics();
        img = new JLabel(icon);
        if (icon.getIconWidth() < 600 && icon.getIconHeight() < 600) {
            setSize(icon.getIconWidth() + 20, icon.getIconHeight() + 80);
        }
        else {
            setSize(600, 600);
            pan.setSize(600,600);
        }
        pan.add(img);
        setContentPane(pan);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //On initialise nos menus
        test1.add(item1);
        test1.add(item3);

        //On ajoute les éléments dans notre sous-menu

        test2_1.add(filtreS);
        test2_1.add(filtreP);
        test2_1.add(filtreR);
        test2_1.add(filtreV);

        //Par défaut
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
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        //Gestion "Enregistrer sous..."
        item3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
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
        item1.addActionListener( e-> {

            JFileChooser chooser = new JFileChooser();

            int rec = chooser.showOpenDialog(null);
            if (rec != JFileChooser.APPROVE_OPTION) {
                System.out.println("Ouverture fichier annulée.");
                return;
            }
            this.curFile = chooser.getSelectedFile();

            switch(filtreActuel) {
                case "Sobel":
                    Sobel t = null;
                    try {
                        t = new Sobel(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        t.filtreSobel();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Sobel";
                    this.imgFiltre = t.getImgCopy();
                    setFiltre(filtreS);
                    item3.setEnabled(true);
                    img = new JLabel(new ImageIcon(t.getImgCopy()));
                    break;

                case "Prewitt":
                    Prewitt p = null;
                    try {
                        p = new Prewitt(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        p.filtrePrewitt();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Prewitt";
                    this.imgFiltre = p.getImgCopy();
                    setFiltre(filtreP);
                    item3.setEnabled(true);
                    img = new JLabel(new ImageIcon(p.getImgCopy()));
                    break;

                case "Roberts":
                    rob r = null;
                    try {
                        r = new rob(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        r.filtrerobert();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Roberts";
                    this.imgFiltre = r.getImgCopy();
                    setFiltre(filtreR);
                    item3.setEnabled(true);
                    img = new JLabel(new ImageIcon(r.getImgCopy()));
                    break;

                case "Vectorisation":
                    vect v = null;
                    try {
                        v = new vect(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        v.vectoristation();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Vectorisation";
                    this.imgFiltre = v.getImgCopy();
                    setFiltre(filtreV);
                    item3.setEnabled(false);
                    img = new JLabel(new ImageIcon(v.getImgCopy()));
                    break;

                default: break;
            }
            pan.removeAll();
            pan.add(img);
            pan.updateUI();
        });

        //Gestion des filtres
        filtreS.addActionListener(e -> {
            System.out.println("Modification du filtre...");

            try {
                pan.removeAll();
                Sobel t = new Sobel(this.curFile);
                t.filtreSobel();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Sobel";
                setFiltre(filtreS);
                item3.setEnabled(true);
                System.out.println("Filtre modifié. Filtre actuel : Sobel.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });

        filtreP.addActionListener(e -> {
            System.out.println("Modification du filtre...");
            try {
                pan.removeAll();
                Prewitt t = new Prewitt(this.curFile);
                t.filtrePrewitt();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Prewitt";
                setFiltre(filtreP);
                item3.setEnabled(true);
                System.out.println("Filtre modifié. Filtre actuel : Prewitt.");
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });

        filtreR.addActionListener(e -> {
            System.out.println("Modification du filtre...");

            try {
                pan.remove(this.img);
                rob t = new rob(this.curFile);
                t.filtrerobert();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Roberts";
                setFiltre(filtreR);
                item3.setEnabled(true);
                System.out.println("Filtre modifié. Filtre actuel : Roberts.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });

        filtreV.addActionListener(e -> {
            System.out.println("Modification du filtre...");

            try {
                pan.removeAll();
                vect t = new vect(this.curFile);
                t.vectoristation();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Vectorisation";
                setFiltre(filtreV);
                item3.setEnabled(false);
                System.out.println("Filtre modifié. Filtre actuel : Vectorisation.");
            } catch (IOException ex) {
                ex.printStackTrace();
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

        int9.addActionListener(e-> {
                System.out.println("int9");
                setIntensite(int9);
        });

        int10.addActionListener(e-> {
                System.out.println("int10");
                setIntensite(int10);
        });

        int11.addActionListener(e-> {
                System.out.println("int11");
                setIntensite(int11);
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

