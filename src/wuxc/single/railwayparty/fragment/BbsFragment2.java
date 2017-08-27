package wuxc.single.railwayparty.fragment;

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
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.adapter.Bbs2Adapter;
import wuxc.single.railwayparty.adapter.Bbs2Adapter.Callback;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.model.Bbs2Model;
import wuxc.single.railwayparty.start.SpecialDetailActivity;

public class BbsFragment2 extends Fragment implements OnTouchListener, Callback, OnClickListener, OnItemClickListener {
	private ListView ListData;
	List<Bbs2Model> list = new ArrayList<Bbs2Model>();
	private static Bbs2Adapter mAdapter;
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

	private RelativeLayout rel_1;
	private TextView text_1;
	private TextView text_line_1;
	private RelativeLayout rel_2;
	private TextView text_2;
	private TextView text_line_2;
	private RelativeLayout rel_3;
	private TextView text_3;
	private TextView text_line_3;
	private RelativeLayout rel_4;
	private TextView text_4;
	private TextView text_line_4;
	private int screenwidth = 0;
	private float scale = 0;
	private float scalepx = 0;
	private float dp = 0;
	private String ticket = "";
	private String chn;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private String classify = "";
	private SharedPreferences PreForDQLH;
	private SharedPreferences ItemNumber;
	public Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_DUE_DATA:
				GetDataDueData(msg.obj);
				break;
			case 66:
				GetRecord(msg.obj);
				try {
					Editor edit = PreForDQLH.edit();
					edit.putBoolean("DQLH", true);
					edit.commit();
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				break;
			}
		}
	};

	private void GetRecord(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;

		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			Data = demoJson.getString("datas");
			if (Type.equals(GET_SUCCESS_RESULT)) {

				JSONArray jArray = null;
				try {
					jArray = new JSONArray(Data);
					JSONObject json_data = null;
					if (jArray.length() == 0) {
						// / Toast.makeText(getActivity(), "������",
						// Toast.LENGTH_SHORT).show();

					} else {
						Editor edit = PreForDQLH.edit();
						edit.putBoolean("DQLH", true);

						for (int i = 0; i < jArray.length(); i++) {

							try {
								json_data = jArray.getJSONObject(i);
								json_data = json_data.getJSONObject("data");
								String keyid = json_data.getString("busKey");
								edit.putBoolean(keyid, true);
							} catch (Exception e) {
								// TODO: handle exception

							}

						}
						edit.commit();
						Editor edit1 = ItemNumber.edit();
						edit1.putInt("DQLHread", (PreForDQLH.getAll().size() - 1));
						Log.e("DQLHread", "" + (PreForDQLH.getAll().size() - 1));
						edit1.commit();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void GetDataDueData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;
		String pager = null;
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");
			pager = demoJson.getString("pager");
			Data = demoJson.getString("datas");
			if (Type.equals(GET_SUCCESS_RESULT)) {
				GetPager(pager);
				GetDataList(Data, curPage);
			} else if (Type.equals(GET_FAIL_RESULT)) {
				Toast.makeText(getActivity(), "����������ʧ��", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "���ݸ�ʽУ��ʧ��", Toast.LENGTH_SHORT).show();
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
				// / Toast.makeText(getActivity(), "������",
				// Toast.LENGTH_SHORT).show();

			} else {
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);
					Log.e("json_data", "" + json_data);
					// JSONObject jsonObject = json_data.getJSONObject("data");
					Bbs2Model listinfo = new Bbs2Model();

					listinfo.setTime(json_data.getString("createtime"));
					listinfo.setTitle(json_data.getString("title"));
					listinfo.setId(json_data.getString("keyid"));
					// listinfo.setBackGround(json_data.getString("sacleImage"));
					listinfo.setContent(json_data.getString("summary"));
					listinfo.setSummary(json_data.getString("summary"));
					listinfo.setCont(true);
					listinfo.setLabel("2:57");
					listinfo.setWidth((int) (screenwidth - 80 * scalepx));
					listinfo.setImageurl(R.drawable.logo);
					listinfo.setHeadimgUrl(json_data.getString("sacleImage"));
					listinfo.setRead(PreForDQLH.getBoolean(json_data.getString("keyid"), false));

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

	private void GetPager(String pager) {
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(pager);

			totalPage = demoJson.getInt("totalPage");
			if (classify.equals("")) {
				int totalcount = 0;
				totalcount = demoJson.getInt("totalRecord");
				Log.e("totalcount", "" + totalcount);
				Editor edit = ItemNumber.edit();
				edit.putInt("DQLHtotal", totalcount);
				edit.commit();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.wuxc_fragment_bbs_2, container, false);
			initview(view);
			setonclicklistener();
			setheadtextview();
			initcolor();
			// getdatalist(curPage);
			PreUserInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
			PreForDQLH = getActivity().getSharedPreferences("DQLH", Context.MODE_PRIVATE);
			ItemNumber = getActivity().getSharedPreferences("ItemNumber", Context.MODE_PRIVATE);

			ReadTicket();

			GetData();
			if (!PreForDQLH.getBoolean("DQLH", false)) {
				GetMyReadRecord();
			}
		}

		return view;

	}

	private void GetMyReadRecord() {
		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		ArrayValues.add(new BasicNameValuePair("accessRecordDto.classify", "dqlh"));
		ArrayValues.add(new BasicNameValuePair("curPage", "" + 1));
		ArrayValues.add(new BasicNameValuePair("pageSize", "" + 100000));
		ArrayValues.add(new BasicNameValuePair("accessRecordDto.bigClassify", "channel"));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/pubshare/accessRecord/getListJsonData", ArrayValues);
				Message msg = new Message();
				msg.obj = DueData;
				msg.what = 66;
				uiHandler.sendMessage(msg);
			}
		}).start();

	}

	private void initcolor() {
		// TODO Auto-generated method stub
		clearcolor();
		text_1.setTextColor(Color.parseColor("#cc0502"));
		text_line_1.setBackgroundColor(Color.parseColor("#cc0502"));

	}

	private void clearcolor() {
		// TODO Auto-generated method stub
		text_1.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_1.setBackgroundColor(Color.parseColor("#00000000"));
		text_2.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_2.setBackgroundColor(Color.parseColor("#00000000"));
		text_3.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_3.setBackgroundColor(Color.parseColor("#00000000"));
		text_4.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_4.setBackgroundColor(Color.parseColor("#00000000"));
	}

	private void initview(View view2) {
		// TODO Auto-generated method stub
		screenwidth = getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth();
		DisplayMetrics mMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		scale = getActivity().getResources().getDisplayMetrics().density;
		// Log.e("mMetrics", mMetrics.toString() + "scale=" + scale + "0.5f"
		// +
		// 0.5f);
		dp = screenwidth / scale + 0.5f;
		scalepx = screenwidth / dp;
		ListData = (ListView) view.findViewById(R.id.list_data);
		rel_1 = (RelativeLayout) view.findViewById(R.id.rel_1);
		text_1 = (TextView) view.findViewById(R.id.text_1);
		text_line_1 = (TextView) view.findViewById(R.id.text_line_1);
		rel_1.setOnClickListener(this);
		rel_2 = (RelativeLayout) view.findViewById(R.id.rel_2);
		text_2 = (TextView) view.findViewById(R.id.text_2);
		text_line_2 = (TextView) view.findViewById(R.id.text_line_2);
		rel_2.setOnClickListener(this);
		rel_3 = (RelativeLayout) view.findViewById(R.id.rel_3);
		text_3 = (TextView) view.findViewById(R.id.text_3);
		text_line_3 = (TextView) view.findViewById(R.id.text_line_3);
		rel_3.setOnClickListener(this);
		rel_4 = (RelativeLayout) view.findViewById(R.id.rel_4);
		text_4 = (TextView) view.findViewById(R.id.text_4);
		text_line_4 = (TextView) view.findViewById(R.id.text_line_4);
		rel_4.setOnClickListener(this);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		ListData.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Bbs2Model data = list.get(position - 1);
		if (true) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), SpecialDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Title", data.getTitle());
			bundle.putString("Time", data.getTime());
			bundle.putString("detail", data.getContent());

			bundle.putString("chn", chn);
			bundle.putString("Id", data.getId());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	private void GetData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		ArrayValues.add(new BasicNameValuePair("chn", "dqlh"));
		chn = "dqlh";
		ArrayValues.add(new BasicNameValuePair("curPage", "" + curPage));
		ArrayValues.add(new BasicNameValuePair("pageSize", "" + pageSize));
		ArrayValues.add(new BasicNameValuePair("classify", "" + classify));

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
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		float tempY = event.getY();
		float tempyfoot = event.getY();
		firstItemIndex = ListData.getFirstVisiblePosition();
		lastItemIndex = ListData.getLastVisiblePosition();
		// Toast.makeText(getActivity(), " lastItemIndex" +
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
				Toast.makeText(getActivity(), "����ˢ��", Toast.LENGTH_SHORT).show();
				GetData();
			}
			int temp = 1;
			temp = (lastItemIndex) % pageSize;
			// temp = 0;
			if (temp == 0 && (startYfoot - tempyfoot > 400)) {
				curPage++;
				if (curPage > totalPage) {
					Toast.makeText(getActivity(), " û�и�����", Toast.LENGTH_SHORT).show();
					// // listinfoagain();
				} else {
					GetData();
					Toast.makeText(getActivity(), "���ڼ�����һҳ", Toast.LENGTH_SHORT).show();
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
				ListData.setPadding(0, -100, 0, (int) ((startYfoot - tempyfoot) / RATIO));
			}
			break;

		default:
			break;
		}
		return false;
	}

	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// // TODO Auto-generated method stub
	// float tempY = event.getY();
	// float tempyfoot = event.getY();
	// firstItemIndex = ListData.getFirstVisiblePosition();
	// lastItemIndex = ListData.getLastVisiblePosition();
	// // Toast.makeText(getActivity(), " lastItemIndex" +
	// // lastItemIndex, Toast.LENGTH_SHORT).show();
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// case MotionEvent.ACTION_MOVE:
	// if (!isRecored && (firstItemIndex == 0)) {
	// isRecored = true;
	// startY = tempY;
	// }
	// int temp = 1;
	// temp = (lastItemIndex) % pageSize;
	// if (!isRecoredfoot && (temp == 0)) {
	// isRecoredfoot = true;
	// startYfoot = tempyfoot;
	// }
	// break;
	// case MotionEvent.ACTION_UP:
	// case MotionEvent.ACTION_CANCEL:
	// isRecored = false;
	// isRecoredfoot = false;
	// break;
	//
	// default:
	// break;
	// }
	//
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// break;
	// case MotionEvent.ACTION_UP:
	// case MotionEvent.ACTION_CANCEL:
	// ListData.setPadding(0, 0, 0, 0);
	// if (tempY - startY < 400) {
	// ListData.setPadding(0, -100, 0, 0);
	// } else {
	// curPage = 1;
	// Toast.makeText(getActivity(), "����ˢ��", Toast.LENGTH_SHORT).show();
	// getdatalist(pageSize);
	// }
	// int temp = 1;
	// temp = (lastItemIndex) % pageSize;
	// // temp = 0;
	// if (temp == 0 && (startYfoot - tempyfoot > 400)) {
	// curPage++;
	// if (curPage > totalPage) {
	// Toast.makeText(getActivity(), " û�и�����", Toast.LENGTH_SHORT).show();
	// // // listinfoagain();
	// } else {
	// getdatalist(pageSize);
	// Toast.makeText(getActivity(), "���ڼ�����һҳ", Toast.LENGTH_SHORT).show();
	// }
	//
	// } else {
	//
	// }
	// break;
	// case MotionEvent.ACTION_MOVE:
	// if (isRecored && tempY > startY) {
	// ListData.setPadding(0, (int) ((tempY - startY) / RATIO - 100), 0, 0);
	// }
	// if (isRecoredfoot && startYfoot > tempyfoot) {
	// // footTextView.setVisibility(View.VISIBLE);
	// ListData.setPadding(0, -100, 0, (int) ((startYfoot - tempyfoot) /
	// RATIO));
	// }
	// break;
	//
	// default:
	// break;
	// }
	// return false;
	// }
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	// // recommendModel data = list.get(position - 1);
	// // Intent intent = new Intent();
	// // intent.setClass(getActivity(), SpecialDetailActivity.class);
	// // Bundle bundle = new Bundle();
	// // bundle.putString("Title", data.getTitle());
	// // bundle.putString("detail", data.getDetail());
	// // bundle.putString("Time", data.getTime());
	// // bundle.putString("Name", "����");
	// // intent.putExtras(bundle);
	// // startActivity(intent);
	// // Toast.makeText(getActivity(), "�����" + position + "��" + "item",
	// // Toast.LENGTH_SHORT).show();
	// Intent intent = new Intent();
	// intent.setClass(getActivity(), DetailActivity.class);
	// Bundle bundle = new Bundle();
	// bundle.putInt("source", R.drawable.detail);
	// bundle.putInt("height", 3048);
	// bundle.putInt("width", 750);
	// intent.putExtras(bundle);
	// startActivity(intent);
	// }

	private void setheadtextview() {
		headTextView = new TextView(getActivity());
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

	protected void go() {
		ListData.setPadding(0, -100, 0, 0);
		mAdapter = new Bbs2Adapter(getActivity(), list, ListData, this);
		ListData.setAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rel_1:
			clearcolor();
			text_1.setTextColor(Color.parseColor("#cc0502"));
			text_line_1.setBackgroundColor(Color.parseColor("#cc0502"));
			classify = "1";
			GetData();
			break;
		case R.id.rel_2:
			clearcolor();
			text_2.setTextColor(Color.parseColor("#cc0502"));
			text_line_2.setBackgroundColor(Color.parseColor("#cc0502"));
			classify = "2";
			GetData();
			break;
		case R.id.rel_3:
			clearcolor();
			text_3.setTextColor(Color.parseColor("#cc0502"));
			text_line_3.setBackgroundColor(Color.parseColor("#cc0502"));
			classify = "3";
			GetData();
			break;
		case R.id.rel_4:
			clearcolor();
			text_4.setTextColor(Color.parseColor("#cc0502"));
			text_line_4.setBackgroundColor(Color.parseColor("#cc0502"));
			classify = "4";
			GetData();
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
			Bbs2Model data = list.get((Integer) v.getTag());

			Intent intent = new Intent();
			intent.setClass(getActivity(), SpecialDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Title", data.getTitle());
			bundle.putString("Time", data.getTime());
			bundle.putString("detail", data.getContent());
			bundle.putString("chn", chn);
			bundle.putString("Id", data.getId());
			bundle.putBoolean("read", data.isRead());
			intent.putExtras(bundle);
			startActivity(intent);
			if (!data.isRead()) {
				Editor edit = PreForDQLH.edit();
				edit.putBoolean(data.getId(), true);
				edit.commit();
				data.setRead(true);
				mAdapter.notifyDataSetChanged();
				Editor edit1 = ItemNumber.edit();
				edit1.putInt("DQLHread", (PreForDQLH.getAll().size() - 1));
				Log.e("DQLHread", "" + (PreForDQLH.getAll().size() - 1));
				edit1.commit();
			}

			// Toast.makeText(getActivity(), "ɾ����" + + "��",
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
