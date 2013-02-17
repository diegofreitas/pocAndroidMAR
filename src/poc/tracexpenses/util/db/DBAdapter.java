/*
 * Copyright 2013 Diego Lins de Freitas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package poc.tracexpenses.util.db;

import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DBAdapter {

    @Inject
    private DBHelper dbHelper;

    private SQLiteDatabase db;

    private final Object transactionLock = new Object();

    public SQLiteDatabase beginTransaction() {
	synchronized (DBAdapter.class) {
	    if (db == null) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		db.execSQL("PRAGMA foreign_keys = ON;");

	    }
	    return db;
	}
    }

    public void endTransaction() {
	synchronized (transactionLock) {
	    if (db != null && !db.isOpen())
		return;
	    try {
		db.execSQL("PRAGMA foreign_keys = OFF;");
		db.setTransactionSuccessful();
	    } finally {
		db.endTransaction();
		db.close();
		db = null;
	    }

	}
    }

    public SQLiteDatabase getDatabase() {
	return db;
    }
}
