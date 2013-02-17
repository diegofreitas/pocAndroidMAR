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
package poc.tracexpenses.domain.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import poc.tracexpenses.domain.model.dao.TransactionsDao;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Category_;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.domain.model.entity.Transaction_;
import poc.tracexpenses.util.DateUtil;
import poc.tracexpenses.util.aspects.Transactional;
import poc.tracexpenses.util.db.DBAdapter;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

public class TransactionsDaoImpl implements TransactionsDao {

    @Inject
    private DBAdapter dbAdapter;

    /*
     * (non-Javadoc)
     * 
     * @see poc.tracexpenses.model.ITransactionsDao#getTransactions()
     */
    @Override
    @Transactional
    public List<Transaction> getTransactions() {
	SQLiteDatabase database = dbAdapter.getDatabase();

	String tables = Transaction_.TABLE + " INNER JOIN " + Category_.TABLE
		+ " ON " + Transaction_.CATEGORY + " = " + Category_.FULL_ID;

	String orderBy = Transaction_.CREATED_AT + " DESC";
	Cursor cursor = database.query(tables, new String[] { "transactions.*",
		"categories._id as cat_id", "categories.name as cat_name" },
		null, null, null, null, orderBy);
	ArrayList<Transaction> result = cursorToList(cursor);
	return result;
    }

    private ArrayList<Transaction> cursorToList(Cursor cursor) {
	ArrayList<Transaction> result = new ArrayList<Transaction>(
		cursor.getCount());
	while (cursor.moveToNext()) { //
	    result.add(toObject(cursor));
	}
	cursor.close();
	return result;
    }

    @Override
    @Transactional
    public void saveTransaction(Transaction transaction) {
	if (transaction.id != 0) {
	    dbAdapter.getDatabase().update(Transaction_.TABLE,
		    toContentValues(transaction), " _id =" + transaction.id,
		    null);
	} else {
	    dbAdapter.getDatabase().insert(Transaction_.TABLE, null,
		    toContentValues(transaction));
	}
    }

    private Transaction toObject(Cursor cursor) {
	Transaction transaction = new Transaction();
	transaction.item = cursor.getString(cursor
		.getColumnIndex(Transaction_.ITEM));
	transaction.amount = cursor.getDouble(cursor
		.getColumnIndex(Transaction_.AMOUNT));
	transaction.createdAt = DateUtil.stringToCallendar(cursor
		.getString((cursor.getColumnIndex(Transaction_.CREATED_AT))));
	transaction.id = cursor.getInt(cursor.getColumnIndex(Transaction_.ID));
	transaction.type = TransactionType.valueOf(cursor.getString(cursor
		.getColumnIndex(Transaction_.TYPE)));
	transaction.category = getCategoryFromCursor(cursor);
	return transaction;
    }

    private Category getCategoryFromCursor(Cursor cursor) {
	Category category = new Category();
	category.id = cursor.getInt(cursor.getColumnIndex("cat_id"));
	category.name = cursor.getString(cursor.getColumnIndex("cat_name"));
	return category;
    }

    private ContentValues toContentValues(Transaction transaction) {
	ContentValues values = new ContentValues();
	values.put(Transaction_.ITEM, transaction.item);
	values.put(Transaction_.AMOUNT, transaction.amount);
	values.put(Transaction_.CREATED_AT,
		DateUtil.callendarToString(transaction.createdAt));
	values.put(Transaction_.TYPE, transaction.type.toString());
	values.put(Transaction_.CATEGORY, transaction.category.id);
	return values;
    }

    @Override
    @Transactional
    public BigDecimal getExpenseTotal() {
	BigDecimal expenseTotal = BigDecimal.ZERO;
	SQLiteDatabase database = dbAdapter.getDatabase();

	Cursor cursor = database.rawQuery(String.format(
		"SELECT SUM(%s) FROM %s WHERE %s = '%s'", Transaction_.AMOUNT,
		Transaction_.TABLE, Transaction_.TYPE,
		TransactionType.EXPENSE.toString()), null);

	if (cursor.moveToNext()) {
	    expenseTotal = BigDecimal.valueOf(cursor.getDouble(0));
	}
	cursor.close();
	return expenseTotal;
    }

    @Override
    @Transactional
    public BigDecimal getIncomesTotal() {
	BigDecimal expenseTotal = BigDecimal.ZERO;
	SQLiteDatabase database = dbAdapter.getDatabase();

	Cursor cursor = database.rawQuery(String.format(
		"SELECT SUM(%s) FROM %s WHERE %s = '%s'", Transaction_.AMOUNT,
		Transaction_.TABLE, Transaction_.TYPE,
		TransactionType.INCOME.toString()), null);

	if (cursor.moveToNext()) {
	    expenseTotal = BigDecimal.valueOf(cursor.getDouble(0));
	}
	cursor.close();
	return expenseTotal;
    }

    @Override
    @Transactional
    public Transaction getTransactionById(int transactionId) {
	SQLiteDatabase database = dbAdapter.getDatabase();
	String tables = Transaction_.TABLE + " INNER JOIN " + Category_.TABLE
		+ " ON " + Transaction_.CATEGORY + " = " + Category_.FULL_ID;
	String orderBy = Transaction_.CREATED_AT + " DESC";

	Cursor cursor = database.query(tables, new String[] { "transactions.*",
		"categories._id as cat_id", "categories.name as cat_name" },
		Transaction_.FULL_ID + " = ?",
		new String[] { String.valueOf(transactionId) }, null, null,
		orderBy);
	cursor.moveToNext();
	Transaction object = toObject(cursor);
	cursor.close();
	return object;
    }

}
