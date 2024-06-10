package gameLaby.laby;

public class Porte extends Entite{
    private boolean ouvert = false;
    public int type;

    public Porte(int dx, int dy, int typePorte) {
        super(dx, dy);
        this.type = typePorte;
    }

    public void ouvrir() {
        System.out.println(getType() +  " ouverte");
        ouvert = true;
    }

    public void fermer() {
        ouvert = false;
        System.out.println(getType() +  " fermée");
    }

    public boolean estOuverte() {
        return ouvert;
    }

    public String getType() {
        if (type == 0) {
            return "porte à clef";
        } if (type == 1) {
            return "porte à mécanisme";
        }
        else {
            throw new Error("Type de porte inconnu");
        }
    }


}
