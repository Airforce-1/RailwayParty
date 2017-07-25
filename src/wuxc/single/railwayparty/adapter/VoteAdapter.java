package wuxc.single.railwayparty.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.adapter.Bbs4Adapter.Callback;
import wuxc.single.railwayparty.cache.VoteCache;
import wuxc.single.railwayparty.internet.ImageLoader;
import wuxc.single.railwayparty.internet.ImageLoader.ImageCallback;
import wuxc.single.railwayparty.internet.URLcontainer;
import wuxc.single.railwayparty.model.VoteModel;;

public class VoteAdapter extends ArrayAdapter<VoteModel> implements OnClickListener {
	private ListView listView;
	private ImageLoader ImageLoader;
	private String imageurl = "";
	private int screenwidth = 0;
	private Activity thisactivity;
	private Callback mCallback;

	public VoteAdapter(Activity activity, List<VoteModel> imageAndTexts, ListView listView, Callback callback) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		this.thisactivity = activity;
		ImageLoader = new ImageLoader();
		mCallback = callback;
	}

	public interface Callback {
		public void click(View v);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mCallback.click(v);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		// Inflate the views from XML
		View rowView = convertView;
		VoteCache viewCache;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			int temp = position % 2;
			if (temp == 0) {
				rowView = inflater.inflate(R.layout.item_vote_right, null);
			} else {
				rowView = inflater.inflate(R.layout.item_vote_left, null);
			}

			viewCache = new VoteCache(rowView);
			rowView.setTag(viewCache);
		} else {
			viewCache = (VoteCache) rowView.getTag();
		}
		VoteModel imageAndText = getItem(position);

		// Load the image and set it on the ImageView
		String imageUrl = imageAndText.getImageUrl();
		ImageView imageView = viewCache.getImageHeadimg();
		imageView.setTag(URLcontainer.urlip + "upload" + imageUrl);
		Log.e("imageUrl", imageUrl);
		if (imageUrl.equals(imageurl) || imageUrl.equals("null")) {
			imageView.setImageResource(R.drawable.vote);
		} else {
			try {
				// String imageName1 = getBitName(imageUrl);
				// String temppath = Environment.getExternalStorageDirectory() +
				// "/chat/" + imageName1 + ".png";
				Bitmap bm1 = null;
				// bm1 = getBitmapByPath(temppath);
				if (bm1 == null) {
					imageUrl = URLcontainer.urlip + "upload" + imageUrl;
					Log.e("imageUrl", imageUrl);
					Drawable cachedImage = ImageLoader.loadDrawable(imageUrl, new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable, String imageUrl) {
							ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
							}
						}
					});
					if (cachedImage == null) {
						imageView.setImageResource(R.drawable.vote);
					} else {
						Drawable d = cachedImage; // xxx根据自己的情况获取drawable

						BitmapDrawable bd = (BitmapDrawable) d;

						Bitmap bm = bd.getBitmap();
						// bm = cutBmp(bm);
						imageView.setImageBitmap(bm);
					}
				} else {
					imageView.setImageBitmap(bm1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
			}

		}
		TextView TextTitle = viewCache.getTextTitle();
		TextTitle.setText("" + imageAndText.getTitle());
		TextView TextDetail = viewCache.getTextDetail();
		TextDetail.setText("" + imageAndText.getDetail());
		LinearLayout lin_all = viewCache.getlin_all();
		lin_all.setTag(position);
		lin_all.setOnClickListener(this);
		return rowView;
	}

	// public Bitmap getBitmapByPath(String fileName) {
	// // String myJpgPath =
	// // Environment.getExternalStorageDirectory()+"pepper/" + fileName;
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// // options.inSampleSize = 12;
	// Bitmap bm = BitmapFactory.decodeFile(fileName, options);
	// return bm;
	// }
	//
	// private String getBitName(String imageUrl) {
	// // TODO Auto-generated method stub
	// String[] temp = imageUrl.split("");
	// String result = "";
	// for (int i = 0; i < temp.length; i++) {
	// if (temp[i].equals("/") || temp[i].equals(".")) {
	// temp[i] = "";
	// }
	// result = result + temp[i];
	// }
	// return result;
	// }
	//
	// public void saveMyBitmap(String bitName, Bitmap mBitmap) throws
	// IOException {
	// String path = Environment.getExternalStorageDirectory() + "/chat/";
	// String myJpgPath = Environment.getExternalStorageDirectory() + "/chat/" +
	// bitName + ".png";
	// File tmp = new File(path);
	// if (!tmp.exists()) {
	// tmp.mkdir();
	// }
	// File f = new File(myJpgPath);
	// f.createNewFile();
	// FileOutputStream fOut = null;
	// try {
	// fOut = new FileOutputStream(f);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
	// try {
	// fOut.flush();
	// fOut.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public Bitmap cutBmp(Bitmap bmp) {
	// Bitmap result;
	// int w = bmp.getWidth();// 输入长方形宽
	// int h = bmp.getHeight();// 输入长方形高
	// int nw;// 输出正方形宽
	// result = Bitmap.createBitmap(bmp, 15 * w / 100, 15 * h / 100, 7 * w / 10,
	// 7 * h / 10);
	// // }
	// return result;
	// }
}
