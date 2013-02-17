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
import poc.tracexpenses.domain.model.entity.Category;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

public class CategoriesAdapter extends BaseAdapter {

    private List<Category> categories;

    @Inject
    LayoutInflater layoutInflater;

    public int getItemPosition(int id) {
	for (int i = 0; i < categories.size(); i++) {
	    if (id == categories.get(i).id) {
		return i;
	    }
	}
	return -1;
    }

    public void setData(List<Category> categories) {
	this.categories = categories;
    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return categories.size();
    }

    @Override
    public Object getItem(int index) {
	// TODO Auto-generated method stub
	return categories.get(index);
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
	    v = layoutInflater.inflate(R.layout.category_item, vGroup, false);
	    viewHolder = new CategoryViewHolder();
	    viewHolder.txtName = (TextView) v.findViewById(R.id.txt_category);
	    v.setTag(viewHolder);
	} else {
	    viewHolder = (CategoryViewHolder) v.getTag();
	}

	Category category = (Category) getItem(index);
	if (category != null) {
	    viewHolder.txtName.setText(category.name);
	}
	return v;
    }

    static class CategoryViewHolder {
	TextView txtName;
    }
}
