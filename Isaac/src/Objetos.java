import java.util.ArrayList;

public class Objetos {
    String nombre;
    int vidaExtra;
    int ataqueExtra;



    public Objetos(String nombre, int vidaExtra, int ataqueExtra) {
        this.nombre = nombre;
        this.vidaExtra = vidaExtra;
        this.ataqueExtra = ataqueExtra;
    }
    public static ArrayList<Objetos> objetos = new ArrayList<>();
    static{
        objetos.add(new Objetos("Corazón", 3, 0));
        objetos.add(new Objetos("Espada de sangre", 0, 2));
        objetos.add(new Objetos("Pócima mágica", 2, 1));
        objetos.add(new Objetos("Muslito de pollo", 1, 1));
        objetos.add(new Objetos("Sopa de champiñones", 3, 0));
        objetos.add(new Objetos("Tirachinas", 0, 3));
        objetos.add(new Objetos("Seta mágica", 3, 3));
        objetos.add(new Objetos("Corazón de alma", 0, 2));
        objetos.add(new Objetos("Espada sagrada", 0, 4));
        objetos.add(new Objetos("Huevo de monstruo", 1, 0));
        objetos.add(new Objetos("Anillo del diablo", 0, 5));
        objetos.add(new Objetos("Garra de gato", 1, 2));
        objetos.add(new Objetos("Bala de cañón", 0, 3));
        objetos.add(new Objetos("Corazón negro", 0, 1));
        objetos.add(new Objetos("Bofetada divina", 2, 1));
        objetos.add(new Objetos("Carne podrida", -1, 0));
        objetos.add(new Objetos("Poción de la suerte", 0, 2));
        objetos.add(new Objetos("Dado del diablo", 0, 1));
        objetos.add(new Objetos("Cola de rata", 0, 2));
        objetos.add(new Objetos("Llave del destino", 0, 3));
        objetos.add(new Objetos("Huevo explosivo", 0, 4));
        objetos.add(new Objetos("Cajón misterioso", 1, 1));
        objetos.add(new Objetos("Perla oscura", 0, 6));
        objetos.add(new Objetos("Corazón dorado", 2, 0));
        objetos.add(new Objetos("Corona de espinas", 0, 5));
        objetos.add(new Objetos("Bola de cristal", 0, 7));
        objetos.add(new Objetos("Cápsula venenosa", 0, 2));
        objetos.add(new Objetos("Espejo del alma", 0, 3));


    }


    public static Objetos objetorandom(){
        // Generar un índice aleatorio
        int indiceAleatorio = (int) (Math.random() * objetos.size());

        // Obtener el objeto aleatorio
        Objetos objetoSeleccionado = objetos.get(indiceAleatorio);

        // Eliminar el objeto de la lista
        objetos.remove(indiceAleatorio);

        // Devolver el objeto seleccionado
        return objetoSeleccionado;

    }

    public int moendas(){
        return (int) (Math.random() * (10 - 3 + 1)) + 3;
    }
    /*public static boolean hollymantel(){
        if(Personajes.personajes(){}
        return true;

        }*/

}
