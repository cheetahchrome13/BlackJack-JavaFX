
package final_project_blackjack;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Tricks class manages animations and Event handlers
 * @author Justin Mangan
 */
public class Tricks {
   // BlackJack node instances referenced via constructor injection
   private final BlackJack blackJack;
   private final Hand playerHand;  
   private final Hand dealerHand;
   private Deck currentDeck;
   private Button anotherBtn;
   private FlowPane playerCards;
   private FlowPane dealerCards;
   private boolean busted; 
   private boolean playerTurn;
   private Button hitBtn;
   private Button stayBtn;
   private Text status;
   private Text dealerTotal;
   private Text playerTotal;
   private Label shuffleMsg;
   private SequentialTransition shuffleFX;
    
    /**
     * Constructor injection gives access to instances of BlackJack, Deck,
     * Hand classes and nodes in BlackJack class
     * Constructor contains all event handlers for nodes 
     * @param blackJack 
     */
    public Tricks(BlackJack blackJack){
        this.blackJack = blackJack;
        this.playerHand = blackJack.getPlayerHand();
        this.dealerHand = blackJack.getDealerHand();
        this.currentDeck = blackJack.getCurrentDeck();
        this.anotherBtn = blackJack.getAnotherBtn();
        this.playerCards = blackJack.getPlayerCards();
        this.dealerCards = blackJack.getDealerCards();
        this.playerTotal = blackJack.getPlayerTotal();
        this.dealerTotal = blackJack.getDealerTotal();
        this.busted = blackJack.isBusted();
        this.playerTurn = blackJack.isPlayerTurn();
        this.hitBtn = blackJack.getHitBtn();
        this.stayBtn = blackJack.getStayBtn();
        this.status = blackJack.getStatus();
        this.shuffleFX = blackJack.getShuffleFX();
        this.shuffleMsg = blackJack.getShuffleMsg();

        /**
         * Hit button Event handler
         */
        hitBtn.setOnAction((e) -> {
            if(playerTurn == true && busted != true){
                currentDeck.drawCard(playerHand, playerCards, playerTotal, 1);

                if(playerHand.countHand() > 21){
                    busted = true; 
                    playerTurn = false;
                    status.setText("Busted Hand");
                    status.setStyle("-fx-font: 36 Algerian; -fx-fill: #cc0000; -fx-stroke: ivory; -fx-stroke-width: .5;");
                    anotherBtn.setText("Another Hand?");
                    anotherBtn.setVisible(true);
                    hitBtn.setVisible(false);
                    stayBtn.setVisible(false);
                }
            }
        });
        
        /**
         * Another button Event handler
         */
        anotherBtn.setOnAction((e) -> {
                anotherHand();            
        });
        
        /**
         * Stay button Event handler
         */
        stayBtn.setOnAction((ActionEvent e) -> {
            hitBtn.setVisible(false);
            stayBtn.setVisible(false);
            anotherBtn.setText("Another Hand?");
            if(playerTurn == true && busted != true){
                playerTurn = false;
                while(dealerHand.countHand() < 17){
                    if(Deck.isDummy() == true){
                       currentDeck.flipCard(dealerHand, dealerCards, dealerTotal, 0);
                       Deck.setDummy(false);
                    }
                    else{
                       currentDeck.drawCard(dealerHand, dealerCards, dealerTotal, 0); 
                    }
                }
                
                int plrTotal = playerHand.countHand();
                int dlrTotal = dealerHand.countHand();
                
                if(dlrTotal <= 21 && plrTotal == dlrTotal){
                    status.setText("Stand-off");
                    status.setStyle("-fx-font: 36 Algerian; -fx-fill: ivory; -fx-stroke: black; -fx-stroke-width: .5;");
                }
                else if(dlrTotal <= 21 && plrTotal < dlrTotal){
                    status.setText("House Wins");
                    status.setStyle("-fx-font: 36 Algerian; -fx-fill: #cc0000; -fx-stroke: ivory; -fx-stroke-width: .5;");
                }
                else {
                    status.setText("Player Wins");
                    status.setStyle("-fx-font: 36 Algerian; -fx-fill: #009900; -fx-stroke: #ffcc00; -fx-stroke-width: .5;");
                }
               
                // Shuffle deck if warranted
                if(currentDeck.getCardCount() <= currentDeck.getSize() * 0.5){
                    playerHand.clearTable();
                    dealerHand.clearTable();
                    playerCards.getChildren().removeAll(playerCards.getChildren());
                    dealerCards.getChildren().removeAll(dealerCards.getChildren());
                    shuffleMsg.setVisible(true);
                    shuffleFX.play();
                }
                else{
                    anotherBtn.setVisible(true);
                }
            }
        });
        
        /**
         * Event handler switches node visibilities
         */
        shuffleFX.setOnFinished((ActionEvent event) -> {
            anotherBtn.setVisible(true);
            shuffleMsg.setVisible(false);
        });
    }
    
