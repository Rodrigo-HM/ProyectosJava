import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

//se implemena ActionListener para poder usar el timer
//se agrega keyListener para poder mover a pacman

public class PacMan extends JPanel implements ActionListener, KeyListener{  
    class Block{
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction='U';  //U=up, D=down, L=left, R=right
        int velocityX=0;
        int velocityY=0;

        Block(Image image, int x, int y, int width, int height){
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            this.startX = x;
            this.startY = y;

        }

        void updateDirection(char direction){  //metodo para actualizar la direccion
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x+=this.velocityX;
            this.y+=this.velocityY;
            for(Block wall : walls){   //revisa si hay colision con una pared
                if(collision(this, wall)){   //si hay colision, el this es o pacman o un fantasma
                    this.x-=this.velocityX;  //se regresa a la posicion anterior
                    this.y-=this.velocityY;  //se regresa a la posicion anterior
                    this.direction=prevDirection;  //se regresa a la direccion anterior
                    updateVelocity();  //se actualiza la velocidad
                }
            }
        }
        void updateVelocity(){          //metodo para elegir direccion
            if(this.direction=='U'){
                this.velocityX=0;
                this.velocityY=-tileSize/4;   //se divide entre 4 para que se mueva mas lento y fluido
            }
            else if(this.direction=='D'){
                this.velocityX=0;
                this.velocityY=tileSize/4;
            }
            else if(this.direction=='L'){
                this.velocityX=-tileSize/4;
                this.velocityY=0;
            }
            else if(this.direction=='R'){
                this.velocityX=tileSize/4;
                this.velocityY=0;
            }

        }
        void reset(){
            this.x=this.startX;
            this.y=this.startY;
        }


    }
    private int rowCont = 21;
    private int colCont = 19;
    private int tileSize = 32;
    private int boardWith= colCont*tileSize;
    private int boardHeight= rowCont*tileSize;


    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinckGhostImage;
    private Image redGhostImage;
    private Image moneda;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    private Image cerezaG;
    private Image cerezaP;
    private Image scaredGhost;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red

    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X   C    X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;
    Block cereza;

