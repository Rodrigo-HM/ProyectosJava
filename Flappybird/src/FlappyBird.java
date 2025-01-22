
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



public class FlappyBird  extends JPanel implements ActionListener, KeyListener {
    int boardWith= 360;
    int boardHeith=640;
    //Imágenes

    Image fondoimg;
    Image birdimg;
    Image topimg;
    Image botimg;

    //PÁJARO

    //Pájaro posicion
    int birdX=boardWith/8;
    int birdY=boardWith/2;
    
    //pajaro medidas
    int birdWidth= 34;
    int birdHeigt= 24;




    class Bird{
        int x= birdX;
        int y= birdY;
        int ancho= birdWidth;
        int alto= birdHeigt;
        Image img;

        Bird(Image img){
            this.img=img;
        }
    }

    //TUBERÍAS
    int tuberiaX=boardWith;
    int tuberiaY=0;
    int tuberiaAncho=64;
    int tuberiaAlto=512;


    class Tuberia{
        int x= tuberiaX;
        int y= tuberiaY;
        int ancho= tuberiaAncho;
        int alto= tuberiaAlto;
        Image img;
        boolean pasa=false;

        Tuberia(Image img){
            this.img=img;
        }
    }

    //LÓGICA DEL JUEGO

    Bird bird;
    int velocidadx=-4; //mover las tuberias para que parezca que el pajaro se mueve
    int velocidady= 0;
    int gravedad= 1;

    ArrayList<Tuberia>tuberias;

    Random rand = new Random();

    Timer gameLoop;
    Timer ubiTuberiatimer;
    boolean gameOver= false;
    double puntuacion=0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWith,boardHeith));
      //  setBackground(Color.blue);
        setFocusable(true); //no esta claro para que, tiene que ver con la clase actual
        addKeyListener(this); //chekea las funcines del teclado

        //cargar imagenes
        fondoimg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topimg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        botimg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //Pájaro
        bird = new Bird(birdimg);
        tuberias=new ArrayList<Tuberia>();

        //Tuberias timer
        ubiTuberiatimer = new Timer(1500, new ActionListener() {  //se sobreescirbe  1000 es un segundo
            @Override
            public void actionPerformed(ActionEvent e) {
                lugarTuberia();
            }
        }
        );
        ubiTuberiatimer.start(); //lo inicializa

        //game timer
        gameLoop = new Timer(1000/60, this); //actualiza cada 60 por segundo
        gameLoop.start();

    }

    public void lugarTuberia(){
        //(0-1) * tuberiaAlto/2 --> (0-256)
        //128
        //0 - 128- (0-256)--> tuberiaAlto/4  -->3/4 tuberiaALto
        int randotubeY=(int) (tuberiaY- tuberiaAlto/4-Math.random()*(tuberiaAlto/2));
        int espacio=boardWith/4;

        Tuberia toptuberia= new Tuberia(topimg);
        toptuberia.y=randotubeY;
        tuberias.add(toptuberia);

        Tuberia botontuberia= new Tuberia(botimg);
        botontuberia.y=toptuberia.y +tuberiaAlto+espacio;
        tuberias.add(botontuberia);
    }

    public void paintComponent(Graphics g) {  //metodo para dibujar el componente
        super.paintComponent(g);    //metodo de JPanel, para limpiar contenido que no se ve por si se actualiza
        draw(g);  //llama al metodo
    }


    public void draw(Graphics g) {     //metodo para establecer medidas y posicion de la imagen

            //fondo
        g.drawImage(fondoimg,0,0,boardWith,boardHeith,null);

        //Pájaro
        g.drawImage(bird.img, bird.x, bird.y, bird.ancho, bird.alto, null);

        //Tuberia
        for (int i = 0; i < tuberias.size(); i++) {
           Tuberia tuberia=tuberias.get(i);
           g.drawImage(tuberia.img, tuberia.x, tuberia.y, tuberia.ancho, tuberia.alto, null);

        }

        //Puntiacion
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over: "+String.valueOf((int) puntuacion), 10, 35);
        }
        else{
            g.drawString(String.valueOf((int) puntuacion), 10, 35);
        }

    }
    public void movimiento (){
        //pájaro
        velocidady+=gravedad;
        bird.y+=velocidady;
        bird.y=Math.max(bird.y,0);  //para que no salga de la pantalla

        //Tuberias
        for (int i = 0; i < tuberias.size(); i++) {
            Tuberia tuberia=tuberias.get(i);
            tuberia.x+=velocidadx;

            if(!tuberia.pasa && bird.x>tuberia.x+tuberiaAncho){  //si bird pasa la posicion de la tuberia al final del ancho
                tuberia.pasa=true;
                puntuacion+=0.5;  //0.5 porque pasa dos tuberias a la vez, asi gana un punto cada vez que pasa
            }

            if(colision(bird, tuberia)){
                gameOver=true;
            }

        }
        if(bird.y>boardHeith){
            gameOver=true;
        }
    }

    public boolean colision(Bird a, Tuberia b){
        return a.x < b.x + b.ancho &&   //a's top left corner doesn't reach b's top right corner
                a.x + a.ancho > b.x &&   //a's top right corner passes b's top left corner
                a.y < b.y + b.alto &&  //a's top left corner doesn't reach b's bottom left corner
                a.y + a.alto > b.y;    //a's bottom left corner passes b's top left corner
    }

    @Override               //se llama cada vex que se dispara el evento
    public void actionPerformed(ActionEvent e) {
        movimiento();
        repaint();  //se redibuja cada vez
        if(gameOver){
            ubiTuberiatimer.stop();
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {  //cualquier tecla
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {  //cuando se presione el espacio
            velocidady=-9;
            if(gameOver){
                //reinicia el juego cuando
                bird.y=birdY;
                velocidady=0;
                tuberias.clear();
                puntuacion=0;
                gameOver=false;
                gameLoop.start();
                ubiTuberiatimer.start();
            }
            }
    }
    @Override
    public void keyTyped(KeyEvent e) {  //caracter (solo letras y eso)

    }

    @Override
    public void keyReleased(KeyEvent e) {  //cuando acaba la tecla

    }
}
