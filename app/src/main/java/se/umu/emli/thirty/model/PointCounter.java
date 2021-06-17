package se.umu.emli.thirty.model;

import java.util.ArrayList;
import java.util.HashMap;

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

    private void initColorHashMap(){
        colorPoints= new HashMap<Integer,Integer>();
        colorPoints.put(Constants.BLUE,0);
        colorPoints.put(Constants.GREEN,0);
        colorPoints.put(Constants.YELLOW,0);
        colorPoints.put(Constants.RED,0);
        colorPoints.put(Constants.PURPLE,0);
        colorPoints.put(Constants.BROWN,0);
    }

    public void countRoundPoint(String round, ArrayList<Dice> dices){
        this.dices = dices;
        int score;

        if(round.equals("Low")){
            score = countLowRound();
        }
        else{
            int roundNr = Integer.parseInt(round);
            sortColorPoints();
            score = countColorPoints(roundNr);
        }

        roundPoints.put(round,score);
        totalPoints+= score;
        latestRoundPoints= score;
    }

    private int countLowRound(){
        int score= 0;

        for (Dice dice : dices){
            if(dice.getDiceValue()<4){
                score += dice.getDiceValue();
            }
        }
        return score;
    }

    private void sortColorPoints(){
        for (Dice dice: dices){
            if(dice.getDiceColor()!=Constants.WHITE){
                int i = colorPoints.get(dice.getDiceColor());
                i += dice.getDiceValue();
                colorPoints.remove(dice.getDiceColor());
                colorPoints.put(dice.getDiceColor(),i);
            }
        }
    }

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
}
