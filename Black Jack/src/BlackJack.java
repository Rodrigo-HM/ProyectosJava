import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;





public class BlackJack {
    private class Card{
        String value;
        String type;

        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        public String toString(){
            return value +"-"+ type;
        }
        public int getValue(){
            if("AJQK".contains(value)){  //A-J-Q-K
                if(value=="A"){
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value);  //2-10
        }

        public boolean isAce(){
            return value=="A";
        }

        public String getImagePatch(){              //asi busca el nombre de la imagen 
            return "./cards/" + toString() + ".png";  
        }
    }

    ArrayList<Card> deck;
    Random random=new Random();

    //dealer
    Card hiddeCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //window
    int boardWidth= 600;
    int boardHeight= boardWidth;

    //fihcas
    int ficha5 = 5;
    int fucha10 = 10;
    int ficha25 = 25;
    int ficha50 = 50;
    int apuesta;
    int dineroTotal = 500;

    int cardWith=110;   //ratio 1/1.4
    int cardHeight=154;

    JFrame frame=new JFrame("Black Jack");
    JPanel gamePanel= new JPanel()  {                //para dibujar las cartas se sobre escribe la funcion paintComponent
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try{
                //dibujar hiddenCard
                Image hiddenCardImg =new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if(!stayButton.isEnabled()){
                    hiddenCardImg= new ImageIcon(getClass().getResource(hiddeCard.getImagePatch())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWith, cardHeight, null);

                //dibujar dealer hand
                for (int i = 0; i < dealerHand.size(); i++) {
                    Card card =dealerHand.get(i);
                    Image cardImg=new ImageIcon(getClass().getResource(card.getImagePatch())).getImage();  //llama al metodo que ve la carta para ponerle nombre
                    g.drawImage(cardImg, cardWith + 25 + (cardWith+5)*i, 20, cardWith,cardHeight, null); //asi deja espacio de las cartas que haya
                }

                //dibujar player hand
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card= playerHand.get(i);
                    Image cardImg=new ImageIcon(getClass().getResource(card.getImagePatch())).getImage();  //llama al metodo que ve la carta para ponerle nombre
                    g.drawImage(cardImg, 20 + (cardWith+5)*i, 320, cardWith,cardHeight,null);
                }

                if (!stayButton.isEnabled()) {
                    dealerSum = recudeDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if (playerSum > 21) {
                        message = "Pierdes!";
                    }
                    else if (dealerSum > 21) {
                        message = "Ganas!";
                    }
                    //both you and dealer <= 21
                    else if (playerSum == dealerSum) {
                        message = "Empate!";
                    }
                    else if (playerSum > dealerSum) {
                        message = "Ganas!";
                    }
                    else if (playerSum < dealerSum) {
                        message = "Pierdes!";
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString(message, 220, 250);
                }


            } catch (Exception e){ // Imprime el seguimiento de la pila de la excepción
                e.printStackTrace();
            }
        }
        
    }; 
    JPanel buttonPanel= new JPanel();
    JButton hitButton= new JButton("Hit");
    JButton stayButton= new JButton("Stay");
    JButton resetButton = new JButton("Reset");
    


    BlackJack(){
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout()); //divide el contenedor en 5 areas 
        gamePanel.setBackground(new Color(53,101,77));
        frame.add(gamePanel); // lo añade al frame

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        resetButton.setFocusable(false);
        buttonPanel.add(resetButton);
        buttonPanel.setBackground(new Color(53,53,53));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar ActionListener al botón de reinicio
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        //para que el boton hit funcione

        hitButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e){
                Card card= deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce()? 1:0;
                playerHand.add(card);
                if(reducePlayerAce() >21){  //para que cuando se pase no pueda sacar mas cartas
                    hitButton.setEnabled(false);
                }


                gamePanel.repaint();  //llama a paintComponent
            }
        });

        //para que el boton stay funcione
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while(dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount +=card.isAce()? 1 : 0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }
    
    public void startGame(){
        //Baraja
        buildDeck();
        shuffleDeck();

        //dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddeCard = deck.remove(deck.size()-1);  //borra la ultima carta de la baraja y la guarda en hiddeCard
        dealerSum += hiddeCard.getValue();
        dealerAceCount += hiddeCard.isAce()? 1:0;

        Card card= deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce()?1:0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddeCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        //PLayer
        playerHand= new ArrayList<Card>();
        playerSum=0;
        playerAceCount=0;

    
        for (int i = 0; i < 2; i++) {
            card= deck.remove(deck.size()-1);
            playerSum +=card.getValue();
            playerAceCount += card.isAce()? 1:0;
            playerHand.add(card);
        
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);



    }
    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck(){
        for (int i = 0; i < deck.size(); i++) {
            int j=random.nextInt(deck.size());
            Card currCard=deck.get(i);
            Card randomCard=deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
            
        }
        System.out.println("AFTER SHUFFLE:");
        System.out.println(deck);
    }

    public int reducePlayerAce(){
        while(playerSum > 21 && playerAceCount > 0){
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int recudeDealerAce(){
        while(dealerSum >21 && dealerAceCount > 0){
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
     // Método para reiniciar el juego
    private void resetGame() {
        // Reiniciar las manos y los puntajes
        dealerHand.clear();
        playerHand.clear();
        dealerSum = 0;
        playerSum = 0;
        dealerAceCount = 0;
        playerAceCount = 0;

        // Repartir nuevas cartas
        startGame();

        // Habilitar los botones de hit y stay
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);

        // Repintar el panel de juego
        gamePanel.repaint();
    }


}
