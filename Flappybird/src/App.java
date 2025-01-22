import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);
         frame.setSize(boardWidth, boardHeight);

       frame.setLocationRelativeTo(null);   //Lo centra en medio de la pantalla
       frame.setResizable(false);               //hace que no se pueda modificar el tamaño de la ventana
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //se para de ejecutar cuando se cierra

       FlappyBird flappyBird = new FlappyBird(); //crea la instancia
       frame.add(flappyBird);               //lo añade al JFrame
        frame.pack();                       //Ajusta el tamaño, si no el marco entraria dentro de tamaño y seria mas pequeño
        flappyBird.requestFocus();         //da el foco al evento para que reaccione al teclado
        frame.setVisible(true);             //muestra la ventana
    }
}
