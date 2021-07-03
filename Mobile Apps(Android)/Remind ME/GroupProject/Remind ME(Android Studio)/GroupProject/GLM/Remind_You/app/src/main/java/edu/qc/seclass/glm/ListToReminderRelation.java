package edu.qc.seclass.glm;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ListToReminderRelation extends RealmObject implements Serializable {

    @PrimaryKey
    @Index
    private int ListToReminderRelationID;

    private String reminderList;
    private String reminder;


    public ListToReminderRelation() {
    }

    public ListToReminderRelation(ReminderList rl, Reminder r){
        reminderList = rl.getListName();
        reminder = r.getName();
    }

    public ListToReminderRelation(String rl, String r)
    {
        reminderList = rl;
        reminder = r;
    }

    public ListToReminderRelation(ListToReminderRelation ltr) {
        reminderList = ltr.getReminderList();
        reminder = ltr.getReminder();
    }

    public String getReminderList() {
        return reminderList;
    }

    public void setReminderList(String reminderList) {
        this.reminderList = reminderList;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public int getListToReminderRelationID() {
        return ListToReminderRelationID;
    }

    public void setListToReminderRelationID(int listToReminderRelationID) {
        ListToReminderRelationID = listToReminderRelationID;
    }

    @NonNull
    @Override
    public String toString() {
        return reminder;
    }
}

