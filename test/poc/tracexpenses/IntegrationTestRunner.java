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

import java.io.File;

import org.junit.runners.model.InitializationError;

import roboguice.RoboGuice;
import roboguice.config.DefaultRoboModule;
import roboguice.inject.RoboInjector;
import roboguice.test.RobolectricRoboTestRunner;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricConfig;


/**
 * 
 * @author joelsnob@gmail.com
 *
 */
public class IntegrationTestRunner extends RobolectricRoboTestRunner {
	
	


	public IntegrationTestRunner(@SuppressWarnings("rawtypes") Class testClass) throws InitializationError {
		super(testClass, 
				new RobolectricConfig(new File( "../pocroboguice/AndroidManifest.xml"), 
				new File("../pocroboguice/res")));
	}

	/**
	 * Classe utilizada para adicionar todos os providers relativos as Activitys.
	 */
	public static class TestModule extends AbstractModule {
		@Override
		protected void configure() {
			//bind(DBHelper.class).toProvider(DBHelperProvider.class).in(ContextSingleton.class);
		}
	}
	
	@Override
	public void prepareTest(Object test) {
		super.prepareTest(test);
		
		TestModule module = new TestModule();
		
		DefaultRoboModule roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
		Module productionModule = Modules.override(roboGuiceModule).with(new TracExpensesModule());
		Module testModule = Modules.override(productionModule).with(module);
		RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, testModule);
		RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
		injector.injectMembers(test);
	}
}
