package com.example.newsapp.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import com.example.newsapp.photo.adapter.AddImageGridAdapter;
import com.example.newsapp.photo.controller.SelectPicPopupWindow;
import com.example.newsapp.photo.photo.Item;
import com.example.newsapp.photo.photo.PhotoAlbumActivity;
import com.example.newsapp.photo.photoviewer.photoviewerinterface.ViewPagerActivity;
import com.example.newsapp.photo.photoviewer.photoviewerinterface.ViewPagerDeleteActivity;
import com.example.newsapp.photo.util.PictureManageUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;

public class MainActivity extends Activity {

	/* ������ʶ�������๦�ܵ�activity */
	private final int CAMERA_WITH_DATA = 3023;
	/* ������ʶ����gallery��activity */
	private final int PHOTO_PICKED_WITH_DATA = 3021;
	// GridViewԤ��ɾ��ҳ�����
	private final int PIC_VIEW_CODE = 2016;
	/* ���յ���Ƭ�洢λ�� */
	private final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/Android/data/com.photo.choosephotos");
	private File mCurrentPhotoFile;// ��������յõ���ͼƬ
	// ������ʾԤ��ͼ
	private ArrayList<Bitmap> microBmList = new ArrayList<Bitmap>();
	// ��ѡͼ����Ϣ(��Ҫ��·��)
	private ArrayList<Item> photoList = new ArrayList<Item>();
	private AddImageGridAdapter imgAdapter;
	private Bitmap addNewPic;
	private GridView gridView;// ��ʾ�����ϴ�ͼƬ
	private SelectPicPopupWindow menuWindow;
	private int pic_number = 0;
	private int screenwidth = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_main_pic);
		screenwidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();

		if (!(PHOTO_DIR.exists() && PHOTO_DIR.isDirectory())) {
			PHOTO_DIR.mkdirs();
		}
		// ���ͼƬ
		gridView = (GridView) findViewById(R.id.allPic);
		// �Ӻ�ͼƬ
		addNewPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.add_new_pic);
		// addNewPic = PictureManageUtil.resizeBitmap(addNewPic, 180, 180);
		pic_number = microBmList.size();
		Log.e("pic_number", "pic_number" + pic_number);
		if (pic_number < 9) {
			microBmList.add(addNewPic);
		}

		imgAdapter = new AddImageGridAdapter(this, microBmList, screenwidth);
		gridView.setAdapter(imgAdapter);
		// �¼����������GridView���ͼƬʱ����ImageView����ʾ����
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (position == (photoList.size())) {
					menuWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
					menuWindow.showAtLocation(MainActivity.this.findViewById(R.id.uploadPictureLayout),
							Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				} else {
					Intent intent = new Intent(MainActivity.this, ViewPagerDeleteActivity.class);
					intent.putParcelableArrayListExtra("files", photoList);
					intent.putExtra(ViewPagerActivity.CURRENT_INDEX, position);
					startActivityForResult(intent, PIC_VIEW_CODE);
				}
			}
		});
	}

	// Ϊ��������ʵ�ּ�����
	private View.OnClickListener itemsOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo: {
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					// �ж��Ƿ���SD��
					doTakePhoto();// �û�����˴��������ȡ
				} else {
					Toast.makeText(MainActivity.this, "û��SD��", Toast.LENGTH_LONG).show();
				}
				break;
			}
			case R.id.btn_pick_photo: {
				// ��ѡ��ͼƬ����
				doPickPhotoFromGallery();
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * ���ջ�ȡͼƬ
	 * 
	 */
	protected void doTakePhoto() {
		try {
			// ������Ƭ�Ĵ洢Ŀ¼
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// �����յ���Ƭ�ļ�����
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "�Ҳ������", Toast.LENGTH_LONG).show();
		}
	}

	public String getPhotoFileName() {
		UUID uuid = UUID.randomUUID();
		return uuid + ".jpg";
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	// ����Gallery����
	protected void doPickPhotoFromGallery() {
		try {
			final ProgressDialog dialog;
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // ����ΪԲ��
			dialog.setMessage("���ݼ�����...");
			dialog.setIndeterminate(false);//
			// dialog.setCancelable(true); //�����˼�ȡ��
			dialog.show();
			Window window = dialog.getWindow();
			View view = window.getDecorView();
			// Tools.setViewFontSize(view,21);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					// ��ʼ����ʾ��
					dialog.dismiss();
				}

			}, 1000);
			// final Intent intent = new
			// Intent(MainActivity.this,GetAllImgFolderActivity.class);
			final Intent intent = new Intent(MainActivity.this, PhotoAlbumActivity.class);
			Bundle bundle = new Bundle();

			bundle.putInt("pic_number", pic_number);
			intent.putExtras(bundle);
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "ͼ�����Ҳ�����Ƭ", Toast.LENGTH_LONG).show();
		}
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				imgAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * ��������ҳ�淵������
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {
			// ����Gallery���ص�
			ArrayList<Item> tempFiles = new ArrayList<Item>();
			if (data == null)
				return;
			tempFiles = data.getParcelableArrayListExtra("fileNames");
			Log.e("test", "��ѡ�е���Ƭ" + tempFiles.toString());

			if (tempFiles == null) {
				return;
			}
			microBmList.remove(addNewPic);
			Bitmap bitmap;
			for (int i = 0; i < tempFiles.size(); i++) {
				bitmap = MediaStore.Images.Thumbnails.getThumbnail(this.getContentResolver(),
						tempFiles.get(i).getPhotoID(), Thumbnails.MINI_KIND, null);
				int rotate = PictureManageUtil.getCameraPhotoOrientation(tempFiles.get(i).getPhotoPath());
				bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
				microBmList.add(bitmap);
				photoList.add(tempFiles.get(i));
			}

			pic_number = microBmList.size();
			Log.e("pic_number", "pic_number" + pic_number);
			if (pic_number < 9) {
				microBmList.add(addNewPic);
			}
			imgAdapter.notifyDataSetChanged();
			break;
		}
		case CAMERA_WITH_DATA: {
			Long delayMillis = 0L;
			if (mCurrentPhotoFile == null) {
				delayMillis = 500L;
			}
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// ��������򷵻ص�,�ٴε���ͼƬ��������ȥ�޼�ͼƬ
					// ȥ��GridView��ļӺ�
					microBmList.remove(addNewPic);
					Item item = new Item();
					item.setPhotoPath(mCurrentPhotoFile.getAbsolutePath());
					photoList.add(item);
					// ����·�����õ�һ��ѹ������Bitmap����߽ϴ�ı��500��������ѹ����
					Bitmap bitmap = PictureManageUtil.getCompressBm(mCurrentPhotoFile.getAbsolutePath());
					// ��ȡ��ת����
					int rotate = PictureManageUtil.getCameraPhotoOrientation(mCurrentPhotoFile.getAbsolutePath());
					// ��ѹ����ͼƬ������ת
					bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
					microBmList.add(bitmap);
					pic_number = microBmList.size();
					Log.e("pic_number", "pic_number" + pic_number);
					if (pic_number < 9) {
						microBmList.add(addNewPic);
					}
					Message msg = handler.obtainMessage(1);
					msg.sendToTarget();
				}
			}, delayMillis);
			break;
		}
		case PIC_VIEW_CODE: {
			ArrayList<Integer> deleteIndex = data.getIntegerArrayListExtra("deleteIndexs");
			if (deleteIndex.size() > 0) {
				for (int i = deleteIndex.size() - 1; i >= 0; i--) {
					microBmList.remove((int) deleteIndex.get(i));
					photoList.remove((int) deleteIndex.get(i));
				}
			}
			pic_number = microBmList.size();
			Log.e("pic_number", "pic_number" + pic_number);
			if (pic_number < 9 && photoList.size() >= microBmList.size()) {
				microBmList.add(addNewPic);
			}
			imgAdapter.notifyDataSetChanged();
			break;
		}
		}
	}

}
