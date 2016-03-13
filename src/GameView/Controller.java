package GameView;

import Control.Control;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.*;
import javafx.scene.*;
import javafx.scene.text.Text;


public class Controller implements Initializable {

    @FXML
    private GridPane pile1, pile2, pile3, pile4, pile5, pile6, pile7, bigPile, smallPile1, smallPile2, smallPile3, smallPile4;

    @FXML
    private ImageView targetKing1, targetKing2, targetKing3, targetKing4;


    private ObservableList<ImageView> observableList;
    private EnumMap<UnsortedPile, ObservableList<Node>> observableUnsortedMap;
    private EnumMap<SuitPile, ObservableList<Node>> observableSuitMap;
    private EnumMap<UnsortedPile, GridPane> unsortedPileGridPaneEnumMap;
    private EnumMap<SuitPile, GridPane> suitPileGridPaneEnumMap;
    private int k = 1;
    private Map<ImageView, String> nodeStringMap;
    private ImageView sorceView;
    private GridPane[] unsortedGridPanes;
    private GridPane[] suitGridPanes;
    private Control c;
    private GameModel gameModel;
    private PlayingField playingField;
    private EnumMap<UnsortedPile, ArrayList<Card>> unsortedPileCardEnumMap;
    private EnumMap<SuitPile, ArrayList<Card>> suitPileCardEnumMap;
    private ArrayList<Card> frechBigPileCardList;
    private ArrayList<Card> dissBigPileList;
    private ObservableList<Node> frechBigPileObservable;
    private ArrayList<ImageView> kingInUse;
    private ArrayList<ImageView> kings;

