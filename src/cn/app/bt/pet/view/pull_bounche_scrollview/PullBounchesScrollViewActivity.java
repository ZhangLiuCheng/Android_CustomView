package cn.app.bt.pet.view.pull_bounche_scrollview;

import android.os.Bundle;
import cn.app.bt.pet.util.AbstractActivity;

import com.example.customview.R;

public class PullBounchesScrollViewActivity extends AbstractActivity {

	private PullBouncheScrollView scrollview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		
		init();
	}

	@Override
	public void initData() {
	}

	@Override
	public void initView() {
		scrollview = (PullBouncheScrollView) findViewById(R.id.scrollview);
		scrollview.addBounchView(getLayoutInflater().inflate(R.layout.view_bouche, null), 200);
	}

	@Override
	public void initViewData() {
		
	}

	@Override
	public void initViewListener() {
		
	}
}
