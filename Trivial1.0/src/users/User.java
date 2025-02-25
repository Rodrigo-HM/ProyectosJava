package users;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L; //para evitar problemas de comatibilidad
    private String nombre;
    private String pass;

    public User(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean cambiarPass(String password) {
        if (password.length() >= 8) {
            this.pass = password;
            return true;
        }
        return false;
    }

    public abstract boolean permisosAdmin();


    public boolean compruebaPass(String password) {
        return this.pass.equals(password);
    }

    @Override
    public String toString() {
        return nombre;
    }
}

