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
package poc.tracexpenses.transactions.view;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;

import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.util.View;

public interface TransactionDetailView extends View {

    String getItemValue();

    String getAmountValue();

    void close();

    void showMessage(String string);

    void loadCategoriesSpinner(List<Category> data);

    Category getCategoryValue();

    TransactionType getType();

    Calendar getCreatedAtValue();

    void setTypeSelected(TransactionType expense);

    void invalidateItemField(String string);

    void invalidateAmountField(String string);

    void setAmountValue(double amount);

    Bundle getBundle();

    void setItemValue(String string);

    void setCreatedAtValue(int i, int j, int k);

    void setCategorySelected(int i);

}
