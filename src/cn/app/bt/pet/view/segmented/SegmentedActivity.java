package cn.app.bt.pet.view.segmented;

import android.os.Bundle;
import android.widget.Toast;
import cn.app.bt.pet.util.AbstractActivity;
import cn.app.bt.pet.view.segmented.SegmentedView.OnValueChageListener;

import com.example.customview.R;

public class SegmentedActivity extends AbstractActivity {

	private SegmentedView mSegmentedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_segmented);
		init();
		
		mSegmentedView.setTabSelected(0);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		mSegmentedView = (SegmentedView) findViewById(R.id.segmentedView);
	}

	@Override
	public void initViewData() {
		mSegmentedView.addTabWithLabel("张飞");
		mSegmentedView.addTabWithLabel("光宇");
		mSegmentedView.addTabWithLabel("赵云");
	}

	@Override
	public void initViewListener() {
		mSegmentedView.setOnValueChageListener(new OnValueChageListener() {
			@Override
			public void valueChange(int index) {
				Toast.makeText(SegmentedActivity.this, "选中了  " + index, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
