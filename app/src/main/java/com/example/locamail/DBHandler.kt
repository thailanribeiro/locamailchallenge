package com.example.locamail
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SENDER_COL + " TEXT,"
                + RECEIVE_COL + " TEXT,"
                + SUBJECT_COL + " TEXT,"
                + PREVIEW_COL + " TEXT,"
                + CONTENT_COL + " TEXT,"
                + MARKUP_COL + " TEXT,"
                + IMPORTANT_COL + " INTEGER,"
                + DELETED_COL + " INTEGER,"
                + SPAM_COL + " INTEGER,"
                + SENDED_COL + " INTEGER,"
                + RECEIVED_COL + " INTEGER,"
                + DATE_COL + " TEXT,"
                + READ_COL + " INTEGER)")

        db.execSQL(query)
    }

    fun addNewEmail(
        sender: String?,
        receive: String?,
        subject: String?,
        preview: String?,
        content: String?,
        markup: String?,
        important: Int,
        deleted: Int,
        spam: Int,
        sended: Int,
        received: Int,
        date: String,
        read: Int
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SENDER_COL, sender)
        values.put(RECEIVE_COL, receive)
        values.put(SUBJECT_COL, subject)
        values.put(PREVIEW_COL, preview)
        values.put(CONTENT_COL, content)
        values.put(MARKUP_COL, markup)
        values.put(IMPORTANT_COL, important)
        values.put(DELETED_COL, deleted)
        values.put(SPAM_COL, spam)
        values.put(SENDED_COL, sended)
        values.put(RECEIVED_COL, received)
        values.put(DATE_COL, date)
        values.put(READ_COL, read)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "emaildb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "emails"
        private const val ID_COL = "id"
        private const val SENDER_COL = "sender"
        private const val RECEIVE_COL = "receiver"
        private const val SUBJECT_COL = "subject"
        private const val PREVIEW_COL = "preview"
        private const val CONTENT_COL = "content"
        private const val MARKUP_COL = "markup"
        private const val IMPORTANT_COL = "important"
        private const val DELETED_COL = "deleted"
        private const val SPAM_COL = "spam"
        private const val SENDED_COL = "sended"
        private const val RECEIVED_COL = "received"
        private const val DATE_COL = "date"
        private const val READ_COL = "read"
    }

    fun readEmails(): ArrayList<EmailModel>? {
        val db = this.readableDatabase
        val cursorEmails: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val emailModelArrayList: ArrayList<EmailModel> = ArrayList()

        if (cursorEmails.moveToFirst()) {
            do {
                emailModelArrayList.add(
                    EmailModel(
                        cursorEmails.getInt(0),
                        cursorEmails.getString(1),
                        cursorEmails.getString(2),
                        cursorEmails.getString(3),
                        cursorEmails.getString(4),
                        cursorEmails.getString(5),
                        cursorEmails.getString(6),
                        cursorEmails.getInt(7) != 0,
                        cursorEmails.getInt(8) != 0,
                        cursorEmails.getInt(9) != 0,
                        cursorEmails.getInt(10) != 0,
                        cursorEmails.getInt(11) != 0,
                        cursorEmails.getString(12),
                        cursorEmails.getInt(13) != 0
                    )
                )
            } while (cursorEmails.moveToNext())
        }
        cursorEmails.close()
        return emailModelArrayList
    }

    fun existEmails(): Boolean {
        val db = this.readableDatabase
        val cursorEmails: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        var existEmails = cursorEmails.count > 0
        cursorEmails.close()

        return existEmails
    }
}