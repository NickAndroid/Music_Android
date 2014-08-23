package com.zhntd.nick.rocklite.views;

import com.zhntd.nick.rocklite.fonts.FontsFactory;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Nick
 * 
 */
public class HeroTextView extends TextView {

	public HeroTextView(Context context) {
		super(context);
		setUseHero();
	}

	public HeroTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setUseHero();
	}

	public HeroTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setUseHero();
	}

	private void setUseHero() {
		setTypeface(FontsFactory.getHero(getContext()));
	}
}
