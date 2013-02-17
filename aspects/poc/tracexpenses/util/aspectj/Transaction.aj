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
package poc.tracexpenses.util.aspectj;

import poc.tracexpenses.TracExpensesApplication;
import poc.tracexpenses.util.aspects.Transactional;
import poc.tracexpenses.util.db.DBAdapter;
import roboguice.RoboGuice;

import com.google.inject.Injector;


/**
 * 
 * @author diego.lins.freitas@gmail.com
 *
 */
public aspect Transaction {

    private Injector injector = RoboGuice.getInjector(TracExpensesApplication
	    .getInstance());

    private DBAdapter dbAdapter;
    
    declare precedence : Asynchrony, Transaction ;

    // execution(* poc.tracexpenses..*(..))&&
    // !within(poc.tracexpenses.util.aspectj.*);
    pointcut transactionalMethodCall(Transactional transactional): 
    	  execution(@Transactional * *(..))&&  @annotation(transactional) && !within(poc.tracexpenses.util.aspectj.*);

    Object around(Transactional transactional) : transactionalMethodCall(transactional) {

	if (dbAdapter == null) {
	    dbAdapter = injector.getInstance(DBAdapter.class);
	}
	synchronized (dbAdapter) {
	    try {
		dbAdapter.beginTransaction();
		return proceed(transactional);
	    } finally {
		dbAdapter.endTransaction();
	    }
	}

    }

}
