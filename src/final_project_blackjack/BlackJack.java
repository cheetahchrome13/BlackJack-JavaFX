
package final_project_blackjack;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.text.Text;

/*******************************************************************************
 * Project: BlackJack
 * Task: Creates an animated BlackJack game
 * Dev; @author Justin Mangan
 * Date: 10 May 2018
 * 
 *******************************************************************************/
public class BlackJack extends Application {
   
    // Fields
    private final Deck currentDeck = new Deck();
    private final Hand playerHand = new Hand();  
    private final Hand dealerHand = new Hand();  
    
    // Buttons
    private Button hitBtn = new Button();
    private final Button stayBtn = new Button();
    private Button anotherBtn = new Button();
    
    // Panes
    private final BorderPane root = new BorderPane();
    private final GridPane grid = new GridPane();
    private final FlowPane playerCards = new FlowPane(Orientation.HORIZONTAL);
    private final FlowPane dealerCards = new FlowPane(Orientation.HORIZONTAL);
    private final HBox msgBoard = new HBox(20);
    private final HBox btnBoard = new HBox(20);
    
    // Labels
    private Label shuffleMsg = new Label();
    
    //Text
    private Text status = new Text();
    private final Text playerTotal = new Text();
    private final Text dealerTotal = new Text();
    
    // Images
    private final Image table = new Image("images/table.jpg");
    private final ImageView back_1 = new ImageView("images/cards/back.png");
    private final ImageView back = new ImageView("images/cards/back.png");
    
    // Animation 
    private final Timeline blinker = Tricks.createBlinker(back_1);
    private final FadeTransition fadeIn = Tricks.createFadeIn(back_1);
    private final FadeTransition fadeOut = Tricks.createFadeOut(back_1);
    private SequentialTransition shuffleFX = new SequentialTransition(back_1, 
            fadeIn, blinker, fadeOut);
     
    // Flags
    private boolean busted; 
    private boolean playerTurn;
    
    /**
     * Constructor injection gives Tricks class access to node and class instances  
     */
    public BlackJack(){
        // Create instance of Tricks and pass it this instance of BlackJack
        Tricks tricks = new Tricks(BlackJack.this);
    }
   
    /**
     * Initialize all the nodes and start the stage
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        
        // Node settings
        hitBtn.setText("Hit Me");
        hitBtn.setStyle("-fx-font: 22 Algerian; -fx-text-fill: #ffcc00; -fx-base: #009900;");
        hitBtn.setEffect(new SepiaTone(0.7));
        hitBtn.setVisible(false);
        stayBtn.setText("Stay");
        stayBtn.setStyle("-fx-font: 22 Algerian; -fx-text-fill: #ffcc00; -fx-base: #ff0000;");
        stayBtn.setEffect(new SepiaTone(0.7));
        stayBtn.setVisible(false);
        anotherBtn.setText("Start Hand");
        anotherBtn.setStyle("-fx-font: 22 Algerian; -fx-text-fill: #ffcc00; -fx-base: #009900;");
        anotherBtn.setEffect(new SepiaTone(0.7));
        anotherBtn.setVisible(false);
        status.setEffect(new SepiaTone(0.7));
        playerTotal.setEffect(new SepiaTone(0.7));
        dealerTotal.setEffect(new SepiaTone(0.7));
        shuffleMsg.setStyle("-fx-font: 36 Algerian; -fx-text-fill: ivory;");
        shuffleMsg.setEffect(new SepiaTone(0.7));
        BackgroundSize bgSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bgImage = new BackgroundImage(table, BackgroundRepeat.REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
        Background bg = new Background(bgImage);
        
        // Grid pane
//        grid.setGridLinesVisible(true);// Layout tool for development
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5);
        
        // Dealer hand
        grid.add(dealerCards, 2, 0, 6, 1);
        grid.add(dealerTotal, 0, 0, 2, 1); 
        
        // Dialog section
        HBox p = new HBox(); 
        p.setPrefSize(0, 100);
        p.getChildren().add(status);
        p.setAlignment(Pos.CENTER);
        grid.add(p, 6, 2);
        back_1.setEffect(new SepiaTone(0.7));
        back_1.setVisible(true);
        grid.add(back_1, 7, 2, 2, 2);
        back.setEffect(new SepiaTone(0.7));
        back.setVisible(true);
        grid.add(back, 7, 2);
        
        // Player hand
        grid.add(playerCards, 2, 3, 5, 1);
        grid.add(playerTotal, 0, 3, 2, 1);

        // Root pane
        root.setBackground(bg);
        root.setCenter(grid);
        msgBoard.getChildren().add(shuffleMsg);
        msgBoard.setAlignment(Pos.CENTER);
        btnBoard.getChildren().addAll(hitBtn, anotherBtn, stayBtn);
        btnBoard.setAlignment(Pos.CENTER);
        HBox.setMargin(hitBtn, new Insets(11.5, 12.5, 100, 14.5));
        HBox.setMargin(anotherBtn, new Insets(11.5, 12.5, 100, 14.5));
        HBox.setMargin(stayBtn, new Insets(11.5, 12.5, 100, 14.5));
        root.setTop(msgBoard);
        root.setBottom(btnBoard);
        
        // Set scene & stage
        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize game
        currentDeck.newDeck();
        shuffleMsg.setText("Shuffling");
        shuffleFX.play();  
    }
    
    // Getters/Setters
    
    /**
     * @return the currentDeck
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }

    /**
     * @return the playerHand
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * @return the dealerHand
     */
    public Hand getDealerHand() {
        return dealerHand;
    }

    /**
     * @return the anotherBtn
     */
    public Button getAnotherBtn() {
        return anotherBtn;
    }

    /**
     * @param anotherBtn the anotherBtn to set
     */
    public void setAnotherBtn(Button anotherBtn) {
        this.anotherBtn = anotherBtn;
    }

    /**
     * @return the playerCards
     */
    public FlowPane getPlayerCards() {
        return playerCards;
    }

    /**
     * @return the dealerCards
     */
    public FlowPane getDealerCards() {
        return dealerCards;
    }

    /**
     * @return the busted
     */
    public boolean isBusted() {
        return busted;
    }

    /**
     * @return the playerTurn
     */
    public boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * @return the hitBtn
     */
    public Button getHitBtn() {
        return hitBtn;
    }

    /**
     * @param hitBtn the hitBtn to set
     */
    public void setHitBtn(Button hitBtn) {
        this.hitBtn = hitBtn;
    }

    /**
     * @return the stayBtn
     */
    public Button getStayBtn() {
        return stayBtn;
    }

    /**
     * @return the shuffleFX
     */
    public SequentialTransition getShuffleFX() {
        return shuffleFX;
    }

    /**
     * @param shuffleFX the shuffleFX to set
     */
    public void setShuffleFX(SequentialTransition shuffleFX) {
        this.shuffleFX = shuffleFX;
    }
    
    /**
     * @return the shuffleMsg
     */
    public Label getShuffleMsg() {
        return shuffleMsg;
    }

    /**
     * @param shuffleMsg the shuffleMsg to set
     */
    public void setShuffleMsg(Label shuffleMsg) {
        this.shuffleMsg = shuffleMsg;
    }
    
    /**
     * @return the status
     */
    public Text getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Text status) {
        this.status = status;
    }
    
    /**
     * @return the playerTotal
     */
    public Text getPlayerTotal() {
        return playerTotal;
    }

    /**
     * @return the dealerTotal
     */
    public Text getDealerTotal() {
        return dealerTotal;
    }
    
    /**
     * Main class
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }   
}
