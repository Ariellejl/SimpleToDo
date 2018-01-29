package com.mjwenn.simpletodo.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Ermano
 * on 1/26/2018.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "simple_to_do_db";

    public static final int VERSION = 1;
}
