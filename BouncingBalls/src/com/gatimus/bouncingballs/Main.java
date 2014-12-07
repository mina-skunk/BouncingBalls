package com.gatimus.bouncingballs;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main extends Activity {
	
	private static final String TAG = "Main:";
	private Resources res;
	private FragmentManager fragMan;
	private DialogFragment about;
	private DialogFragment help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Create");
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
		res = getApplicationContext().getResources();
		fragMan = this.getFragmentManager();
		about = new About();
		help = new Help();
	} //onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "Create Options Menu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} //onCreateOptionsMenu

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, item.getTitle().toString() + " Option Selected");
		int id = item.getItemId();
		switch(id){
		case R.id.action_settings :
			Intent intent = new Intent(Main.this, Settings.class);
            startActivity(intent);
			break;
		case R.id.action_about :
			about.show(fragMan, res.getString(R.string.action_about));
			break;
		case R.id.action_help :
			help.show(fragMan, res.getString(R.string.action_help));
			break;
		case R.id.action_quit : System.exit(0);
			break;
		default : 
			break;
		}
		return super.onOptionsItemSelected(item);
	} //onOptionsItemSelected
	
	public class MyView extends View {
        public MyView(Context context) {
             super(context);
             // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
           // TODO Auto-generated method stub
           super.onDraw(canvas);
           int x = getWidth();
           int y = getHeight();
           int radius;
           radius = 100;
           Paint paint = new Paint();
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           canvas.drawPaint(paint);
           // Use Color.parseColor to define HTML colors
           paint.setColor(Color.parseColor("#CD5C5C"));
           canvas.drawCircle(x / 2, y / 2, radius, paint);
       }
    }
	
} //class
