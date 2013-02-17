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

import java.util.Calendar;
import java.util.List;

import poc.tracexpenses.R;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.transactions.presenter.TransactionDetailPresenter;
import poc.tracexpenses.transactions.view.TransactionDetailView;
import poc.tracexpenses.util.DefaultView;
import roboguice.activity.RoboActivity;
import roboguice.activity.event.OnCreateEvent;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.activity_transaction)
public class TransactionActivity extends DefaultView implements
	TransactionDetailView, OnItemSelectedListener {

    @Inject
    private TransactionDetailPresenter presenter;

    @Inject
    private CategoriesAdapter categoriesAdapter;

    @Inject
    private TransactionTypeAdapter transactionTypeAdapter;

    @InjectView(R.id.spn_type)
    private Spinner spnType;

    @InjectView(R.id.edt_item)
    private EditText edtItem;

    @InjectView(R.id.edt_amount)
    private EditText edtAmount;

    @InjectView(R.id.spn_category)
    private Spinner spnCategory;

    @InjectView(R.id.dtp_createdAt)
    private DatePicker dtpCreatedAt;

    public void onCreateObserver(@Observes final OnCreateEvent createEvent) {
	presenter.setView(this);
	spnType.setAdapter(transactionTypeAdapter);
	presenter.loadCategories();
    }

    public void saveClickListener(View v) {
	presenter.performSaveClick();
    }

   /* @Override
    public void startActivity(Class<? extends Activity> class1, Bundle extras) {

    }*/

    @Override
    public String getItemValue() {
	return edtItem.getEditableText().toString();
    }

    @Override
    public String getAmountValue() {
	return edtAmount.getEditableText().toString();
    }

    @Override
    public void close() {
	this.finish();
    }

    @Override
    public void showMessage(String string) {
	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadCategoriesSpinner(List<Category> data) {
	categoriesAdapter.setData(data);
	spnCategory.setOnItemSelectedListener(this);
	spnCategory.setAdapter(categoriesAdapter);
    }

    @Override
    public Category getCategoryValue() {
	return (Category) spnCategory.getSelectedItem();
    }

    @Override
    public TransactionType getType() {
	return (TransactionType) spnType.getSelectedItem();
    }

    @Override
    public Calendar getCreatedAtValue() {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.DAY_OF_MONTH, dtpCreatedAt.getDayOfMonth());
	calendar.set(Calendar.MONTH, dtpCreatedAt.getMonth());
	calendar.set(Calendar.YEAR, dtpCreatedAt.getYear());
	return calendar;
    }

    public void cancelClickListener(View v) {
	this.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
	    long id) {
	presenter.onCategorySelected((Category) categoriesAdapter
		.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void setTypeSelected(TransactionType type) {
	spnType.setSelection(transactionTypeAdapter.getItemPosition(type));
    }

    @Override
    public void invalidateItemField(String string) {
	edtItem.setError(string);
    }

    @Override
    public void invalidateAmountField(String string) {
	edtAmount.setError(string);
    }

    @Override
    public void setAmountValue(double amount) {
	edtAmount.setText(String.valueOf(amount));
    }

    @Override
    public Bundle getBundle() {
	// TODO Auto-generated method stub
	return getIntent().getExtras();
    }

    @Override
    public void setItemValue(String string) {
	edtItem.setText(string);
    }

    @Override
    public void setCreatedAtValue(int dayOfMonth, int monthOfYear, int year) {
	dtpCreatedAt.init(year, monthOfYear, dayOfMonth, null);
    }

    @Override
    public void setCategorySelected(int id) {
	spnCategory.setSelection(categoriesAdapter.getItemPosition(id));
    }

}
