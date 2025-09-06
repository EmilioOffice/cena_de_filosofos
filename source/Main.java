import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Se crea la GUI en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("La Cena de los Filósofos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            
            Mesa mesa = new Mesa(5); // Inicia con 5 filósofos
            frame.add(mesa);
            
            frame.setVisible(true);
        });
    }
}