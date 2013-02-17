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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.transactions.presenter.TransactionsListPresenter;
import poc.tracexpenses.transactions.view.TransactionsListView;
import poc.tracexpenses.transactions.view.impl.TransactionActivity;
import poc.tracexpenses.transactions.view.impl.TransactionsActivity;
import poc.tracexpenses.util.db.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;
import com.xtremelabs.robolectric.matchers.StartedMatcher;
import com.xtremelabs.robolectric.util.DatabaseConfig.UsingDatabaseMap;
import com.xtremelabs.robolectric.util.SQLiteMap;

/**
 * 
 * @author diego.lins.freitas@gmail.com
 *
 */
@UsingDatabaseMap(SQLiteMap.class)
@RunWith(IntegrationTestRunner.class)
public class TransactionListPresenterImplTest {

    @Inject
    TransactionsListPresenter presenter;
    
    @Inject
    DBHelper dbHelper;

    @Test
    public void shouldLoadTransactions() {
	dbHelper.getWritableDatabase().execSQL("insert into transactions(_id,item,amount,category,type,created_at) values(1,'Sushi',100.0, 1,'EXPENSE','2013-02-01');");
	
	final List<Transaction> transactions = new ArrayList<Transaction>();
	TransactionsListView mock = new TransactionsListView() {
	   // @Override
	    public void startActivity(Class<? extends Activity> class1, Bundle extras) { }
	    @Override
	    public void updateBalance(String valueOf) {}
	    @Override
	    public void onLoadTransactions(List<Transaction> list) {
		transactions.addAll(list);
		
	    }
	    @Override
	    public void showMessage(String string) {
		// TODO Auto-generated method stub
		
	    }
	};
	

	presenter.setView(mock);
	
	presenter.loadTransactions();
	
	Assert.assertFalse(transactions.isEmpty());
	Transaction transaction = transactions.get(0);
	Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(transaction.id));
	
    }

    @Test
    public void shouldStartTransactioinDatailWithTransactionData() {
	dbHelper.getWritableDatabase().execSQL("insert into transactions(_id,item,amount,category,type,created_at) values(1,'Sushi',100.0, 1,'EXPENSE','2013-02-01');");
	
	TransactionsActivity transactionsActivity = new TransactionsActivity();

	int transactionId = 1;
	presenter.setView(transactionsActivity);
	presenter.performOpenTransation(transactionId);

	Intent bundle = new Intent(transactionsActivity,
		TransactionActivity.class);
	bundle.putExtra("amount", 100.0);
	bundle.putExtra("id", 1);
	bundle.putExtra("item", "Sushi");
	bundle.putExtra("type", TransactionType.EXPENSE.toString());
	bundle.putExtra("category", 1);
	bundle.putExtra("day", 1);
	bundle.putExtra("month", 2);
	bundle.putExtra("year", 2013);

	Assert.assertThat(transactionsActivity, new StartedMatcher(bundle));
    }

}
