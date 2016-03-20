原文：http://blog.csdn.net/anydrew/article/details/50935362

## 前言

众所周知，自定义 ViewGroup 中这几个方法非常重要：onMeasure, onLayout。初学者学习自定义 View 时，想必对 onMeasure 比较困惑，onMeasure 是什么，为什么要测量，怎么测量？

网上有很多关于 onMeasure 的文章，诸如《onMeasure 详解xxx》、《onMeasure xxx源码分析》。好像都不能彻底解决心中的疑惑。本文就从“是什么”，“为什么”和“怎么样”这三个角度，根据我自己的理解，带大家了解 Android 的测量原理。

## 是什么？

测量的定义：确定自己的实际尺寸。

## 为什么？

Android 中， View 的 `layout_width` `layout_height` 并不是固定值，它可以设置成 `dp`、`MATCH_PARENT`、`WRAP_CONTENT` 三种形式。我们试想，如果用户设置了精确值 dp，那就好说，直接给 View 设置成精确的宽度，一旦用户给子 View 设置了 `MATCH_PARENT`（匹配父窗体），子 View 的宽度应该是父窗体的宽度，而问题是子 View 并不知道父窗体多宽。为了解决这样的矛盾，Android 系统干脆规定：**子 View 的宽高必须交给父 View 去测量**。在测量时，**父 View 会根据自己的尺寸和子 View 的LayoutParams**，计算出一个 MeasureSpec（测量规则），**也是父 View 对子 View 尺寸的期望**。然后遍历调用子 View 的`measure(widthMeasureSpec，heightMeasureHeight)`  进行实际测量。整个过程如下图所示。

## 测量过程

整个测量过程如下图：

![这里写图片描述](http://img.blog.csdn.net/20160320110934311)

### 测量模式

#### EXACTLY: 精确模式

用这个模式去测量，并在测量时提供一个值，那么 View 就会以这个值确定自己的实际尺寸。
	如：

```java
int widthSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
// 第二个参数为高度的测量规则，写 0  表示省略
view.measure(widthSpec, 0); 
```

#### AT_MOST：最大模式

用这个模式去测量，并在测量时提供一个最大值，那么 View 就先以自己的内容为准，在不超过最大值的前提下，最终确定自己的尺寸
	如：
```java
int widthSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.AT_MOST);
// 第二个参数为高度的测量规则，写 0  表示省略
view.measure(widthSpec, 0); 
```

#### UNSPECIFIED：不确定模式

用这个模式去测量， View 就会任意确定自己的尺寸，不管你传什么值进去都是没有意义。很少用，为了避免和 `AT_MOST` 搞混，暂时将它忽略。
	如：

```java
int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
// 第二个参数为高度的测量规则，写 0  表示省略
view.measure(widthSpec, 0); 
```

### 测量规则

`widthMeasureSpec` 和 `heightMeasureSpec`是测量规则，是一个 int 类型，但是它并不是实际的尺寸，而是尺寸和测量模式的合成值。它在 int 类型的 32 位二进制位中，31-30 这两位表示模式，0~29 这三十位表示宽和高的实际值。通过 `MeasureSpec` 类提供的静态方法，我们可以从 `widthMeasureSpec` 和 `heightMeasureSpec` 中提取测量模式和期望尺寸。

代码如下：

```java
int mode = MeasureSpec.getMode(widthMeasureSpec);
int size = MeasureSpec.getSize(widthMeasureSpec);
```

### 如何确定的子类的测量模式

上面说了，父类测量子类是调用子 View 的 `measure(widthMeasureSpec，heightMeasureHeight)`，这个两个测量 `widthMeasureSpec` 规则是父类根据自己的宽度和子类的 `LayoutParams` 计算出来的。

那么到底怎么计算测量规则，看下面一张图就全明白了（**注意相同的颜色**）。

![这里写图片描述](http://img.blog.csdn.net/20160320102042457)


我将上述关系表转化成代码（以宽度为例）：

```
/**
 * 根据父 View 规则和子 View 的 LayoutParams，计算子类的宽度(width)测量规则
 * @param widthMeasureSpec
 * @param view
 */
private int createChildWidthMeasureSpec(int parentWidthMeasureSpec, View view) {
	// 获取父 View 的测量模式
	int parentWidthMode = MeasureSpec.getMode(parentWidthMeasureSpec);
	// 获取父 View 的测量尺寸
	int parentWidthSize = MeasureSpec.getSize(parentWidthMeasureSpec);
	
	// 定义子 View 的测量规则
	int childWidthMeasureSpec = 0;
	
	// 获取子 View 的 LayoutParams
	LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
	
	
	if (parentWidthMode == MeasureSpec.EXACTLY){
		/* 这是当父类的模式是 dp 的情况 */
		if (layoutParams.width > 0){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
		} else if(layoutParams.width == LayoutParams.WRAP_CONTENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.AT_MOST);
		} else if (layoutParams.width == LayoutParams.MATCH_PARENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.EXACTLY);
		}
	} else if (parentWidthMode == MeasureSpec.AT_MOST){
		/* 这是当父类的模式是 WRAP_CONTENT 的情况 */
		if (layoutParams.width > 0){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
		} else if(layoutParams.width == LayoutParams.WRAP_CONTENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.AT_MOST);
		} else if (layoutParams.width == LayoutParams.MATCH_PARENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.EXACTLY);
		}
	} else if (parentWidthMode == MeasureSpec.UNSPECIFIED){
		/* 这是当父类的模式是 MATCH_PARENT 的情况 */
		if (layoutParams.width > 0){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
		} else if(layoutParams.width == LayoutParams.WRAP_CONTENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		} else if (layoutParams.width == LayoutParams.MATCH_PARENT){
			childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
	}
	
	// 返回子 View 的测量规则
	return childWidthMeasureSpec;
}

```

这代码显然是冗余的，我们可以简化成一个公式，但是为了方便理解，还是不简化了。高度的代码也是一样的。

## 开始测量

现在知道了测量原理和具体的测量代码，就可以模拟 LinearLayout 在 ViewGroup 中对子类，进行测量

```
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 干掉父 View onMeasure 先
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// 遍历子 View，测量
		for (int i = 0; i < getChildCount(); i++){
			View view = getChildAt(i);
			
			// 获取子 View 的宽度测量规则
			int createChildWidthMeasureSpec = createChildWidthMeasureSpec(widthMeasureSpec, view);

			// 获取子 View 的高度测量规则
			int createChildHeightMeasureSpec = createChildHeightMeasureSpec(heightMeasureSpec, view);
			
			// 开始测量
			view.measure(createChildWidthMeasureSpec, createChildHeightMeasureSpec);
			
			// 输出测量结果
			System.out.println("View MeasureWidth: " + view.getMeasuredWidth());
			System.out.println("View MeasureHeight: " + view.getMeasuredHeight());
		}

	// 最终，父 View，根据子 View 的测量结果，才可以设置自己的尺寸
	setMeasuredDimension(parentWidth, parentHeight);
```

在父 View 添加了几个 TextView, 测量后输出结果：

![这里写图片描述](http://img.blog.csdn.net/20160320113738411)

## 附录：

[Android自定义控件系列七：详解onMeasure()方法中如何测量一个控件尺寸(一)](http://blog.csdn.net/cyp331203/article/details/45027641) 

《Android 群英传》3.2 章：控件的测量



