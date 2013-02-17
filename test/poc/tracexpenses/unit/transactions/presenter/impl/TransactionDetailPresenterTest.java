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

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.domain.bo.CategoryBo;
import poc.tracexpenses.domain.bo.InvalidDataException;
import poc.tracexpenses.domain.bo.TransactionsBo;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.domain.model.entity.Transaction;
import poc.tracexpenses.domain.model.entity.TransactionType;
import poc.tracexpenses.transactions.presenter.TransactionDetailPresenter;
import poc.tracexpenses.transactions.presenter.impl.TransactionDetailPresenterImpl;
import poc.tracexpenses.transactions.view.TransactionDetailView;
import android.os.Bundle;

/**
 * 
 * @author diego.coronel@gmail.com
 *
 */
@RunWith(IntegrationTestRunner.class)
public class TransactionDetailPresenterTest {

    TransactionDetailPresenter presenter;
    
    @Mock
    TransactionDetailView view = Mockito.mock(TransactionDetailView.class);
    
    @Mock
    TransactionsBo transactionsBo;
    
    @Mock
    CategoryBo categoryBo;

    @Before
    public void init() {
    	MockitoAnnotations.initMocks(this);

	presenter = new TransactionDetailPresenterImpl();
	presenter.setView(view);

	Whitebox.setInternalState(presenter, "trasactionsBo", transactionsBo);
	Whitebox.setInternalState(presenter, "categoriesBo", categoryBo);
    }

    @Test
    public void shouldPerformClick() throws InvalidDataException {
	// Given
	Mockito.when(view.getAmountValue()).thenReturn("1.1");
	Mockito.when(view.getItemValue()).thenReturn("Product A");
	Mockito.when(view.getCategoryValue()).thenReturn(new Category());

	Mockito.when(view.getType()).thenReturn(TransactionType.EXPENSE);
	Mockito.when(view.getCreatedAtValue()).thenReturn(
		new GregorianCalendar());

	// When
	presenter.performSaveClick();

	// Then
	Mockito.verify(transactionsBo, Mockito.times(1)).saveTransaction(
		(Transaction) Mockito.any());
    }

    
    @Test
    public void shouldNotSaveForMissingRequiredFields() throws InvalidDataException {
	// Given
	Mockito.when(view.getAmountValue()).thenReturn("");
	Mockito.when(view.getItemValue()).thenReturn("");
	Mockito.when(view.getCategoryValue()).thenReturn(new Category());

	Mockito.when(view.getType()).thenReturn(TransactionType.EXPENSE);
	Mockito.when(view.getCreatedAtValue()).thenReturn(
		new GregorianCalendar());

	// When
	presenter.performSaveClick();

	// Then

	Mockito.verify(transactionsBo, Mockito.never()).saveTransaction(
		(Transaction) Mockito.any());
	
	// TODO: Deveria verificar se chamou o Toast...	
	//fail( "Falta tratar quando nao conseguiu salvar..." );
    }

    @Test
    public void shouldLoadCategories() {
	// When
	presenter.loadCategories();

	// Then
	Mockito.verify(categoryBo, Mockito.times(1)).getCategories();
    }

    @Test
    public void shouldFireOnCategorySelected() {
	// Callback
	final TransactionType[] type = new TransactionType[1];

	// Training
	Mockito.doAnswer(new Answer<Void>() {
	    @Override
	    public Void answer(InvocationOnMock invocation) throws Throwable {
		type[0] = (TransactionType) invocation.getArguments()[0];
		return null;
	    }
	}).when(view).setTypeSelected((TransactionType) Mockito.any());

	// Given
	Category category = new Category();
	category.type = TransactionType.EXPENSE;

	// When
	presenter.onCategorySelected(category);

	// Then
	Assert.assertEquals(TransactionType.EXPENSE, type[0]);
    }
    
    /**
     * O usu�rio pode escolher um tipo de diferente do default da categoria.
     */
    @Test
    public void shouldLoadTypeBasedOnTransactionData(){
	final TransactionType[] type = new TransactionType[1];

	// Training
	Mockito.doAnswer(new Answer<Void>() {
	    @Override
	    public Void answer(InvocationOnMock invocation) throws Throwable {
		type[0] = (TransactionType) invocation.getArguments()[0];
		return null;
	    }
	}).when(view).setTypeSelected((TransactionType) Mockito.any());

	// Given
	Category category = new Category();
	category.type = TransactionType.EXPENSE;
	Bundle transactionData = new Bundle();
	transactionData.putString("type", TransactionType.INCOME.toString());
	Whitebox.setInternalState(presenter, "transactionData", transactionData );
	// When
	presenter.onCategorySelected(category);

	// Then
	Assert.assertEquals(TransactionType.INCOME, type[0]);
    }

}
