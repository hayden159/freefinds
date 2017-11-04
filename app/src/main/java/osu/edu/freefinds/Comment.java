package osu.edu.freefinds;

/**
 * Created by Liz on 11/2/2017.
 */

public class Comment {
    private String content;
    private int hourPosted;
    private int minutePosted;
    private String id;

    public Comment(){
        //empty
    }

    public Comment(String cont, int hour, int minute){
        this.content = cont;
        this.hourPosted = hour;
        this.minutePosted = minute;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHourPosted() {
        return hourPosted;
    }

    public void setHourPosted(int hourPosted) {
        this.hourPosted = hourPosted;
    }

    public int getMinutePosted() {
        return minutePosted;
    }

    public void setMinutePosted(int minutePosted) {
        this.minutePosted = minutePosted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
