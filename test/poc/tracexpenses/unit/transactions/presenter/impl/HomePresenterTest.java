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

import poc.tracexpenses.IntegrationTestRunner;
import poc.tracexpenses.main.presenter.HomePresenter;
import poc.tracexpenses.main.presenter.impl.HomePresenterImpl;
import poc.tracexpenses.main.view.HomeView;
import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author diego.coronel@gmail.com
 *
 */
@RunWith(IntegrationTestRunner.class)
public class HomePresenterTest {

    @Mock
    HomeView view;

    HomePresenter presenter;

    @Before
    public void init() {
    	MockitoAnnotations.initMocks(this);

	presenter = new HomePresenterImpl();
	presenter.setView(view);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPerformNewTransaction() {
	//When
	presenter.performNewTransaction();
	
	//Then
	Mockito.verify( view, Mockito.times(1)).startActivity( (Class<? extends Activity>) Mockito.any()  , (Bundle) Mockito.any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPerformHistory() {
	//When
	presenter.performHistory();
	
	//Then
	Mockito.verify( view, Mockito.times(1)).startActivity( (Class<? extends Activity>) Mockito.any()  , (Bundle) Mockito.any());
    }

}
