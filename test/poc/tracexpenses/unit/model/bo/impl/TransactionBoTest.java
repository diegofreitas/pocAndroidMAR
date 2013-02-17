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
package poc.tracexpenses.unit.model.bo.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.domain.bo.InvalidDataException;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.bo.impl.TransactionsBoImpl;
import poc.tracexpenses.domain.model.dao.TransactionsDao;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import roboguice.event.EventManager;
import android.os.Bundle;

/**
 * 
 * @author diego.coronel@gmail.com
 *
 */
@RunWith(IntegrationTestRunner.class)
public class TransactionBoTest {

    TransactionsBo bo;

    @Mock
    TransactionsDao transactionsDao;

    @Before
    public void init() {
	MockitoAnnotations.initMocks(this);

	bo = new TransactionsBoImpl();
	
	Whitebox.setInternalState(bo, "evtMg", Mockito.mock(EventManager.class));

	Whitebox.setInternalState(bo, "transactionsDao", transactionsDao);
    }

    @Test
    public void shouldGetTransactions() {
	// When
	bo.getTransactions();

	// Then
	Mockito.verify(transactionsDao, Mockito.times(1)).getTransactions();
    }

    @Test
    public void shouldSaveNewTransaction() throws InvalidDataException {
	// Given
	Transaction entity = new Transaction();

	entity.amount = 2.3;
	entity.category = new Category();
	entity.createdAt = new GregorianCalendar();
	entity.item = "Product A";
	entity.type = TransactionType.EXPENSE;

	// When
	bo.saveTransaction(entity);

	// Then
	Mockito.verify(transactionsDao, Mockito.times(1)).saveTransaction((Transaction) Mockito.any());
    }
    
    @Test(expected = InvalidDataException.class)
    public void shouldNotSaveTransactionNull() throws InvalidDataException {
	bo.saveTransaction(null);
    }
    
    @Test
    public void shouldLoadBalance() {
	class Values {
	    BigDecimal income, expense, result;
	    public Values( BigDecimal in, BigDecimal exp, BigDecimal re ) {
		this.income = in;
		this.expense = exp;
		this.result = re;
	    }
	}
		
	Values[] values = new Values[] {
            new Values( new BigDecimal( 10.3 ), new BigDecimal( 2.2 ), 	new BigDecimal( 8.1 ) ),
            new Values( new BigDecimal( 7 ), 	new BigDecimal( 7 ), 	new BigDecimal( 0 ) ),
            new Values( new BigDecimal( 3.3 ), 	new BigDecimal( 5 ), 	new BigDecimal( -1.7 ) ),
            new Values( new BigDecimal( 0 ), 	new BigDecimal( 0 ), 	new BigDecimal( 0 ) ),
	};

	for ( Values v : values ) {
	    //Training
	    Mockito.when(transactionsDao.getExpenseTotal()).thenReturn( v.expense);
	    Mockito.when(transactionsDao.getIncomesTotal()).thenReturn(v.income);

	    //When
	    BigDecimal value = bo.getTotalBalance();

	    MathContext precision = new MathContext(2);
	    value = value.round(precision);

	    BigDecimal result = v.result.round(precision);

	    //Then
	    Assert.assertEquals( result, value);
	}
		
	Mockito.verify( transactionsDao, Mockito.times(values.length) ).getExpenseTotal();
	Mockito.verify( transactionsDao, Mockito.times(values.length) ).getIncomesTotal();
    }
    
    @Test
    public void shouldReturnTransaction() {
	//Given
	final int id = 1;

	Transaction tras = new Transaction();
	tras.amount = 1.56;
	tras.category = new Category();
	tras.createdAt = new GregorianCalendar();
	tras.item = "Item A";
	tras.type = TransactionType.INCOME;
	tras.id = id;

	Mockito.when( transactionsDao.getTransactionById(id) ).thenReturn( tras );

    	//When
	Bundle bd = bo.getTransactionAsBundle(id);
		
    	//Then
	Assert.assertEquals( 1.56, 	bd.get("amount") );
	Assert.assertEquals( "Item A",  bd.get("item") );
	Assert.assertEquals(1, 		bd.get("id"));
    }
    
}
