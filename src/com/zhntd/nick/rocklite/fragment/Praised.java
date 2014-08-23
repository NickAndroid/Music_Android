package com.zhntd.nick.rocklite.fragment;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhntd.nick.rocklite.Project;
import com.zhntd.nick.rocklite.R;
import com.zhntd.nick.rocklite.RockLite;
import com.zhntd.nick.rocklite.loaders.TrackListAdapter;
import com.zhntd.nick.rocklite.modle.Track;
import com.zhntd.nick.rocklite.service.CoreService;
import com.zhntd.nick.rocklite.utils.QuerTools;

public class Praised extends Base {

	private ListView mListView;
	private TrackListAdapter mAdapter;

	private RockLite mActivity;
	private CoreService mServiceCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mListView = (ListView) inflater.inflate(R.layout.fragment_all, null);
		display();
		return mListView;
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (RockLite) activity;
		mServiceCallback = mActivity.getServiceCallback();
		Log.i("nick", mServiceCallback.toString());
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	void display() {
		new TrackLoaderTask().execute();
	}

	/**
	 * @return
	 */
	List<Track> getTracks() {
		return new QuerTools(getActivity()).getListFrmDataBase(
				Project.DB_PRAISED_NAME, Project.TB_PRAISED_NAME, 1,
				"TITLE DESC", false);
	}

	/**
	 * @param tracks
	 */
	void inflateListView(final List<Track> tracks) {

		if (mAdapter == null) {
			mAdapter = new TrackListAdapter(tracks, getActivity(),
					ImageLoader.getInstance());
			mListView.setAdapter(mAdapter);

			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					mServiceCallback.setupPLayList(tracks);
					mServiceCallback.setCurrentCursor(position);
					mServiceCallback.playTrack(position);

				}
			});
		} else {
			mAdapter.updateList(tracks);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					mServiceCallback.setupPLayList(tracks);
					mServiceCallback.setCurrentCursor(position);
					mServiceCallback.playTrack(position);

				}
			});
		}

	}

	final class TrackLoaderTask extends AsyncTask<Void, Void, List<Track>> {

		@Override
		protected List<Track> doInBackground(Void... arg0) {
			return getTracks();
		}

		@Override
		protected void onPostExecute(List<Track> result) {
			inflateListView(result);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onPraisedPressed() {
		new TrackLoaderTask().execute();
	}

}
