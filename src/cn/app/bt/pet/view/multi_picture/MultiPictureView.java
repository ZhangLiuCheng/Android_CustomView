package cn.app.bt.pet.view.multi_picture;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class MultiPictureView extends ViewGroup {
	
	public interface OperationCallback {
		void invoke();
	}
	
	private final int duration = 300;
	
	private int rowCount = 4;	// 一行默认5个
	private int maxCount = 8;	// 最多子元素
	private int size = -1;		// 子控件的长度和宽度的值
	private int space;			// 控件间的间隔
	
	private Button mAddView;
	private OperationCallback operationCallback;

	public MultiPictureView(Context context) {
		super(context);
		init();
		initViewListener();
	}
	
	public MultiPictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		initViewListener();
	}
	
	public void setCount(int count) {
		this.rowCount = count;
		
		calculSize();
		requestLayout();
	}
	
	public void setOperationCallback(OperationCallback operationCallback) {
		this.operationCallback = operationCallback;
	}

	private void init() {
		mAddView = new Button(getContext());
		mAddView.setText("＋");
		mAddView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != operationCallback) {
					operationCallback.invoke();
				}
			}
		});
		this.addView(mAddView);
	}
	
	private void initViewListener() {
		for (int i = 0; i < getChildCount(); i++) {
			final View view = getChildAt(i);
			if (view != mAddView) {
				view.setOnClickListener(mListener);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if (size == -1) {
			calculSize();
		}
		for (int i = 0; i < getChildCount(); i++) {
			final View view = getChildAt(i);
			view.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			final Point p = getViewPosition(i);
			final View view = getChildAt(i);
			view.layout(p.x, p.y, p.x + size, p.y + size);
			
			if (canUseNewApi()) {
				view.setX(p.x);
				view.setY(p.y);
			}
		}
	}
	
	@Override
	public void addView(View child, int index, LayoutParams params) {
		// 数量等于最大数量得时候，将AddView隐藏
		if (getChildCount() == maxCount) {
			mAddView.setVisibility(INVISIBLE);
		}
		super.addView(child, getChildCount() - 1, params);
		if (canUseNewApi()) {
			prepareAddAnim();
		}
		initViewListener();
	}
	
	private void calculSize() {
		final int width = getMeasuredWidth();
		this.space = width / 12 / this.rowCount;
		this.size = (width - space * (this.rowCount + 1)) / this.rowCount;
	}
	
	private Point getViewPosition(int index) {
		final int remainder = index % this.rowCount;
		final int scalar = index / this.rowCount;

		final Point p = new Point();
		p.x = (remainder + 1) * space + remainder * size;
		p.y = (scalar + 1) * space + scalar * size;
		return p;
	}
	
	private OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (canUseNewApi()) {
				int index = -1;
				for (int i = 0; i < getChildCount(); i ++) {
					if (v == getChildAt(i)) {
						index = i;
						break;
					}
				}
				prepareDeleteAnima(v, index);
			} else {
				if (getChildCount() - 1 == maxCount && mAddView.getVisibility() == INVISIBLE) {
					mAddView.setVisibility(VISIBLE);
				}
				removeView(v);
			}
		}
	};
	
	private void prepareAddAnim() {
		if (getChildCount() >= 2) {
			final View lastAddView = getChildAt(getChildCount() - 2);
			lastAddView.setAlpha(0);
			lastAddView.setScaleX(0);
			lastAddView.setScaleY(0);
			lastAddView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(duration).start();
		}
		final Point p = getViewPosition(getChildCount() - 1);
		animMoveView(mAddView, p);
	}
	
	private void prepareDeleteAnima(View view, int index) {
		view.setAlpha(0.2f);
		view.animate().setDuration(duration).scaleX(0.2f).scaleY(0.2f).alpha(0).setListener(getListener(view)).start();
		
		for (int i = index + 1; i < getChildCount(); i++) {
			final View v = getChildAt(i);
			boolean flag = v == mAddView && i == maxCount;
			if (!flag) {
				animMoveView(v, getViewPosition(i - 1));
			}
		}
		
		// 数量小于最大数量的时候，将AddView显示出来
		if (getChildCount() - 1 == maxCount && mAddView.getVisibility() == INVISIBLE) {
			final Point p = getViewPosition(getChildCount() - 2);
			mAddView.setX(p.x);
			mAddView.setY(p.y);
			mAddView.setAlpha(0);
			mAddView.setVisibility(VISIBLE);
			mAddView.animate().alpha(1).setDuration(duration * 2).start();
		}
	}
	
	private void animMoveView(View view, Point destPoint) {
		view.animate().x(destPoint.x).y(destPoint.y).setDuration(duration).start();
	}
	
	private AnimatorListener getListener(final View view) {
		final AnimatorListener listener = new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				MultiPictureView.this.removeView(view);
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		};
		return listener;
	}
	
	private boolean canUseNewApi() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return true;
		}
		return false;
	}
}
