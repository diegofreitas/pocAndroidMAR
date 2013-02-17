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

import poc.tracexpenses.domain.model.entity.Category_;
import poc.tracexpenses.domain.model.entity.Transaction_;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper";
    public static final String DB_NAME = "tracex.db"; //
    public static final int DB_VERSION = 1; //

    // private Context context;

    @Inject
    public DBHelper(Application app) {
	super(app, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL("DROP TABLE IF EXISTS categories");
	db.execSQL("DROP TABLE IF EXISTS transactions");

	StringBuffer sql = new StringBuffer();
	sql.append("create table ")
		.append(Transaction_.TABLE)
		.append(" ( ")
		.append(Transaction_.ID
			+ " INTEGER  primary key AUTOINCREMENT , ")
		.append(Transaction_.CREATED_AT + " text, ")
		.append(Transaction_.ITEM + " text, ")
		.append(Transaction_.AMOUNT + " double, ")
		.append(Transaction_.TYPE + " text, ")
		.append(Transaction_.CATEGORY + " INTEGER , ")
		.append("foreign key(" + Transaction_.CATEGORY
			+ ") references " + Category_.TABLE + "("
			+ Category_.ID + ")").append(" );"); //
	db.execSQL(sql.toString());

	sql = new StringBuffer();
	sql.append("create table ")
		.append(Category_.TABLE)
		.append(" ( ")
		.append(Category_.ID + " INTEGER  primary key AUTOINCREMENT , ")
		.append(Category_.NAME + " text, ")
		.append(Category_.TYPE + " text ")

		.append(" );"); //
	db.execSQL(sql.toString());

	db.execSQL("insert into categories(name,type) values('Salary','INCOME');");
	db.execSQL("insert into categories(name,type) values('Entreteiment','EXPENSE');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	onCreate(db);
    }
}
