package com.example.fishselector;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnQueryTextListener {

	TextView txt;
	Fishes fishes = new Fishes();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		txt = new TextView(this);
		txt.setPadding(10, 10, 10, 10);
		txt.setText("WTF");
		setContentView(txt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// Associate searchable configuration with the SearchVie
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		newText = newText.isEmpty() ? "" : "Query so far: " + newText;
		txt.setText(newText);
		txt.setTextColor(Color.GREEN);

		return true;

	}

	@Override
	// if database isnt recognized reinstall app
	public boolean onQueryTextSubmit(String query) {
		txt.setText("Searching for: " + query + "...");
		txt.setTextColor(Color.RED);

		DB db = new DB(this);
		try {
			db.createDataBase();
			Log.d(STORAGE_SERVICE, "this passed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.openDataBase();
		Log.d(STORAGE_SERVICE, "this passed");
		SQLiteDatabase qdb = db.getReadableDatabase();
		Log.d(STORAGE_SERVICE, "this passed");
		Cursor recordset2 = qdb.rawQuery(
				"SELECT * FROM fishes where name like '%" + query + "%'", null);

		String results = "";

		while (recordset2.moveToNext()) {
			results += recordset2.getString(1) + " has a "
					+ recordset2.getString(2) + " rating" + "\n";
		}

		txt.setText(results);
		return true;
	}

}
