package Model;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by Rickard on 2016-01-28.
 */
public class PlayingField {
    private EnumMap<Model.UnsortedPile, ArrayList<Model.Card>> unsortedPileArrayListEnumMap;
    private EnumMap<Model.SuitPile, ArrayList<Model.Card>> suitPileArrayListEnumMap;
    private ArrayList<Model.Card> localCardDeck;
    private ArrayList<Card> bigPile;
    private int j = 1;
    private int k = 0;

    public PlayingField() {
        unsortedPileArrayListEnumMap = new EnumMap<UnsortedPile, ArrayList<Card>>(UnsortedPile.class);
        suitPileArrayListEnumMap = new EnumMap<SuitPile, ArrayList<Card>>(SuitPile.class);
        localCardDeck = CardDeck.getCardDeck();
        bigPile = new ArrayList<>();
        setPlayingField();
    }

    private void setPlayingField() {

        int deckCount = 0;
        int pileCount = 1;
        for (UnsortedPile e : UnsortedPile.values()) {
            unsortedPileArrayListEnumMap.put(e, new ArrayList<>());
            for (int i = 0; i < pileCount; i++) {
                unsortedPileArrayListEnumMap.get(e).add(localCardDeck.get(deckCount));
                deckCount++;
            }
            pileCount++;
        }

        for (SuitPile e : SuitPile.values()) {
            suitPileArrayListEnumMap.put(e, new ArrayList<>());
        }
        for (int i = deckCount; i < localCardDeck.size(); i++) {
            bigPile.add(localCardDeck.get(i));

        }
        int totalCount = 0;
        for (UnsortedPile e : UnsortedPile.values()) {
            totalCount += unsortedPileArrayListEnumMap.get(e).size();
        }
    }

    public void moveCardToSuitPile(Card fromCard, Card toCard) {
        if (toCard.getId().equals("target")) {
            moveCardToEmptyTarget(fromCard);
        } else {
            for (UnsortedPile e : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(e).contains(fromCard)) {
                    int i = unsortedPileArrayListEnumMap.get(e).size();
                    if (i > 0) {
                        unsortedPileArrayListEnumMap.get(e).get(i - 1).turUp();
                    }
                }
            }
            findAndRemoveCard(fromCard);

            for (SuitPile e : SuitPile.values()) {
                if (suitPileArrayListEnumMap.get(e).contains(toCard)) {
                    suitPileArrayListEnumMap.get(e).add(fromCard);
                }
            }
        }
    }


    public void moveCardsToUnsortedPile(Card fromCard, Card toCard) {
        if (bigPile.contains(fromCard)) {
            for (UnsortedPile e : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(e).contains(toCard)) {
                    unsortedPileArrayListEnumMap.get(e).add(fromCard);
                }
            }
            bigPile.remove(fromCard);


        } else {
            ArrayList<Card> tempArray = new ArrayList<>();
            for (UnsortedPile e : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(e).contains(fromCard)) {
                    int i = unsortedPileArrayListEnumMap.get(e).indexOf(fromCard);
                    int size = unsortedPileArrayListEnumMap.get(e).size();
                    for (int j = i; j < size; j++) {

                        tempArray.add(unsortedPileArrayListEnumMap.get(e).get(j));

                    }
                }
            }
            for (Card c : tempArray) {
                findAndRemoveCard(c);
            }
            for (UnsortedPile e : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(e).contains(toCard)) {
                    unsortedPileArrayListEnumMap.get(e).addAll(tempArray);
                }
            }
        }
    }

    public void findAndRemoveCard(Card fromCard) {
        for (UnsortedPile e : UnsortedPile.values()) {
            if (unsortedPileArrayListEnumMap.get(e).contains(fromCard)) {
                int i = unsortedPileArrayListEnumMap.get(e).indexOf(fromCard);
                unsortedPileArrayListEnumMap.get(e).remove(i);
            }
        }
        if(bigPile.contains(fromCard)){
            bigPile.remove(fromCard);
        }
    }

    public void findAndRemoveCard(UnsortedPile e, Card fromCard) {
        unsortedPileArrayListEnumMap.get(e).remove(fromCard);
        if(bigPile.contains(fromCard)){
            bigPile.remove(fromCard);
        }
    }


    public void moveCardToEmptyTarget(Card fromCard) {
        for (SuitPile e : SuitPile.values()) {
            if (e.equals(SuitPile.PILE_CLOVER) && fromCard.getId().charAt(0) == 'c') {

                suitPileArrayListEnumMap.get(e).add(0, fromCard);
                findAndRemoveCard(fromCard);

            } else if (e.equals(SuitPile.PILE_DIMOND) && fromCard.getId().charAt(0) == 'd') {
                suitPileArrayListEnumMap.get(e).add(0, fromCard);
                findAndRemoveCard(fromCard);

            } else if (e.equals(SuitPile.PILE_HEART) && fromCard.getId().charAt(0) == 'h') {
                suitPileArrayListEnumMap.get(e).add(0, fromCard);
                findAndRemoveCard(fromCard);

            } else if (e.equals(SuitPile.PILE_SPADES) && fromCard.getId().charAt(0) == 's') {
                suitPileArrayListEnumMap.get(e).add(0, fromCard);
                findAndRemoveCard(fromCard);
            }
        }
    }

    public void moveKingToEmptyTarget(UnsortedPile e, Card fromCard) {
        if (bigPile.contains(fromCard)) {
            for (UnsortedPile f : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(f).isEmpty()) {
                    unsortedPileArrayListEnumMap.get(f).add(fromCard);
                    bigPile.remove(fromCard);
                }
            }
        } else {
            ArrayList<Card> tempArray = new ArrayList<>();
            for (UnsortedPile f : UnsortedPile.values()) {
                if (unsortedPileArrayListEnumMap.get(f).isEmpty()) {
                    int i = unsortedPileArrayListEnumMap.get(e).indexOf(fromCard);
                    System.out.println("i playingfied längd på listor " + i + " "+ unsortedPileArrayListEnumMap.get(e).size());
                    for (int j = i; j < unsortedPileArrayListEnumMap.get(e).size(); j++) {
                        tempArray.add(unsortedPileArrayListEnumMap.get(e).get(j));
                    }
                    unsortedPileArrayListEnumMap.get(f).addAll(tempArray);
                    for (Card c : tempArray) {
                        findAndRemoveCard(e, c);
                    }
                }
            }

        }
    }


    public EnumMap<UnsortedPile, ArrayList<Card>> getUnsortedPileEnumMap() {
        return unsortedPileArrayListEnumMap;
    }

    public EnumMap<SuitPile, ArrayList<Card>> getSuitPileArrayListEnumMap() {
        return suitPileArrayListEnumMap;
    }

    public ArrayList<Card> getBigPile() {
        return bigPile;
    }


}
