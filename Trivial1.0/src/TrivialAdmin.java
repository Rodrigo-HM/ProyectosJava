import users.User;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class TrivialAdmin {

    public void administrar() {
        // Mostrar todas las partidas
        System.out.println("Ha entrado en admin");
        ArrayList<String> partidas = ficheros.GestionaFicheros.leePartida();
        String partidasTexto = "Partidas Registradas:\n";
        for (String partida : partidas) {
            partidasTexto += partida + "\n"; // Concatenación manual
        }
        JOptionPane.showMessageDialog(null, partidasTexto, "Historial de Partidas", JOptionPane.INFORMATION_MESSAGE);

        // Mostrar usuarios ordenados alfabéticamente
        ArrayList<User> usuarios = ficheros.GestionaFicheros.cargaUsers();
        usuarios.sort(Comparator.comparing(User::getNombre, String.CASE_INSENSITIVE_ORDER)); // Orden alfabético usando comparator

        String usuariosTexto = "Usuarios registrados:\n";
        for (User user : usuarios) {
            usuariosTexto += user.getNombre() + "\n"; // Concatenación manual
        }
        JOptionPane.showMessageDialog(null, usuariosTexto, "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }
}


