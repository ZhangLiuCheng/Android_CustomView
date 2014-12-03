package cn.app.bt.pet.view.photo;

import com.example.customview.R;

import android.os.Bundle;
import cn.app.bt.pet.util.AbstractActivity;

public class PhotoActivity extends AbstractActivity {

	private PhotoView mPhotoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		init();
	}
	
	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		mPhotoView = (PhotoView) findViewById(R.id.photoView);
	}

	@Override
	public void initViewData() {
		mPhotoView.setImageResource(R.drawable.head_bg2);
//		mPhotoView.setImageResource(R.drawable.ic_launcher);
	}

	@Override
	public void initViewListener() {

	}
}
