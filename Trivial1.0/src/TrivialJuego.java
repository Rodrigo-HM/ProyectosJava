import ficheros.GestionaFicheros;
import preguntas.Opcion;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

import javax.swing.*;
import java.util.*;


public class TrivialJuego extends javax.swing.JFrame {

    private static ArrayList<Pregunta> todasLasPreguntas;
    private int contador=5;
    private Player jugador;
    private int puntuacion=0;
    private String correcta;


   /* public static void main(String args[]) {

        new TrivialJuego();
    } */

    public TrivialJuego(Player jugador) {
        this.jugador = jugador;  // Guardamos el jugador recibido
        initComponents();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //cierra con la x
        setVisible(true);
        todasLasPreguntas = ficheros.GestionaFicheros.cargarPreguntas(); //carga las preguntas del fichero en todasLasPreguntas
        //jugar();
    }

    public static Pregunta escogerPregunta() {
        if (todasLasPreguntas.isEmpty()) {
            System.out.println("No hay más preguntas disponibles.");
            return null;
        }

        Random random = new Random();
        return todasLasPreguntas.remove(random.nextInt(todasLasPreguntas.size())); // Evita repetir preguntas, la devuelve en el metodo y la borra de todas las preguntas, no del fichero
    }

    public void mostrarPregunta() {
        Pregunta preguntaAleatoria = escogerPregunta();

        if (preguntaAleatoria == null) {
            jLabel1.setText("No hay más preguntas disponibles.");
            return;
        }

        jLabel1.setText(preguntaAleatoria.getPregunta());

        // Obtener opciones de respuesta y mezclarlas
        ArrayList<Opcion> opciones = new ArrayList<>(List.of(preguntaAleatoria.getOpciones()));
        Collections.shuffle(opciones); // Mezclar las opciones

        // Asignar las opciones a los botones
        jButton1.setText(opciones.get(0).getEnunciado());
        jButton2.setText(opciones.get(1).getEnunciado());
        jButton3.setText(opciones.get(2).getEnunciado());
        jButton4.setText(opciones.get(3).getEnunciado());


        // Buscar la respuesta correcta
        correcta=preguntaAleatoria.getOpcionCorrecta();

    }

    void escogerRespuesta(int n) {
        String seleccion = switch (n) {
            case 0 -> jButton1.getText().trim();  //con .trim quita los espacios para que sea igual
            case 1 -> jButton2.getText().trim();
            case 2 -> jButton3.getText().trim();
            case 3 -> jButton4.getText().trim();
            default -> "";
        };
        System.out.println(seleccion);
        contador--;

        if (seleccion.equals(correcta)) {
            puntuacion++;
            jLabelPuntuacion.setText("Puntuación: " + puntuacion); // ACTUALIZAR PUNTUACIÓN
            JOptionPane.showMessageDialog(this, "Su respuesta es correcta", "¡Bien hecho!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrecto. La respuesta es: " + correcta, "Inténtalo de nuevo", JOptionPane.ERROR_MESSAGE);
        }

        jugar();
    }

    public void jugar() {
        if (todasLasPreguntas.isEmpty()||contador==0) {
            JOptionPane.showMessageDialog(this, "Juego Terminado\nPuntuación total: "+puntuacion, "¡Felicidades!", JOptionPane.PLAIN_MESSAGE);


            // Crear y guardar la partida con un Player en vez de un String
            Partida partida = new Partida(new Date(), puntuacion, jugador);
            ficheros.GestionaFicheros.guardaPartida(partida);

            System.exit(0); //para que se cierre el programa
        }
        mostrarPregunta();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel(); // Para la pregunta
        jPanel3 = new javax.swing.JPanel(); // Para las opciones
        jLabelPuntuacion = new javax.swing.JLabel(); // NUEVO - Para la puntuación

        jLabel1 = new javax.swing.JLabel(); // Texto de la pregunta

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());  // Organiza con BorderLayout

        jPanel1.setLayout(new java.awt.BorderLayout()); // Usa BorderLayout para posicionar los elementos
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pregunta");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        // NUEVO - Agregar el marcador de puntuación arriba a la derecha
        jLabelPuntuacion.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelPuntuacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPuntuacion.setText("Puntuación: " + puntuacion);
        jPanel1.add(jLabelPuntuacion, java.awt.BorderLayout.NORTH); // Lo coloca arriba

        jPanel3.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jButton1.setText("Opción 1");
        jButton1.addActionListener(evt -> escogerRespuesta(0));
        jPanel3.add(jButton1);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24));
        jButton2.setText("Opción 2");
        jButton2.addActionListener(evt -> escogerRespuesta(1));
        jPanel3.add(jButton2);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 24));
        jButton3.setText("Opción 3");
        jButton3.addActionListener(evt -> escogerRespuesta(2));
        jPanel3.add(jButton3);

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 24));
        jButton4.setText("Opción 4");
        jButton4.addActionListener(evt -> escogerRespuesta(3));
        jPanel3.add(jButton4);

        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);
        getContentPane().add(jPanel1);
        pack();
    }


    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPuntuacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;

}