    public void handleOnDragDetected(MouseEvent event) {

        Node n = (Node) event.getSource();
        try {
            Dragboard dragboard = n.startDragAndDrop(TransferMode.ANY);
            ClipboardContent landing = new ClipboardContent();
            Image i = ((ImageView) event.getSource()).getImage();
            landing.putImage(i);
            dragboard.setContent(landing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleOnDragOver(DragEvent event) {
        try {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleOnClicked(MouseEvent event) {
        try {
            Node ivTemp = frechBigPileObservable.remove(frechBigPileObservable.size() - 1);
            frechBigPileObservable.add(0, ivTemp);
            Card c = frechBigPileCardList.remove(frechBigPileCardList.size() - 1);
            frechBigPileCardList.add(0, c);
            showFaceUpCards();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleOnDragDropped(DragEvent event) {
        try {
            boolean isok = false;
            Dragboard dragboard = event.getDragboard();
            ImageView n = (ImageView) event.getSource();
            ImageView m = (ImageView) event.getGestureSource();
            if (c.isGodMach(nodeStringMap.get(n), nodeStringMap.get(m))) {
                isok = true;
                event.setDropCompleted(isok);
                event.consume();

                updateLokalField();
                showFaceUpCards();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkWin(){
       int i = 0;
        for(UnsortedPile e : UnsortedPile.values()){
           if(unsortedPileCardEnumMap.get(e).isEmpty()){
              i++;
           }
        }
        if(i == 7){
            System.out.println("Grattis, du har vunnit!");
        }

    }
    public void updateLokalField() {
        unsortedPileCardEnumMap = playingField.getUnsortedPileEnumMap();

        suitPileCardEnumMap = playingField.getSuitPileArrayListEnumMap();
        frechBigPileCardList = playingField.getBigPile();
        updateUnsortedPiles();
        upDateSuitPiles();
        upDateBigPile();
        checkWin();
    }

    public boolean addEmptyTarget(UnsortedPile e) {
        boolean returnBol = false;
        for (ImageView iv : kings) {
            if (observableUnsortedMap.get(e).contains(iv)) {
                return returnBol;
            } else {
                if (!kingInUse.contains(iv)) {
                    observableUnsortedMap.get(e).clear();
                    observableUnsortedMap.get(e).add(iv);
                    kingInUse.add(iv);
                    returnBol = true;
                    return returnBol;
                }
            }
        }
        return returnBol;
    }

    private void removeEmptyKingTargets(UnsortedPile e) {
        ImageView tempIv = null;
        Card tempCard = null;
        if (!unsortedPileCardEnumMap.get(e).isEmpty()) {
            for (Node n : observableUnsortedMap.get(e)) {
                for (ImageView iv : kings) {
                    if (n.equals(iv)) {
                        tempIv = iv;
                        kingInUse.remove(iv);
                        for(Card c : unsortedPileCardEnumMap.get(e)){
                            if(c.getId().equals(nodeStringMap.get(iv))){
                                tempCard = c;
                            }
                        }
                    }
                }
            }
            observableUnsortedMap.get(e).remove(tempIv);
            unsortedPileCardEnumMap.get(e).remove(tempCard);
        }
    }

    public void upDateBigPile() {
        ArrayList<String> tempList = new ArrayList<>();
        ArrayList<ImageView> tempIvList = new ArrayList<>();
        for (Card c : frechBigPileCardList) {
            tempList.add(c.getId());
        }
        for (String s : tempList) {
            for (Map.Entry<ImageView, String> entry : nodeStringMap.entrySet()) {
                if (entry.getValue().equals(s)) {
                    tempIvList.add(entry.getKey());
                }
            }
        }

        frechBigPileObservable.clear();
        frechBigPileObservable.addAll(tempIvList);
    }


    public void updateUnsortedPiles() {
        ArrayList<Node> tempList = new ArrayList<>();
        for (UnsortedPile e : UnsortedPile.values()) {
            if (unsortedPileCardEnumMap.get(e).size() == 0) {
                addEmptyTarget(e);
            } else {
                removeEmptyKingTargets(e);

                for (int i = 0; i < unsortedPileCardEnumMap.get(e).size(); i++) {
                    for (Map.Entry<ImageView, String> entry : nodeStringMap.entrySet()) {
                        if (entry.getValue().equals(unsortedPileCardEnumMap.get(e).get(i).getId())) {
                            tempList.add(entry.getKey());

                        }
                    }
                }
                unsortedPileGridPaneEnumMap.get(e).getChildren().clear();
                for (Node iv : tempList) {
                    int i = unsortedPileGridPaneEnumMap.get(e).getChildren().size();
                    unsortedPileGridPaneEnumMap.get(e).add(iv, 0, i);
                }
                tempList.clear();
            }
        }
    }

    public void upDateSuitPiles() {
        for (SuitPile e : SuitPile.values()) {
            int i = suitPileCardEnumMap.get(e).size();
            if (i > 0) {
                String id = suitPileCardEnumMap.get(e).get(i - 1).getId();
                ImageView iv = null;
                for (Map.Entry<ImageView, String> entry : nodeStringMap.entrySet()) {
                    if (entry.getValue().equals(id)) {
                        iv = entry.getKey();
                    }
                }

                if (e == SuitPile.PILE_CLOVER && id.charAt(0) == 'c') {
                    observableSuitMap.get(e).clear();
                    observableSuitMap.get(e).add(iv);


                } else if (e == SuitPile.PILE_HEART && id.charAt(0) == 'h') {
                    observableSuitMap.get(e).clear();
                    observableSuitMap.get(e).add(iv);


                } else if (e == SuitPile.PILE_DIMOND && id.charAt(0) == 'd') {
                    observableSuitMap.get(e).clear();
                    observableSuitMap.get(e).add(iv);

                }

                if (e == SuitPile.PILE_SPADES && id.charAt(0) == 's') {
                    observableSuitMap.get(e).clear();
                    observableSuitMap.get(e).add(iv);

                }
            }
        }
    }

    private void showFaceUpCards() {
        gameModel.openClosedCards();
        for (UnsortedPile e : UnsortedPile.values()) {
            unsortedPileCardEnumMap = playingField.getUnsortedPileEnumMap();
            ArrayList<Card> cardLt = unsortedPileCardEnumMap.get(e);
            if (cardLt.size() != 0) {
                if (cardLt.get(cardLt.size() - 1).isFaceUp()) {
                    String s = cardLt.get(cardLt.size() - 1).getId();
                    Image i = new Image("GameView/icon/" + s + ".png");
                    for (Map.Entry<ImageView, String> entry : nodeStringMap.entrySet()) {
                        if (entry.getValue().equals(s)) {
                            entry.getKey().setImage(i);
                        }
                    }
                }
            }
        }

        int j = frechBigPileCardList.size();
        if (j > 0) {
            frechBigPileCardList.get(j - 1).turUp();
            String s = frechBigPileCardList.get(j - 1).getId();
            Image i = new Image("GameView/icon/" + s + ".png");
            ImageView iv = (ImageView) frechBigPileObservable.get(j - 1);
            iv.setImage(i);
        }
    }

    private void setStringNodeList(ObservableList<Node> nodeList) {

        for (int i = 0; i < frechBigPileCardList.size(); i++) {
            nodeStringMap.put((ImageView) nodeList.get(i), frechBigPileCardList.get(i).getId());
        }

    }

    private void setStringNodList(UnsortedPile e, ObservableList<Node> nodList) {
        int count = 0;
        ArrayList<Card> tempList = unsortedPileCardEnumMap.get(e);
        for (Node n : nodList) {
            nodeStringMap.put((ImageView) n, tempList.get(count).getId());
            count++;
        }
    }

    private void setStringNodList(SuitPile e, ObservableList<Node> nodList) {
        int count = 0;

        for (Node n : nodList) {
            nodeStringMap.put((ImageView) n, "target");
            count++;
        }
    }

    private void setStringNodeList(ImageView iv) {
        nodeStringMap.put(iv, "targetKing");
    }


    public void initMap() {
        for (UnsortedPile e : UnsortedPile.values()) {
            ObservableList<Node> o = unsortedPileGridPaneEnumMap.get(e).getChildren();
            observableUnsortedMap.put(e, o);
        }

        for (SuitPile e : SuitPile.values()) {
            ObservableList<Node> o = suitPileGridPaneEnumMap.get(e).getChildren();
            observableSuitMap.put(e, o);
        }
    }

    public void initStartField() {
        int i = 0;
        for (UnsortedPile e : UnsortedPile.values()) {
            unsortedPileGridPaneEnumMap.put(e, unsortedGridPanes[i]);
            i++;
        }
        i = 0;
        for (SuitPile e : SuitPile.values()) {
            suitPileGridPaneEnumMap.put(e, suitGridPanes[i]);
            i++;
        }

        for (UnsortedPile e : UnsortedPile.values()) {
            ObservableList<Node> nodeList = unsortedPileGridPaneEnumMap.get(e).getChildren();
            setStringNodList(e, nodeList);
        }
        for (SuitPile e : SuitPile.values()) {
            ObservableList<Node> nodeList = suitPileGridPaneEnumMap.get(e).getChildren();
            setStringNodList(e, nodeList);
        }

        for (ImageView iv : kings) {
            setStringNodeList(iv);
        }

        setStringNodeList(frechBigPileObservable);
        int j = 1;

        showFaceUpCards();
        initMap();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameModel = new GameModel();
        this.c = new Control(this);
        c.setMode(gameModel);
        this.playingField = gameModel.getPlayingField();
        c.setPlayingField(playingField);
        c.updateUnsortedPiles(gameModel.getPlayingField().getUnsortedPileEnumMap());
        observableList = FXCollections.observableArrayList();
        nodeStringMap = new HashMap<ImageView, String>();
        observableUnsortedMap = new EnumMap<UnsortedPile, ObservableList<Node>>(UnsortedPile.class);
        unsortedPileGridPaneEnumMap = new EnumMap<UnsortedPile, GridPane>(UnsortedPile.class);
        suitPileGridPaneEnumMap = new EnumMap<SuitPile, GridPane>(SuitPile.class);
        observableSuitMap = new EnumMap<SuitPile, ObservableList<Node>>(SuitPile.class);
        suitPileCardEnumMap = new EnumMap<SuitPile, ArrayList<Card>>(SuitPile.class);
        frechBigPileCardList = new ArrayList<>();
        dissBigPileList = new ArrayList<>();
        frechBigPileObservable = bigPile.getChildren();
        kingInUse = new ArrayList<>();
        kings = new ArrayList<>();

        kings.add(targetKing1);
        kings.add(targetKing2);
        kings.add(targetKing3);
        kings.add(targetKing4);

        unsortedPileCardEnumMap = playingField.getUnsortedPileEnumMap();
        suitPileCardEnumMap = playingField.getSuitPileArrayListEnumMap();
        frechBigPileCardList = playingField.getBigPile();


        suitGridPanes = new GridPane[4];
        suitGridPanes[0] = smallPile1;
        suitGridPanes[1] = smallPile2;
        suitGridPanes[2] = smallPile3;
        suitGridPanes[3] = smallPile4;

        unsortedGridPanes = new GridPane[7];
        unsortedGridPanes[0] = pile1;
        unsortedGridPanes[1] = pile2;
        unsortedGridPanes[2] = pile3;
        unsortedGridPanes[3] = pile4;
        unsortedGridPanes[4] = pile5;
        unsortedGridPanes[5] = pile6;
        unsortedGridPanes[6] = pile7;
        ColumnConstraints cc = new ColumnConstraints();

        for (GridPane gp : unsortedGridPanes) {
            gp.getStyleClass().add("grid");
        }

        for (GridPane gp : suitGridPanes) {
            gp.getStyleClass().add("suitGrid");
        }
        initStartField();
    }
}
