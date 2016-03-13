package Control;

import GameView.Controller;
import Model.*;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rickard on 2016-01-28.
 */
public class Control implements ControlInterface {
    private GameModel model;
    private Controller viewControl;
    private PlayingField playingField;
    private EnumMap<UnsortedPile, ArrayList<Card>> unsortedPiles;
    private EnumMap<SuitPile, ArrayList<Card>> suitPiles;
    private Card fromCard;
    private Card toCard;
    private Enum toEnum;
    private Enum fromEnum;

    public Control(Controller viewControl) {
        this.viewControl = viewControl;
    }

    public void setMode(GameModel model) {
        this.model = model;
    }

    public boolean isGodMach(String to, String from) {
        findCards(to, from);
        return checkMatch();
    }


    private boolean checkMatch() {
        boolean returnBol = false;
        int iTo = 0;
        if(Character.isDigit(toCard.getId().charAt(1))) {
             iTo = Integer.parseInt(toCard.getId().substring(1));
        }
        int iFrom = Integer.parseInt(fromCard.getId().substring(1));


        if (toCard.equals(fromCard)) {
            return false;
        }

        if (toCard.getId().equals("target")) {
            if (iFrom == 1) {
                playingField.moveCardToEmptyTarget(fromCard);
                returnBol = true;
            }
        } else if (toCard.getId().equals("targetKing")) {
            if (iFrom == 13) {
                for (UnsortedPile e : UnsortedPile.values()) {
                    if (e.equals(fromEnum)) {
                        playingField.moveKingToEmptyTarget(e, fromCard);
                    }
                }
                returnBol = true;

            }
        } else if (fromEnum instanceof SuitPile) {
            returnBol = false;

        } else if (toEnum instanceof SuitPile) {
            if (toCard.getId().charAt(0) == fromCard.getId().charAt(0)) {
                if (iTo == iFrom-1) {
                   int size = playingField.getUnsortedPileEnumMap().get(fromEnum).size();
                    if(playingField.getUnsortedPileEnumMap().get(fromEnum).get(size-1).equals(fromCard)) {
                        playingField.moveCardToSuitPile(fromCard, toCard);
                        returnBol = true;
                    }
                }
            }
        } else if (toEnum instanceof UnsortedPile) {

            if (toCard.getId().charAt(0) == 'h' || toCard.getId().charAt(0) == 'd') {
                if (fromCard.getId().charAt(0) == 'c' || fromCard.getId().charAt(0) == 's'){
                    if(iFrom == (iTo-1)){
                        returnBol = true;
                        playingField.moveCardsToUnsortedPile(fromCard, toCard);
                    }

                }
            } else if (toCard.getId().charAt(0) == 'c' || toCard.getId().charAt(0) == 's') {
                if (fromCard.getId().charAt(0) == 'h' || fromCard.getId().charAt(0) == 'd') {
                    if(iFrom == iTo-1){
                        returnBol = true;
                        playingField.moveCardsToUnsortedPile(fromCard, toCard);
                    }
                }
            }
        }

            return returnBol;
        }



  private void  findCards(String to, String from) {
      if(to.equals("target")){
          for (UnsortedPile e : UnsortedPile.values()) {
              for (Card c : unsortedPiles.get(e)) {
                  if (c.getId().equals(from)) {
                      fromCard = c;
                      fromEnum = e;
                  }
              }
          }
          toCard = new Card("target");

      }else if(to.equals("targetKing")){
          for(UnsortedPile e : UnsortedPile.values()){
              for (Card c : unsortedPiles.get(e)){
                  if((c.getId().equals(from))){
                      fromCard = c;
                      fromEnum = e;
                  }
              }
          }
          toCard = new Card("targetKing");

      } else {
          for (UnsortedPile e : UnsortedPile.values()) {
              for (Card c : unsortedPiles.get(e)) {
                  if (c.getId().equals(from)) {
                      fromCard = c;
                      fromEnum = e;
                  }
                  if (c.getId().equals(to)) {
                      toCard = c;
                      toEnum = e;
                  }
              }
          }
      }

      for (SuitPile e : SuitPile.values()) {
          for (Card c : suitPiles.get(e)) {
              if (c.getId().equals(from)) {
                  fromCard = c;
                  fromEnum = e;
              }
              if (c.getId().equals(to)) {
                  toCard = c;
                  toEnum = e;
              }
          }
      }

      for(Card c : playingField.getBigPile()){
        if(c.getId().equals(from)){
              fromCard = c;
          }
      }
  }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
        setUnsortedPiles();

    }

    private void setUnsortedPiles() {
       this.unsortedPiles = playingField.getUnsortedPileEnumMap();
       this.suitPiles = playingField.getSuitPileArrayListEnumMap();
    }


    @Override
    public String getMovableCard(ObservableList<javafx.scene.Node> nodeList) {
        return null;
    }

    @Override
    public boolean isMixedListOk() {
        return false;
    }

    public void updateUnsortedPiles(EnumMap<UnsortedPile, ArrayList<Card>> unsortedPiles) {
        this.unsortedPiles = unsortedPiles;
    }

    public EnumMap<UnsortedPile, ArrayList<Card>> getUnsortedPiles() {
        return unsortedPiles;
    }

}
