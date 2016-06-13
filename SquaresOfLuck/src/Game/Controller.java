package Game;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Controller {

    private static LinkedList<String> freeRectanglesColl = new LinkedList<>();
    LinkedHashSet<String> playersRectangles = new LinkedHashSet<>();
    LinkedHashSet<String> computersRectangles = new LinkedHashSet<>();
    Integer playerScore = 0;
    Integer computerScore = 0;
    public static boolean easyDifficulty;

    //sounds
    final String smallTaikoPath = "src\\Game\\sounds\\taiko.mp3";
    final String bigTaikoPath = "src\\Game\\sounds\\bigtaiko.mp3";
    final AudioClip smallTaikoSound = new AudioClip(new File(smallTaikoPath).toURI().toString());
    final AudioClip bigTaikoSound = new AudioClip(new File(bigTaikoPath).toURI().toString());


    @FXML
    private static Scene gameScene;
    @FXML
    private static Scene endGameScene;
    @FXML
    private static Label itemsRemainingLabel;
    @FXML
    private static Label rectanglesLeftLabel;


    public void clickStartGameButton(ActionEvent startGame) throws IOException {
        Parent goToGameScene = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Scene gameScene = new Scene(goToGameScene);
        Stage app_stage = (Stage) ((Node) startGame.getSource()).getScene().getWindow();
        app_stage.setScene(gameScene);
        Controller.gameScene = gameScene;
        itemsRemainingLabel = (Label) gameScene.lookup("#pl1_itemsRemLabel");
        rectanglesLeftLabel = (Label) Controller.gameScene.lookup("#rectanglesLeftLabel");
        Parent goToEndGame = FXMLLoader.load(getClass().getResource("endGameScene.fxml"));
        endGameScene = new Scene(goToEndGame);
        //checking difficulty
        RadioButton easyDifficultyButton = (RadioButton)((Node) startGame.getSource()).getScene().lookup("#easyDiff");
        if (easyDifficultyButton.isSelected()) {
            easyDifficulty = true;
        } else {
            easyDifficulty = false;
        }
        compsItems = setComputerItems();
        app_stage.show();
        loadFreeRectangles();
    }

    public void clickQuitButton(ActionEvent quit) {
        System.exit(0);
    }

    public void goToMainMenuScene(ActionEvent goToMenu) throws IOException {
        Parent goToMainMenuScene = FXMLLoader.load(getClass().getResource("mainMenuScene.fxml"));
        Scene mainMenuScene = new Scene(goToMainMenuScene);
        Stage app_stage = (Stage) ((Node) goToMenu.getSource()).getScene().getWindow();
        app_stage.setScene(mainMenuScene);
        app_stage.show();
    }

    public void goToRulesScene(ActionEvent goToHowToPlayScene) throws IOException {
        Parent goToRulesScene = FXMLLoader.load(getClass().getResource("rulesScene.fxml"));
        Scene rulesScene = new Scene(goToRulesScene);
        Stage app_stage = (Stage) ((Node) goToHowToPlayScene.getSource()).getScene().getWindow();
        app_stage.setScene(rulesScene);
        app_stage.show();
    }

    public void goToEndGame() throws IOException {
        Stage app_stage = (Stage) gameScene.getWindow();
        app_stage.setScene(endGameScene);
        Label resultLabel = (Label) endGameScene.lookup("#endGame_resultLabel");
        if (playerScore > computerScore) {
            resultLabel.setText("Player wins with a score of " + playerScore + ". Computer scored " + computerScore);
        } else if (computerScore > playerScore) {
            resultLabel.setText("Computer wins with a score of " + computerScore + ". Player scored " + playerScore);
        } else {
            resultLabel.setText("It's a tie with a score of " + playerScore);
        }
        app_stage.show();
        return;
    }

    private void loadFreeRectangles() {
        freeRectanglesColl.add("00");
        freeRectanglesColl.add("01");
        freeRectanglesColl.add("02");
        freeRectanglesColl.add("03");
        freeRectanglesColl.add("04");
        freeRectanglesColl.add("05");
        freeRectanglesColl.add("10");
        freeRectanglesColl.add("11");
        freeRectanglesColl.add("12");
        freeRectanglesColl.add("13");
        freeRectanglesColl.add("14");
        freeRectanglesColl.add("15");
        freeRectanglesColl.add("20");
        freeRectanglesColl.add("21");
        freeRectanglesColl.add("22");
        freeRectanglesColl.add("23");
        freeRectanglesColl.add("24");
        freeRectanglesColl.add("25");
        freeRectanglesColl.add("30");
        freeRectanglesColl.add("31");
        freeRectanglesColl.add("32");
        freeRectanglesColl.add("33");
        freeRectanglesColl.add("34");
        freeRectanglesColl.add("35");
        freeRectanglesColl.add("40");
        freeRectanglesColl.add("41");
        freeRectanglesColl.add("42");
        freeRectanglesColl.add("43");
        freeRectanglesColl.add("44");
        freeRectanglesColl.add("45");
        freeRectanglesColl.add("50");
        freeRectanglesColl.add("51");
        freeRectanglesColl.add("52");
        freeRectanglesColl.add("53");
        freeRectanglesColl.add("54");
        freeRectanglesColl.add("55");
    }

    Byte playersItems = 3;

    public void rectangleClicked(MouseEvent rectClicked) throws IOException {
        Rectangle clickedRectangle = (Rectangle) rectClicked.getSource();
        String rectNumber = clickedRectangle.getId().substring(4, 6);
        if (computersRectangles.contains(rectNumber)) {
            clickedRectangle.setFill(Color.RED);
            smallTaikoSound.play();
            playersItems = 3;
            itemsRemainingLabel.setText(playersItems.toString());
            compsItems = setComputerItems();
            computerTurn();
            return;
        } else {
            if (playersRectangles.contains(rectNumber)) {
                return;
            } else {
                playersRectangles.add(rectNumber);
                clickedRectangle.setFill(Color.BLUE);
                bigTaikoSound.play();
                String idOfRectOnEndGameToFill = "#end_" + rectNumber;
                Rectangle endGameRect = (Rectangle) endGameScene.lookup(idOfRectOnEndGameToFill);
                endGameRect.setFill(Color.BLUE);
                freeRectanglesColl.remove(rectNumber);
                rectanglesLeftLabel.setText(freeRectanglesColl.size() + "");
                playerScore++;
                playersItems--;
                itemsRemainingLabel.setText(playersItems.toString());
                if (!anyFreeRectangles()) {
                    playersItems = 3;
                    goToEndGame();
                    return;
                }
                if (playersItems == 0) {
                    compsItems = setComputerItems();
                    computerTurn();
                    playersItems = 3;
                    itemsRemainingLabel.setText(playersItems.toString());
                }
            }
        }

    }

    private boolean anyFreeRectangles() {
        if (freeRectanglesColl.size() == 0) {
            return false;
        }
        return true;
    }

    static Byte compsItems;
    private void computerTurn() throws IOException {
        if (compsItems == 0) {
            compsItems = setComputerItems();
            return;
        }

        Random indexMaker = new Random();
        //computer takes a random rect from the free ones
        Integer freeRectCollIndex;
        Integer bound = freeRectanglesColl.size() - 1;
        if (bound > 0) {
            freeRectCollIndex = indexMaker.nextInt(bound);
        } else {
            freeRectCollIndex = 0;
        }

        String indexes = freeRectanglesColl.get(freeRectCollIndex);
        computersRectangles.add(indexes);
        String idOfRectOnEndGameToFill = "#end_" + indexes;
        Rectangle endGameRect = (Rectangle) endGameScene.lookup(idOfRectOnEndGameToFill);
        endGameRect.setFill(Color.RED);
        computerScore++;
        freeRectanglesColl.remove(indexes);
        rectanglesLeftLabel.setText(freeRectanglesColl.size() + "");
        if (freeRectanglesColl.isEmpty()) {
            compsItems = setComputerItems();
            goToEndGame();
            return;
        }
        compsItems--;
        computerTurn();
    }

    private static Byte setComputerItems() {
        if (easyDifficulty) {
            return 1;
        } else {
            return 2;
        }
    }
}
