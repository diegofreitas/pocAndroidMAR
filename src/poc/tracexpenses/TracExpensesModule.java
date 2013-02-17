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
package poc.tracexpenses;

import poc.tracexpenses.domain.bo.CategoryBo;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.bo.impl.CategoryBoImpl;
import poc.tracexpenses.domain.bo.impl.TransactionsBoImpl;
import poc.tracexpenses.domain.model.dao.CategoryDao;
import poc.tracexpenses.domain.model.dao.TransactionsDao;
import poc.tracexpenses.domain.model.dao.impl.CategoryDaoImpl;
import poc.tracexpenses.domain.model.dao.impl.TransactionsDaoImpl;
import poc.tracexpenses.main.presenter.HomePresenter;
import poc.tracexpenses.main.presenter.impl.HomePresenterImpl;
import poc.tracexpenses.transactions.presenter.TransactionDetailPresenter;
import poc.tracexpenses.transactions.presenter.TransactionsListPresenter;
import poc.tracexpenses.transactions.presenter.impl.TransactionDetailPresenterImpl;
import poc.tracexpenses.transactions.presenter.impl.TransactionsListPresenterImpl;

import com.google.inject.AbstractModule;

public class TracExpensesModule extends AbstractModule {

    @Override
    protected void configure() {
    	bind(TransactionDetailPresenter.class).to(TransactionDetailPresenterImpl.class);
    	bind(TransactionsListPresenter.class).to(TransactionsListPresenterImpl.class);
    	bind(HomePresenter.class).to(HomePresenterImpl.class);
    	
    	bind(TransactionsBo.class).to(TransactionsBoImpl.class);
    	bind(CategoryBo.class).to(CategoryBoImpl.class);
    
    	
    	bind(TransactionsDao.class).to(TransactionsDaoImpl.class);
    	bind(CategoryDao.class).to(CategoryDaoImpl.class);
    }
}
