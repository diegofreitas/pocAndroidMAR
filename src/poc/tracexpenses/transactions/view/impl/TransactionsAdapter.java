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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

public class TransactionsAdapter extends BaseAdapter {

    private List<Transaction> transaction;

    @Inject
    LayoutInflater layoutInflater;

    public void setData(List<Transaction> transactions) {
	this.transaction = transactions;
    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return transaction.size();
    }

    @Override
    public Object getItem(int index) {
	// TODO Auto-generated method stub
	return transaction.get(index);
    }

    @Override
    public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public View getView(int index, View v, ViewGroup vGroup) {
	TransactionsViewHolder viewHolder;

	if (v == null) {
	    v = layoutInflater.inflate(R.layout.transaction_row, vGroup, false);
	    viewHolder = new TransactionsViewHolder();
	    viewHolder.imgTran = (ImageView) v
		    .findViewById(R.id.img_transaction);
	    viewHolder.txtItem = (TextView) v.findViewById(R.id.txt_item);
	    viewHolder.txtCategory = (TextView) v
		    .findViewById(R.id.txt_category);
	    viewHolder.txtAmount = (TextView) v.findViewById(R.id.txt_amount);
	    v.setTag(viewHolder);
	} else {
	    viewHolder = (TransactionsViewHolder) v.getTag();
	}

	Transaction transaction = (Transaction) getItem(index);
	if (transaction != null) {
	    viewHolder.txtItem.setText(transaction.item);
	    viewHolder.imgTran.setImageResource(transaction.type.resourceId);
	    viewHolder.txtAmount.setText(String.valueOf(transaction.amount));
	    viewHolder.txtCategory.setText(transaction.category.name);
	}
	return v;
    }

    static class TransactionsViewHolder {
	ImageView imgTran;
	TextView txtItem;
	TextView txtCategory;
	TextView txtAmount;

    }
}
