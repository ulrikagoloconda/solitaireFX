package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Rickard on 2016-01-28.
 */
public class CardDeck {
   private static ArrayList<Card> cardDeck;
    private static char[] suit;
    static {
        cardDeck = new ArrayList<>();
        suit = new char[4];
        suit[0] = 'c';
        suit[1] = 'd';
        suit[2] = 'h';
        suit[3] = 's';
        createCardDeck();
    }

    private static void createCardDeck() {
        for(char c : suit){
            for(int i = 1; i <= 13; i++){
                cardDeck.add(new Card(""+c+i));
            }
        }
        Collections.shuffle(cardDeck);
        for(Card card : cardDeck){
        }
    }

    public static ArrayList<Card> getCardDeck(){

        return cardDeck;
    }
}
