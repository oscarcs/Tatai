package views.game_menu;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import game.Game;
import game.GameData;
import game.User;
import numbers.QuestionFactory;
import numbers.SimpleQuestionFactory;
import numbers.SingleNumberQuestionFactory;
import views.level.Level;
import views.level.LevelView;
import views.main_container.MainContainer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for the GameMenuView, it is called when the user clicks on Play in the main menu. It handles creation of the Game model.
 */
public class GameMenu {

    private int maxNumber = 9;
    private int minNumber = 1;
    private Game game;
    private HashMap<String, QuestionFactory> questionFactoryMap;

    @FXML
    RadioButton tenButton, hundredButton;

    @FXML
    ComboBox<String> gameModeBox;

    /**
     * Method that is called when the playButton is pressed.
     */
    @FXML
    public void playHit() {
        QuestionFactory questionFactory = questionFactoryMap.get(gameModeBox.getSelectionModel().getSelectedItem());
        questionFactory.setMax(maxNumber);
        questionFactory.setMin(minNumber);
        game = new Game(questionFactory, 10);

        // Create a new level and set it as the view.
        LevelView levelView = new LevelView();
        MainContainer.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        game.setLevel(level);
        level.setGame(game);
    }

    /**
     * Method called when the controller is created (after all the JavaFX elements load).
     */
    @FXML
    public void initialize() {
        // Game Types
        questionFactoryMap = new HashMap<>();
        questionFactoryMap.put("Simple Question", new SimpleQuestionFactory());
        questionFactoryMap.put("Single Number", new SingleNumberQuestionFactory());

        gameModeBox.setItems(FXCollections.observableArrayList(questionFactoryMap.keySet()));
        gameModeBox.getSelectionModel().select(0);

        // Radio Buttons
        ToggleGroup group = new ToggleGroup();
        tenButton.setSelected(true);
        tenButton.setToggleGroup(group);
        hundredButton.setToggleGroup(group);
        updateSecondLevelStatus();

    }

    /**
     * Checks whether the user is allowed to play the second level and will disable or enable the selection accordingly
     */
    private void updateSecondLevelStatus() {
        tenButton.setSelected(true);
        QuestionFactory questionFactory = questionFactoryMap.get(gameModeBox.getSelectionModel().getSelectedItem());
        if (MainContainer.instance().getUser().canPlayLevelTwo(questionFactory.asString())) {
            hundredButton.setDisable(false);
        } else {
            hundredButton.setDisable(true);
        }
    }

    /**
     * Method when the max number: ten radio button is pressed.
     */
    @FXML
    public void tenHit() {
        maxNumber = 9;
    }

    /**
     * Method called when the user changes the selected game mode.
     */
    @FXML
    public void gameModeChange() {
        updateSecondLevelStatus();
    }

    /**
     * Method called when the user selects the max number: one hundred radio buttom.
     */
    @FXML
    public void hundredHit() {
        maxNumber = 99;
    }


}
