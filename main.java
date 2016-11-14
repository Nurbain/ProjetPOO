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
        chooser.showOpenDialog(null);
        curFile = chooser.getSelectedFile();
        String file = curFile.getAbsolutePath();
        System.out.println(file);
        Prewitt t = new Prewitt(curFile, file);
        t.filtrePrewitt();
    }
}