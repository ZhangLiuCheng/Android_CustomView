package cn.app.bt.pet.view.slidingmenu;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 模仿 ios智慧无锡 上的左右滑动菜单.
 * @author zhangliucheng
 *
 */
@SuppressLint("NewApi")
public class SlidingMenuView extends RelativeLayout /*implements OnTouchListener*/ {
	
	private final LinearLayout mLeftView = new LinearLayout(getContext());
	private final LinearLayout mMiddleView = new LinearLayout(getContext());
	private final LinearLayout mRightView = new LinearLayout(getContext());
	
	private boolean initialize;

	private float touchDownX = -1;
	private int space;
	
	private ViewState viewState;
	private ValueChangeListener valueChangeListener;
	
	public enum ViewState {
		LEFT_VIEW,
		MIDDLE_VIEW,
		RIGHT_VIEW
	}
	
	/**
	 * 滑动切换view后的回调.
	 */
	public interface ValueChangeListener {
		void valueChange(ViewState viewState, View view);
	}

	public SlidingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initialize = false;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		space = getWidth() * 3 / 4;
		if (space != 0 && !initialize) {
			initialize = true;
			initView();
			initViewListener();
		}
	}
	
	public ViewState getViewState() {
		return viewState;
	}

	public void setValueChangeListener(ValueChangeListener valueChangeListener) {
		this.valueChangeListener = valueChangeListener;
	}

	/**
	 * add Left View.
	 * @param view
	 */
	public void addLeftView(View view) {
		mLeftView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	}
	
	/**
	 * add Middle View.
	 * @param view
	 */
	public void addMiddleView(View view) {
		mMiddleView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	}
	
	/**
	 * add Right View.
	 * @param view
	 */
	public void addRightView(View view) {
		mRightView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	}
	
	/**
	 * 显示leftView.
	 */
	public void showLeftView() {
		animatorMiddleView(space, new AnimatorListenerNullImpl() {
			@Override
			public void onAnimationEnd(Animator arg0) {
				viewState = ViewState.LEFT_VIEW;
				if (null != valueChangeListener) {
					valueChangeListener.valueChange(viewState, mLeftView);
				}
			}
		});
	}
	
	/**
	 * 显示MiddleView.
	 */
	public void showMiddleView() {
		animatorMiddleView(0, new AnimatorListenerNullImpl() {
			@Override
			public void onAnimationEnd(Animator arg0) {
				viewState = ViewState.MIDDLE_VIEW;
				if (null != valueChangeListener) {
					valueChangeListener.valueChange(viewState, mMiddleView);
				}
			}
		});
	}
	
	/**
	 * 显示RightView.
	 */
	public void showRightView() {
		animatorMiddleView(-space, new AnimatorListenerNullImpl() {
			@Override
			public void onAnimationEnd(Animator arg0) {
				viewState = ViewState.RIGHT_VIEW;
				if (null != valueChangeListener) {
					valueChangeListener.valueChange(viewState, mRightView);
				}
			}
		});
	}
	
	/**
	 * 通过 ObjectAnimator改变middleView的位置.
	 * @param destPosition		middleView的目标位置
	 * @param listener			动画执行的监听事件
	 */
	private void animatorMiddleView(int destPosition, AnimatorListenerNullImpl listener) {
		final LayoutParams params = (LayoutParams) mMiddleView.getLayoutParams();
		if (null == params) {
			return;
		}
		final ObjectAnimator oa = ObjectAnimator.ofInt(this, "middleViewPosition", params.leftMargin, destPosition);
		oa.setInterpolator(new LinearInterpolator());
		oa.setDuration(Math.abs(destPosition - params.leftMargin) / 2);
		oa.start();
		oa.addListener(listener);
	}
	
	/**
	 * 这个方法供 ObjectAnimator.ofInt(this, "middleViewPosition", src, dest);使用.
	 * @param distance
	 */
	public void setMiddleViewPosition(int distance) {
		final LayoutParams params = (LayoutParams) mMiddleView.getLayoutParams();
		params.leftMargin = distance;
		params.rightMargin = -distance;
		mMiddleView.setLayoutParams(params);
		
		if (distance > 0) {
			if (mLeftView.getVisibility() == INVISIBLE) {
				mLeftView.setVisibility(VISIBLE);
			}
			if (mRightView.getVisibility() == VISIBLE) {
				mRightView.setVisibility(INVISIBLE);
			}
			/*
			mLeftView.setPivotX(0);
			
			final float alpha = 0.4f + (params.leftMargin * 0.6f / space);
			mLeftView.setAlpha(alpha);
			mLeftView.setRotationY(10 - params.leftMargin * 1.0f / space * 10f);
			*/
			final float alpha = 0.2f + (params.leftMargin * 0.8f / space);
			mLeftView.setAlpha(alpha);
			mLeftView.setTranslationX(params.leftMargin * 150f / space - 150);
		} else {
			if (mLeftView.getVisibility() == VISIBLE) {
				mLeftView.setVisibility(INVISIBLE);
			}
			if (mRightView.getVisibility() == INVISIBLE) {
				mRightView.setVisibility(VISIBLE);
			}
			final float alpha = 0.2f + (params.rightMargin * 0.8f / space);
			mRightView.setAlpha(alpha);
			mRightView.setTranslationX(150 - params.rightMargin * 150f / space);
		}
	}
	
	private void initView() {
		viewState = ViewState.MIDDLE_VIEW;
		
		//添加底层左右操作的view
		final RelativeLayout backLayout = new RelativeLayout(getContext());
		this.addView(backLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		final RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(space, 
				LayoutParams.MATCH_PARENT);
		backLayout.addView(mLeftView, leftParams);
		
		final RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(space, 
				LayoutParams.MATCH_PARENT);
		rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
		backLayout.addView(mRightView, rightParams);

		
		//添加中间的view
		final LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(mMiddleView, p);
		mMiddleView.setBackgroundColor(Color.WHITE);
	}
	
	private void initViewListener() {
//		mMiddleView.setClickable(true);
//		mMiddleView.setOnTouchListener(this);
	}
	
	/** 手势touch时候移动 middleView. **/
	private void moveMiddleViewPosition(int distance) {
		final LayoutParams params = (LayoutParams) mMiddleView.getLayoutParams();
		distance += params.leftMargin;
		
		if (Math.abs(distance) > space) {
			return;
		}
		setMiddleViewPosition(distance);
	}
	
	/** 停止滑动，判断应该显示那个view. **/
	private void stopMiddleViewPosition() {
		final LayoutParams params = (LayoutParams) mMiddleView.getLayoutParams();
		final int leftMargin = params.leftMargin;
		//向右边滑动
		if (leftMargin >= 0) {
			//显示leftView
			if (leftMargin > space / 2) {
				showLeftView();
			//显示middleView
			} else {
				showMiddleView();
			}
		//向左滑动
		} else {
			//显示rightView
			if (leftMargin < -space / 2) {
				showRightView();
			//显示middleView
			} else {
				showMiddleView();
			}
		}
	}
	
	private float x, y;
	private TouchGesture mTouchGesture = TouchGesture.Normal;
	
	private static enum TouchGesture {
		Normal,
		Land,
		Port
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (mTouchGesture == TouchGesture.Land) {
				return true;
			} else if (mTouchGesture == TouchGesture.Port) {
				return super.onInterceptTouchEvent(ev);
			}
		}
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getRawX();
			y = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(ev.getRawX() - x) > 20) {
				mTouchGesture = TouchGesture.Land;
			} else if (Math.abs(ev.getRawY() - y) > 20) {
				mTouchGesture = TouchGesture.Port;
			}
			break;
		case MotionEvent.ACTION_UP: 
		case MotionEvent.ACTION_CANCEL:
			mTouchGesture = TouchGesture.Normal;
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDownX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (-1 == touchDownX) {
				touchDownX = event.getRawX();
			}
			final float distance = event.getRawX() - touchDownX;
			touchDownX = event.getRawX();
			moveMiddleViewPosition((int) distance); 
			break;
		case MotionEvent.ACTION_UP:
			stopMiddleViewPosition();
			touchDownX = -1;
			
			mTouchGesture = TouchGesture.Normal;
			break;
		default:
			break;
		}
		return true;
	}
	
	/*
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("SlidingMenuView =======>   onTouch");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDownX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			final float distance = event.getRawX() - touchDownX;
			touchDownX = event.getRawX();
			moveMiddleViewPosition((int) distance); 
			break;
		case MotionEvent.ACTION_UP:
			stopMiddleViewPosition();
			break;
		default:
			break;
		}
		return true;
	}
	*/
	
	private static class AnimatorListenerNullImpl implements AnimatorListener {
		@Override
		public void onAnimationCancel(Animator animation) {
		}
		@Override
		public void onAnimationEnd(Animator animation) {
		}
		@Override
		public void onAnimationRepeat(Animator animation) {
		}
		@Override
		public void onAnimationStart(Animator animation) {
		}
	}
}
