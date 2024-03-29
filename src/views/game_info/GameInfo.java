package views.game_info;

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

import game.GameData;
import game.RoundData;


/**
 * Controller for GameInfo. Shows a breakdown of each game.
 * @author szhu842
 */
public class GameInfo implements Initializable{

    @FXML
    Text scoreText, titleText;
    @FXML
    TableView<RoundData> tableView;
    private GameData gameData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Sets the gameData of the game that is to be displayed.
     * @param gameData
     */
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
        titleText.setText("Breakdown of Game on " + gameData.getTime());
        scoreText.setText("Score: " + this.gameData.getScore());

        //Create table columns
        TableColumn<RoundData, Integer> roundNumberColumn = new TableColumn<>("Round Number");
        roundNumberColumn.setMaxWidth(100);
        roundNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roundNumber"));

        TableColumn<RoundData, Integer> correctColumn = new TableColumn<>("Correct");
        correctColumn.setMaxWidth(100);
        correctColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));

        TableColumn<RoundData, String> userAnswerColumn = new TableColumn<>("You Answered");
        userAnswerColumn.setMaxWidth(100);
        userAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));

        TableColumn<RoundData, String> actualAnswerColumn = new TableColumn<>("Actual Answer ");
        actualAnswerColumn.setMaxWidth(100);
        actualAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        tableView.setItems(gameRounds());
        tableView.getColumns().addAll(roundNumberColumn, correctColumn, userAnswerColumn, actualAnswerColumn);

        // Sort by roundNumber
        tableView.getSortOrder().add(roundNumberColumn);
    }

    /**
     * Returns an ObservableList for the tableView.
     * @return
     */
    private ObservableList<RoundData> gameRounds() {
        return FXCollections.observableArrayList(gameData.rounds());
    }
}
