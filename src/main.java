/**
 * Fichier main permettant de choisir un fichier et lancer l'affichage du programme.
 *
 * @author Matthieu LEON et Nathan URBAIN
 */

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException {

        JFileChooser chooser = new JFileChooser();
        File curFile;
        int rec = chooser.showOpenDialog(null);
        if (rec != JFileChooser.APPROVE_OPTION)
            System.exit(0);

        curFile = chooser.getSelectedFile();

        ZFenetre f = new ZFenetre(curFile);
    }
}