import javax.swing.*;

public class RobotsApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ui.GamePanel::new);
    }
}