    /**
     * Dealing effects for new hand animation
     */
    public void anotherHand(){
        // Shuffle if warranted
        if(currentDeck.getCardCount() <= currentDeck.getSize() * 0.5){
            currentDeck.newDeck();  
        }
        
        // Clear the table 
        anotherBtn.setVisible(false);
        playerHand.clearTable(); 
        dealerHand.clearTable(); 
        playerCards.getChildren().removeAll(playerCards.getChildren());  
        dealerCards.getChildren().removeAll(dealerCards.getChildren()); 
        playerTotal.setText(""); 
        dealerTotal.setText("");
        busted = false; 
        playerTurn = true;
        
        //Deal initial hands
        slideInFirstDeal();   
        hitBtn.setVisible(true);
        stayBtn.setVisible(true);
        status.setText("");
    }
    
    /**
     * Effects for shuffle animation
     * @param node
     * @return 
     */
    public static Timeline createBlinker(Node node) {
        Timeline blink = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0.025),
                        new KeyValue(
                                node.opacityProperty(), 
                                0, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0.05),
                        new KeyValue(
                                node.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                )
        );
        blink.setCycleCount(30);

        return blink;
    }
    
    /**
     * Fade-in effects for shuffle 
     * @param node
     * @return 
     */
    public static FadeTransition createFadeIn(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(0);
        fade.setToValue(0.5);

        return fade;
    }
    
    /**
     * Fade-out effects for shuffle
     * @param node
     * @return 
     */
    public static FadeTransition createFadeOut(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }
    
    /**
     * Chained Timelines for first hands dealt animation
     */
    public void slideInFirstDeal(){
        Timeline action1 = new Timeline(new KeyFrame(Duration.millis(400), e -> currentDeck.drawCard(playerHand, playerCards, playerTotal, 1)));
        action1.setCycleCount(1);
        action1.play();
        action1.setOnFinished((ActionEvent event) -> {
           Timeline action2 = new Timeline(new KeyFrame(Duration.millis(400), e -> currentDeck.drawCard(dealerHand, dealerCards, dealerTotal, 0)));
            action2.setCycleCount(1);
            action2.play();
            action2.setOnFinished((ActionEvent event1) -> {
                Timeline action3 = new Timeline(new KeyFrame(Duration.millis(400), e -> currentDeck.drawCard(playerHand, playerCards, playerTotal, 1)));
                action3.setCycleCount(1);
                action3.play();
                action3.setOnFinished((ActionEvent event2) -> {
                    Timeline action4 = new Timeline(new KeyFrame(Duration.millis(400), e -> currentDeck.drawCard(dealerHand, dealerCards, 0)));
                    action4.play();
                });
            });
        });
    }
    
    /**
     * 
     */
    public static void showFlippedCard(Node node){
        node.setVisible(true);
    }
    
    /**
     * Slide-in effects for card dealing animation
     * @param node
     * @param x 
     */
    public static void slideIn(Node node, double cardX, double cardY, int pos, int size){
        TranslateTransition transition = new TranslateTransition();
        TranslateTransition transition2 = new TranslateTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(node);
        transition.setToX(+400 - (size * 73));
        transition.setToY(pos == 0 ? +100 : -100);
        transition.setAutoReverse(true);
        transition.setCycleCount(1);

        transition.setOnFinished((ActionEvent event) -> {
            node.setVisible(true);
            transition2.setDuration(Duration.millis(300));
            transition2.setNode(node);
            transition2.setToX(cardX);
            transition2.setToY(cardY);
            transition2.setAutoReverse(true);
            transition.setCycleCount(2);

            transition2.play();
        });
                  
    transition.play();
    }
}
