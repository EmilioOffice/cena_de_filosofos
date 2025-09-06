import java.util.Random;

public class Filosofo implements Runnable {

    public enum Estado {
        PENSANDO,
        HAMBRIENTO,
        COMIENDO
    }

    private final int id;
    private final Tenedor tenedorIzquierdo;
    private final Tenedor tenedorDerecho;
    private volatile boolean corriendo = true;
    private volatile Estado estado;
    private final Random random = new Random();
    private final boolean esZurdo;

    public Filosofo(int id, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, boolean esZurdo) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.estado = Estado.PENSANDO;
        this.esZurdo = esZurdo;
    }

    @Override
    public void run() {
        while (corriendo) {
            try {
                pensar();
                tomarTenedores();
                comer();
                soltarTenedores();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                corriendo = false;
            }
        }
    }

    private void pensar() throws InterruptedException {
        setEstado(Estado.PENSANDO);
        Thread.sleep(random.nextInt(3000) + 1000);
    }

    private void tomarTenedores() throws InterruptedException {
        setEstado(Estado.HAMBRIENTO);
        
        while (corriendo) {
            // Prevenir interbloqueo: el filósofo "zurdo" toma los tenedores en orden inverso
            if (esZurdo) {
                if (tenedorDerecho.tomar(id)) {
                    if (tenedorIzquierdo.tomar(id)) {
                        break; // Ambos tenedores tomados, salir del bucle
                    } else {
                        tenedorDerecho.soltar();
                    }
                }
            } else { // Comportamiento normal (propenso a interbloqueo)
                if (tenedorIzquierdo.tomar(id)) {
                    if (tenedorDerecho.tomar(id)) {
                        break;
                    } else {
                        tenedorIzquierdo.soltar();
                    }
                }
            }
            Thread.sleep(100);
        }
    }

    private void comer() throws InterruptedException {
        if (!corriendo) return;
        setEstado(Estado.COMIENDO);
        Thread.sleep(random.nextInt(4000) + 2000);
    }

    private void soltarTenedores() {
        tenedorIzquierdo.soltar();
        tenedorDerecho.soltar();
    }

    public void detener() {
        corriendo = false;
    }

    public int getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    private void setEstado(Estado estado) {
        this.estado = estado;
    }
}