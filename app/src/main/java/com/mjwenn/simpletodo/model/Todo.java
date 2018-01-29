package com.mjwenn.simpletodo.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Ermano
 * on 1/26/2018.
 */

@Table(database = MyDatabase.class)
public class Todo extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private long ID;

    @Column
    private String task;

    @Column
    private String description;

    @Column
    private int prority;

    @Column
    private String dueDate;

    @Column
    private String dateCreated;

    @Column
    private int status;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrority() {
        return prority;
    }

    public void setPrority(int prority) {
        this.prority = prority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
