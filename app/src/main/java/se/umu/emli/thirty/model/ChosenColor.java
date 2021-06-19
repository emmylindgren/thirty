package se.umu.emli.thirty.model;

/** Chosen color model class. Keeps track of chosen color, which id that color belongs to
 * and which id was previously the chosen color.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class ChosenColor {
    private int chosenColor;
    private int colorButtonId;
    private int oldColorButtonId;

    public ChosenColor() {
        colorButtonId =0;
        oldColorButtonId=0;
        chosenColor= Constants.WHITE;
    }

    public ChosenColor(int chosenColor,int colorButtonId){
        this.chosenColor = chosenColor;
        this.colorButtonId = colorButtonId;
        oldColorButtonId = colorButtonId;
    }

    public int getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(int chosenColor) {
        this.chosenColor = chosenColor;
    }

    public int getColorButtonId() {
        return colorButtonId;
    }

    public void setColorButtonId(int colorButtonId) {
        this.colorButtonId = colorButtonId;
    }

    public int getOldColorButtonId() {
        return oldColorButtonId;
    }

    public void setOldColorButtonId(int oldColorButtonId) {
        this.oldColorButtonId = oldColorButtonId;
    }

    public boolean hasOldColorButton(){
        return oldColorButtonId != 0 && oldColorButtonId != colorButtonId;
    }
}
