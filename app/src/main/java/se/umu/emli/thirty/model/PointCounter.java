package se.umu.emli.thirty.model;

import java.util.ArrayList;
import java.util.HashMap;

/** PointCounter model class. Keeps track of and calculates points for each round, and total points.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class PointCounter {
    private int totalPoints;
    private int latestRoundPoints;
    private HashMap<String,Integer> roundPoints;
    private HashMap<Integer,Integer> colorPoints;
    private ArrayList<Dice> dices;


    public PointCounter() {
        roundPoints = new HashMap<String,Integer>();
        totalPoints = 0;
        latestRoundPoints=0;
        initColorHashMap();
    }

    public PointCounter(HashMap points, int latestRoundPoints){
        roundPoints = points;
        totalPoints= roundPoints.get("total");
        this.latestRoundPoints = latestRoundPoints;
        initColorHashMap();
    }

    private void initColorHashMap(){
        colorPoints= new HashMap<Integer,Integer>();
        colorPoints.put(Constants.BLUE,0);
        colorPoints.put(Constants.GREEN,0);
        colorPoints.put(Constants.YELLOW,0);
        colorPoints.put(Constants.RED,0);
        colorPoints.put(Constants.PURPLE,0);
        colorPoints.put(Constants.BROWN,0);
    }

    /**
     * Counts the points of the dices for the selected round. For round "low" all colored dices with
     * a value of 3 or under are counted. But for other rounds the points are sorted into color
     * points and then counts the color points separately to make sure no color is cheating.
     *
     * Adds that round point to arraylist of round points to keep track of, updates total and latest
     * round points.
     * @param round, a string containing the selected round.
     * @param dices, a list of dices which the points are to be counted.
     */
    public void countRoundPoint(String round, ArrayList<Dice> dices){
        this.dices = dices;
        int point;

        if(round.equals("Low")){
            point = countLowRound();
        }
        else{
            int roundNr = Integer.parseInt(round);
            sortColorPoints();
            point = countColorPoints(roundNr);
        }

        roundPoints.put(round,point);
        totalPoints+= point;
        latestRoundPoints= point;
    }

    private int countLowRound(){
        int point= 0;

        for (Dice dice : dices){
            if(dice.diceIsColored()){
                if(dice.getDiceValue()<4){
                    point += dice.getDiceValue();
                }
            }
        }
        return point;
    }

    private void sortColorPoints(){
        for (Dice dice: dices){
            if(dice.diceIsColored()){
                int i = colorPoints.get(dice.getDiceColor());
                i += dice.getDiceValue();
                colorPoints.remove(dice.getDiceColor());
                colorPoints.put(dice.getDiceColor(),i);
            }
        }
    }

    /**
     * Checks each color to se if points are equal to the chosen round number, or a multiple
     * of the chosen round number. If they are the points are added to the round point.
     * @param roundNr, the chosen round number.
     * @return the collected round points of all colors that passed.
     */
    private int countColorPoints(int roundNr){
        int points = 0;
        int colorPoint;

        for(HashMap.Entry<Integer, Integer> color : colorPoints.entrySet()) {
            colorPoint = color.getValue();

            if(colorPoint % roundNr == 0){
                points += colorPoint;
            }

            color.setValue(0);
        }
        return points;
    }

    public String getTotalPointsString(){
        return Integer.toString(totalPoints);

    }

    public String getLatestRoundPointsString(){
        return Integer.toString(latestRoundPoints);
    }

    public int getLatestRoundPointsInt(){ return latestRoundPoints;}

    public HashMap getAllPoints(){

        if(roundPoints.containsKey("total")){
            roundPoints.remove("total");
        }

        roundPoints.put("total",totalPoints);
        return roundPoints;
    }
}
