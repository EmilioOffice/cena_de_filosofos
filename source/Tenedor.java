import java.util.concurrent.locks.ReentrantLock;

public class Tenedor {
    private final int id;
    private final ReentrantLock lock = new ReentrantLock();
    private int poseedor; // ID del filósofo que lo posee

    public Tenedor(int id) {
        this.id = id;
        this.poseedor = -1; // -1 indica que está libre
    }

    public boolean tomar(int filosofoId) {
        if (lock.tryLock()) {
            this.poseedor = filosofoId;
            return true;
        }
        return false;
    }

    public void soltar() {
        if (lock.isHeldByCurrentThread()) {
            this.poseedor = -1;
            lock.unlock();
        }
    }

    public int getId() {
        return id;
    }

    public int getPoseedor() {
        return poseedor;
    }

    public boolean estaLibre() {
        return !lock.isLocked();
    }
}