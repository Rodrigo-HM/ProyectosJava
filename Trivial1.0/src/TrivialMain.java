import users.User;
import users.Player;
import users.Admin;
import ficheros.GestionaFicheros;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class TrivialMain {
    private static ArrayList<User> users = GestionaFicheros.cargaUsers();

    public static void main(String[] args) {
        boolean salirbucle=true;
        while (salirbucle) {
            String[] opciones = {"Registro Player", "Registro Admin", "Inicio de Sesión", "Salir"};
            int opcion = JOptionPane.showOptionDialog(
                    null,  //no esta asociado a nada
                    "Seleccione una opción",
                    "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, //Para usar los botones de opciones
                    JOptionPane.PLAIN_MESSAGE,  //pone mensaje normal, sin alerta
                    null, //sin icono
                    opciones,
                    opciones[0]  //opcion que aparece por defecto
            );

            switch (opcion) {
                case 0:
                    registrarUsuario(false);
                    break;
                case 1:
                    registrarUsuario(true);
                    break;
                case 2:
                    if (iniciarSesion()) {
                        salirbucle = false; // Cierra el menú si el usuario inicia sesión
                    }
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo...");  //sale el mensaje y sale
                    salirbucle=false;
                    break;

            }
        }
    }

    private static void registrarUsuario(boolean esAdmin) {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre de usuario:");
        if (nombre == null || nombre.trim().isEmpty()) return; // Cancelado o vacío, cuando no se escribe nada

        if (existeUsuario(nombre)) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:");
        if (contrasena == null || contrasena.length() < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String repetirContrasena = JOptionPane.showInputDialog("Repita la contraseña:");
        if (!contrasena.equals(repetirContrasena)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User nuevoUsuario = esAdmin ? new Admin(nombre, contrasena) : new Player(nombre, contrasena);  //si es usuario normal o admin
        users.add(nuevoUsuario); //lo añade a la lista de usuarios
        try {
            GestionaFicheros.guardaUsers(users);
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean existeUsuario(String nombre) {  //metodo para ver si el usuario existe
        for (User user : users) {
            if (user.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private static boolean iniciarSesion() { //lo hacemos boolean para que cuando inice sesion se cierre y empeice jugar();
        String nombre = JOptionPane.showInputDialog("Ingrese nombre de usuario:");
        if (nombre == null || nombre.trim().isEmpty()) return false;

        String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:");
        if (contrasena == null) return false;
        Player player=new Player(nombre,contrasena);

        for (User user : users) {
            if (user.getNombre().equalsIgnoreCase(nombre) && user.compruebaPass(contrasena)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
                if (user.permisosAdmin()) {
                    new TrivialAdmin().administrar(); //si esta bien el usuario y es admin, abre administrar
                } else {
                    new TrivialJuego(player).jugar(); //si esta bien el usuario y no es admin, abre el juego
                }
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

