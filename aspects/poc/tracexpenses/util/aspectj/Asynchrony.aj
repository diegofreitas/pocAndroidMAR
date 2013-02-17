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
import poc.tracexpenses.util.Event;
import poc.tracexpenses.util.aspects.Asynchronous;
import roboguice.RoboGuice;
import roboguice.event.EventListener;
import roboguice.event.EventManager;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author diego.lins.freitas@gmail.com
 *
 */
public aspect Asynchrony {


    

    
    //private Context contex;

    // execution(* poc.tracexpenses..*(..))&&
    // !within(poc.tracexpenses.util.aspectj.*);
    pointcut asynchronousMethodCall(Asynchronous asynchronous): 
    	  execution(@Asynchronous * *(..))&&  @annotation(asynchronous) && !within(poc.tracexpenses.util.aspectj.*);

    Object around(final Asynchronous asynchronous) : asynchronousMethodCall(asynchronous) {

								//get the current context(the running activity
	final EventManager evtManager = RoboGuice.getInjector(TracExpensesApplication.getContext()).getInstance(EventManager.class);
	
	
	

	final AsyncTask<Object, Object, Object> asynctask = new AsyncTask<Object, Object, Object>() {
	    @Override
	    protected Object doInBackground(Object... params) {
		Object result = proceed(asynchronous);
		return result;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		Class<? extends Event> post = asynchronous.post();
		try {
		    Log.i("ASYNC",result.toString());
		    Event newInstance = post.newInstance();
		    newInstance.data = result;
		    evtManager.fire(newInstance);
		} catch (IllegalAccessException e) {
		    throw new RuntimeException(e);
		} catch (InstantiationException e) {
		    throw new RuntimeException(e);
		}
	    }
	};
	//TODO Test this
	if(asynchronous.cancel() != null && !Event.class.equals(asynchronous.cancel())){
	    evtManager.registerObserver(asynchronous.cancel(), new EventListener() {
		@Override
		public void onEvent(Object arg0) {
		    asynctask.cancel(false);
		}
	    });
	}
	
	asynctask.execute();
	return null;
    }

}
