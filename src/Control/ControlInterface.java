package Control;

import Model.Card;
import Model.UnsortedPile;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by Rickard on 2016-01-30.
 */
public interface ControlInterface {
    String getMovableCard(ObservableList<Node> nodeList);

    boolean isMixedListOk();

    void updateUnsortedPiles(EnumMap<UnsortedPile, ArrayList<Card>> unsortedPiles);

    EnumMap<UnsortedPile, ArrayList<Card>> getUnsortedPiles();
}
