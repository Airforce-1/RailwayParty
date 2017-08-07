package wuxc.single.railwayparty.start;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.adapter.imagePPTAdapter;
import wuxc.single.railwayparty.adapter.imagePPTAdapter.Callback;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.model.imagePPTModel;

public class ImagePPT extends Activity implements OnTouchListener, Callback, OnClickListener, OnItemClickListener {
	private ListView ListData;
	List<imagePPTModel> list = new ArrayList<imagePPTModel>();
	private static imagePPTAdapter mAdapter;
	private int firstItemIndex = 0;
	private int lastItemIndex = 0;
	private float startY = 0;
	private float startYfoot = 0;
	private boolean isRecored;
	private boolean isRecoredfoot;
	private int pageSize = 10;
	private int totalPage = 10;
	private int curPage = 1;
	private final static int RATIO = 2;
	private TextView headTextView = null;
	private View view;// ����Fragment view
	private boolean[] read = { false, false, true, true, true, true, true, true, true, true, true , false, false, true, true, true, true, true, true, true, true, true , false, false, true, true, true, true, true, true, true, true, true , false, false, true, true, true, true, true, true, true, true, true , false, false, true, true, true, true, true, true, true, true, true };
	private int[] headimg = { R.drawable.ppt1, R.drawable.ppt2, R.drawable.ppt3, R.drawable.ppt4, R.drawable.ppt5,
			R.drawable.ppt6, R.drawable.ppt7, R.drawable.ppt8, R.drawable.ppt9, R.drawable.ppt10, R.drawable.ppt11,
			R.drawable.ppt12, R.drawable.ppt13, R.drawable.ppt14, R.drawable.ppt15, R.drawable.ppt16, R.drawable.ppt17,
			R.drawable.ppt18, R.drawable.ppt19, R.drawable.ppt20, R.drawable.ppt21, R.drawable.ppt22, R.drawable.ppt23,
			R.drawable.ppt24, R.drawable.ppt25, R.drawable.ppt26, R.drawable.ppt27, R.drawable.ppt28,
			R.drawable.ppt29 };
	private String ticket = "";
	private String chn;
	private String userPhoto;
	private String LoginId;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private SharedPreferences PreALLChannel;// �洢����Ƶ����Ϣ
	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private TextView TextArticle;
	private TextView TextVideo;
	private int type = 2;
	private int screenwidth = 0;
	private TextView text_title;
	private TextView text_time;
	private ImageView image_back;
	private String detail = "�˴�ר����ķ�Χ������ũ�񹤽϶�Ľ��������졢�ɿ󡢲�����������С���Ͷ��ܼ�����ҵ�Լ����徭����֯��������ݰ������ǹ���ҵ���Ͷ���ǩ���Ͷ���ͬ��������չ���֧���йع涨֧��ְ�����������������͹��ʹ涨������֧���Ӱ๤������������μ���ᱣ�պͽ�����ᱣ�շ���������ؽ�ֹʹ��ͯ���涨�Լ�Ůְ����δ���깤�����Ͷ������涨��������������Ͷ����Ϸ��ɷ���������";
	private String Id = "";
	private String Name;
	private String Title;
	private String Time;

