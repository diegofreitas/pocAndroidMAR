package poc.tracexpenses.util;

import poc.tracexpenses.TracExpensesApplication;
import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DefaultView extends RoboActivity implements View {
   
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	TracExpensesApplication.setContext(this); //this should be called before
        super.onCreate(savedInstanceState);
    }


    @Override
    public void startActivity(Class<? extends Activity> view, Bundle extras) {
	Intent intent = new Intent(this, view);
	if(extras != null){
	    intent.putExtras(extras);
	}
	this.startActivity(intent);
    }
    
   /* public void onStartActivityListener(@Observes OnStartActivityEvent event){
	Intent intent = new Intent(this,event.activity);
	if(event.bundle != null){
	    intent.putExtras(event.bundle);
	}
	this.startActivity(intent);
    }*/

}
