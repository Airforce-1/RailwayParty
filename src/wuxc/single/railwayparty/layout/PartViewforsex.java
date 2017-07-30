package wuxc.single.railwayparty.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.branch.Statisticsforsex;

public class PartViewforsex extends View {

	private Paint xLinePaint;// ������ ���� ���ʣ�
	private Paint hLinePaint;// ������ˮƽ�ڲ� ���߻���
	private Paint titlePaint;// �����ı��Ļ���
	private Paint paint;// ���λ��� ��״ͼ����ʽ��Ϣ
	private double[] progress;// 7 ��
	private double[] aniProgress;// ʵ�ֶ�����ֵ
	private final int TRUE = 0;// ����״ͼ����ʾ����
	private int[] text;
	// ��������������
	private String[] ySteps;
	// ������ײ���������
	private String[] xWeeks;

	private HistogramAnimation ani;

	public PartViewforsex(Context context) {
		super(context);
		init(context, null);
	}

	public PartViewforsex(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		ySteps = new String[] { "100%", "75%", "50%", "25%", "0" };
		xWeeks = new String[] { "Ů", "��" };
		text = new int[] { 0, 0 };
		aniProgress = new double[] { 0, 0 };
		ani = new HistogramAnimation();
		ani.setDuration(100);

		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();

		xLinePaint.setColor(Color.DKGRAY);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
	}

	public void setText(int[] text) {

		this.text = text;

		this.postInvalidate();// �������߳� ������ͼ�ķ������á�
	}

	public void setProgress(double[] data) {
		this.progress = data;
		// this.invalidate(); //ʧЧ����˼��
		// this.postInvalidate(); // �������߳� ������ͼ�ķ������á�
		this.startAnimation(ani);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight() - 200;

		// 1 ���������ߣ�startX, startY, stopX, stopY, paint
		int startX = dip2px(getContext(), 50);
		int startY = dip2px(getContext(), 10);
		int stopX = dip2px(getContext(), 50);
		int stopY = dip2px(getContext(), 320);
		canvas.drawLine(100, 10, 100, height, xLinePaint);

		canvas.drawLine(100, height, width - 10, height, xLinePaint);

		// 2 ���������ڲ���ˮƽ��

		int leftHeight = height - 20;// ������ܵ� ��Ҫ���ֵĸ߶ȣ�

		int hPerHeight = leftHeight / 4;// �ֳ��Ĳ���

		hLinePaint.setTextAlign(Align.CENTER);
		for (int i = 0; i < 4; i++) {
			canvas.drawLine(100, 20 + i * hPerHeight, width - 10, 20 + i * hPerHeight, hLinePaint);
		}

		// 3 ���� Y ������

		titlePaint.setTextAlign(Align.RIGHT);
		titlePaint.setTextSize(25);
		titlePaint.setAntiAlias(true);
		titlePaint.setStyle(Paint.Style.FILL);
		for (int i = 0; i < ySteps.length; i++) {
			canvas.drawText(ySteps[i], 100, 30 + i * hPerHeight, titlePaint);
		}

		// 4 ���� X �� ������
		int xAxisLength = width - 30;
		int columCount = xWeeks.length + 1;
		int step = xAxisLength / columCount;
		int temp = step;
		for (int i = 0; i < columCount - 1; i++) {
			// text, baseX, baseY, textPaint
			canvas.drawText(xWeeks[i], 55 + temp * (i + 1), height + 60, titlePaint);
		}

		// 5 ���ƾ���

		if (aniProgress != null && aniProgress.length > 0) {
			for (int i = 0; i < aniProgress.length; i++) {// ѭ��������7����״ͼ�λ�����
				double value = aniProgress[i];
				paint.setAntiAlias(true);// �����Ч��
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(22);// �����С
				paint.setColor(Color.parseColor("#c40505"));// ������ɫ
				Rect rect = new Rect();// ��״ͼ����״

				rect.left = 30 + step * (i + 1) - 40;
				rect.right = 30 + step * (i + 1) + 40;
				int rh = (int) (leftHeight - leftHeight * (value / Statisticsforsex.total));
				rect.top = rh + 20;
				rect.bottom = height;

				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.column);

				canvas.drawBitmap(bitmap, null, rect, paint);

				if (this.text[i] == TRUE) {
					canvas.drawText(value + "", 30 + step * (i + 1) - 30, rh + 10, paint);
				}

			}
		}

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * ����animation��һ��������
	 * 
	 * @author ���볬
	 *
	 */
	private class HistogramAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = (int) (progress[i] * interpolatedTime);
				}
			} else {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = progress[i];
				}
			}
			postInvalidate();
		}
	}

}