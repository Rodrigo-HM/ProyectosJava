package ficheros;

import preguntas.Opcion;
import preguntas.Pregunta;
import users.Partida;
import users.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GestionaFicheros {
    private static final File filePreguntas=new File("src/ficheros/Preguntas.txt");
    private static final File fileUser=new File("src/ficheros/users.dat");
    private static final File filePartidas= new File("src/ficheros/Partidas.txt");

    //guarda con ObjectOutputStream todas las personas usuarias en el fichero binario fileUsers.
    public static void guardaUsers(ArrayList<User>users) throws  IOException{
        try {
            // Se crea un 'oos' para escribir objetos en una secuencia de bytes, que envuelve
            //un FileOutputStream(que se utiliza para escribir datos en un archivo) y que
            //está representado por fileUser, que es la ruta relativa del archivo donde se almacenarán los objetos.
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileUser));
            oos.writeObject(users);//se serializa todo el contenido de la lista users y se guarda en el archivo fileUser
            oos.close();//cierra oos inmediatamente despues de escribir el objeto
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());;
            //Manejar errores de entrada y salida de datos
        }
    }
    public static ArrayList<User> cargaUsers() {
        ArrayList<User> users = new ArrayList<>();

        if (fileUser.exists() && fileUser.canRead()) { // Verifica si el archivo existe y se puede leer
            try  {
                //Se crea un flujo de entrada (FileInputStream) para leer el contenido binario del archivo fileUser, y después
                //se envuelvelve en un ObjectInputStream, que se usa para leer objetos en lugar de solo bytes.
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileUser));
                //Se necesita hacer un casting a ArrayList<User> porque ois.readObject() devuelve un Object, y sabemos que el archivo guarda una lista de usuarios.
                users = (ArrayList<User>) ois.readObject();
            } catch (IOException   ioe) {
                System.out.println("Error al cargar usuarios: " + ioe.getMessage());
            } catch(ClassNotFoundException cnfe) {
                System.out.println("Error al cargar usuarios: " + cnfe.getMessage());
            }
        } else {
            System.out.println("El archivo no existe o no se puede leer.");
        }
        return users;
    }


    public static ArrayList<Pregunta> cargarPreguntas() {
        ArrayList<Pregunta> preguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePreguntas))) {
            String linea;

            while ((linea = br.readLine()) != null) { // Leer la pregunta
                String preguntaTexto = linea.trim(); // Eliminar espacios en blanco

                // Leer las siguientes cuatro líneas como opciones
                String opcionCorrecta = br.readLine().trim(); // La primera opción es la correcta
                String opcion2 = br.readLine().trim();
                String opcion3 = br.readLine().trim();
                String opcion4 = br.readLine().trim();

                // Verificar que ninguna opción sea null (por si el archivo está mal formateado)
                if (preguntaTexto != null && opcionCorrecta != null && opcion2 != null && opcion3 != null && opcion4 != null) {
                    // Crear las opciones
                    Opcion op1 = new Opcion(opcionCorrecta, true);
                    Opcion op2 = new Opcion(opcion2, false);
                    Opcion op3 = new Opcion(opcion3, false);
                    Opcion op4 = new Opcion(opcion4, false);

                    // Crear la pregunta con sus opciones
                    Pregunta pregunta = new Pregunta(preguntaTexto, new Opcion[]{op1, op2, op3, op4});

                    // Agregar la pregunta a la lista
                    preguntas.add(pregunta);
                } else {
                    System.out.println("Error en el formato del archivo, pregunta incompleta.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return preguntas;
    }

    public static void guardaPartida(Partida partida){
        if (filePartidas.exists()&&filePartidas.canWrite()){
            try {
                //abrimos un bufferedWritter para poder escribir sobre el fichero de texto, envolviendo un FileWriter
                BufferedWriter bw= new BufferedWriter(new FileWriter(filePartidas, true));//se pone el true para no sobreescribir lo que haya en el fichero
                Date ahora = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //para cambiar formato
                String fechaHora = formatter.format(ahora);

                //Crear línea con los datos de la partida
                String linea = fechaHora + ", USUARIO: " + partida.getPlayer() + ", PUNTUACIÓN: " + partida.getPuntuacion();

                bw.write(linea);
                bw.newLine();// Insertamos un salto de linea
                bw.close(); // Se cierra manualmente

                System.out.println("Partida guardada correctamente.");

            } catch (IOException e) {
                System.out.println(e.getMessage());;
            }
        }else{
            System.out.println("No se puede escribir en el archivo de partidas.");
        }
    }
    public static ArrayList<String> leePartida() {
        ArrayList<String> infoPartidas = new ArrayList<>();

        if (filePartidas.exists() && filePartidas.canRead()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePartidas))) {
                String linea;
                while ((linea = br.readLine()) != null) { // Lee la línea y la guarda correctamente
                    infoPartidas.add(linea);
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de partidas: " + e.getMessage());
            }
        }

        return infoPartidas;
    }


}








