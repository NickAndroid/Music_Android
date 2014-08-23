package com.zhntd.nick.rocklite.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class RelativeImageWare extends RelativeLayout implements ImageAware {

	public RelativeImageWare(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RelativeImageWare(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RelativeImageWare(Context context) {
		super(context);
	}

	@Override
	public ViewScaleType getScaleType() {
		
		return ViewScaleType.CROP;
	}

	@Override
	public View getWrappedView() {
		return null;
	}

	@Override
	public boolean isCollected() {
		return false;
	}

	@Override
	public boolean setImageDrawable(Drawable drawable) {
		if (drawable != null) {
			setBackground(drawable);
			return true;
		}
		return false;
	}

	@Override
	public boolean setImageBitmap(Bitmap bitmap) {
		return false;
	}

}
