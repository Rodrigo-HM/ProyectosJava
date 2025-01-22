import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class WhacAMole {
    int boardWidth= 600;
    int boardHeight= 650;

    JFrame frame=new JFrame("Whac-A-Mole");  //para crear la ventana
    JLabel textLable=new JLabel();       //para mostrar texto o imagenes
    JLabel puntuacionmaxima=new JLabel();
    JPanel textPanel = new JPanel();        //para organizar los componentes
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton reiniciar=new JButton("Restart");
    List<JButton> currPlantTiles = new ArrayList<>();

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score;
    int puntuacionmax;

    int plantInterval = 1500; // Intervalo inicial en milisegundos
    final int minPlantInterval = 300; // Intervalo mínimo en milisegundos

    WhacAMole() {
       // frame.setVisible(true);             //hacer visible el frame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());   //organizar en norte, sur, este, oeste, centro
        

        textLable.setFont(new Font("Arial", Font.PLAIN, 50)); //fuente, estilo, tamaño
        textLable.setHorizontalAlignment(JLabel.CENTER);        //alineación horizontal
        textLable.setText("Score: 0"); //texto
        textLable.setOpaque(true); //permite cambiar el color de fondo

        puntuacionmaxima.setFont(new Font("Arial", Font.BOLD, 25)); //fuente, estilo, tamaño
       // puntuacionmaxima.setHorizontalAlignment(JLabel.CENTER);        //alineación horizontal
        puntuacionmaxima.setText(" TOP: 0"); //texto
       // puntuacionmaxima.setOpaque(true); //permite cambiar el color de fondo

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLable,BorderLayout.CENTER);
        textPanel.add(puntuacionmaxima,BorderLayout.WEST);
        textPanel.add(reiniciar,BorderLayout.EAST);  //lo pone a la drecha
        reiniciar.setFocusable(false);      //quita el recuadro de seleccion
        frame.add(textPanel, BorderLayout.NORTH); //añadir al frame en la parte norte

        boardPanel.setLayout(new GridLayout(3, 3)); //3x3
        frame.add(boardPanel); 

        //plantIcon = new ImageIcon(getClass().getResource("./piranha.png")); asi no sale escalada

        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)); //escalar la imagen

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score=0;
        puntuacionmax=0;

        for(int i=0; i<9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);   //añadir al panel
            tile.setFocusable(false); //no se puede seleccionar
           // tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile=(JButton)e.getSource();
                    if(tile==currMoleTile){
                        score+=10;
                        textLable.setText("Score: "+Integer.toString(score));
                    }
                    else if(currPlantTiles.contains(tile)){
                        textLable.setText("Game Over: "+Integer.toString(score));
                        puntuacionmaxima.setText(" TOP: "+Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i=0; i<9;i++){
                            board[i].setEnabled(false);
                        }
                        if(score>puntuacionmax){
                            puntuacionmax=score;
                            puntuacionmaxima.setText(" TOP: "+Integer.toString(puntuacionmax));
                        }
                        
                        
                    }
            
                }
            });
        }
        reiniciar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                    score=0;
                    textLable.setText("Score: "+Integer.toString(score));
                    plantInterval = 1500; // Reiniciar el intervalo al valor inicial
                    setPlantTimer.setDelay(plantInterval); // Actualizar el intervalo del Timer
                    setMoleTimer.start();
                    setPlantTimer.start();
                    for(int i=0; i<9;i++){
                        board[i].setEnabled(true);
                        board[i].setIcon(null);
                    }

                    currMoleTile = null;   //para que no se quede el icono
                    currPlantTiles.clear(); //para que no se quede el icono
                
            }

        });

        setMoleTimer= new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num=random.nextInt(9);
                JButton tile=board[num];

                //evitar que apareza en el mismo lugar
                if(currPlantTiles.contains(tile)) return;

                currMoleTile=tile;
                currMoleTile.setIcon(moleIcon);
                
            }
        });
        setPlantTimer = new Timer(plantInterval, new ActionListener() {
            //Lógica para mostrar las plantas
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton plantTile : currPlantTiles) {
                    plantTile.setIcon(null);
                }
                currPlantTiles.clear();

                int num1 = random.nextInt(9);
                int num2;
                do {
                    num2 = random.nextInt(9);
                } while (num1 == num2);

                JButton tile1 = board[num1];
                JButton tile2 = board[num2];

                if (currMoleTile == tile1 || currMoleTile == tile2) return;

                tile1.setIcon(plantIcon);
                tile2.setIcon(plantIcon);

                currPlantTiles.add(tile1);
                currPlantTiles.add(tile2);
                // Reducir el intervalo del Timer
                if (plantInterval > minPlantInterval) {
                    plantInterval -= 100; // Reducir el intervalo en 100 ms
                    setPlantTimer.setDelay(plantInterval); // Actualizar el intervalo del Timer
                }
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);  //se pone aqui para que se muestren los componenetes ya cargados

    }

}
