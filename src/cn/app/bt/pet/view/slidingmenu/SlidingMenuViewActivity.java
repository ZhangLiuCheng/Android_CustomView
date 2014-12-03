package cn.app.bt.pet.view.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import cn.app.bt.pet.adapter.PullListAdapter;
import cn.app.bt.pet.util.AbstractActivity;
import cn.app.bt.pet.view.pull_listview.PullListView;
import cn.app.bt.pet.view.pull_listview.PullListView.ListViewPullDelegate;

import com.example.customview.R;

public class SlidingMenuViewActivity extends AbstractActivity {

	private SlidingMenuView mSlidingMenuView;
	
	private PullListView mPullListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_menu_view);
		
		init();
		mSlidingMenuView.showMiddleView();
	}

	@Override
	public void initData() {
	}

	@Override
	public void initView() {
		mSlidingMenuView = (SlidingMenuView) findViewById(R.id.slidingMenuView);
	}

	@Override
	public void initViewData() {
		final View leftView = new View(this);
		leftView.setBackgroundColor(Color.RED);
		
		final View rightView = new View(this);
		rightView.setBackgroundColor(Color.BLUE);
		
		mSlidingMenuView.addLeftView(leftView);
		mSlidingMenuView.addRightView(rightView);
		
		mPullListView = new PullListView(this);
		PullListAdapter adapter = new PullListAdapter(this);
		adapter.setData(getTestData());
		mPullListView.setAdapter(adapter);
		mSlidingMenuView.addMiddleView(mPullListView);
	}

	@Override
	public void initViewListener() {
		mPullListView.setListViewDelegate(new ListViewPullDelegate() {
			@Override
			public void pullListViewTriggerRefresh(final PullListView listView) {
				refreshListView(listView);
			}
			
			@Override
			public void pullListViewTriggerLoadMore(final PullListView listView) {
				loadMoreListView(listView);
			}
		});
	}
	
	private void refreshListView(final PullListView listView) {
		final PullListAdapter adapter = (PullListAdapter) listView.getAdapter();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					adapter.setData(getTestData());
					SlidingMenuViewActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							listView.refreshFinish();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void loadMoreListView(final PullListView listView) {
		final PullListAdapter adapter = (PullListAdapter) listView.getAdapter();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					adapter.addData(getTestData());
					SlidingMenuViewActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							listView.loadMoreFinish();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private List<String> getTestData() {
		List<String> val = new ArrayList<String>();
		val.add("1");
		val.add("2");
		val.add("3");
		val.add("4");
		val.add("5");
		val.add("6");
		val.add("7");
		val.add("8");
		val.add("9");
		val.add("10");
		val.add("11");
		return val;
	}
}
