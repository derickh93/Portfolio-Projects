package edu.qc.seclass.glm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Reminder extends RealmObject implements Serializable {

    @PrimaryKey
    @Index
    private int reminderID;

    private String name;
    private String type;
    private String date;
    private String time;
    private boolean isSelected;


    public Reminder() {

    }
    public Reminder(Reminder r){
        this.name = r.getName();
        this.type = r.getType();
        this.time = r.getTime();
        this.date = r.getDate();
        this.reminderID = r.getReminderID();
        this.isSelected = r.getIsSelected();
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    public Reminder(String name, String type, String date, String time,boolean selected) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.isSelected = selected;
    }

    public int getReminderID () {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDay() {
        String[] date = this.date.split ( "-" );
        int day = Integer.parseInt ( date[0].trim() );
        return day;
    }

    public int getYear() {
        String[] date = this.date.split ( "-" );
        int year = Integer.parseInt ( date[2].trim() );
        return year;
    }

    public int getMonth() {
        String[] date = this.date.split ( "-" );
        int month = Integer.parseInt ( date[1].trim() );
        return month-1;
    }

    public int getHour() {
        String[] time = this.time.split ( ":" );
        int hour = Integer.parseInt ( time[0].trim() );
        int min = Integer.parseInt ( time[1].trim() );

        return hour;
    }

    public int getMinute() {
        String[] time = this.time.split ( ":" );
        int hour = Integer.parseInt ( time[0].trim() );
        int min = Integer.parseInt ( time[1].trim() );
        return min;
    }

    public String toString() {
        return name + "\n" + type + "\n" + date + " " + time;
    }
}
