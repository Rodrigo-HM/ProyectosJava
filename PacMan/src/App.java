import javax.swing.JFrame;



public class App {
    public static void main(String[] args) throws Exception {
    int rowCont = 21;
    int colCont = 19;
    int tileSize = 32;
    int boardWith= colCont*tileSize;
    int boardHeight= rowCont*tileSize;


    JFrame frame= new JFrame("Pac Man");
       //frame.setVisible(true);
    frame.setSize(boardWith,boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    PacMan pacManGame = new PacMan();
    frame.add(pacManGame);
    frame.pack();  //ajusta el JFrame automatico, para mostrar todos los componentes
    pacManGame.requestFocus();  //se pone para que PacMan tenga el foco y pueda recibir los eventos del teclado
    frame.setVisible(true);
    }
}