    Timer gameLoop;
    Timer tiempocereza;
    int contadorcereza=0;
    char[] directions= {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score=0;
    int lives=3;
    boolean gameover=false;
    boolean asustados=false;


    PacMan(){
        setPreferredSize(new Dimension(boardWith, boardHeight));
        setBackground(new Color(92, 59, 52));
        addKeyListener(this);  //se agrega el keyListener
        setFocusable(true);    //se pone en foco para que pueda recibir eventos

      //cargar imagenes
    wallImage = new ImageIcon(getClass().getResource("./roca.png")).getImage();
    blueGhostImage = new ImageIcon(getClass().getResource("./mascara.png")).getImage();
    orangeGhostImage = new ImageIcon(getClass().getResource("./monstruopng.png")).getImage();
    pinckGhostImage = new ImageIcon(getClass().getResource("./Mulliboom.png")).getImage();
    redGhostImage = new ImageIcon(getClass().getResource("./enemigo1.png")).getImage();

    pacmanUpImage = new ImageIcon(getClass().getResource("./isaccE.png")).getImage();
    pacmanDownImage = new ImageIcon(getClass().getResource("./isaacF.png")).getImage();
    pacmanLeftImage = new ImageIcon(getClass().getResource("./isaacI.png")).getImage();
    pacmanRightImage = new ImageIcon(getClass().getResource("./isaacD.png")).getImage();

    cerezaG = new ImageIcon(getClass().getResource("./seta.png")).getImage();
    cerezaP = new ImageIcon(getClass().getResource("./seta.png")).getImage();

    scaredGhost = new ImageIcon(getClass().getResource("./muligan.png")).getImage();
    moneda = new ImageIcon(getClass().getResource("./moneda.png")).getImage();

    loadMap();    
    for(Block ghost: ghosts){
        char newDirection = directions[random.nextInt(4)]; //se elige una direccion aleatoria del 0 al 3
        ghost.updateDirection(newDirection);  //se actualiza la direccion del fantasma
    }
    //20 fps (1000/50)
    gameLoop = new Timer(50, this);  //cada 50 milisegundos se ejecuta el actionPerformed y el this es la clase packman
    gameLoop.start();  //inicia el timer
    // Configura el Timer para la cereza
    tiempocereza = new Timer(1000, new ActionListener() {  //clase anonima donde todo va dentro
        @Override
        public void actionPerformed(ActionEvent e) { 
            contadorcereza++;
            if (contadorcereza >= 10) {  // 10 segundos
                tiempocereza.stop();
                asustados=false;
            }
        }
    });

    }



    public void loadMap(){
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for(int r=0; r< rowCont; r++){        
            for(int c=0; c< colCont; c++){
                String row =tileMap[r];            //recorre todo el mapa y lo guarda en row
                char tileMapChar= row.charAt(c);  //recore todo el mapa y lo guarda en tileMapChar

                int x= c*tileSize;              //multiplica la columna por el tamaño de la casilla
                int y= r*tileSize;

                if(tileMapChar == 'C'){
                    cereza = new Block(cerezaG, x, y, tileSize, tileSize); //si el caracter es C crea un bloque de cereza
                    
                }
                else if(tileMapChar == 'X'){         
                Block wall = new Block(wallImage, x, y, tileSize, tileSize); //si el caracter es X crea un bloque de pared
                walls.add(wall);                                             //agrega la pared al conjunto de paredes    
                }
                else if(tileMapChar=='b'){      //si el caracter es b crea un bloque de fantasma azul
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar=='o'){      //si el caracter es o crea un bloque de fantasma naranja
                Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                ghosts.add(ghost);
                }
                else if(tileMapChar=='p'){      //si el caracter es p crea un bloque de fantasma rosa
                Block ghost = new Block(pinckGhostImage, x, y, tileSize, tileSize);
                ghosts.add(ghost);
                }
                else if(tileMapChar=='r'){      //si el caracter es r crea un bloque de fantasma rojo
                Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                ghosts.add(ghost);
                }
                else if (tileMapChar=='P'){     //si el caracter es P crea un bloque de pacman
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if(tileMapChar==' '){      //si el caracter es ' ' crea un bloque de comida
                Block food = new Block(moneda, x, y, tileSize, tileSize); //la comida es un cuadro de 4x4 y se coloca en el centro de la casilla con y+14 y x+14
                foods.add(food);
                }
                
                
            }   
        }
        
    }
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);
        if(cereza!=null){ g.drawImage(cereza.image, cereza.x, cereza.y, cereza.width, cereza.height, null);
        }

        for(Block wall : walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        for(Block food : foods){
           // g.setColor(Color.WHITE);
            g.drawImage(food.image,food.x, food.y, food.width, food.height,null);
        }
        for(Block ghost : ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }    
        

    //Puntuacion
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        if(gameover){
            g.drawString("Game Over: "+String.valueOf(score), tileSize/2, tileSize/2);
        }
        else{
            g.drawString("x" +String.valueOf(lives)+" Score: "+String.valueOf(score), tileSize/2, tileSize/2);
        }
    }

    public void move(){  //metodo para mover a pacman
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        //colisiones con las paredes
        for(Block wall : walls){
            if(collision(pacman, wall)){  //si pacman colisiona con una pared (con el metodo de abajo)
                pacman.x -= pacman.velocityX; //se regresa a la posicion anterior
                pacman.y -= pacman.velocityY; //se regresa a la posicion anterior
                break; //se sale del ciclo para no seguir revisando las demas paredes
            }
        }
        //colisiones de fantasmas
        for (Block ghost : ghosts) {
            if (asustados==false&&collision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameover = true;
                    return;
                }
                resetPositions();
            }

            if (ghost.y == tileSize*9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWith) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        //check food collision
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);

        //check cereza collision
        if (cereza!=null&&collision(pacman, cereza)) {
            score += 50;
            cereza= null;  //elimina la cereza
            asustados=true;
            tiempocereza.start();
        }
        // Actualiza las imágenes de los fantasmas si están asustados
        if (asustados) {
            for (Block ghost : ghosts) {
                ghost.image = scaredGhost;
            }
            Block fantasmacomido = null;
            for (Block ghost : ghosts) {
                if (collision(pacman, ghost)) {
                    fantasmacomido = ghost;
                    score += 50;
                }
            }
            ghosts.remove(fantasmacomido);
        }
        // Actualiza las imágenes de los fantasmas si no están asustados
        if(!asustados){
            int contador=-1;
            for (Block ghost : ghosts) {
                contador++;
                switch (contador) {
                    case 0:
                    ghost.image = redGhostImage;
                        
                        break;
                    case 1:
                    ghost.image = blueGhostImage;
                        break;
                    case 2:
                    ghost.image = orangeGhostImage;
                        break;
                    case 3:
                    ghost.image = pinckGhostImage;
                        break;
                
                    default:
                        break;
                }
                
            }
        }


        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }
    
    public boolean collision(Block a, Block b){  //metodo para detectar colisiones (copiado de internet)
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    public void resetPositions(){
        pacman.reset();
        pacman.velocityX=0;
        pacman.velocityY=0;
        for(Block ghost: ghosts){ //para hacerlo en todos los fantasmas
            ghost.reset();
            char newDirection = directions[random.nextInt(4)]; //se elige una direccion aleatoria del 0 al 3
            ghost.updateDirection(newDirection);  //se actualiza la direccion del fantasma
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {  //se crea al implementar la interfaz ActionListener en la clase (con quick fix)
        move();                                    //va con el timer, es decir hace eso cada vez que salta el timer
        repaint();
        if(gameover){
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}  //para tecla tipeada

    @Override
    public void keyPressed(KeyEvent e) {} //para tecla presionada

    @Override
    public void keyReleased(KeyEvent e) { //para tecla liberada
        if (gameover){
            loadMap();
            resetPositions();
            lives=3;
            score=0;
            gameover=false;
            gameLoop.start();
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){             //si la tecla presionada es la flecha arriba
            pacman.updateDirection('U');      //actualiza la direccion de pacman
            //pacman.image = pacmanUpImage;             Si lo pones aqui en vez de abajo, cambia aunque no se mueva
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            pacman.updateDirection('D');
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            pacman.updateDirection('L');
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            pacman.updateDirection('R');
            
        }

        if(pacman.direction=='U'){  //si la direccion de pacman es arriba
            pacman.image = pacmanUpImage;  //cambia la imagen de pacman
        }
        else if(pacman.direction=='D'){
            pacman.image = pacmanDownImage;
        }
        else if(pacman.direction=='L'){
            pacman.image = pacmanLeftImage;
        }
        else if(pacman.direction=='R'){
            pacman.image = pacmanRightImage;
        }
    }

}

