package wuxc.single.railwayparty.start;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.GetUnreadNumber;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.internet.URLcontainer;
import wuxc.single.railwayparty.layout.RoundImageView;

public class wsdxActivity extends Activity implements OnClickListener, OnItemClickListener {
	// private EditText EditAnswer;
	// private Button BtnAnswer;
	private ImageView ImageBack;
	private ImageView ImagePic;
	// private ScrollView ScrollLayout;
	private ListView ListData;
	// private TextView TextWarning;
	private TextView TextDetail;
	private TextView TextTime;
	private TextView TextName;
	private TextView TextTitle;

	private String Name;
	private String Title;
	private String Time;
	// List<CommentModel> list = new ArrayList<CommentModel>();
	// private static CommentAdapter mAdapter;
	private int pageSize = 10;
	private int totalPage = 5;
	private int curPage = 1;
	private int screenwidth = 0;
	private float scale = 0;
	private float scalepx = 0;
	private float dp = 0;
	private String detail = "�˴�ר����ķ�Χ������ũ�񹤽϶�Ľ��������졢�ɿ󡢲�����������С���Ͷ��ܼ�����ҵ�Լ����徭����֯��������ݰ������ǹ���ҵ���Ͷ���ǩ���Ͷ���ͬ��������չ���֧���йع涨֧��ְ�����������������͹��ʹ涨������֧���Ӱ๤������������μ���ᱣ�պͽ�����ᱣ�շ���������ؽ�ֹʹ��ͯ���涨�Լ�Ůְ����δ���깤�����Ͷ������涨��������������Ͷ����Ϸ��ɷ���������";
	private String Id = "";
	private String ticket = "";
	private String chn;
	private String userPhoto;
	private String LoginId;

	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private TextView text_detail;
	private String content;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private Button btn_go;
	private int classify = 0;
	private String filePath = "";
	private int recLen = 60;
	private String cover = "";
	Timer timer = new Timer();
	private boolean read = false;
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// GetData();
				// Toast.makeText(getApplicationContext(), "���ڼ�������",
				// Toast.LENGTH_SHORT).show();
				show();
				break;
			case GET_DUE_DATA:
				GetDataDueData(msg.obj);
				break;
			default:
				break;

			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.special_detail_activity2);
		Intent intent = this.getIntent(); // ��ȡ���е�intent����
		Bundle bundle = intent.getExtras(); // ��ȡintent�����bundle����
		Name = bundle.getString("Name");
		Title = bundle.getString("Title");
		Time = bundle.getString("Time");
		Id = bundle.getString("Id");

		chn = bundle.getString("chn");
		btn_go = (Button) findViewById(R.id.btn_go);
		btn_go.setOnClickListener(this);
		btn_go.setText("���޸���");
		try {
			detail = bundle.getString("detail");
			ticket = bundle.getString("ticket");
			cover = bundle.getString("cover");
			read = bundle.getBoolean("read");
		} catch (Exception e) {
			// TODO: handle exception
		}
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		initview();
		setonclicklistener();
		setlistheight(0);
		settext();
		starttimedelay();
		timer.schedule(task, 1000, 1000); // timeTask
		TextName.setText("���ߣ�" + Name);
		TextTime.setText(Time);
		String html = "<html>" + "<body>" + "<table>" + "<tr>" + "<td>�ɶ��츮</td>" + "</tr>" + "</table>" + "</body>"
				+ "</html>";
		text_detail.setText("ժҪ��" + detail);
		// if (chn.equals("wsdx")) {
		// webView = (android.webkit.WebView) findViewById(R.id.webview);
		// // StringBuilder sb = new StringBuilder();
		// // sb.append(detail);
		// Log.e("here", "here");
		// // webView.loadUrl("http://ww.baidu.com");
		// webView.getSettings().setJavaScriptEnabled(true);
		// webView.setWebChromeClient(new WebChromeClient());
		// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		//
		// webView.loadDataWithBaseURL(URLcontainer.urlip, detail, "text/html",
		// "utf-8", null);

		// } else {
		GetData();
		if (!read) {
			record();
		}
		final ArrayList ArrayValues = new ArrayList();
		// ArrayValues.add(new BasicNameValuePair("ticket", ticket));
		// ArrayValues.add(new BasicNameValuePair("applyType", "" + 2));
		// ArrayValues.add(new BasicNameValuePair("helpSType", "" +
		// type));
		// ArrayValues.add(new BasicNameValuePair("modelSign",
		// "KNDY_APPLY"));
		// ArrayValues.add(new BasicNameValuePair("curPage", "" +
		// curPage));
		// ArrayValues.add(new BasicNameValuePair("pageSize", "" +
		// pageSize));
		// final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		// chn = GetChannelByKey.GetSign(PreALLChannel, "ְ��֮��");
		ArrayValues.add(new BasicNameValuePair("chn", chn));

		ArrayValues.add(new BasicNameValuePair("datakey", "" + Id));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/cms/common/browserModelItem", ArrayValues);

			}
		}).start();
		// }

		// detail=getNewContent(detail);
	}

	private void record() {
		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();

		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));

		ArrayValues.add(new BasicNameValuePair("accessRecordDto.classify", chn));

		ArrayValues.add(new BasicNameValuePair("accessRecordDto.busKey", "" + Id));
		ArrayValues.add(new BasicNameValuePair("accessRecordDto.bigClassify", "channel"));
		ArrayValues.add(new BasicNameValuePair("accessRecordDto.title", "" + Title));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/pubshare/accessRecord/save", ArrayValues);

			}
		}).start();
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			recLen++;
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				if (recLen < 0) {
					timer.cancel();

				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
	}

	private void ReadTicket() {
		// TODO Auto-generated method stub
		ticket = PreUserInfo.getString("ticket", "");

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
		ArrayValues.add(new BasicNameValuePair("chn", chn));
		ArrayValues.add(new BasicNameValuePair("datakey", "" + Id));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/cms/channel/channleContentData", ArrayValues);
				Message msg = new Message();
				msg.obj = DueData;
				msg.what = GET_DUE_DATA;
				uiHandler.sendMessage(msg);
			}
		}).start();

	}

	private void show() {
		// TODO Auto-generated method stub

	}

	protected void GetDataDueData(Object obj) {

		// TODO Auto-generated method stub
		// String Type = null;
		String Data = null;
		// String pager = null;
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			String Type = demoJson.getString("type");

			Data = demoJson.getString("data");
			if (Type.equals(GET_SUCCESS_RESULT)) {

				GetDataList(Data, curPage);
			} else if (Type.equals(GET_FAIL_RESULT)) {
				// Toast.makeText(getApplicationContext(), "����������ʧ��",
				// Toast.LENGTH_SHORT).show();
			} else {
				// Toast.makeText(getApplicationContext(), "���ݸ�ʽУ��ʧ��",
				// Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			GetUnreadNumber.getunreadnumber(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void GetDataList(String data, int curPage2) {
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(data);

			classify = demoJson.getInt("fileClassify");
			JSONArray jArray = null;

			jArray = new JSONArray(demoJson.getString("videoFile"));

			try {
				JSONObject demoJson1 = jArray.getJSONObject(0);
				filePath = URLcontainer.urlip + "upload" + demoJson1.getString("filePath");
			} catch (Exception e) {
				// TODO: handle exception
				filePath = "";
			}
			if (filePath == null || filePath.equals("")) {
				btn_go.setText("���޸���");
			} else {
				if (classify == 1) {
					btn_go.setText("����PPT");
				} else if (classify == 2) {
					btn_go.setText("����Ƶ");
				} else if (classify == 3) {
					btn_go.setText("����Ƶ");
				} else {
					btn_go.setText("������������");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void starttimedelay() {
		// ԭ�򣺲���ʱ�Ļ�list�Ử������
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Message msg1 = new Message();
				msg1.what = 3;
				uiHandler.sendMessage(msg1);

			}

		}, 2000);
	}

	// private void getdatalist(int arg) {
	// if (arg == 1) {
	// list.clear();
	// }
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// for (int i = 0; i < 10; i++) {
	//
	// CommentModel listinfo = new CommentModel();
	// listinfo.setTime("2016-12-14 20:00:00");
	// listinfo.setComment("������һƪ�����£�ѧϰ��");
	// listinfo.setRoundUrl("");
	// listinfo.setName("��־��");
	//
	// list.add(listinfo);
	//
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// if (arg == 1) {
	// go();
	// } else {
	// mAdapter.notifyDataSetChanged();
	// }
	// setlistheight(list.size());
	// if (arg == totalPage) {
	// TextWarning.setText("û�и�����");
	// } else {
	// TextWarning.setText("������ظ���");
	// }
	// }

	private void setlistheight(int size) {
		// TODO Auto-generated method stub
		screenwidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		DisplayMetrics mMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		scale = getResources().getDisplayMetrics().density;
		dp = screenwidth / scale + 0.5f;
		scalepx = screenwidth / dp;
		int height = (int) (size * 91 * scalepx);
		RelativeLayout.LayoutParams layoutParams1 = (android.widget.RelativeLayout.LayoutParams) ListData
				.getLayoutParams();
		layoutParams1.height = height;
		ListData.setLayoutParams(layoutParams1);
		height = (int) ((screenwidth - 20 * scalepx) / 2);
		layoutParams1 = (android.widget.RelativeLayout.LayoutParams) ImagePic.getLayoutParams();
		layoutParams1.height = height;
		ImagePic.setLayoutParams(layoutParams1);

	}

	// protected void go() {
	// mAdapter = new CommentAdapter(this, list, ListData);
	// ListData.setAdapter(mAdapter);
	// }

	private void settext() {
		// TODO Auto-generated method stub
		// TextWarning.setText("���ڼ�������...");
		TextDetail.setText(detail);
		TextTime.setText(Time);
		TextName.setText(Name);
		TextTitle.setText(Title);
	}

	private void initview() {
		// TODO Auto-generated method stub
		// EditAnswer = (EditText) findViewById(R.id.edit_answer);
		// BtnAnswer = (Button) findViewById(R.id.btn_answer);
		ImageBack = (ImageView) findViewById(R.id.image_back);
		ImagePic = (ImageView) findViewById(R.id.image_pic);
		// ScrollLayout = (ScrollView) findViewById(R.id.scrolllayout);
		ListData = (ListView) findViewById(R.id.list_data);
		// TextWarning = (TextView) findViewById(R.id.text_warning);
		TextDetail = (TextView) findViewById(R.id.text_detail);
		TextTime = (TextView) findViewById(R.id.text_time);
		TextName = (TextView) findViewById(R.id.text_name);
		TextTitle = (TextView) findViewById(R.id.text_title);
		text_detail = (TextView) findViewById(R.id.text_detail);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		// BtnAnswer.setOnClickListener(this);
		ImageBack.setOnClickListener(this);
		ListData.setOnItemClickListener(this);
		// TextWarning.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (true) {
				sendpost();
				finish();

				final ArrayList ArrayValues = new ArrayList();
				// ArrayValues.add(new BasicNameValuePair("ticket",
				// ticket));
				// ArrayValues.add(new BasicNameValuePair("applyType",
				// "" + 2));
				// ArrayValues.add(new BasicNameValuePair("helpSType",
				// "" + type));
				// ArrayValues.add(new BasicNameValuePair("modelSign",
				// "KNDY_APPLY"));
				// ArrayValues.add(new BasicNameValuePair("curPage", ""
				// + curPage));
				// ArrayValues.add(new BasicNameValuePair("mobile", "" +
				// text_phone.getText().toString()));
				// final ArrayList ArrayValues = new ArrayList();
				ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
				// chn = GetChannelByKey.GetSign(PreALLChannel, "ְ��֮��");
				// ArrayValues.add(new BasicNameValuePair("chn",
				// "dyq"));
				// chn = "dyq";
				ArrayValues.add(new BasicNameValuePair("learnRecordDto.title", Title));
				ArrayValues.add(new BasicNameValuePair("learnRecordDto.timeLength", "" + (recLen / 60)));
				ArrayValues.add(new BasicNameValuePair("learnRecordDto.cover", "" + cover));
				ArrayValues.add(new BasicNameValuePair("learnRecordDto.content", "" + detail));
				// ArrayValues.add(new BasicNameValuePair("classify", ""
				// +
				// classify));

				new Thread(new Runnable() { // �����߳��ϴ��ļ�
					@Override
					public void run() {
						String DueData = "";
						DueData = HttpGetData.GetData("api/pb/learnRecord/save", ArrayValues);
						Message msg = new Message();
						msg.obj = DueData;
						msg.what = 17;
						uiHandler.sendMessage(msg);
					}
				}).start();

			}
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	private void sendpost() {
		if (recLen > 600) {
			// TODO Auto-generated method stub
			final ArrayList ArrayValues = new ArrayList<BasicNameValuePair>();
			ArrayValues.add(new BasicNameValuePair("userScoreDto.inOut", "1"));
			ArrayValues.add(new BasicNameValuePair("userScoreDto.classify", "specialActivity"));
			ArrayValues.add(new BasicNameValuePair("userScoreDto.amount", "2"));
			ArrayValues.add(new BasicNameValuePair("userScoreDto.reason", "���ϵ�Уѧϰ��" + Title + "��"));
			ArrayValues.add(new BasicNameValuePair("ticket", ticket));
			new Thread(new Runnable() { // �����߳��ϴ��ļ�
				@Override
				public void run() {
					HttpGetData.GetData("api/console/userScore/save", ArrayValues);

				}
			}).start();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			sendpost();
			finish();
			final ArrayList ArrayValues = new ArrayList();
			// ArrayValues.add(new BasicNameValuePair("ticket",
			// ticket));
			// ArrayValues.add(new BasicNameValuePair("applyType",
			// "" + 2));
			// ArrayValues.add(new BasicNameValuePair("helpSType",
			// "" + type));
			// ArrayValues.add(new BasicNameValuePair("modelSign",
			// "KNDY_APPLY"));
			// ArrayValues.add(new BasicNameValuePair("curPage", ""
			// + curPage));
			// ArrayValues.add(new BasicNameValuePair("mobile", "" +
			// text_phone.getText().toString()));
			// final ArrayList ArrayValues = new ArrayList();
			ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
			// chn = GetChannelByKey.GetSign(PreALLChannel, "ְ��֮��");
			// ArrayValues.add(new BasicNameValuePair("chn",
			// "dyq"));
			// chn = "dyq";
			ArrayValues.add(new BasicNameValuePair("learnRecordDto.title", Title));
			ArrayValues.add(new BasicNameValuePair("learnRecordDto.timeLength", "" + (recLen / 60)));
			ArrayValues.add(new BasicNameValuePair("learnRecordDto.cover", "" + cover));
			ArrayValues.add(new BasicNameValuePair("learnRecordDto.cover", "" + detail));
			// ArrayValues.add(new BasicNameValuePair("classify", ""
			// +
			// classify));

			new Thread(new Runnable() { // �����߳��ϴ��ļ�
				@Override
				public void run() {
					String DueData = "";
					DueData = HttpGetData.GetData("api/pb/learnRecord/save", ArrayValues);
					// Message msg = new Message();
					// msg.obj = DueData;
					// msg.what = 17;
					// uiHandler.sendMessage(msg);
				}
			}).start();
			break;
		case R.id.btn_go:
			if (filePath == null || filePath.equals("")) {
				Toast.makeText(getApplicationContext(), "���޸���", Toast.LENGTH_SHORT).show();

			} else {
				if (classify == 1) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(filePath);
					intent.setData(content_url);
					startActivity(intent);
				} else if (classify == 2) {
					Intent it = new Intent(Intent.ACTION_VIEW);
					it.setDataAndType(Uri.parse(filePath), "audio/MP3");
					startActivity(it);
				} else if (classify == 3) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse(filePath), "video/mp4");
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(filePath);
					intent.setData(content_url);
					startActivity(intent);
				}
			}
			break;
		// case R.id.btn_answer:
		// break;
		// case R.id.text_warning:
		// curPage++;
		// if (!(curPage > totalPage)) {
		// getdatalist(curPage);
		// Toast.makeText(getApplicationContext(), "���ڼ���",
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// CommentModel data = list.get(position);
		// Intent intent = new Intent();
		// intent.setClass(getApplicationContext(),
		// CommentDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("Name", data.getName());
		// bundle.putString("Time", data.getTime());
		// bundle.putString("Comment", data.getComment());
		// intent.putExtras(bundle);
		// startActivity(intent);
	}

}
