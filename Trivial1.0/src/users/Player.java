package users;

import java.io.Serializable;

public class Player extends User implements Serializable {

    public Player(String nombre, String pass) {
        super(nombre, pass);
    }


    @Override
    public boolean permisosAdmin() {
        return false;
    }


}

