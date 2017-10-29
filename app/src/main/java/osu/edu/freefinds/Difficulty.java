package osu.edu.freefinds;
/**
 * Created by stephaniesmacbook on 10/26/17.
 */

public class Difficulty {

    public String mDescription;
    public int mLevel;

    public Difficulty(int level) {
        switch(level) {
            case 1 :
                setEasy();
                break;
            case 2 :
                setMedium();
                break;
            case 3 :
                setHard();
                break;
            default:
                break;
        }
    }

    public void setEasy(){
        mDescription = "This event requires almost nothing. You might need to provide your email " +
                "address or listen to a very short speech, but the total time is less than five minutes.";
        mLevel = 1;
    }
    public void setMedium(){
        mDescription = "This event requires moderate effort. You might need to listen to a longer speech " +
                "or participate in an activity. The total time is more than 5 minutes but less than 20 minutes.";
        mLevel = 2;
    }
    public void setHard(){
        mDescription = "This event will take some time. You might need to fill out a long survey " +
                "or sit in on a long talk. This activity takes longer than 20 minutes.";
        mLevel = 3;
    }
}
