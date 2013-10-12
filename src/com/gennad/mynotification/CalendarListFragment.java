package com.gennad.mynotification;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class CalendarListFragment extends ListFragment implements LoaderCallbacks<Cursor>    {
	SimpleCursorAdapter mAdapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);	
		getLoaderManager().initLoader(0, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity(), 
				android.R.layout.simple_list_item_2, 
				null, //cursor 
				new String[] {Calendars.ACCOUNT_NAME, Calendars.CALENDAR_DISPLAY_NAME},
				new int[] { android.R.id.text1,  android.R.id.text2,}, 0); 
		setListAdapter(mAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				Calendars.CONTENT_URI, 
				null, null, null, null);		
		return cursorLoader;
	}
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);		
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		EventListFragment eventListFragment = new EventListFragment();
		Bundle args = new Bundle();   
		args.putLong("calendarId", id);
		eventListFragment.setArguments(args);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.layout_container, eventListFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
	
} 
