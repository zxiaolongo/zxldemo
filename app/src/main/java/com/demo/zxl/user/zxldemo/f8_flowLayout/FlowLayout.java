package com.demo.zxl.user.zxldemo.f8_flowLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup{
	//控件和控件间的水平间距
	public static final int HORIZONTALSPACING = UiUtils.dip2px(6);
	//行和行之前的竖直间距
	public static final int VERTICALSPACING = UiUtils.dip2px(10);
	//创建一个行对象的集合用于存储多个行对象
	//可以记录目前一共有多少行
	//可以通过循环遍历获取集合中的每一个行对象的高度,用于后期对自定义控件高度的计算
	private List<Line> lineList = new ArrayList<>();
	private Line line;

	public FlowLayout(Context context) {
		this(context,null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs,-1);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//给FlowLayout1中的每一个view对象指定所在屏幕位置
		//在此处需要指定每一行的位置,上一个对象top+上一个行对象高度+竖直方向间距 = 下一个行对象top

		//获取第一个行对象的左侧和顶部坐标
		int left = getPaddingLeft();
		int top = getPaddingTop();

		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);
			line.lineViewOnLayout(left,top);
			top += line.lineHeight+VERTICALSPACING;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		lineList.clear();
		//1,创建一个行对象(用于放置控件),创建一个行对象的集合用于存储多个行对象(后期在计算控件高度的时候,需要使用)
		line = new Line();
		//2.获取自定义控件,获取系统提供给此自定义控件的默认宽度(填充满屏幕横屏)
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		//3.减掉控件可能会有的内间距,得到的才是行对象总共可用的宽度
		int invalidWidthSize = widthSize - getPaddingLeft() - getPaddingRight();

		//4.将添加view的3中情况在此处体现
		//因为每一个TextView都是FlowLayout的孩子控件,所以在此处进行循环遍历获取每一个childView
		for (int i = 0; i < getChildCount(); i++) {
			//childView就是每一个索引位置的textView
			View childView = getChildAt(i);

			//系统测量,获取每一个索引位置textView控件的宽度
			childView.measure(0,0);
			int childWidth = childView.getMeasuredWidth();

			if (line.getLineViewCount() == 0){
				//情况一:此行没有任何的控件,现在添加的是第一个控件
				line.addLineView(childView);
			}else if(line.lineWidth+HORIZONTALSPACING+childWidth>invalidWidthSize){
				//情况二:此行已经没有空间放置目前循环遍历到的view控件了,则需要换行放置
				//行对象已使用宽度+水平间距+目前遍历到控件宽度>最大可用宽度  换行放置
				//记录没换行前行对象
				lineList.add(line);
				//新创建 一个行对象,添加放不下的控件
				line = new Line();
				line.addLineView(childView);
			}else{
				//情况三:目前遍历到的view能够直接放在行对象上
				line.addLineView(childView);
			}
			//如何将最后一个行对象添加在行对象集合中
			if (i == getChildCount()-1){
				//拿到了最后一个TextView索引
				lineList.add(line);
			}
		}

		//计算自定义控件的高度
		//行对象的高度之和+行和行间距之和+顶部和底部内间距 = 自定义控件的高度
		int totalHeight = 0;
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);
			totalHeight += line.lineHeight;
		}
		totalHeight += (lineList.size()-1)*VERTICALSPACING;

		//要此自定义控件,安装计算的高度显示
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight,MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 行对象用于记录添加了多少个view对象,在其内部
	 */
	public class Line{
		//记录此行已使用宽的变量
		private int lineWidth = 0;
		//记录此行高度变量
		private int lineHeight = 0;
		//记录此行中放了多少控件view
		private List<View> lineViewList = new ArrayList<>();

		//获取此行有多少个控件方法
		public int getLineViewCount(){
			return lineViewList.size();
		}
		//如何向行中添加一个view对象
		public void addLineView(View view){
			//让系统辅助测量view宽度和高度
			view.measure(0,0);

			int viewWidth = view.getMeasuredWidth();
			int viewHeight = view.getMeasuredHeight();

			//如果现在加的是行中的第一个控件
			if (lineViewList.size() == 0){
				//控件的宽度就是已使用的行宽度
				lineWidth = viewWidth;
			}else{
				//累加控件的宽度之和,和水平间距之和
				lineWidth += viewWidth+HORIZONTALSPACING;
			}

			lineHeight = viewHeight>lineHeight?viewHeight:lineHeight;
			//向行对象中添加一个view对象
			lineViewList.add(view);
		}

		/**
		 * @param left 行对象中第一个控件距离左侧间距
		 * @param top   行对象中第一个控件距离顶部间距
		 */
		//让行对象的view宽度发生变化
		public void lineViewOnLayout(int left,int top){
			//1.通过系统测量获取自定义控件的宽度大小-左右内间距,就是行对象可用宽度
			int invalidWidthSize = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
			//2.通过可用总大小-目前此行已用宽度 = 剩余等待分配宽度
			int totalSurplusWidth = invalidWidthSize - lineWidth;
			//3.将总剩余宽度按照此行有几个控件进行平均分配
			int surplusWidth = totalSurplusWidth / getLineViewCount();
			//4.给此行中的每一个控件补上步骤3中的剩余宽度
			for (int i = 0; i < lineViewList.size(); i++) {
				View view = lineViewList.get(i);

				//在系统测量控件的宽度后,补上平均分配给每一个控件的宽度
				view.measure(0,0);
				int currentWidth = view.getMeasuredWidth()+surplusWidth;
				int currentHeight = view.getMeasuredHeight();

				int width32 = MeasureSpec.makeMeasureSpec(currentWidth,MeasureSpec.EXACTLY);
				int height32 = MeasureSpec.makeMeasureSpec(currentHeight,MeasureSpec.EXACTLY);

				//将宽高的32位数作用在view控件上
				view.measure(width32,height32);

				//行对象中每一个view位置的指定
				view.layout(left,top,left+currentWidth,top+currentHeight);
				//下一个控件的左边缘 == 上一个控件的宽度+上一控件左边缘+控件的水平间距
				left += currentWidth+HORIZONTALSPACING;
			}
		}
	}
}
