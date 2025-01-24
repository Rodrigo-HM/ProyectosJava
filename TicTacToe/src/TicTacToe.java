import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class TicTacToe {

    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe"); //crea un frame
    JLabel textLabel = new JLabel(); //para el texto
    JPanel textPanel = new JPanel(); //para el texto agrupado
    JPanel boardPanel = new JPanel(); //para el tablero

    JButton [][] board=new JButton[3][3]; //crea un tablero de 3x3
    String playerX ="X";
    String playerO ="O";
    String currentPLayer=playerX;

    boolean gameover=false;
    int turn=0;

    TicTacToe() {
        frame.setVisible(true); 
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); //organiza los componentes en el frame en norte sur este oeste y centro

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white); //para el texto
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic Tac Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout()); //organiza los componentes en el frame en norte sur este oeste y centro
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH); // Añadir el textPanel al frame 

        boardPanel.setLayout(new GridLayout(3, 3)); //crea un grid de 3x3
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);




        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                JButton tile=new JButton();
                board[r][c]=tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusPainted(false); //para que no se vea el borde


                

                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameover) return;

                        JButton tile = (JButton) e.getSource();
                        if(tile.getText()==""){
                            tile.setText(currentPLayer);

                            //maquina
                            int contador=-1;
                            ArrayList<Integer>row=new ArrayList<>();
                            ArrayList<Integer>col=new ArrayList<>();
                            for(int r=0; r<3; r++){
                                for(int c=0; c<3; c++){
                                    if(board[r][c].getText()=="X"){
                                        contador++;
                                        row.add(r);
                                        col.add(c);

                                    }
                                    //solo para verlo (se puede quitar
                                    if(r==2&&c==2){
                                        System.out.println(contador);
                                        System.out.println(row);
                                        System.out.println(col);}
                                }
                            }

                            if(contador==1){

                                if(board[row.get(0)][col.get(0)].equals(playerX)){
                                    tile=board[1][1];
                                    tile.setText(currentPLayer);

                                }

                            }



                            turn++;
                            checkWinner();
                            if(!gameover){
                                currentPLayer=currentPLayer.equals(playerX)?playerO:playerX;
                                textLabel.setText(currentPLayer+"'s turn.");
                            }



                            
                        }
                        
                    }

                    
                });

            }
        }

        
    }
    void checkWinner(){
        //horizontal
        for(int r=0; r<3; r++){
            if(board[r][0].getText()=="") continue; //si esta vacio continua porque no gana

            if(board[r][0].getText()==board[r][1].getText() &&
                board[r][1].getText()==board[r][2].getText()){
                    for(int i=0; i<3; i++){
                        setWinner(board[r][i]);
                    }
                    gameover=true;
                    return;
            }
        }
        //vertical
        for(int c=0; c<3; c++){
            if(board[0][c].getText()=="") continue;

            if(board[0][c].getText()==board[1][c].getText() &&
                board[1][c].getText()==board[2][c].getText()){
                    for(int i=0; i<3; i++){
                        setWinner(board[i][c]);
                    }
                    gameover=true;
                    return;
            }
        }

        //diagonal
        if( board[0][0].getText()==board[1][1].getText() &&
            board[1][1].getText()==board[2][2].getText() &&
            board[0][0].getText()!=""){
                for(int i=0; i<3; i++){
                    setWinner(board[i][i]);
                }
                gameover=true;
                return;
        }

        if( board[0][2].getText()==board[1][1].getText() &&
            board[1][1].getText()==board[2][0].getText() &&
            board[0][2].getText()!=""){
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);
                gameover=true;
                return;
        }

        if(turn==9){
            for(int r=0; r<3; r++){
                for(int c=0; c<3; c++){
                    setTie(board[r][c]);

                }
            }
            gameover=true;
        }
    }
    void setWinner(JButton tile){
        tile.setBackground(Color.gray);
        tile.setForeground(Color.green);
        textLabel.setText(currentPLayer+" wins!");
    }
    void setTie(JButton tile){
        tile.setBackground(Color.gray);
        tile.setForeground(Color.orange);
        textLabel.setText("It's a tie!");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
