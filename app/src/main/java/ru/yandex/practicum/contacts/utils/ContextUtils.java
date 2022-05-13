package ru.yandex.practicum.contacts.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import androidx.core.content.ContextCompat;

import java.util.function.Consumer;

public class ContextUtils {

    public static boolean hasContactPermissions(Context context) {
        return hasPermission(context, Manifest.permission.READ_CONTACTS) &&
                hasPermission(context, Manifest.permission.WRITE_CONTACTS);
    }

    private static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void query(Context context, Uri uri, String[] projection, Consumer<Cursor> callback) {
        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    callback.accept(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
    }

    public static void query(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, Consumer<Cursor> callback) {
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor.moveToFirst()) {
                do {
                    callback.accept(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
    }

    public static void query(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, Consumer<Cursor> callback) {
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor.moveToFirst()) {
                do {
                    callback.accept(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
    }
}
