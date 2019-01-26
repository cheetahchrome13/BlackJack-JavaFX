
package final_project_blackjack;

import java.util.*;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * Deck class acts as the card dealer
 * 
 * @author Justin Mangan
 */
public final class Deck {
   //Fields
   private List deck;
   private int idx = 0;
   private ImageView dummyImg;
   private static boolean dummy = false;

    /**
     * Constructor assures a game with at least one deck gets initialized
     * by calling the one-arg Deck constructor
     */
    public Deck(){
       this(1); 
    }
   
    /**
     * Deck constructor
     * @param decks 
     */
    public Deck(int decks) {
        deck = new ArrayList();
        //index = 0;
        
        try{
            for(int i = 0; i < decks; i++){
                Iterator suitIterator = Suit.VALUES.iterator();
                while (suitIterator.hasNext()) {
                    Suit suit = (Suit) suitIterator.next();
                    Iterator rankIterator = Rank.VALUES.iterator();
                    while (rankIterator.hasNext()) {
                        Rank rank = (Rank) rankIterator.next();
                        Card card = new Card(suit, rank, new Image(Card.getImageFile(suit, rank)));
                        deck.add(card);
                    }
                }
            }
        } 
        catch (Exception ex){
            System.out.println(ex.getMessage()); 
        }
    }
    
    /**
     * Manages dealt cards
     * @param hand
     * @param pane
     * @param lbl
     * @param pos 
     */
    public void drawCard(Hand hand, FlowPane pane, Text txt, int pos){
        try{
            Card card = dealCard(); 
            ImageView img = new ImageView(card.getImage());
            img.setEffect(new SepiaTone(0.7));
            img.setVisible(false);
            pane.getChildren().add(img);
            double cardX = img.getLayoutX();
            double cardY = img.getLayoutY();
            int size = hand.getHandSize();
            
            Tricks.slideIn(img, cardX, cardY, pos, size);

            hand.addCard(card); 
            
            int handTotal = hand.countHand(); 
            
            StringBuilder total = new StringBuilder(); 
            if(hand.getAceLow() > 0){
                total.append(handTotal - 10).append("/");
            }
            
            total.append(handTotal);
            txt.setStyle("-fx-font: 40 Algerian; -fx-fill: #336699; -fx-stroke: ivory; -fx-stroke-width: .5;");
            txt.setText(total.toString());
            
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Overloaded method for dummy card
     * @param hand
     * @param pane
     * @param pos 
     */
    public void drawCard(Hand hand, FlowPane pane, int pos){
        try{
            dummyImg = new ImageView(Card.getImageFile());
            dummyImg.setEffect(new SepiaTone(0.7));
            dummyImg.setVisible(false);
            pane.getChildren().add(dummyImg);
            double cardX = dummyImg.getLayoutX();
            double cardY = dummyImg.getLayoutY();
            int size = hand.getHandSize();
            Tricks.slideIn(dummyImg, cardX, cardY, pos, size);
            dummy = true;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } 
    }
    
    /**
     * Adds a card in place of dummy card
     * @param hand
     * @param pane
     * @param lbl
     * @param pos 
     */
    public void flipCard(Hand hand, FlowPane pane, Text txt, int pos){
        try{
            pane.getChildren().remove(dummyImg);
            Card card = dealCard(); 
            ImageView img = new ImageView(card.getImage());
            img.setEffect(new SepiaTone(0.7));
            img.setVisible(false);
            pane.getChildren().add(img);
            Tricks.showFlippedCard(img);
            hand.addCard(card);
            int handTotal = hand.countHand(); 
            StringBuilder total = new StringBuilder();
            
            if(hand.getAceLow() > 0){
                total.append(handTotal - 10).append("/");
            }
            total.append(handTotal);
            txt.setStyle("-fx-font: 40 Algerian; -fx-fill: #336699; -fx-stroke: ivory; -fx-stroke-width: .5;");
            txt.setText(total.toString());   
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } 
    }
    
    /**
     * Resets/shuffles Deck
     */
    public void newDeck(){
        resetIdx(); 
        shuffle();
    }

    /**
     * Returns deck size
     * @return 
     */
    public int getSize() {
       return deck.size();
    }

    /**
     * counts cards dealt
     * @return 
     */
    public int getCardCount() {
       return deck.size() - idx;
    }
   
    /**
     * Deals cards
     * @return 
     */
    public Card dealCard() {
        if (idx >= deck.size()){
           return null;
        }   
        else{
           return (Card) deck.get(idx++);
        }   
    }

    /**
     * Shuffles deck
     */
    public void shuffle() {
       Collections.shuffle(deck);
    }
   
    /**
     * Resets index
     */
    public void resetIdx() {
       idx = 0;
    }   

    /**
     * @return the dummyImg
     */
    public ImageView getDummyImg() {
        return dummyImg;
    }

    /**
     * @return the dummy
     */
    public static boolean isDummy() {
        return dummy;
    }

    /**
     * @param dummy the dummy to set
     */
    public static void setDummy(boolean dummy) {
        Deck.dummy = dummy;
    }
}
