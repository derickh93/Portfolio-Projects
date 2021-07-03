package edu.ncc.nccdepartmentdatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DepartmentInfoSource {

    private SQLiteDatabase database;
    private static String ORDER_BY = DepartmentInfoHelper.LOCATION;

    private DepartmentInfoHelper deptHelper;

    private String[] allColumns = { DepartmentInfoHelper._ID, DepartmentInfoHelper.NAME, DepartmentInfoHelper.LOCATION,
            DepartmentInfoHelper.PHONE, DepartmentInfoHelper.EMAIL};

    public DepartmentInfoSource(Context context) {
        deptHelper = new DepartmentInfoHelper(context);
    }

    public void open() throws SQLException {
        database = deptHelper.getWritableDatabase();
    }

    public void close() {
        deptHelper.close();
    }

    public DepartmentEntry addDept(String name, String location, String phone, String email) {
        ContentValues values = new ContentValues();
        values.put(DepartmentInfoHelper.NAME, name);
        values.put(DepartmentInfoHelper.LOCATION, location);
        values.put(DepartmentInfoHelper.PHONE, phone);
        values.put(DepartmentInfoHelper.EMAIL, email);
        long insertId = database.insert(DepartmentInfoHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME, allColumns, DepartmentInfoHelper._ID + " = " +insertId, null, null, null, null);
        cursor.moveToFirst();
        DepartmentEntry entry = cursorToEntry(cursor);
        cursor.close();
        return entry;
    }

    public void deleteDept(DepartmentEntry dept) {
        long id = dept.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DepartmentInfoHelper.TABLE_NAME, DepartmentInfoHelper._ID
                + " = " + id, null);
    }

    public List<DepartmentEntry> getAllDepartments() {
        List<DepartmentEntry> dpts = new ArrayList<>();
        DepartmentEntry entry;
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entry = cursorToEntry(cursor);
            dpts.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return dpts;
    }

    public List<DepartmentEntry> findDepartments(String sel, String order, String... str) {
        List<DepartmentEntry> dpts = new ArrayList<>();
        DepartmentEntry entry;
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME, allColumns,sel, str, null, null, order);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entry = cursorToEntry(cursor);
            dpts.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return dpts;
    }

    private DepartmentEntry cursorToEntry(Cursor cursor) {
        DepartmentEntry entry = new DepartmentEntry();
        entry.setId(cursor.getLong(0));
        entry.setName(cursor.getString(1));
        entry.setLocation(cursor.getString(2));
        entry.setPhone(cursor.getString(3));
        entry.setEmail(cursor.getString(4));
        return entry;
    }
}