public class Enemigo {
    String nombre;
    int vida;
    int vidamaxima;

    public void setVidamaxima(int vidamaxima) {
        this.vidamaxima = vidamaxima;
    }

    public int getVidamaxima() {
        return vidamaxima;
    }

    public Enemigo(String nombre, int vida, int vidamacima) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidamaxima = vidamacima;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Enemigo(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
    public static String representarVida(int vidaActual, int vidaMaxima) {
        String corazones = "";
        for (int i = 0; i < vidaMaxima; i++) {
            if (i < vidaActual) {
                corazones += "♥";
            } else {
                corazones += "♡";
            }
        }
        return corazones;
    }

}