	public Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_DUE_DATA:
				GetDataDueData(msg.obj);
				break;
			default:
				break;
			}
		}
	};

	protected void GetDataDueData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;
		String pager = null;
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");
			// pager = demoJson.getString("pager");
			Data = demoJson.getString("datas");
			if (Type.equals(GET_SUCCESS_RESULT)) {
				GetPager(Data);
				GetDataList(Data, curPage);
			} else if (Type.equals(GET_FAIL_RESULT)) {
				Toast.makeText(getApplicationContext(), "����������ʧ��", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "���ݸ�ʽУ��ʧ��", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void GetDataList(String data, int arg) {
		;
		if (arg == 1) {
			list.clear();
		}
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(data);
			JSONObject json_data = null;
			if (jArray.length() == 0) {
				// / Toast.makeText(getApplicationContext(), "������",
				// Toast.LENGTH_SHORT).show();

			} else {
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);
					Log.e("json_data", "" + json_data);
					// JSONObject jsonObject = json_data.getJSONObject("data");
					imagePPTModel listinfo = new imagePPTModel();
					try {
						listinfo.setBi(json_data.getInt("iszengread"));
					} catch (Exception e) {
						// TODO: handle exception
						listinfo.setBi(0);
					}
					String date = getdate(json_data.getString("releaseDate"));
					listinfo.setTime(date);
					listinfo.setTitle(json_data.getString("title"));
					listinfo.setId(json_data.getString("keyid"));
					// listinfo.setBackGround(json_data.getString("sacleImage"));
					listinfo.setContent(json_data.getString("summary"));
					listinfo.setSummary(json_data.getString("summary"));
					listinfo.setCont(true);
					listinfo.setGuanzhu("231");
					listinfo.setZan("453");
					listinfo.setImageurl(headimg[i]);
					listinfo.setHeadimgUrl(json_data.getString("sacleImage"));
					listinfo.setRead(true);
					try {
						listinfo.setLink(json_data.getString("otherLinks"));
						if (json_data.getString("summary").equals("") || json_data.getString("summary") == null
								|| json_data.getString("summary").equals("null")) {
							listinfo.setContent(json_data.getString("source"));
							listinfo.setCont(false);
						}

					} catch (Exception e) {
						// TODO: handle exception
					}
					list.add(listinfo);

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (arg == 1) {
			go();
		} else {
			mAdapter.notifyDataSetChanged();
		}

	}

	private String getdate(String string) {
		// TODO Auto-generated method stub
		String result = "07-28";
		try {
			String[] bStrings = string.split("-");
			result = bStrings[1] + "-" + bStrings[2];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	private void GetPager(String pager) {
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(pager);

			totalPage = demoJson.getInt("totalPage");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wuxc_imageppt);
		Intent intent = this.getIntent(); // ��ȡ���е�intent����
		Bundle bundle = intent.getExtras(); // ��ȡintent�����bundle����
		Name = bundle.getString("Name");
		Title = bundle.getString("Title");
		Time = bundle.getString("Time");
		Id = bundle.getString("Id");

		chn = bundle.getString("chn");
		try {
			detail = bundle.getString("detail");
			ticket = bundle.getString("ticket");
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();
		screenwidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		setonclicklistener();
		setheadtextview();

		getdatalist(curPage);
		PreUserInfo = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		// GetData();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "�����" + position + "��",
		// Toast.LENGTH_SHORT).show();

		imagePPTModel data = list.get(position - 1);
		if (true) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), SpecialDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Title", data.getTitle());
			bundle.putString("Time", data.getTime());
			bundle.putString("detail", data.getContent());
			bundle.putString("chn", chn);
			bundle.putString("Id", data.getId());
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), webview.class);
			Bundle bundle = new Bundle();
			bundle.putString("url", data.getLink());
			// // bundle.putString("Time", "2016-11-23");
			// // bundle.putString("Name", "С��");
			// // bundle.putString("PageTitle", "�ղ�����");
			// // bundle.putString("Detail",
			// //
			// "�й��������������ţ���ƹ����ţ�ԭ���й�������������ţ����й��������쵼��һ������������������й�������ɵ�Ⱥ������֯������������ίԱ�����й�����ίԱ���쵼�������ŵĵط�������֯��ͬ������ίԱ���쵼��ͬʱ�ܹ������ϼ���֯�쵼��1922��5�£��ŵĵ�һ�δ������ڹ��ݾ��У���ʽ�����й�������������ţ�1925��1��26�ոĳ��й��������������š�1959��5��4�չ���������䲼�������Żա�");
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	private void GetData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();
		// ArrayValues.add(new BasicNameValuePair("ticket", ticket));
		// ArrayValues.add(new BasicNameValuePair("applyType", "" + 2));
		// ArrayValues.add(new BasicNameValuePair("helpSType", "" + type));
		// ArrayValues.add(new BasicNameValuePair("modelSign", "KNDY_APPLY"));
		// ArrayValues.add(new BasicNameValuePair("curPage", "" + curPage));
		// ArrayValues.add(new BasicNameValuePair("pageSize", "" + pageSize));
		// final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		// chn = GetChannelByKey.GetSign(PreALLChannel, "ְ��֮��");
		ArrayValues.add(new BasicNameValuePair("chn", "tzggd"));
		chn = "tzggd";
		ArrayValues.add(new BasicNameValuePair("curPage", "" + curPage));
		ArrayValues.add(new BasicNameValuePair("pageSize", "" + pageSize));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/cms/channel/channleListData", ArrayValues);
				Message msg = new Message();
				msg.obj = DueData;
				msg.what = GET_DUE_DATA;
				uiHandler.sendMessage(msg);
			}
		}).start();

	}

	private void ReadTicket() {
		// TODO Auto-generated method stub
		ticket = PreUserInfo.getString("ticket", "");
		userPhoto = PreUserInfo.getString("userPhoto", "");
		LoginId = PreUserInfo.getString("userName", "");
	}

	private void initview() {
		// TODO Auto-generated method stub
		ListData = (ListView) findViewById(R.id.list_data);
		text_title = (TextView) findViewById(R.id.text_title);
		text_time = (TextView) findViewById(R.id.text_time);
		image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		text_time.setText(Time);
		text_title.setText(Title);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		ListData.setOnItemClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		 float tempY = event.getY();
		 float tempyfoot = event.getY();
		 firstItemIndex = ListData.getFirstVisiblePosition();
		 lastItemIndex = ListData.getLastVisiblePosition();
		 // Toast.makeText(getApplicationContext(), " lastItemIndex" +
		 // lastItemIndex, Toast.LENGTH_SHORT).show();
		 switch (event.getAction()) {
		 case MotionEvent.ACTION_DOWN:
		 case MotionEvent.ACTION_MOVE:
		 if (!isRecored && (firstItemIndex == 0)) {
		 isRecored = true;
		 startY = tempY;
		 }
		 int temp = 1;
		 temp = (lastItemIndex) % pageSize;
		 if (!isRecoredfoot && (temp == 0)) {
		 isRecoredfoot = true;
		 startYfoot = tempyfoot;
		 }
		 break;
		 case MotionEvent.ACTION_UP:
		 case MotionEvent.ACTION_CANCEL:
		 isRecored = false;
		 isRecoredfoot = false;
		 break;
		
		 default:
		 break;
		 }
		
		 switch (event.getAction()) {
		 case MotionEvent.ACTION_DOWN:
		 break;
		 case MotionEvent.ACTION_UP:
		 case MotionEvent.ACTION_CANCEL:
		 ListData.setPadding(0, 0, 0, 0);
		 if (tempY - startY < 400) {
		 ListData.setPadding(0, -100, 0, 0);
		 } else {
		 curPage = 1;
		 Toast.makeText(getApplicationContext(), "����ˢ��",
		 Toast.LENGTH_SHORT).show();
			getdatalist(curPage);
		 }
		 int temp = 1;
		 temp = (lastItemIndex) % pageSize;
		 // temp = 0;
		 if (temp == 0 && (startYfoot - tempyfoot > 400)) {
		 curPage++;
		 if (curPage > totalPage) {
		 Toast.makeText(getApplicationContext(), " û�и�����",
		 Toast.LENGTH_SHORT).show();
		 // // listinfoagain();
		 } else {
		getdatalist(curPage);
		 Toast.makeText(getApplicationContext(), "���ڼ�����һҳ",
		 Toast.LENGTH_SHORT).show();
		 }
		
		 } else {
		
		 }
		 break;
		 case MotionEvent.ACTION_MOVE:
		 if (isRecored && tempY > startY) {
		 ListData.setPadding(0, (int) ((tempY - startY) / RATIO - 100), 0, 0);
		 }
		 if (isRecoredfoot && startYfoot > tempyfoot) {
		 // footTextView.setVisibility(View.VISIBLE);
		 ListData.setPadding(0, -100, 0, (int) ((startYfoot - tempyfoot) /
		 RATIO));
		 }
		 break;
		
		 default:
		 break;
		 }
		return false;
	}

	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	// // recommendModel data = list.get(position - 1);
	// // Intent intent = new Intent();
	// // intent.setClass(getApplicationContext(), SpecialDetailActivity.class);
	// // Bundle bundle = new Bundle();
	// // bundle.putString("Title", data.getTitle());
	// // bundle.putString("detail", data.getDetail());
	// // bundle.putString("Time", data.getTime());
	// // bundle.putString("Name", "����");
	// // intent.putExtras(bundle);
	// // startActivity(intent);
	// // Toast.makeText(getApplicationContext(), "�����" + position + "��" +
	// "item",
	// // Toast.LENGTH_SHORT).show();
	// Intent intent = new Intent();
	// intent.setClass(getApplicationContext(), DetailActivity.class);
	// Bundle bundle = new Bundle();
	// bundle.putInt("source", R.drawable.detail);
	// bundle.putInt("height", 3048);
	// bundle.putInt("width", 750);
	// intent.putExtras(bundle);
	// startActivity(intent);
	// }

	private void setheadtextview() {
		headTextView = new TextView(getApplicationContext());
		headTextView.setGravity(Gravity.CENTER);
		headTextView.setMinHeight(100);
		headTextView.setText("����ˢ��...");
		headTextView.setTypeface(Typeface.DEFAULT_BOLD);
		headTextView.setTextSize(15);
		headTextView.invalidate();
		ListData.addHeaderView(headTextView, null, false);
		ListData.setPadding(0, -100, 0, 0);
		ListData.setOnTouchListener(this);
	}

	private void getdatalist(int arg) {
		if (arg == 1) {
			list.clear();
		}
		// TODO Auto-generated method stub

		try {

			for (int i = 0; i < 10; i++) {

				imagePPTModel listinfo = new imagePPTModel();
				listinfo.setTime("2017-08-30");
				listinfo.setTitle("���ݵ���������Ŀ");
				listinfo.setContent("������ȷ������׼��������Ϊ�淶���������ͨ�����¡�Ϊ���������ʡ�");
				listinfo.setGuanzhu("231");
				listinfo.setZan("453");
				listinfo.setRead(read[i]);
				listinfo.setImageurl(headimg[i]);
				if (i % 2 == 0) {
					listinfo.setHeadimgUrl("/2017/08/07/894263289180196864.png");
				}else {
					listinfo.setHeadimgUrl("/2017/08/07/894265226881536000.png");
					
				}

				listinfo.setWidth(screenwidth);
				list.add(listinfo);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (arg == 1) {
			go();
		} else {
			mAdapter.notifyDataSetChanged();
		}

	}

	protected void go() {
		ListData.setPadding(0, -100, 0, 0);
		mAdapter = new imagePPTAdapter(this, list, ListData, this);
		ListData.setAdapter(mAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void click(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lin_all:
			imagePPTModel data = list.get((Integer) v.getTag());

			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), StandardImageXML.class);
			Bundle bundle = new Bundle();
			bundle.putString("url", data.getHeadimgUrl());
			bundle.putInt("inturl", data.getImageurl());

			intent.putExtras(bundle);
			startActivity(intent);
			// Toast.makeText(getApplicationContext(), "ɾ����" + (Integer)
			// v.getTag() + "��",
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
