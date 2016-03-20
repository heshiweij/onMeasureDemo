package com.example.onmeasuredemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class FlowLayout extends LinearLayout {

	public FlowLayout(Context context) {
		super(context);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			int createChildWidthMeasureSpec = createChildWidthMeasureSpec(
					widthMeasureSpec, view);
			int createChildHeightMeasureSpec = createChildHeightMeasureSpec(
					heightMeasureSpec, view);
			view.measure(createChildWidthMeasureSpec,
					createChildHeightMeasureSpec);

			System.out.println("View MeasureWidth: " + view.getMeasuredWidth());
			System.out.println("View MeasureHeight: "+ view.getMeasuredHeight());
		}

		int width = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(width, 500);
	}

	/**
	 * 根据父 View 规则和子 View 的 LayoutParams，计算子类的宽度(width)测量规则
	 * 
	 * @param widthMeasureSpec
	 * @param view
	 */
	private int createChildWidthMeasureSpec(int parentWidthMeasureSpec,
			View view) {
		// 获取父 View 的测量模式
		int parentWidthMode = MeasureSpec.getMode(parentWidthMeasureSpec);
		// 获取父 View 的测量尺寸
		int parentWidthSize = MeasureSpec.getSize(parentWidthMeasureSpec);

		// 定义子 View 的测量规则
		int childWidthMeasureSpec = 0;

		// 获取子 View 的 LayoutParams
		LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

		if (parentWidthMode == MeasureSpec.EXACTLY) {
			/* 这是当父类的模式是 dp 的情况 */
			if (layoutParams.width > 0) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.width, MeasureSpec.EXACTLY);
			} else if (layoutParams.width == LayoutParams.WRAP_CONTENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentWidthSize, MeasureSpec.AT_MOST);
			} else if (layoutParams.width == LayoutParams.MATCH_PARENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentWidthSize, MeasureSpec.EXACTLY);
			}
		} else if (parentWidthMode == MeasureSpec.AT_MOST) {
			/* 这是当父类的模式是 WRAP_CONTENT 的情况 */
			if (layoutParams.width > 0) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.width, MeasureSpec.EXACTLY);
			} else if (layoutParams.width == LayoutParams.WRAP_CONTENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentWidthSize, MeasureSpec.AT_MOST);
			} else if (layoutParams.width == LayoutParams.MATCH_PARENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentWidthSize, MeasureSpec.EXACTLY);
			}
		} else if (parentWidthMode == MeasureSpec.UNSPECIFIED) {
			/* 这是当父类的模式是 MATCH_PARENT 的情况 */
			if (layoutParams.width > 0) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.width, MeasureSpec.EXACTLY);
			} else if (layoutParams.width == LayoutParams.WRAP_CONTENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			} else if (layoutParams.width == LayoutParams.MATCH_PARENT) {
				childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
		}

		// 返回子 View 的测量规则
		return childWidthMeasureSpec;
	}

	/**
	 * 根据父 View 规则和子 View 的 LayoutParams，计算子类的宽度(width)测量规则
	 * 
	 * @param widthMeasureSpec
	 * @param view
	 */
	private int createChildHeightMeasureSpec(int parentHeightMeasureSpec,
			View view) {
		int parentHeightMode = MeasureSpec.getMode(parentHeightMeasureSpec);
		int parentHeightSize = MeasureSpec.getSize(parentHeightMeasureSpec);

		int childHeightMeasureSpec = 0;

		LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

		if (parentHeightMode == MeasureSpec.EXACTLY) {
			if (layoutParams.height > 0) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.height, MeasureSpec.EXACTLY);
			} else if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentHeightSize, MeasureSpec.AT_MOST);
			} else if (layoutParams.height == LayoutParams.MATCH_PARENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentHeightSize, MeasureSpec.EXACTLY);
			}
		} else if (parentHeightMode == MeasureSpec.AT_MOST) {
			if (layoutParams.height > 0) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.height, MeasureSpec.EXACTLY);
			} else if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentHeightSize, MeasureSpec.AT_MOST);
			} else if (layoutParams.height == LayoutParams.MATCH_PARENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						parentHeightSize, MeasureSpec.EXACTLY);
			}
		} else if (parentHeightMode == MeasureSpec.UNSPECIFIED) {
			if (layoutParams.height > 0) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						layoutParams.height, MeasureSpec.EXACTLY);
			} else if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			} else if (layoutParams.height == LayoutParams.MATCH_PARENT) {
				childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
		}

		return childHeightMeasureSpec;
	}
}
