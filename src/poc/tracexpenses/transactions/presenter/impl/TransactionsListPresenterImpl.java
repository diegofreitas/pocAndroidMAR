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

import java.math.BigDecimal;
import java.util.List;

import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.bo.event.OnBalanceLoadedEvent;
import poc.tracexpenses.domain.bo.event.OnGetTotalBalanceEvent;
import poc.tracexpenses.domain.bo.event.OnTransactionsLoadedEvent;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.transactions.presenter.TransactionsListPresenter;
import poc.tracexpenses.transactions.view.TransactionsListView;
import poc.tracexpenses.transactions.view.impl.TransactionActivity;
import poc.tracexpenses.util.View;
import roboguice.event.EventManager;
import roboguice.event.EventThread;
import roboguice.event.Observes;
import android.os.Bundle;

import com.google.inject.Inject;

public class TransactionsListPresenterImpl implements TransactionsListPresenter {

    private TransactionsListView view;

    @Inject
    private TransactionsBo transactionsBo;

    public void loadTransactions() {
	transactionsBo.getTransactions();
    }

    @Override
    public void setView(View transactionsActivity) {
	this.view = (TransactionsListView) transactionsActivity;
    }

    @SuppressWarnings("unchecked")
    public void onTransactionsLoaded(
	    @Observes(EventThread.UI) OnTransactionsLoadedEvent event) {
	this.view.onLoadTransactions((List<Transaction>) event.data);
    }

    @Override
    public void loadBalance() {
	transactionsBo.getTotalBalance();
    }

    public void onBalanceLoadedListener(
	    @Observes(EventThread.UI) OnBalanceLoadedEvent event) {
	this.view.updateBalance(String.valueOf(((BigDecimal) event.data)
		.doubleValue()));
    }

    public void onGetTotalBalanceEventListener(
	    @Observes(EventThread.UI) OnGetTotalBalanceEvent event) {
	this.view.showMessage(event.data.toString());
    }

    @Override
    public void performOpenTransation(int transactionId) {
	Bundle transactionData = transactionsBo
		.getTransactionAsBundle(transactionId);
	view.startActivity(TransactionActivity.class, transactionData);
	
    }

}
