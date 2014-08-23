package com.zhntd.nick.rocklite.views;

import com.zhntd.nick.rocklite.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Nick
 * 
 */
public class ViewHolderList {

    public TextView mTitleView;
    public TextView mArtistView;
    public ImageView mArtView;

    public ViewHolderList(View rootView) {
        this.mTitleView = (TextView) rootView.findViewById(R.id.line_one);
        this.mArtistView = (TextView) rootView.findViewById(R.id.line_two);
        this.mArtView = (ImageView) rootView.findViewById(R.id.art);
    }
}
