
package final_project_blackjack;

import java.util.*;

/**
 * Hand class counts hands
 * @author Justin Mangan
 */
public class Hand {
   private int total; 
   private int aceLow; 
   private final List hand = new ArrayList(); 

    /**
     * Adds cards to hands
     * @param card 
     */
    public void addCard( Card card ) {
        total += card.getValue(); 
        if(card.getRank() == Rank.ACE){
          aceLow += 1;  
        }
        if(aceLow > 0){
            if(total > 21){
               total -= 10; 
               aceLow -= 1; 
            }
        }
        hand.add(card);
    }

    /**
     * Returns card
     * @param index
     * @return 
     */
    public Card getCard( int index ) {
      return (Card) hand.get( index );
    }
    
    /**
     * Clears hands
     */
    public void clearTable() {
        hand.clear();
        total = 0; 
        aceLow = 0; 
   }

    /**
     * Returns hand size
     * @return 
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * returns index of card
     * @param card
     * @return 
     */
    public int findCard(Card card) {
        return hand.indexOf(card);
    }

    /**
     * Returns ace low 
     * @return 
     */
    public int getAceLow(){
        return aceLow; 
    }
   
    /**
     * Returns hand total
     * @return 
     */
    public int countHand(){
        return total; 
    }     
}