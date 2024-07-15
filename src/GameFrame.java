import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
        public GameFrame() {
            this.setTitle("2048");
            this.setSize(600, 650);
            this.getContentPane().setBackground(new Color(66, 136, 83));
            this.setDefaultCloseOperation(3);
            this.setLocationRelativeTo((Component)null);
            this.setResizable(false);
        }
}
