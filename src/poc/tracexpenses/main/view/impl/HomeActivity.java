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
package poc.tracexpenses.main.view.impl;

import poc.tracexpenses.R;
import poc.tracexpenses.main.presenter.HomePresenter;
import poc.tracexpenses.main.view.HomeView;
import poc.tracexpenses.util.DefaultView;
import roboguice.activity.RoboActivity;
import roboguice.activity.event.OnCreateEvent;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.inject.Inject;

@ContentView(R.layout.activity_home)
public class HomeActivity extends DefaultView implements HomeView {

    @Inject
    private HomePresenter presenter;

    public void onCreateObserver(@Observes final OnCreateEvent createEvent) {
	presenter.setView(this);
    }


    public void newTransactionListener(View v) {
	presenter.performNewTransaction();
    }

    public void accountsClickListener(View v) {
	// TODO Auto-generated method stub

    }

    public void historyClickListener(View v) {
	presenter.performHistory();
    }

    public void statisticsClickListener(View v) {
	// TODO Auto-generated method stub

    }

}
