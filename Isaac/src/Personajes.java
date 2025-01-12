import java.util.ArrayList;
import java.util.Scanner;

public class Personajes {
    String nombre;
    int vida;
    int ataque;
    int vidamaxima;



    public static ArrayList<Personajes> personajes = new ArrayList<>();

    static {
        personajes.add(new Personajes("Isaac", 5, 2, 10));
        personajes.add(new Personajes("Cain", 4, 3, 8));
        personajes.add(new Personajes("Magdalena", 8, 1, 15));
        personajes.add(new Personajes("The Lost", 1, 10, 1));
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getVidamaxima() {
        return vidamaxima;
    }

    public Personajes(String nombre, int vida, int ataque, int vidamaxima) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.vidamaxima = vidamaxima;
    }

    public Personajes(String nombre, int vida, int ataque) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
    }

    public void atacar(Enemigo enemigo) {
        enemigo.vida -= this.ataque;
        System.out.println(nombre + " atacó al enemigo " + enemigo.nombre + " causando " + ataque + " de daño.");
    }

    public void recogerObjeto(Objetos objeto) {

        this.vida += objeto.vidaExtra;
        this.ataque += objeto.ataqueExtra;
        if(vida>=vidamaxima) {
            vida=vidamaxima;
        }
        System.out.println(nombre + " recogió " + objeto.nombre + ". Vida: +" + objeto.vidaExtra + ", Ataque: +" + objeto.ataqueExtra);
    }
    public static String representarVida(int vidaActual, int vidaMaxima) {
        String corazones = "";
        for (int i = 0; i < vidaMaxima; i++) {
            if (i < vidaActual) {
                corazones += "♥"; // Corazón lleno
            } else {
                corazones += "♡"; // Corazón vacío
            }
        }
        return corazones;
    }

    public static Personajes seleccionarPersonaje() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Selecciona un personaje:");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.println((i + 1) + ". " + personajes.get(i).getNombre() +
                    " (Vida: " + personajes.get(i).getVida() +"/"+personajes.get(i).getVidamaxima()+ ", Ataque: " + personajes.get(i).getAtaque() + ")");
        }

        int eleccion;
        do {
            System.out.print("Introduce el número de tu personaje (1-" + personajes.size() + "): ");
            eleccion = scanner.nextInt();
        } while (eleccion < 1 || eleccion > personajes.size());

        System.out.println("Has seleccionado a: " + personajes.get(eleccion-1).getNombre());
        return personajes.get(eleccion - 1);
    }
}


