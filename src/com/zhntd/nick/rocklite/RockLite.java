package com.zhntd.nick.rocklite;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dk.animation.SwitchAnimationUtil;
import com.dk.animation.SwitchAnimationUtil.AnimationType;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhntd.nick.rocklite.fragment.AllTracks;
import com.zhntd.nick.rocklite.fragment.Base;
import com.zhntd.nick.rocklite.fragment.MenuDrawer;
import com.zhntd.nick.rocklite.fragment.Praised;
import com.zhntd.nick.rocklite.service.CoreService;
import com.zhntd.nick.rocklite.service.CoreService.MyBinder;
import com.zhntd.nick.rocklite.service.CoreService.StateChangedListener;

public class RockLite extends FragmentActivity implements OnClickListener,
		StateChangedListener {

	private ServiceConnection mServiceConnection;
	private CoreService mCoreService;

	SectionsPagerAdapter mSectionsPagerAdapter;

	private ImageView mLocalImageView, mFavouriteImageView, mMoreImageView;
	private List<ImageView> mNaViews;

	private List<Base> mFragments;

	private Animation mAnimation, mAnimationFade;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	// control buttons
	private ImageButton mPlayButton, mNextButton, mPreviousButton,
			mPraiseButton;
	private TextView titleTextView;
	private ImageView mArtImageView;

	private RelativeLayout mRootLayout;

	// menus
	private MenuDrawer mNavigationDrawerFragment;
	private DrawerLayout mDrawerLayout = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		if (mRootLayout == null) {
			mRootLayout = (RelativeLayout) findViewById(R.id.root_layout);
		}
		
		styleActionBar();
		
		findControlButtons();
		
		initImageLoader(this);

		initPages();
		bindToService();
		startService();

		new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(),
				AnimationType.SCALE);

		initAnim();

		initDrawer();
		
	}

	@Override
	protected void onResume() {
		if (mCoreService != null)
			onPlayStateChanged();

		super.onResume();
	}

	private void initDrawer() {
		mNavigationDrawerFragment = (MenuDrawer) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// init drawerLayout
		mDrawerLayout = mNavigationDrawerFragment.mDrawerLayout;
	}

	// open or close the menu
	private void toogleDrawer() {

		if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else {
			mDrawerLayout.openDrawer(GravityCompat.START);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			toogleDrawer();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void initPager() {

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				updateNaviItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mViewPager.startAnimation(mAnimation);
	}

	private void initPages() {
		mFragments = new ArrayList<Base>();
		mFragments.add(new AllTracks());
		mFragments.add(new Praised());
	}

	private void styleActionBar() {
		// Set up the action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setCustomView(R.layout.cust_action_bar);

		mFavouriteImageView = (ImageView) findViewById(R.id.favour);
		mLocalImageView = (ImageView) findViewById(R.id.local);
		mMoreImageView = (ImageView) findViewById(R.id.more);

		mMoreImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toogleDrawer();
			}
		});

		mFavouriteImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1, true);
			}
		});

		mLocalImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0, true);
			}
		});

		mNaViews = new ArrayList<ImageView>();
		mNaViews.add(mLocalImageView);
		mNaViews.add(mFavouriteImageView);
	}

	private void updateNaviItem(int position) {

		for (int i = 0; i < mNaViews.size(); i++) {

			if (position != i)
				mNaViews.get(i).setBackground(
						getResources().getDrawable(R.drawable.pressed_to));
		}

		mNaViews.get(position).setBackground(
				getResources().getDrawable(R.drawable.seleted));

	}

	private void initAnim() {
		mAnimation = AnimationUtils.loadAnimation(this,
				R.anim.view_push_down_in);

		mAnimationFade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
	}

	private void findControlButtons() {
		mPlayButton = (ImageButton) findViewById(R.id.btn_play_local);
		mNextButton = (ImageButton) findViewById(R.id.btn_next_local);
		mPreviousButton = (ImageButton) findViewById(R.id.btn_pre_local);
		mPraiseButton = (ImageButton) findViewById(R.id.btn_praised);

		mPlayButton.setOnClickListener(this);
		mPreviousButton.setOnClickListener(this);
		mNextButton.setOnClickListener(this);
		// image
		mArtImageView = (ImageView) findViewById(R.id.iv_art_bottom);
		titleTextView = (TextView) findViewById(R.id.title);

		mPraiseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mCoreService != null) {
					if (mCoreService.onPraisedBtnPressed()) {
						mPraiseButton
								.setImageResource(R.drawable.desk2_btn_loved_prs);
					} else {
						mPraiseButton
								.setImageResource(R.drawable.desk2_btn_love_prs);
					}
				}
				// hard code here...
				mFragments.get(1).onPraisedPressed();
			}
		});
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	void startService() {
		final Intent intent = new Intent();
		intent.setClass(RockLite.this, CoreService.class);
		startService(intent);
	}

	void bindToService() {

		mServiceConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName arg0) {

			}

			@Override
			public void onServiceConnected(ComponentName arg0, IBinder iBinder) {
				MyBinder myBinder = (MyBinder) iBinder;
				mCoreService = myBinder.getService();
				Log.i("nick_music", mCoreService.toString());
				mCoreService.setActivityCallback(RockLite.this);

				// update
				initPager();
				onPlayStateChanged();
			}
		};

		final Intent intent = new Intent();
		intent.setClass(RockLite.this, CoreService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

	}

	public CoreService getServiceCallback() {
		return mCoreService;
	}

	@Override
	protected void onDestroy() {
		unbindService(mServiceConnection);
		super.onDestroy();
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// DO NOTHING
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "NICK";
		}
	}

	/**
	 * @return
	 */
	public List<Base> getPages() {
		return mFragments;
	}

	public void updateControlButtonBackground() {
		if (mCoreService.getIsPlaying()) {
			mPlayButton.setImageResource(R.drawable.notification_pause);
		} else {
			mPlayButton.setImageResource(R.drawable.notification_play);
		}
	}

	public void updateArtImage(ImageView imageView) {

		if (mCoreService.getPlayList() != null) {

			ImageLoader.getInstance().displayImage(
					mCoreService.getCurrentAlbumUri().toString(), imageView);
		}
	}

	public void updateTitle(String title) {
		titleTextView.setText(title);
	}
	
	public void updatePrisedImg() {
		
		if (mCoreService != null && mCoreService.checkIfPraised()) {
			mPraiseButton.setImageResource(R.drawable.desk2_btn_loved_prs);
		} else {
			mPraiseButton.setImageResource(R.drawable.desk2_btn_love_prs);
		}
	}
	

	@Override
	public void onClick(View v) {
		int btnId = v.getId();
		switch (btnId) {

		case R.id.btn_play_local:
			if (mCoreService.getIsPlaying()) {
				mCoreService.pausePlayer();
			} else {
				mCoreService.resumePlayer();
			}
			break;

		case R.id.btn_next_local:
			mCoreService.playNextTrack();
			break;

		case R.id.btn_pre_local:
			mCoreService.playPreviousTrack();
			break;

		default:
			break;
		}
	}

	@Override
	public void onPlayStateChanged() {
		updateControlButtonBackground();
		updateArtImage(mArtImageView);
		updateTitle(mCoreService.getCurrentTitle());
		updatePrisedImg();
	}

	public void onBlurReady(Drawable drawable) {

		if (drawable != null) {
			mRootLayout.setBackground(drawable);
			mRootLayout.startAnimation(mAnimationFade);
			drawable = null;
		}
	}
}
