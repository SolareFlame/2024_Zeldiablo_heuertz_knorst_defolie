package gameLaby.laby;

public class Katana extends Entite {
    public Katana(int dx, int dy) {
        super(dx, dy);
    }

    public void ramasser() {
        System.out.println("katana ramassé");
        this.x = -1;
        this.y = -1;
    }
}
