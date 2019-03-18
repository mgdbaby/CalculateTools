package com.amao.calculate.core;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;


public abstract class BaseCursor implements Cursor {

    private Cursor cursor;

    public BaseCursor() {
        this(true);
    }

    public BaseCursor(boolean isAutoLoad) {
        if (isAutoLoad) {
            this.cursor = load(defaultSelection(), defaultSelectionArgs());
        }
    }

    public Cursor loadCursor() {
        this.cursor = load(defaultSelection(), defaultSelectionArgs());
        return this;
    }

    public BaseCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public abstract Cursor load(String selection, String... selectionArgs);

    public int getViewType() {
        return 0;
    }

    protected String defaultSelection() {
        return null;
    }

    protected String[] defaultSelectionArgs() {
        return null;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public int getPosition() {
        return cursor == null ? -1 : cursor.getPosition();
    }

    @Override
    public boolean move(int offset) {
        return cursor != null && cursor.move(offset);
    }

    @Override
    public boolean moveToPosition(int position) {
        if (cursor == null) {
            return false;
        }

        if (cursor.getPosition() == position) {
            return true;
        }
        return cursor.moveToPosition(position);
    }

    @Override
    public boolean moveToFirst() {
        return cursor != null && cursor.moveToFirst();
    }

    @Override
    public boolean moveToLast() {
        return cursor != null && cursor.moveToLast();
    }

    @Override
    public boolean moveToNext() {
        return cursor != null && cursor.moveToNext();
    }

    @Override
    public boolean moveToPrevious() {
        return cursor != null && cursor.moveToPrevious();
    }

    @Override
    public boolean isFirst() {
        return cursor != null && cursor.isFirst();
    }

    @Override
    public boolean isLast() {
        return cursor != null && cursor.isLast();
    }

    @Override
    public boolean isBeforeFirst() {
        return cursor != null && cursor.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() {
        return cursor != null && cursor.isAfterLast();
    }

    @Override
    public int getColumnIndex(String columnName) {
        return cursor == null ? -1 : cursor.getColumnIndex(columnName);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        if (cursor == null) {
            throw new IllegalArgumentException("Cursor is null.");
        }
        return cursor.getColumnIndexOrThrow(columnName);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return cursor == null ? null : cursor.getColumnName(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        return cursor == null ? null : cursor.getColumnNames();
    }

    @Override
    public int getColumnCount() {
        return cursor == null ? 0 : cursor.getColumnCount();
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return cursor == null ? null : cursor.getBlob(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        return cursor == null ? "" : cursor.getString(columnIndex);
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        if (cursor != null) cursor.copyStringToBuffer(columnIndex, buffer);
    }

    @Override
    public short getShort(int columnIndex) {
        return cursor == null ? 0 : cursor.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        return cursor == null ? 0 : cursor.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) {
        return cursor == null ? 0L : cursor.getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) {
        return cursor == null ? 0f : cursor.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) {
        return cursor == null ? 0 : cursor.getDouble(columnIndex);
    }

    @Override
    public int getType(int columnIndex) {
        return cursor == null ? 0 : cursor.getType(columnIndex);
    }

    @Override
    public boolean isNull(int columnIndex) {
        return cursor != null && cursor.isNull(columnIndex);
    }

    @Override
    public void deactivate() {
        if (cursor != null) cursor.deactivate();
    }

    @Override
    public boolean requery() {
        return cursor != null && cursor.requery();
    }

    @Override
    public void close() {
        if (cursor != null) cursor.close();
    }

    @Override
    public boolean isClosed() {
        return cursor != null && cursor.isClosed();
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        if (cursor != null) cursor.registerContentObserver(observer);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        if (cursor != null) cursor.unregisterContentObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (cursor != null) cursor.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (cursor != null) cursor.unregisterDataSetObserver(observer);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        if (cursor != null) cursor.setNotificationUri(cr, uri);

    }

    @Override
    public Uri getNotificationUri() {
        return cursor == null ? null : cursor.getNotificationUri();
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return cursor != null && cursor.getWantsAllOnMoveCalls();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setExtras(Bundle extras) {
        if (cursor != null) cursor.setExtras(extras);
    }

    @Override
    public Bundle getExtras() {
        return cursor == null ? null : cursor.getExtras();
    }

    @Override
    public Bundle respond(Bundle extras) {
        return cursor == null ? null : cursor.respond(extras);
    }
}
