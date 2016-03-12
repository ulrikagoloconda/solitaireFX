package Model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by Rickard on 2016-01-28.
 */
public class GameModel {
    PlayingField playingField;

    public GameModel() {
        playingField = new PlayingField();
    }


    public void openClosedCards() {
        for (UnsortedPile e : UnsortedPile.values()) {
            ArrayList<Card> tempList = playingField.getUnsortedPileEnumMap().get(e);
            if (tempList.size() != 0) {
                if (!tempList.get(tempList.size() - 1).isFaceUp()) {
                    tempList.get(tempList.size() - 1).turUp();
                }
            }
        }
    }
    public PlayingField getPlayingField(){
        return playingField;
    }
}
