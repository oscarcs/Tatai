package views.game_end;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import game.Game;
import game.RoundData;
import views.main_container.MainContainer;

/**
 * Controller used at the end of a game to show the user how they did.
 */
public class GameEnd implements Initializable {

    @FXML
    Text scoreText, infoText, titleText;

    @FXML
    TableView<RoundData> tableView;

    private Game game;

    /**
     * Initialize.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ...
    }

    /**
     * Method that is called when the game has been set.
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
        titleText.setText("Well done, " + MainContainer.instance().getUser().getUsername());
        scoreText.setText("You got: " + game.getScoreAsString());

        //Create Columns for the Table
        TableColumn<RoundData, Integer> roundNumberColumn = new TableColumn<>("Round Number");
        roundNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roundNumber"));

        TableColumn<RoundData, Integer> correctColumn = new TableColumn<>("Correct");
        correctColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));

        TableColumn<RoundData, String> userAnswerColumn = new TableColumn<>("You Answered");
        userAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));

        TableColumn<RoundData, String> actualAnswerColumn = new TableColumn<>("Actual Answer ");
        actualAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        TableColumn<RoundData, String> questionColumn = new TableColumn<>("Question: ");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        TableColumn<RoundData, Integer> attemptsColumn = new TableColumn<>("Attempts");
        attemptsColumn.setCellValueFactory(new PropertyValueFactory<>("attempts"));

        roundNumberColumn.setMaxWidth(100);
        correctColumn.setMaxWidth(100);
        userAnswerColumn.setMaxWidth(100);
        actualAnswerColumn.setMaxWidth(100);
        questionColumn.setMaxWidth(100);
        attemptsColumn.setMaxWidth(100);

        tableView.setItems(gameRounds());
        tableView.getColumns().addAll(
            roundNumberColumn, 
            correctColumn, 
            userAnswerColumn, 
            actualAnswerColumn, 
            questionColumn, 
            attemptsColumn
        );

        // If the user is now legible for level two tell them.
        if (!MainContainer.instance().getUser().canPlayLevelTwo(game.gameTypeName()) &&
            game.getScore() >= 8
        ) {
            infoText.setText(
                "You've gotten above 80% for the first time, " + 
                "you can now play with numbers up to 100!"
            );
        }

        // Sort by round number.
        tableView.getSortOrder().add(roundNumberColumn);

        // Save the game to the user profile.
        MainContainer.instance().addGame(game.getGameData());
    }

    /**
     * Helper function that is used to create an ObservableList for the TableView.
     * @return
     */
    private ObservableList<RoundData> gameRounds() {
        return FXCollections.observableArrayList(game.getGameData().rounds());
    }
}
