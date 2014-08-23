package com.zhntd.nick.rocklite.loaders;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhntd.nick.rocklite.R;
import com.zhntd.nick.rocklite.modle.Track;
import com.zhntd.nick.rocklite.views.ViewHolderList;

/**
 * @author Nick
 * 
 */
public class TrackListAdapter extends BaseAdapter {

    private List<Track> mTracks;
    private Context mContext;
    private ViewHolderList mViewHolderList;
    // loader
    private ImageLoader mImageLoader;
    static String mArtworkUri = "content://media/external/audio/albumart";
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private boolean isListScrolling = false;

    public TrackListAdapter(List<Track> mTracks, Context mContext,
            ImageLoader imageLoader) {
        super();
        this.mTracks = mTracks;
        this.mContext = mContext;
        this.mImageLoader = imageLoader;
        initLoader();
    }

    /**
     * @param scrolling
     */
    public void isListScrolling(boolean scrolling) {
        this.isListScrolling = scrolling;
        if (!scrolling)
            notifyDataSetChanged();
    }

    public void updateList(List<Track> tracks) {
    	this.mTracks = tracks;
        this.notifyDataSetChanged();
    }

    void initLoader() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_artist)
                .showImageForEmptyUri(R.drawable.default_artist)
                .showImageOnFail(R.drawable.default_artist).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_list_item, null);
            mViewHolderList = new ViewHolderList(convertView);
            convertView.setTag(mViewHolderList);
        } else {
            mViewHolderList = (ViewHolderList) convertView.getTag();
        }
        // update list content
        Track track = mTracks.get(position);
        mViewHolderList.mTitleView.setText(track.getTitle());
        mViewHolderList.mArtistView.setText(track.getArtist());
        // art
        String uri = mArtworkUri + File.separator + track.getAlbumId();

        if (!isListScrolling)
            mImageLoader.displayImage(uri, mViewHolderList.mArtView, options,
                    animateFirstListener);

        return convertView;
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    /**
     * @author Nick
     * 
     */
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                FadeInBitmapDisplayer.animate(imageView, 1000);
                if (firstDisplay) {
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
