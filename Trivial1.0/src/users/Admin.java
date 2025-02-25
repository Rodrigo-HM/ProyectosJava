package users;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    public Admin(String nombre, String pass) {
        super(nombre, pass);
    }

    @Override
    public boolean permisosAdmin() {
        return true;
    }


}

