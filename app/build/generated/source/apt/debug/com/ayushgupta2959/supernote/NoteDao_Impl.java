package com.ayushgupta2959.supernote;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllNotes;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `note_table`(`id`,`title`,`description`,`priority`,`image`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        stmt.bindLong(4, value.getPriority());
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindBlob(5, value.getImage());
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `note_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `note_table` SET `id` = ?,`title` = ?,`description` = ?,`priority` = ?,`image` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        stmt.bindLong(4, value.getPriority());
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindBlob(5, value.getImage());
        }
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllNotes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM note_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Note note) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfNote.insert(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Note note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Note note) {
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllNotes() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllNotes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllNotes.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Note>> getAllNodes() {
    final String _sql = "SELECT * FROM note_table ORDER BY priority DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"note_table"}, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final byte[] _tmpImage;
            _tmpImage = _cursor.getBlob(_cursorIndexOfImage);
            _item = new Note(_tmpTitle,_tmpDescription,_tmpPriority,_tmpImage);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
