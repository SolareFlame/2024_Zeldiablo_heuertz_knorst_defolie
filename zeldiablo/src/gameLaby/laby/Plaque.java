package gameLaby.laby;

public class Plaque extends Entite{

    public boolean active = false;

    public Plaque(int dx, int dy) {
        super(dx, dy);
    }

    public void changerEtat() {
        active = !active;
    }

    public boolean estActivee() {
        return active;
    }
}
