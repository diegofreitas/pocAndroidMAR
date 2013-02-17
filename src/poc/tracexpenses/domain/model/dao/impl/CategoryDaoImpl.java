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
package poc.tracexpenses.domain.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import poc.tracexpenses.domain.model.dao.CategoryDao;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Category_;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.util.aspects.Transactional;
import poc.tracexpenses.util.db.DBAdapter;
import android.database.Cursor;

import com.google.inject.Inject;

public class CategoryDaoImpl implements CategoryDao {

    @Inject
    private DBAdapter dbAdapter;

    @Override
    
    public List<Category> getCategories() {
	Cursor cursor = dbAdapter.getDatabase().query(Category_.TABLE,
		new String[] { Category_.ID, Category_.NAME, Category_.TYPE },
		null, null, null, null, null);
	ArrayList<Category> result = cursorToList(cursor);
	return result;
    }

    private ArrayList<Category> cursorToList(Cursor cursor) {
	ArrayList<Category> result = new ArrayList<Category>(cursor.getCount());
	while (cursor.moveToNext()) { //
	    result.add(toObject(cursor));
	}
	cursor.close();
	return result;
    }

    /*
     * @Override
     * 
     * @Trasnactional public void saveTransaction(Transaction transaction) {
     * dbAdapter.getDatabase().insert(Transaction_.TABLE, null,
     * toContentValues(transaction)); }
     */

    private Category toObject(Cursor cursor) {
	Category category = new Category();
	category.name = cursor.getString(cursor.getColumnIndex(Category_.NAME));
	category.id = cursor.getInt(cursor.getColumnIndex(Category_.ID));
	category.type = TransactionType.valueOf(cursor.getString(cursor
		.getColumnIndex(Category_.TYPE)));
	return category;
    }

    @Override
    public Category getById(int id) {
	// TODO Auto-generated method stub
	return null;
    }

    /*
     * private ContentValues toContentValues(Category category) { ContentValues
     * values = new ContentValues(); values.put(Category_.NAME, category.name);
     * //values.put(Transaction_.AMOUNT, category.amount); return values; }
     */

}
