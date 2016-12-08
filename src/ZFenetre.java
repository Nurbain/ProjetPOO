import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe ZFenetre qui permet de créer et gérer tout l'affichage du programme.
 *
 * @author Matthieu LEON et Nathan URBAIN
 */
public class ZFenetre extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu test1 = new JMenu("Fichier");
    private JMenu test2_1 = new JMenu("Filtre");
    private JMenu test2 = new JMenu("Paramètres");

    private JMenuItem item1 = new JMenuItem("Ouvrir...");
    private JMenuItem item2 = new JMenuItem("Fermer");
    private JMenuItem item3 = new JMenuItem("Enregistrer sous...");

    private JCheckBoxMenuItem filtreS = new JCheckBoxMenuItem("Sobel");
    private JCheckBoxMenuItem filtreP = new JCheckBoxMenuItem("Prewitt");
    private JCheckBoxMenuItem filtreR = new JCheckBoxMenuItem("Roberts");
    private JCheckBoxMenuItem filtreVN = new JCheckBoxMenuItem("Vectorisation naive");
    private JCheckBoxMenuItem filtreVP = new JCheckBoxMenuItem("Vectorisation");

    private JLabel img;
    private JPanel pan;

    //Permet de retenir le filtre utilisé actuellement
    private String filtreActuel;
    private File curFile;
    private BufferedImage imgFiltre;

    /**
     * Selectionne le bon filtre dans le JMenuItem 'filtre'
     *
     * @param Jb
     *          Correspond au JCheckBoxMenuItem qui a été coché.
     */
    public void setFiltre(JCheckBoxMenuItem Jb) {
        filtreS.setSelected(false);
        filtreP.setSelected(false);
        filtreR.setSelected(false);
        filtreVN.setSelected(false);
        filtreVP.setSelected(false);
        Jb.setSelected(true);
    }

    /**
     * Constructeur pour créer et afficher la fenêtre principale du programme.
     * Va permettre de gérer l'ouverture d'un nouveau fichier, l'enregistrement, le changement de filtre.
     *
     * @param file
     *          Fichier contenant l'image cible.
     * @throws IOException Pour l'ouverture de l'image
     */
    public ZFenetre(File file) throws IOException {

        curFile = file;
        setTitle("Projet BOUYA");

        pan = new JPanel();

        //Gestion fenetre choix filtre au lancement
        String[] filtre = {"Sobel", "Prewitt", "Roberts", "Vectorisation naive", "Vectorisation"};

        JOptionPane jop = new JOptionPane();

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

            case "Vectorisation naive": vectnaive v = new vectnaive(this.curFile);
                                    v.vectoristation();
                                    filtreActuel = "Roberts";
                                    this.imgFiltre = v.getImgCopy();
                                    setFiltre(filtreVN);
                                    item3.setEnabled(false);
                                    break;
            case "Vectorisation": vectplus vp = new vectplus(this.curFile);
                                    vp.vectoristation();
                                    filtreActuel = "Roberts";
                                    this.imgFiltre = vp.getImgCopy();
                                    setFiltre(filtreVP);
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
        test2_1.add(filtreVN);
        test2_1.add(filtreVP);

        //Ajout du sous-menu dans notre menu
        test2.add(this.test2_1);
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

                case "Vectorisation naive":
                    vectnaive v = null;
                    try {
                        v = new vectnaive(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        v.vectoristation();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Vectorisation naive";
                    this.imgFiltre = v.getImgCopy();
                    setFiltre(filtreVN);
                    item3.setEnabled(false);
                    img = new JLabel(new ImageIcon(v.getImgCopy()));
                    break;

                case "Vectorisation":
                    vectplus vp = null;
                    try {
                        vp = new vectplus(this.curFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        vp.vectoristation();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    filtreActuel = "Vectorisation";
                    this.imgFiltre = vp.getImgCopy();
                    setFiltre(filtreVP);
                    item3.setEnabled(false);
                    img = new JLabel(new ImageIcon(vp.getImgCopy()));
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

        filtreVN.addActionListener(e -> {
            System.out.println("Modification du filtre...");

            try {
                pan.removeAll();
                vectnaive t = new vectnaive(this.curFile);
                t.vectoristation();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Vectorisation";
                setFiltre(filtreVN);
                item3.setEnabled(false);
                System.out.println("Filtre modifié. Filtre actuel : Vectorisation naive.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
            }
        });

        filtreVP.addActionListener(e -> {
            System.out.println("Modification du filtre...");

            try {
                pan.removeAll();
                vectplus t = new vectplus(this.curFile);
                t.vectoristation();
                img = new JLabel(new ImageIcon(t.getImgCopy()));
                pan.add(img);
                pan.updateUI();
                this.filtreActuel = "Vectorisation";
                setFiltre(filtreVP);
                item3.setEnabled(false);
                System.out.println("Filtre modifié. Filtre actuel : Vectorisation.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erreur modification filtre. Abandon.");
                return;
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

