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
package poc.tracexpenses.domain.bo.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import poc.tracexpenses.domain.bo.InvalidDataException;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.bo.event.OnBalanceLoadedEvent;
import poc.tracexpenses.domain.bo.event.OnGetTotalBalanceEvent;
import poc.tracexpenses.domain.bo.event.OnTransactionSavedEvent;
import poc.tracexpenses.domain.bo.event.OnTransactionsLoadedEvent;
import poc.tracexpenses.domain.model.dao.TransactionsDao;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.util.aspects.Asynchronous;
import poc.tracexpenses.util.aspects.Transactional;
import roboguice.event.EventManager;
import android.os.Bundle;

import com.google.inject.Inject;

public class TransactionsBoImpl implements TransactionsBo {

    @Inject
    private TransactionsDao transactionsDao;

    @Inject
    private EventManager evtMg;

    /*
     * @Inject private Handler uiHanlder;
     */
    @Override
    @Asynchronous(post = OnTransactionsLoadedEvent.class)
    public List<Transaction> getTransactions() {
	return transactionsDao.getTransactions();
    }

    @Override
    @Asynchronous(post = OnTransactionSavedEvent.class)
    public void saveTransaction(Transaction transaction)
	    throws InvalidDataException {
	if (transaction == null) {
	    throw new InvalidDataException("Transaction is null");
	}
	transactionsDao.saveTransaction(transaction);
    }

    @Override
    @Asynchronous(post = OnBalanceLoadedEvent.class)
    public BigDecimal getTotalBalance() {
	BigDecimal expensesTotal = transactionsDao.getExpenseTotal();
	evtMg.fire(new OnGetTotalBalanceEvent("Got total expenses"));
	BigDecimal incomesTotal = transactionsDao.getIncomesTotal();
	evtMg.fire(new OnGetTotalBalanceEvent("Got total incomes"));
	return incomesTotal.subtract(expensesTotal);
    }

    @Override
    public Bundle getTransactionAsBundle(int transactionId) {
	Transaction tx = transactionsDao.getTransactionById(transactionId);
	Bundle transactionData = new Bundle();
	transactionData.putInt("id", tx.id);
	transactionData.putString("item", tx.item);
	transactionData.putDouble("amount", tx.amount);
	transactionData.putString("type", tx.type.toString());
	transactionData.putInt("category", tx.category.id);
	transactionData.putInt("day", tx.createdAt.get(Calendar.DAY_OF_MONTH));
	transactionData.putInt("month", tx.createdAt.get(Calendar.MONTH) + 1);
	transactionData.putInt("year", tx.createdAt.get(Calendar.YEAR));

	return transactionData;
    }

}
