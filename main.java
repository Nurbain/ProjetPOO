/**
 * Created by Stery on 14/11/2016.
 */

import javax.swing.*;
import java.io.*;
import java.lang.*;

public class main {

    public static void main(String[] args) throws IOException{

        JFileChooser chooser = new JFileChooser();
        File curFile;
        int rec = chooser.showOpenDialog(null);
        if ( rec != JFileChooser.APPROVE_OPTION )
            System.exit(0);

        curFile = chooser.getSelectedFile();
        String file = curFile.getAbsolutePath();
        Sobel t = new Sobel(curFile);
        t.filtreSobel();

        ZFenetre f = new ZFenetre(curFile, t.getImgCopy());
    }
}