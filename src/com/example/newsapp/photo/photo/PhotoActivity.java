package com.example.newsapp.photo.photo;

import java.util.ArrayList;

import com.example.newsapp.photo.ActivityManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;

public class PhotoActivity extends Activity implements View.OnClickListener {
	private GridView gv;
	private Album album;
	private PicAdappter adapter;
	private TextView tv;
	private int chooseNum = 0;
	private Button finishBtn;// ��ɰ�ť
	private int pic_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_list_album_gridview);
		tv = (TextView) findViewById(R.id.photo_album_chooseNum);
		finishBtn = (Button) findViewById(R.id.finishBtn);
		album = (Album) getIntent().getExtras().get("album");
		pic_number = (Integer) getIntent().getExtras().get("pic_number");
		Log.e("pic_number from PhotoActivity", "pic_number" + pic_number);
		/** ��ȡ�Ѿ�ѡ���ͼƬ **/
		for (int i = 0; i < album.getBitList().size(); i++) {
			if (album.getBitList().get(i).isSelect()) {
				chooseNum++;
			}
		}
		chooseNum = pic_number;
		gv = (GridView) findViewById(R.id.photo_gridview);
		adapter = new PicAdappter(this, album);
		gv.setAdapter(adapter);
		tv.setText("ѡ��" + chooseNum + "��");
		finishBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Item> fileNamesList = new ArrayList<Item>();
				for (int i = 0; i < album.getBitList().size(); i++) {
					if (album.getBitList().get(i).isSelect()) {
						fileNamesList.add(album.getBitList().get(i));
					}
				}
				Intent it = new Intent();
				it.putParcelableArrayListExtra("fileNames", fileNamesList);
				ActivityManager.getActivity("PhotoAlbumActivity").setResult(RESULT_OK, it);
				ActivityManager.getActivity("PhotoAlbumActivity").finish();
				ActivityManager.removeActivity("PhotoAlbumActivity");
				PhotoActivity.this.finish();
			}
		});
	}

	public void back(View v) {
		this.finish();
	}

	@Override
	public void onClick(View view) {
		if (view.getTag() != null) {
			if (chooseNum < 9) {

				if (view.getTag().toString().startsWith("select")) {
					String vPosition = view.getTag().toString().substring(7);
					if (album.getBitList().get(Integer.parseInt(vPosition)).isSelect()) {
						album.getBitList().get(Integer.parseInt(vPosition)).setSelect(false);
						chooseNum--;
					} else {
						album.getBitList().get(Integer.parseInt(vPosition)).setSelect(true);
						chooseNum++;
					}
					tv.setText("ѡ��" + chooseNum + "��");
					adapter.notifyDataSetChanged();
				} else {
					String vPosition = view.getTag().toString().substring(6);
					if (album.getBitList().get(Integer.parseInt(vPosition)).isSelect()) {
						album.getBitList().get(Integer.parseInt(vPosition)).setSelect(false);
						chooseNum--;
					} else {
						album.getBitList().get(Integer.parseInt(vPosition)).setSelect(true);
						chooseNum++;
					}
					tv.setText("ѡ��" + chooseNum + "��");
					adapter.notifyDataSetChanged();
					// Intent intent = new Intent(PhotoActivity.this,
					// ViewPagerActivity.class);
					// final String paths =
					// album.getBitList().get(Integer.parseInt(vPosition)).getPhotoPath();
					// new
					// AlertDialog.Builder(PhotoActivity.this).setMessage(paths)
					// .setPositiveButton("ɾ����", new
					// DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface arg0, int arg1) {
					// File f = new File(paths);
					// if(!f.exists()){
					// Toast.makeText(PhotoActivity.this, "��ͼƬ������",
					// Toast.LENGTH_SHORT).show();
					// }else{
					// f.delete();
					// }
					// }
					// })
					// .setNegativeButton("ȡ��", null)
					// .show();
					//
					// //List->>ArrayList
					// ArrayList<Item> fileNames= new ArrayList<Item> ();
					// for(int i = 0;i<album.getBitList().size();i++){
					// fileNames.add(album.getBitList().get(i));
					// }
					// intent.putExtra(ViewPagerActivity.FILES, fileNames);
					// intent.putExtra(ViewPagerActivity.CURRENT_INDEX,
					// Integer.parseInt(vPosition));
					//// startActivity(intent);
				}
			} else {
				Toast.makeText(getApplicationContext(), "������ͼƬ", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
