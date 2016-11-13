/**
 * Created by Stery on 11/11/2016.
 */

import javax.swing.*;


public class mainClass extends JFrame {
    public mainClass() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        this.getContentPane().add(fileChooser);
        fileChooser.setVisible(true);
    }
}
