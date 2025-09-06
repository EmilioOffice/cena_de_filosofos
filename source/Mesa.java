import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class Mesa extends JPanel {

    private final List<Filosofo> filosofos = new CopyOnWriteArrayList<>();
    private final List<Tenedor> tenedores = new CopyOnWriteArrayList<>();
    private final List<Thread> hilosFilosofos = new CopyOnWriteArrayList<>();
    private boolean previeneInterbloqueo = true;
    private int numeroDeFilosofos;

    public Mesa(int numInicialFilosofos) {
        this.numeroDeFilosofos = numInicialFilosofos;
        setBackground(new Color(240, 240, 240));
        
        JToggleButton btnPrevencion = new JToggleButton("Prevenir Interbloqueo: ON", true);
        btnPrevencion.addActionListener(e -> {
            previeneInterbloqueo = btnPrevencion.isSelected();
            btnPrevencion.setText("Prevenir Interbloqueo: " + (previeneInterbloqueo ? "ON" : "OFF"));
            reiniciarSimulacion();
        });

        JButton btnAddFilosofo = new JButton("Añadir Filósofo y Reiniciar");
        btnAddFilosofo.addActionListener(e -> {
            numeroDeFilosofos++;
            reiniciarSimulacion();
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            detenerHilosActuales();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            System.exit(0);
        });

        JPanel panelDeControl = new JPanel();
        panelDeControl.add(btnPrevencion);
        panelDeControl.add(btnAddFilosofo);
        panelDeControl.add(btnSalir);
        
        setLayout(new BorderLayout());
        add(panelDeControl, BorderLayout.SOUTH);
        
        inicializarSimulacion();
        
        Timer timer = new Timer(100, e -> repaint());
        timer.start();
    }
    
    private void reiniciarSimulacion() {
        detenerHilosActuales();
        inicializarSimulacion();
    }
    
    private void detenerHilosActuales() {
        for (Filosofo f : filosofos) {
            f.detener();
        }
        for (Thread t : hilosFilosofos) {
            t.interrupt();
        }
        
        try {
            for (Thread t : hilosFilosofos) {
                t.join(100); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        filosofos.clear();
        tenedores.clear();
        hilosFilosofos.clear();
    }
    
    private void inicializarSimulacion() {
        for (int i = 0; i < numeroDeFilosofos; i++) {
            tenedores.add(new Tenedor(i));
        }

        for (int i = 0; i < numeroDeFilosofos; i++) {
            
            Tenedor tenedorDerecho = tenedores.get(i);
            Tenedor tenedorIzquierdo = tenedores.get((i - 1 + numeroDeFilosofos) % numeroDeFilosofos);

            boolean esZurdo = previeneInterbloqueo && (i == numeroDeFilosofos - 1);
            
            Filosofo filosofo = new Filosofo(i, tenedorIzquierdo, tenedorDerecho, esZurdo);
            filosofos.add(filosofo);
            Thread hilo = new Thread(filosofo, "Filósofo " + i);
            hilosFilosofos.add(hilo);
            hilo.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 - 40;
        int tableRadius = Math.min(centerX, centerY) - 50;
        int philosopherRadius = tableRadius - 30;

        g2d.setColor(new Color(210, 180, 140));
        g2d.fillOval(centerX - tableRadius, centerY - tableRadius, tableRadius * 2, tableRadius * 2);
        g2d.setColor(new Color(139, 69, 19));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(centerX - tableRadius, centerY - tableRadius, tableRadius * 2, tableRadius * 2);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < filosofos.size(); i++) {
            double angle = 2 * Math.PI * i / filosofos.size();
            int x = (int) (centerX + philosopherRadius * Math.cos(angle));
            int y = (int) (centerY + philosopherRadius * Math.sin(angle));
            
            Filosofo f = filosofos.get(i);
            Filosofo.Estado estado = f.getEstado();
            
            switch (estado) {
                case PENSANDO: g2d.setColor(Color.BLUE); break;
                case HAMBRIENTO: g2d.setColor(Color.ORANGE); break;
                case COMIENDO: g2d.setColor(Color.GREEN); break;
            }
            
            g2d.fillOval(x - 25, y - 25, 50, 50);
            g2d.setColor(Color.WHITE);
            g2d.drawString("F" + f.getId(), x - 7, y + 5);
            g2d.setColor(Color.BLACK);
            g2d.drawString(estado.toString(), x - 40, y + 45);
        }

        int forkRadius = philosopherRadius - 40;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < tenedores.size(); i++) {
            double angle = 2 * Math.PI * (i + 0.5) / tenedores.size();
            int x = (int) (centerX + forkRadius * Math.cos(angle));
            int y = (int) (centerY + forkRadius * Math.sin(angle));
            
            Tenedor t = tenedores.get(i);
            if (t.estaLibre()) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString("Libre", x - 15, y + 25);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString("F" + t.getPoseedor(), x - 10, y + 25);
            }
            g2d.fillRect(x - 5, y - 15, 10, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawString("T" + t.getId(), x - 5, y - 20);
        }
    }
}