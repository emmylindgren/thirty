package se.umu.emli.thirty.model;


public class ChosenColor {
    private int chosenColor;
    private int colorButtonId;
    private int oldColorButtonId;

    public ChosenColor() {
        colorButtonId =0;
        oldColorButtonId=0;
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
        if(oldColorButtonId==0 || oldColorButtonId == colorButtonId){
            return false;
        }
        return true;
    }
}
