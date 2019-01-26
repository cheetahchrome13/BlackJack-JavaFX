
package final_project_blackjack;

import java.util.*;

/**
 * Suit class establishes names and symbols for suits
 * @author Justin Mangan
 */
public final class Suit {
   private final String name;
   private final String code; 
   
   public final static Suit CLUBS = new Suit( "Clubs", "c" );
   public final static Suit DIAMONDS = new Suit( "Diamonds", "d" );
   public final static Suit HEARTS = new Suit( "Hearts", "h" );
   public final static Suit SPADES = new Suit( "Spades", "s" );
   
   // Create immutable fixed-size List using Arrays.asList() method
   public final static List VALUES = 
      Collections.unmodifiableList(Arrays.asList(new Suit[] { 
             CLUBS, 
             DIAMONDS, 
             HEARTS, 
             SPADES 
         }));

   /**
    * Suit constructor
    * @param nameValue
    * @param codeValue 
    */
   private Suit(String nameValue, String codeValue) {
      name = nameValue;
      code = codeValue;
   }
      
   /**
    * Returns suit name
    * @return 
    */
   public String getName() {
       return name;
   }
 
   /**
    * Returns suit code
    * @return 
    */
   public String getCode() {
      return code;
   }
}