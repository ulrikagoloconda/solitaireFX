package Model;

/**
 * Created by Rickard on 2016-01-28.
 */
public class Card {
   private String id;
   private boolean faceUp;
   public Card(String id){
        this.id = id;
        faceUp = true;
    }
    public String getId(){
        return id;
    }
    public void turUp(){
        faceUp = true;
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    public String toString(){
        return id;
    }
}
