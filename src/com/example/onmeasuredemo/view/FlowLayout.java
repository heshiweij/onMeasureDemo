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
	 * ���ݸ� View ������� View �� LayoutParams����������Ŀ��(width)��������
	 * 
	 * @param widthMeasureSpec
	 * @param view
	 */
	private int createChildWidthMeasureSpec(int parentWidthMeasureSpec,
			View view) {
		// ��ȡ�� View �Ĳ���ģʽ
		int parentWidthMode = MeasureSpec.getMode(parentWidthMeasureSpec);
		// ��ȡ�� View �Ĳ����ߴ�
		int parentWidthSize = MeasureSpec.getSize(parentWidthMeasureSpec);

		// ������ View �Ĳ�������
		int childWidthMeasureSpec = 0;

		// ��ȡ�� View �� LayoutParams
		LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

		if (parentWidthMode == MeasureSpec.EXACTLY) {
			/* ���ǵ������ģʽ�� dp ����� */
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
			/* ���ǵ������ģʽ�� WRAP_CONTENT ����� */
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
			/* ���ǵ������ģʽ�� MATCH_PARENT ����� */
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

		// ������ View �Ĳ�������
		return childWidthMeasureSpec;
	}

	/**
	 * ���ݸ� View ������� View �� LayoutParams����������Ŀ��(width)��������
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
