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
package poc.tracexpenses.transactions.view.impl;

import java.util.List;

import poc.tracexpenses.R;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.transactions.presenter.TransactionsListPresenter;
import poc.tracexpenses.transactions.view.TransactionsListView;
import poc.tracexpenses.util.DefaultView;
import roboguice.activity.RoboActivity;
import roboguice.activity.event.OnResumeEvent;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.activity_transactions)
public class TransactionsActivity extends DefaultView implements
	TransactionsListView, OnItemClickListener {

    @InjectView(R.id.lst_transactions)
    private ListView lstTransactions;

    @InjectView(R.id.txt_balance)
    private TextView txtBalance;

    @Inject
    private TransactionsListPresenter transactionsPresenter;

    @Inject
    private TransactionsAdapter adapter;

    public void onCreateObserver(@Observes final OnResumeEvent createEvent) {
	transactionsPresenter.setView(this);
	transactionsPresenter.loadTransactions();
	transactionsPresenter.loadBalance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * poc.tracexpenses.transactions.view.TransactionsView#onLoadTransactions
     * (poc.tracexpenses.TransactionsAdapter)
     */
    @Override
    public void onLoadTransactions(List<Transaction> transactions) {
	adapter.setData(transactions);
	lstTransactions.setAdapter(adapter);
	lstTransactions.setOnItemClickListener(this);
    }

 
    @Override
    public void updateBalance(String valueOf) {
	txtBalance.setText(valueOf);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
	    long arg3) {
	Transaction item = (Transaction) adapter.getItem(position);
	transactionsPresenter.performOpenTransation(item.id);
    }

    @Override
    public void showMessage(String string) {
	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

}
