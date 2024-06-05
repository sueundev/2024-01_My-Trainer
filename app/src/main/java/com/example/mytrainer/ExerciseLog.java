package com.example.mytrainer;

public class ExerciseLog {
    private long timestamp;
    private String upperBodyCount;
    private String lowerBodyCount;
    private String date;
    private int duration;

    public ExerciseLog(long timestamp, String upperBodyCount, String lowerBodyCount, String date) {
        this.timestamp = timestamp;
        this.upperBodyCount = upperBodyCount;
        this.lowerBodyCount = lowerBodyCount;
        this.date = date;
        int a=0;
    }
    public int getDuration(){
        return duration;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public String getUpperBodyCount() {
        return upperBodyCount;
    }

    public String getLowerBodyCount() {
        return lowerBodyCount;
    }

    public String getDate() {
        return date;
    }
}
