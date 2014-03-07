package de.hypoport;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import de.hypoport.activity.*;

public class MainActivity extends ListActivity
  {

	private static final int REQUEST_CHOPATREE = 1;
	
    /**
     * The collection gets instantiated in onCreate(android.os.Bundle)
     * because the MainMenuItem constructor needs access to android.content.res.Resources
     */
    private static MainMenuItem[] mMainMenuItems;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMainMenuItems = new MainMenuItem[]{
			new MainMenuItem(R.string.menu_chopatree, ChopATreeActivity.class)
		  };

        setListAdapter(new ArrayAdapter<MainMenuItem>(this,
													  android.R.layout.simple_list_item_1,
													  android.R.id.text1,
													  mMainMenuItems));
	  }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        // Launch the sample associated with this list position.
		try {
			startActivityForResult(new Intent(MainActivity.this, mMainMenuItems[position].activityClass), REQUEST_CHOPATREE);
		  }
		catch (Exception e) {

		  }
	  }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CHOPATREE) {
			if (resultCode == RESULT_OK) {
				if (data != null) {
					//TODO: Aktion verarbeiten
					showToastMessage("Holz verfügbar");
				  }
			  }
		  }		
	  }

	private class MainMenuItem
	  {
        private CharSequence title;
        private Class<? extends Activity> activityClass;

        public MainMenuItem(int titleResId, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = getResources().getString(titleResId);
		  }

        @Override
        public String toString() {
            return title.toString();
		  }
	  }

	private void showToastMessage(int id) {
		showToastMessage(getString(id));
	  }

	private void showToastMessage(String message) {
		System.out.println(message); //FIXME: Das hier ist nur zum Spielen :)
		Toast toast = Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG);
		toast.show();
	  }

  }
