package edu.qc.seclass.glm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ReminderType extends RealmObject implements Serializable {

    @PrimaryKey
    @Index
    private int reminderTypeID;

    private String name;

    public ReminderType() {

    }

    public ReminderType(ReminderType rt) {
       this.name = rt.getName();
       this.reminderTypeID = rt.getReminderTypeID();
    }

    public int getReminderTypeID() {
        return reminderTypeID;
    }

    public void setReminderTypeID(int reminderTypeID) {
        this.reminderTypeID = reminderTypeID;
    }

    public ReminderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
