package Model;

import Control.Control;
import GameView.GamFxApp;

/**
 * Created by Rickard on 2016-01-28.
 */
public class GameMain {
    public static GameModel gameModel;
    static {

    }
    public static void main(String[] args) {
        GamFxApp m = new GamFxApp();
        m.enterGame();
    }
}