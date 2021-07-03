package edu.qc.seclass.glm;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ReminderList extends RealmObject implements Serializable {

    @PrimaryKey
    @Index
    private int reminderListID;

    private String listName;

    public ReminderList() {
    }

    public ReminderList(ReminderList r){
        this.listName = r.getListName();
        this.reminderListID = r.getReminderListID();
    }

    public int getReminderListID() {
        return reminderListID;
    }

    public void setReminderListID(int reminderListID) {
        this.reminderListID = reminderListID;
    }

    public ReminderList(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String toString() {
        return listName;
    }

}
