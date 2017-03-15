package com.extraware.xwormapi;
/**
 * Created by JP on 15-03-2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class XGestorBD extends SQLiteOpenHelper {
    public XGestorBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
