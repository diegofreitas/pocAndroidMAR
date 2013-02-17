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

import android.app.Application;
import android.content.Context;

public class TracExpensesApplication extends Application {

    private static TracExpensesApplication instance;
    
    public static TracExpensesApplication getInstance() {
	return instance;
    }
    
    private static ThreadLocal<Context> currentThread = new ThreadLocal<Context>();
    
    public static void setContext(Context context){
	currentThread.set(context);
    }
    
    public static Context getContext(){
	return currentThread.get();
    }
    
    @Override
    public void onCreate() {
	super.onCreate();
	instance = this;
    }

}
