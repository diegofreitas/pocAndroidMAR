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

import java.util.List;

import poc.tracexpenses.domain.bo.CategoryBo;
import poc.tracexpenses.domain.bo.InvalidDataException;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.bo.event.OnCategoriesLoadedEvent;
import poc.tracexpenses.domain.bo.event.OnTransactionSavedEvent;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.transactions.presenter.TransactionDetailPresenter;
import poc.tracexpenses.transactions.view.TransactionDetailView;
import poc.tracexpenses.util.View;
import roboguice.event.EventThread;
import roboguice.event.Observes;
import android.os.Bundle;

import com.google.inject.Inject;

public class TransactionDetailPresenterImpl implements
	TransactionDetailPresenter {

    @Inject
    private TransactionsBo trasactionsBo;
    private TransactionDetailView view;

    @Inject
    private CategoryBo categoriesBo;
    private Bundle transactionData;

    @Override
    public void setView(View transactionsActivity) {
	this.view = (TransactionDetailView) transactionsActivity;
	transactionData = view.getBundle();

    }

    @Override
    public void performSaveClick() {
	Transaction transaction = validateForm();

	try {
	    if (transaction != null) {
		if (transactionData != null) {
		    int id = transactionData.getInt("id");
		    transaction.id = id;
		}
		trasactionsBo.saveTransaction(transaction);
	    }

	} catch (InvalidDataException e) {
	    // TODO: Deveria ter um tratamento bonito.. mostrando um toast ou
	    // algo do tipo...
	}

    }

    private Transaction validateForm() {
	Transaction transaction = new Transaction();

	if ("".equals(view.getAmountValue())
		|| (transaction.amount = Double.valueOf(view.getAmountValue())) == 0) {
	    view.invalidateAmountField("Amount is required");
	    return null;
	}

	if ("".equals((transaction.item = view.getItemValue()))) {
	    view.invalidateItemField("Item is required");
	    return null;
	}

	transaction.category = view.getCategoryValue();
	transaction.type = view.getType();
	transaction.createdAt = view.getCreatedAtValue();
	return transaction;
    }

    public void onTransactionSaved(@Observes(EventThread.UI) OnTransactionSavedEvent event) {
	this.view.close();
	this.view.showMessage("Transaction Saved");
    }

    @Override
    public void loadCategories() {
	categoriesBo.getCategories();
    }

    @SuppressWarnings("unchecked")
    public void onCategoriesLoaded(@Observes(EventThread.UI) OnCategoriesLoadedEvent event) {
	this.view.loadCategoriesSpinner((List<Category>) event.data);
	if (transactionData != null) { 
	    this.fillForm(transactionData);
	}
    }

    @Override
    public void onCategorySelected(Category item) {
	if (transactionData == null) {
	    this.view.setTypeSelected(item.type);
	}else{
	    this.view.setTypeSelected(TransactionType.valueOf(transactionData.getString("type")));
	}
	
    }

    private void fillForm(Bundle transactionData) {
	view.setItemValue(transactionData.getString("item"));
	this.view.setAmountValue(transactionData.getDouble("amount"));
	this.view.setCategorySelected(transactionData.getInt("category"));
	view.setCreatedAtValue(transactionData.getInt("day"),
		transactionData.getInt("month") - 1,
		transactionData.getInt("year"));
    }

}
