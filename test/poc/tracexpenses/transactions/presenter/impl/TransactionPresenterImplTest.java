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
package poc.tracexpenses.transactions.presenter.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.domain.bo.InvalidDataException;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.model.dao.TransactionsDao;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.transactions.presenter.TransactionDetailPresenter;
import poc.tracexpenses.transactions.view.TransactionDetailView;
import poc.tracexpenses.util.DateUtil;
import poc.tracexpenses.util.db.DBHelper;
import android.os.Bundle;

import com.google.inject.Inject;
import com.xtremelabs.robolectric.util.DatabaseConfig.UsingDatabaseMap;
import com.xtremelabs.robolectric.util.SQLiteMap;
/**
 * 
 * @author diego.lins.freitas@gmail.com
 *
 */
@UsingDatabaseMap(SQLiteMap.class)
@RunWith(IntegrationTestRunner.class)
public class TransactionPresenterImplTest {

    @Inject
    TransactionDetailPresenter presenter;

    @Inject
    DBHelper dbHelper;

    @Inject
    TransactionsDao dao;

    TransactionDetailView activity;

    @Test
    public void shouldFillFormOnActivityStartedWithData() {
	activity = Mockito.mock(TransactionDetailView.class);

	Bundle bundle = new Bundle();

	bundle.putInt("id", 1);
	bundle.putString("item", "Sushi");
	bundle.putDouble("amount", 100.0);
	bundle.putString("type", TransactionType.EXPENSE.toString());
	bundle.putInt("category", 1);
	bundle.putInt("day", 1);
	bundle.putInt("month", 2);
	bundle.putInt("year", 2013);

	Mockito.doReturn(bundle).when(activity).getBundle();

	presenter.setView(activity);
	presenter.loadCategories();
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Mockito.verify(activity, Mockito.times(1)).setAmountValue(100.0);
	Mockito.verify(activity, Mockito.times(1)).setItemValue("Sushi");
	Mockito.verify(activity, Mockito.never()).setTypeSelected(TransactionType.EXPENSE); //it is called in category change
	Mockito.verify(activity, Mockito.times(1)).setCategorySelected(1);
	Mockito.verify(activity, Mockito.times(1))
		.setCreatedAtValue(1, 1, 2013);

    }

    @Test
    @Ignore
    public void shouldSaveLoadedTransaction() {
	dbHelper.getWritableDatabase()
		.execSQL(
			"insert into transactions(_id,item,amount,category,type,created_at) values(1,'Sushi',100.0, 1,'EXPENSE','2013-02-01');");

	activity = Mockito.mock(TransactionDetailView.class);

	Transaction transaction = new Transaction();
	transaction.id = 1;
	transaction.item = "Sushi";
	transaction.amount = 100.0;
	transaction.type = TransactionType.EXPENSE;
	Category category = new Category();
	category.id = 1;
	transaction.category = category;
	transaction.createdAt = DateUtil.stringToCallendar("2013-02-01");

	Mockito.when(activity.getAmountValue()).thenReturn("200.0");
	Mockito.when(activity.getItemValue()).thenReturn("Vitaminada");
	Mockito.when(activity.getCreatedAtValue()).thenReturn(
		DateUtil.stringToCallendar("2013-02-02"));
	Mockito.when(activity.getCategoryValue()).thenReturn(category);
	Mockito.when(activity.getType()).thenReturn(TransactionType.INCOME);

	presenter.setView(activity);

	Whitebox.setInternalState(presenter, "currentTransaction", transaction);

	presenter.performSaveClick();

	// TODO HOW TO ASSERT THAT THE DATA WAS EDITED????
	// THIS INTEGRATED TESTS ARE HARD TO DO :(
	//Loading it from database? 
	
	/*
	 * String tables = Transaction_.TABLE + " INNER JOIN " + Category_.TABLE
	 * + " ON " + Transaction_.CATEGORY + " = " + Category_.FULL_ID; String
	 * orderBy = Transaction_.CREATED_AT + " DESC";
	 * 
	 * Cursor cursor = dbHelper.getWritableDatabase().query(tables,new
	 * String[] {"transactions.*","categories._id as cat_id",
	 * "categories.name as cat_name"}, Transaction_.FULL_ID +" = ?", new
	 * String[]{String.valueOf(1)}, null, null,orderBy);
	 * Assert.assertTrue("No transaction found",cursor.moveToNext());
	 * Assert.assertEquals(transaction.item,
	 * cursor.getString(cursor.getColumnIndex(Transaction_.ITEM)));
	 * Assert.assertEquals(transaction.amount,
	 * cursor.getString(cursor.getColumnIndex(Transaction_.AMOUNT)));
	 */
    }

    @Test
    public void shouldSaveLoadedTransaction2() throws InvalidDataException {

	// Given
	Transaction transaction = new Transaction();
	activity = Mockito.mock(TransactionDetailView.class);
	presenter.setView(activity);
	TransactionsBo transactionsBoMock = Mockito.mock(TransactionsBo.class);
	Whitebox.setInternalState(presenter, "trasactionsBo",
		transactionsBoMock);

	transaction.id = 1;
	transaction.item = "Sushi";
	transaction.amount = 100.0;
	transaction.type = TransactionType.EXPENSE;
	Category category = new Category();
	category.id = 1;
	transaction.category = category;
	transaction.createdAt = DateUtil.stringToCallendar("2013-02-01");

	// When
	Mockito.when(activity.getAmountValue()).thenReturn("200.0");
	Mockito.when(activity.getItemValue()).thenReturn("Vitaminada");
	Mockito.when(activity.getCreatedAtValue()).thenReturn(
		DateUtil.stringToCallendar("2013-02-02"));
	Mockito.when(activity.getCategoryValue()).thenReturn(category);
	Mockito.when(activity.getType()).thenReturn(TransactionType.INCOME);
	presenter.performSaveClick();

	// Then
	Mockito.verify(transactionsBoMock, Mockito.only()).saveTransaction(
		Matchers.any(Transaction.class));

    }
}
