/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mytunes;

/**
 *
 * @author 030834321
 */
public class MyTunes {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            GUI gui = new GUI();
//            gui.createAndShowGUI();
//        });
//    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI1().setVisible(true);
            }
        });
    }
    
}
