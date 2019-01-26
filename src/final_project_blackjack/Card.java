
package final_project_blackjack;

import javafx.scene.image.Image;

/**
 * Card class Defines cards using the Suit and rank classes
 * @author Justin Mangan
 */
public class Card {
   private final Suit suitValue;
   private final Rank rankValue;
   private final Image image;

//   /**
//    * 
//    */
//   public Card(){
//       
//   }
   
   /**
    * Card constructor
    * @param suit
    * @param rank
    * @param cardFace 
    */
   public Card(Suit suit, Rank rank, Image cardFace) {
      image = cardFace;
      suitValue = suit;
      rankValue = rank;
   }

   /**
    * Returns image file for card
    * @param suit
    * @param rank
    * @return 
    */
   public static String getImageFile(Suit suit, Rank rank) {
      return "images/cards/"+ rank.getCode() + suit.getCode() + ".gif";
   }
   
   /**
    * Overloaded method returns image file for dummy card
    * @param suit
    * @param rank
    * @return 
    */
   public static String getImageFile() {
      return "images/cards/back.png";
   }

   /**
    * Returns card suit
    * @return 
    */
   public Suit getSuit() {
      return suitValue;
   }

   /**
    * returns card rank
    * @return 
    */
   public Rank getRank() {
      return rankValue;
   }
   
   /**
    * Returns value of card
    * @return 
    */
   public int getValue() {
       String rank = rankValue.getCode();  
       try{
           // try to turn it into an integer 
           return Integer.parseInt(rank);  
       } catch (Exception ex){
           
           // we failed, so it is a letter
           if(rank.equals("a")){
               // it is an ace
               return 11; 
           } else {
               // it is a face card 
               return 10; 
           }
       }
   }

   /**
    * Returns card image
    * @return 
    */
   public Image getImage() {
      return image;
   }
}

