package com.ipin.activity;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.ipin.application.IpinApplication;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.PostActivityConst;
import com.ipin.utils.PostActivitySwitcher;
import com.ipin.utils.Tips;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import javax.microedition.khronos.opengles.GL;

@SuppressWarnings("deprecation")
public class UserLocation extends MapActivity implements OnItemClickListener,
		PostActivityConst {
	private static final String TAG = "Index_UserLocation";
	
	private SensorManager mSensorManager;
	private RotateView mRotateView;
	private MapView mMapView;
	private MyLocationOverlay mMyLocationOverlay;

	// 创建菜单选项
	private SoftwareMenu softwareMenu = null;
	// 页面转换器
	private Intent intent = null;

	private class RotateView extends ViewGroup implements SensorListener {
		private static final float SQ2 = 1.414213562373095f;
		private final SmoothCanvas mCanvas = new SmoothCanvas();
		private float mHeading = 0;

		public RotateView(Context context) {
			super(context);
		}

		public void onSensorChanged(int sensor, float[] values) {
			// Log.d(TAG, "x: " + values[0] + "y: " + values[1] + "z: " +
			// values[2]);
			synchronized (this) {
				mHeading = values[0];
				invalidate();
			}
		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			canvas.save(Canvas.MATRIX_SAVE_FLAG);
			canvas.rotate(-mHeading, getWidth() * 0.5f, getHeight() * 0.5f);
			mCanvas.delegate = canvas;
			super.dispatchDraw(mCanvas);
			canvas.restore();
		}

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			final int width = getWidth();
			final int height = getHeight();
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View view = getChildAt(i);
				final int childWidth = view.getMeasuredWidth();
				final int childHeight = view.getMeasuredHeight();
				final int childLeft = (width - childWidth) / 2;
				final int childTop = (height - childHeight) / 2;
				view.layout(childLeft, childTop, childLeft + childWidth,
						childTop + childHeight);
			}
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
			int h = getDefaultSize(getSuggestedMinimumHeight(),
					heightMeasureSpec);
			int sizeSpec;
			if (w > h) {
				sizeSpec = MeasureSpec.makeMeasureSpec((int) (w * SQ2),
						MeasureSpec.EXACTLY);
			} else {
				sizeSpec = MeasureSpec.makeMeasureSpec((int) (h * SQ2),
						MeasureSpec.EXACTLY);
			}
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				getChildAt(i).measure(sizeSpec, sizeSpec);
			}
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			// TODO: rotate events too
			return super.dispatchTouchEvent(ev);
		}

		public void onAccuracyChanged(int sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mRotateView = new RotateView(this);
		mMapView = new MapView(this, "MapViewCompassDemo_DummyAPIKey");
		mRotateView.addView(mMapView);
		setContentView(mRotateView);

		mMapView.setSatellite(true);
		mMapView.setStreetView(true);
		
		mMyLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMyLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mMapView.getController().animateTo(
						mMyLocationOverlay.getMyLocation());
			}
		});
		mMapView.getOverlays().add(mMyLocationOverlay);
		mMapView.getController().setZoom(18);
		mMapView.setClickable(true);
		mMapView.setEnabled(true);

		// 创建菜单选项
		softwareMenu = new SoftwareMenu(this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager
				.registerListener(mRotateView,
						SensorManager.SENSOR_ORIENTATION,
						SensorManager.SENSOR_DELAY_UI);
		mMyLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(mRotateView);
		mMyLocationOverlay.disableMyLocation();
		super.onStop();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	static final class SmoothCanvas extends Canvas {
		Canvas delegate;

		private final Paint mSmooth = new Paint(Paint.FILTER_BITMAP_FLAG);

		@Override
		public void setBitmap(Bitmap bitmap) {
			delegate.setBitmap(bitmap);
		}

		@Override
		public void setViewport(int width, int height) {
			delegate.setViewport(width, height);
		}

		@Override
		public boolean isOpaque() {
			return delegate.isOpaque();
		}

		@Override
		public int getWidth() {
			return delegate.getWidth();
		}

		@Override
		public int getHeight() {
			return delegate.getHeight();
		}

		@Override
		public int save() {
			return delegate.save();
		}

		@Override
		public int save(int saveFlags) {
			return delegate.save(saveFlags);
		}

		@Override
		public int saveLayer(RectF bounds, Paint paint, int saveFlags) {
			return delegate.saveLayer(bounds, paint, saveFlags);
		}

		@Override
		public int saveLayer(float left, float top, float right, float bottom,
				Paint paint, int saveFlags) {
			return delegate.saveLayer(left, top, right, bottom, paint,
					saveFlags);
		}

		@Override
		public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags) {
			return delegate.saveLayerAlpha(bounds, alpha, saveFlags);
		}

		@Override
		public int saveLayerAlpha(float left, float top, float right,
				float bottom, int alpha, int saveFlags) {
			return delegate.saveLayerAlpha(left, top, right, bottom, alpha,
					saveFlags);
		}

		@Override
		public void restore() {
			delegate.restore();
		}

		@Override
		public int getSaveCount() {
			return delegate.getSaveCount();
		}

		@Override
		public void restoreToCount(int saveCount) {
			delegate.restoreToCount(saveCount);
		}

		@Override
		public void translate(float dx, float dy) {
			delegate.translate(dx, dy);
		}

		@Override
		public void scale(float sx, float sy) {
			delegate.scale(sx, sy);
		}

		@Override
		public void rotate(float degrees) {
			delegate.rotate(degrees);
		}

		@Override
		public void skew(float sx, float sy) {
			delegate.skew(sx, sy);
		}

		@Override
		public void concat(Matrix matrix) {
			delegate.concat(matrix);
		}

		@Override
		public void setMatrix(Matrix matrix) {
			delegate.setMatrix(matrix);
		}

		@Override
		public void getMatrix(Matrix ctm) {
			delegate.getMatrix(ctm);
		}

		@Override
		public boolean clipRect(RectF rect, Region.Op op) {
			return delegate.clipRect(rect, op);
		}

		@Override
		public boolean clipRect(Rect rect, Region.Op op) {
			return delegate.clipRect(rect, op);
		}

		@Override
		public boolean clipRect(RectF rect) {
			return delegate.clipRect(rect);
		}

		@Override
		public boolean clipRect(Rect rect) {
			return delegate.clipRect(rect);
		}

		@Override
		public boolean clipRect(float left, float top, float right,
				float bottom, Region.Op op) {
			return delegate.clipRect(left, top, right, bottom, op);
		}

		@Override
		public boolean clipRect(float left, float top, float right, float bottom) {
			return delegate.clipRect(left, top, right, bottom);
		}

		@Override
		public boolean clipRect(int left, int top, int right, int bottom) {
			return delegate.clipRect(left, top, right, bottom);
		}

		@Override
		public boolean clipPath(Path path, Region.Op op) {
			return delegate.clipPath(path, op);
		}

		@Override
		public boolean clipPath(Path path) {
			return delegate.clipPath(path);
		}

		@Override
		public boolean clipRegion(Region region, Region.Op op) {
			return delegate.clipRegion(region, op);
		}

		@Override
		public boolean clipRegion(Region region) {
			return delegate.clipRegion(region);
		}

		@Override
		public DrawFilter getDrawFilter() {
			return delegate.getDrawFilter();
		}

		@Override
		public void setDrawFilter(DrawFilter filter) {
			delegate.setDrawFilter(filter);
		}

		@Override
		public GL getGL() {
			return delegate.getGL();
		}

		@Override
		public boolean quickReject(RectF rect, EdgeType type) {
			return delegate.quickReject(rect, type);
		}

		@Override
		public boolean quickReject(Path path, EdgeType type) {
			return delegate.quickReject(path, type);
		}

		@Override
		public boolean quickReject(float left, float top, float right,
				float bottom, EdgeType type) {
			return delegate.quickReject(left, top, right, bottom, type);
		}

		@Override
		public boolean getClipBounds(Rect bounds) {
			return delegate.getClipBounds(bounds);
		}

		@Override
		public void drawRGB(int r, int g, int b) {
			delegate.drawRGB(r, g, b);
		}

		@Override
		public void drawARGB(int a, int r, int g, int b) {
			delegate.drawARGB(a, r, g, b);
		}

		@Override
		public void drawColor(int color) {
			delegate.drawColor(color);
		}

		@Override
		public void drawColor(int color, PorterDuff.Mode mode) {
			delegate.drawColor(color, mode);
		}

		@Override
		public void drawPaint(Paint paint) {
			delegate.drawPaint(paint);
		}

		@Override
		public void drawPoints(float[] pts, int offset, int count, Paint paint) {
			delegate.drawPoints(pts, offset, count, paint);
		}

		@Override
		public void drawPoints(float[] pts, Paint paint) {
			delegate.drawPoints(pts, paint);
		}

		@Override
		public void drawPoint(float x, float y, Paint paint) {
			delegate.drawPoint(x, y, paint);
		}

		@Override
		public void drawLine(float startX, float startY, float stopX,
				float stopY, Paint paint) {
			delegate.drawLine(startX, startY, stopX, stopY, paint);
		}

		@Override
		public void drawLines(float[] pts, int offset, int count, Paint paint) {
			delegate.drawLines(pts, offset, count, paint);
		}

		@Override
		public void drawLines(float[] pts, Paint paint) {
			delegate.drawLines(pts, paint);
		}

		@Override
		public void drawRect(RectF rect, Paint paint) {
			delegate.drawRect(rect, paint);
		}

		@Override
		public void drawRect(Rect r, Paint paint) {
			delegate.drawRect(r, paint);
		}

		@Override
		public void drawRect(float left, float top, float right, float bottom,
				Paint paint) {
			delegate.drawRect(left, top, right, bottom, paint);
		}

		@Override
		public void drawOval(RectF oval, Paint paint) {
			delegate.drawOval(oval, paint);
		}

		@Override
		public void drawCircle(float cx, float cy, float radius, Paint paint) {
			delegate.drawCircle(cx, cy, radius, paint);
		}

		@Override
		public void drawArc(RectF oval, float startAngle, float sweepAngle,
				boolean useCenter, Paint paint) {
			delegate.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
		}

		@Override
		public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {
			delegate.drawRoundRect(rect, rx, ry, paint);
		}

		@Override
		public void drawPath(Path path, Paint paint) {
			delegate.drawPath(path, paint);
		}

		@Override
		public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {
			if (paint == null) {
				paint = mSmooth;
			} else {
				paint.setFilterBitmap(true);
			}
			delegate.drawBitmap(bitmap, left, top, paint);
		}

		@Override
		public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
			if (paint == null) {
				paint = mSmooth;
			} else {
				paint.setFilterBitmap(true);
			}
			delegate.drawBitmap(bitmap, src, dst, paint);
		}

		@Override
		public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
			if (paint == null) {
				paint = mSmooth;
			} else {
				paint.setFilterBitmap(true);
			}
			delegate.drawBitmap(bitmap, src, dst, paint);
		}

		@Override
		public void drawBitmap(int[] colors, int offset, int stride, int x,
				int y, int width, int height, boolean hasAlpha, Paint paint) {
			if (paint == null) {
				paint = mSmooth;
			} else {
				paint.setFilterBitmap(true);
			}
			delegate.drawBitmap(colors, offset, stride, x, y, width, height,
					hasAlpha, paint);
		}

		@Override
		public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
			if (paint == null) {
				paint = mSmooth;
			} else {
				paint.setFilterBitmap(true);
			}
			delegate.drawBitmap(bitmap, matrix, paint);
		}

		@Override
		public void drawBitmapMesh(Bitmap bitmap, int meshWidth,
				int meshHeight, float[] verts, int vertOffset, int[] colors,
				int colorOffset, Paint paint) {
			delegate.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts,
					vertOffset, colors, colorOffset, paint);
		}

		@Override
		public void drawVertices(VertexMode mode, int vertexCount,
				float[] verts, int vertOffset, float[] texs, int texOffset,
				int[] colors, int colorOffset, short[] indices,
				int indexOffset, int indexCount, Paint paint) {
			delegate.drawVertices(mode, vertexCount, verts, vertOffset, texs,
					texOffset, colors, colorOffset, indices, indexOffset,
					indexCount, paint);
		}

		@Override
		public void drawText(char[] text, int index, int count, float x,
				float y, Paint paint) {
			delegate.drawText(text, index, count, x, y, paint);
		}

		@Override
		public void drawText(String text, float x, float y, Paint paint) {
			delegate.drawText(text, x, y, paint);
		}

		@Override
		public void drawText(String text, int start, int end, float x, float y,
				Paint paint) {
			delegate.drawText(text, start, end, x, y, paint);
		}

		@Override
		public void drawText(CharSequence text, int start, int end, float x,
				float y, Paint paint) {
			delegate.drawText(text, start, end, x, y, paint);
		}

		@Override
		public void drawPosText(char[] text, int index, int count, float[] pos,
				Paint paint) {
			delegate.drawPosText(text, index, count, pos, paint);
		}

		@Override
		public void drawPosText(String text, float[] pos, Paint paint) {
			delegate.drawPosText(text, pos, paint);
		}

		@Override
		public void drawTextOnPath(char[] text, int index, int count,
				Path path, float hOffset, float vOffset, Paint paint) {
			delegate.drawTextOnPath(text, index, count, path, hOffset, vOffset,
					paint);
		}

		@Override
		public void drawTextOnPath(String text, Path path, float hOffset,
				float vOffset, Paint paint) {
			delegate.drawTextOnPath(text, path, hOffset, vOffset, paint);
		}

		@Override
		public void drawPicture(Picture picture) {
			delegate.drawPicture(picture);
		}

		@Override
		public void drawPicture(Picture picture, RectF dst) {
			delegate.drawPicture(picture, dst);
		}

		@Override
		public void drawPicture(Picture picture, Rect dst) {
			delegate.drawPicture(picture, dst);
		}
	}

	// 创建MENU选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	// 创建MENU监听
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (softwareMenu.isInitialDialog()) {
			softwareMenu.showDialog();
		} else {
			softwareMenu.initialAndShowDialog();
		}
		return false;
	}

	// 菜单点击监听
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int itemNumber, long longNumber) {
		// TODO Auto-generated method stub
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication)UserLocation.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			Tips.exitAlertTips(this);
			return;
		}
		intent = PostActivitySwitcher.getSoftwareSwitchIntent(this, tag);
		if (intent == null) {
			return;
		} else {
			startActivity(intent);
			finish();
		}
	}

	// 防止屏幕切换刷新
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}
}
