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
package poc.tracexpenses.unit.transactions.presenter.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.transactions.presenter.TransactionsListPresenter;
import poc.tracexpenses.transactions.presenter.impl.TransactionsListPresenterImpl;
import poc.tracexpenses.transactions.view.TransactionsListView;

/**
 * 
 * @author diego.coronel@gmail.com
 *
 */
@RunWith(IntegrationTestRunner.class)
public class TransactionListPresenterTest {
    
    TransactionsListPresenter presenter;
    
    @Mock
    TransactionsListView view;
    
    @Mock
    TransactionsBo transactionsBo;
    
    @Before
    public void init() {
	MockitoAnnotations.initMocks(this);
	
	presenter = new TransactionsListPresenterImpl();
	presenter.setView(view);
	
	Whitebox.setInternalState(presenter, "transactionsBo", transactionsBo);
    }
    
    @Test
    public void shouldLoadTransactions() {
	//When
	presenter.loadTransactions();
	
	//Then
	Mockito.verify(transactionsBo, Mockito.atLeastOnce()).getTransactions();
    }
    
    @Test
    public void shouldLoadBalance() {
	//When
	presenter.loadBalance();
	
	//Then
	Mockito.verify(transactionsBo, Mockito.atLeastOnce()).getTotalBalance();
    }
    
    
}
