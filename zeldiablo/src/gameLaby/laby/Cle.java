package gameLaby.laby;

public class Cle extends Entite{

    public Cle(int dx, int dy) {
        super(dx, dy);
    }

    public void ramasser() {
        System.out.println("cle ramassée");
        this.x = -1;
        this.y = -1;
    }

    public boolean estRamasse() {
        return this.x == -1 && this.y == -1;
    }
}
