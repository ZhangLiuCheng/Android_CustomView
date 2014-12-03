package cn.app.bt.pet.view.multi_picture;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import cn.app.bt.pet.util.AbstractActivity;
import cn.app.bt.pet.view.multi_picture.MultiPictureView.OperationCallback;

import com.example.customview.R;

public class MultiPictureActivity extends AbstractActivity {
	
	private MultiPictureView multiPictureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_picture);
		init();
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void initView() {
		multiPictureView = (MultiPictureView) findViewById(R.id.multiPictureView);
	}

	@Override
	public void initViewData() {
		
	}

	@Override
	public void initViewListener() {
		
		final int[] color = {Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW};
		multiPictureView.setOperationCallback(new OperationCallback() {
			@Override
			public void invoke() {
				Random r = new Random();
				int c = r.nextInt(color.length);
				System.out.println(c);
				Button b = new Button(MultiPictureActivity.this);
				b.setBackgroundColor(color[c]);
				multiPictureView.addView(b);
			}
		});
		
	}
}
