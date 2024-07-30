
package mytunes;

public class MyTunes {
    
    public static void main(String args[]) {
        Database database = new Database();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new GUI1(database).setVisible(true);
            }
        });
    }
    
}
