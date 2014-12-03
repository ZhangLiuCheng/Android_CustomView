package cn.app.bt.pet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.app.bt.pet.back_anim.BackAnimActivity;
import cn.app.bt.pet.http.HttpActivity;
import cn.app.bt.pet.util.AbstractActivity;
import cn.app.bt.pet.view.image_edittext.SmileUtils;
import cn.app.bt.pet.view.multi_picture.MultiPictureActivity;
import cn.app.bt.pet.view.photo.PhotoActivity;
import cn.app.bt.pet.view.pull_bounche_scrollview.PullBounchesScrollViewActivity;
import cn.app.bt.pet.view.pull_listview.PullListViewActivity;
import cn.app.bt.pet.view.segmented.SegmentedActivity;
import cn.app.bt.pet.view.slidingmenu.SlidingMenuViewActivity;

import com.example.customview.R;

public class MainActivity extends AbstractActivity {

	private TextView val;

	private Button mBtnScrollViewTest;
	private Button mBtnListViewTest;
	private Button mBtnSlidingMenuTest;
	private Button mBtnAddMultiPicture;
	private Button mSegmented;
	private Button mPhotoView;
	private Button mHttp;
	private Button mBackAnim;
	
	private String test = "a";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test = "zlc";
		init();
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		val = (TextView) findViewById(R.id.val);

		mBtnScrollViewTest = (Button) findViewById(R.id.btn1);
		mBtnListViewTest = (Button) findViewById(R.id.btn2);
		mBtnSlidingMenuTest = (Button) findViewById(R.id.btn3);
		mBtnAddMultiPicture = (Button) findViewById(R.id.btn4);
		mSegmented = (Button) findViewById(R.id.segmented);
		mPhotoView = (Button) findViewById(R.id.photoView);
		mHttp = (Button) findViewById(R.id.http);
		mBackAnim = (Button) findViewById(R.id.backAnim);
	}

	@Override
	public void initViewData() {

		val.append(SmileUtils.getSmiledText(MainActivity.this, "asdf[):]gfgfg[:@]"));
	}

	@Override
	public void initViewListener() {

		mBtnScrollViewTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, PullBounchesScrollViewActivity.class);
				startActivityWithAnim(intent);
			}
		});

		mBtnListViewTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, PullListViewActivity.class);
				startActivityWithAnim(intent);
			}
		});

		mBtnSlidingMenuTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, SlidingMenuViewActivity.class);
				startActivityWithAnim(intent);
			}
		});

		mBtnAddMultiPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, MultiPictureActivity.class);
				startActivityWithAnim(intent);
			}
		});

		mSegmented.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, SegmentedActivity.class);
				startActivityWithAnim(intent);
			}
		});

		mPhotoView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
				startActivityWithAnim(intent);
			}
		});
		
		mHttp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, HttpActivity.class);
				startActivityWithAnim(intent);
			}
		});
		
		mBackAnim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, BackAnimActivity.class);
				startActivityWithAnim(intent);
			}
		});
	}
}
