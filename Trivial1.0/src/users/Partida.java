package users;

import java.util.Date;

public class Partida {
    private Date date;
    private int puntuacion;
    private Player player;

    public Partida(Date date, int puntuacion, Player player) {
        this.date = date;
        this.puntuacion = puntuacion;
        this.player = player;
    }
    private int sumarPunto(){
        return puntuacion ++;
    }

    public Date getDate() {
        return date;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Player getPlayer() {
        return player;
    }
}

