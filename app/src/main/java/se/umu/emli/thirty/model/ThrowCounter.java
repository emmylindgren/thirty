package se.umu.emli.thirty.model;

public class ThrowCounter {
    public int nrOfThrows;
    public int maxNrOfThrows;
    public boolean throwsAreUp;


    public ThrowCounter(int nrOfThrows) {
        maxNrOfThrows = 3;
        throwsAreUp = false;
        this.nrOfThrows=nrOfThrows;
    }

    public boolean throwsAreUp() {
        checkThrows();
        nrOfThrows++;
        return throwsAreUp;
    }

    public boolean firstThrowsBeenMade(){
        if(nrOfThrows==0) {
            return false;
        }
        return true;
    }

    public void resetThrows(){
        nrOfThrows=0;
        throwsAreUp=false;
    }

    private void checkThrows(){
        if(nrOfThrows >= 3){
            throwsAreUp = true;
        }
    }

}
