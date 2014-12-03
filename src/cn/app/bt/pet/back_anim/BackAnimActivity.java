package cn.app.bt.pet.back_anim;

import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.app.bt.pet.util.AbstractActivity;

import com.example.customview.R;

public class BackAnimActivity extends AbstractActivity {

	private Button mBut1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back_anim);
		
		init();
	}
	
	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		mBut1 = (Button) findViewById(R.id.button1);
	}

	@Override
	public void initViewData() {

	}

	@Override
	public void initViewListener() {
		mBut1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
}
