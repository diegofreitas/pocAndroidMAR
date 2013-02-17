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

import poc.tracexpenses.R;
import poc.tracexpenses.domain.model.entity.TransactionType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

public class TransactionTypeAdapter extends BaseAdapter {

    private TransactionType[] types = TransactionType.values();

    @Inject
    LayoutInflater layoutInflater;

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return types.length;
    }

    @Override
    public Object getItem(int index) {
	// TODO Auto-generated method stub
	return types[index];
    }

    public int getItemPosition(TransactionType type) {
	for (int i = 0; i < types.length; i++) {
	    if (type == types[i]) {
		return i;
	    }
	}
	return 0;
    }

    @Override
    public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public View getView(int index, View v, ViewGroup vGroup) {
	CategoryViewHolder viewHolder;

	if (v == null) {
	    v = layoutInflater.inflate(R.layout.transaction_type_item, vGroup,
		    false);
	    viewHolder = new CategoryViewHolder();
	    viewHolder.txtType = (TextView) v.findViewById(R.id.txt_type);
	    viewHolder.imgType = (ImageView) v.findViewById(R.id.img_type);
	    v.setTag(viewHolder);
	} else {
	    viewHolder = (CategoryViewHolder) v.getTag();
	}

	TransactionType type = (TransactionType) getItem(index);
	if (type != null) {
	    viewHolder.txtType.setText(type.toString());// usar string.xml
	    viewHolder.imgType.setImageResource(type.resourceId);
	}
	return v;
    }

    static class CategoryViewHolder {
	ImageView imgType;
	TextView txtType;
    }
}
