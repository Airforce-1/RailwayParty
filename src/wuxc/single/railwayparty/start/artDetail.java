package wuxc.single.railwayparty.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.internet.URLcontainer;
import wuxc.single.railwayparty.layout.RoundImageView;

public class artDetail extends Activity implements OnClickListener, OnItemClickListener {
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
	private WebView webView;
	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private TextView text_detail;
	private String content;
	private static SharedPreferences PreUserInfo;// �洢������Ϣ
	private TextView text_tupian;
	private TextView text_fujian;
	private LinearLayout lin_fujian;
	private LinearLayout lin_tupian;
	private static int number = 0;
	private int fujian = 0;
	private String filePath = "";
	private String ext = "";
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
		setContentView(R.layout.art_detail);
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
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		initview();
		setonclicklistener();
		setlistheight(0);
		settext();
		starttimedelay();
		TextName.setText("���ߣ�" + Name);
		TextTime.setText(Time);
		String html = "<html>" + "<body>" + "<table>" + "<tr>" + "<td>�ɶ��츮</td>" + "</tr>" + "</table>" + "</body>"
				+ "</html>";
		text_detail.setText("ժҪ��" + detail);
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
		if (chn.equals("wsdx")) {
			webView = (android.webkit.WebView) findViewById(R.id.webview);
			// StringBuilder sb = new StringBuilder();
			// sb.append(detail);
			Log.e("here", "here");
			// webView.loadUrl("http://ww.baidu.com");
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebChromeClient(new WebChromeClient());
			webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

			webView.loadDataWithBaseURL(URLcontainer.urlip, detail, "text/html", "utf-8", null);

		} else {
			GetData();
		}

		// detail=getNewContent(detail);
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
		webView = (android.webkit.WebView) findViewById(R.id.webview);
		// StringBuilder sb = new StringBuilder();
		// sb.append(detail);
		Log.e("here", "here1");
		// webView.loadUrl("http://ww.baidu.com");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.loadDataWithBaseURL(URLcontainer.urlip, content, "text/html", "utf-8", null);

	}

	private String getNewContent(String htmltext) {

		Document doc = Jsoup.parse(htmltext);
		Elements elements = doc.getElementsByTag("img");
		for (Element element : elements) {
			element.attr("width", "100%").attr("height", "auto");
		}

		Log.d("VACK", doc.toString());
		return doc.toString();
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

	// private void GetDataList(String data, int arg) {
	//
	// if (arg == 1) {
	// list.clear();
	// }
	// JSONArray jArray = null;
	// try {
	// jArray = new JSONArray(data);
	// JSONObject json_data = null;
	// if (jArray.length() == 0) {
	// Toast.makeText(getApplicationContext(), "������",
	// Toast.LENGTH_SHORT).show();
	//
	// } else {
	// for (int i = 0; i < jArray.length(); i++) {
	// json_data = jArray.getJSONObject(i);
	// Log.e("json_data", "" + json_data);
	// JSONObject jsonObject = json_data.getJSONObject("data");
	// CommentModel listinfo = new CommentModel();
	//
	// listinfo.setTime(jsonObject.getString("createTime"));
	// listinfo.setComment(jsonObject.getString("content"));
	// listinfo.setRoundUrl(jsonObject.getString("userPhoto"));
	// listinfo.setName(jsonObject.getString("user_name"));
	// list.add(listinfo);
	//
	// }
	// }
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// if (arg == 1) {
	// go();
	// } else {
	// mAdapter.notifyDataSetChanged();
	// }
	//
	// }

	// private void GetPager(String pager) {
	// // TODO Auto-generated method stub
	// try {
	// JSONObject demoJson = new JSONObject(pager);
	//
	// totalPage = demoJson.getInt("totalPage");
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	//
	// private void GetData() {
	// // TODO Auto-generated method stub
	//
	// // TODO Auto-generated method stub
	// final ArrayList ArrayValues = new ArrayList();
	// ArrayValues.add(new BasicNameValuePair("ticket", ticket));
	// ArrayValues.add(new BasicNameValuePair("chn", chn));
	// ArrayValues.add(new BasicNameValuePair("datakey", Id));
	// ArrayValues.add(new BasicNameValuePair("curPage", "" + curPage));
	// ArrayValues.add(new BasicNameValuePair("pageSize", "" + pageSize));
	// new Thread(new Runnable() { // �����߳��ϴ��ļ�
	// @Override
	// public void run() {
	// String DueData = "";
	// DueData = HttpGetData.GetData("api/cms/common/getChannelCommentData",
	// ArrayValues);
	// Message msg = new Message();
	// msg.obj = DueData;
	// msg.what = GET_DUE_DATA;
	// uiHandler.sendMessage(msg);
	// }
	// }).start();
	//
	// }

	private void GetDataList(String data, int curPage2) {
		fujian = 0;
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(data);

			content = demoJson.getString("content");
			try {
				JSONArray jArray = null;
				jArray = new JSONArray(demoJson.getString("docAtt"));
				JSONObject json_data = null;
				json_data = jArray.getJSONObject(0);

				filePath = json_data.getString("filePath");
				ext = json_data.getString("ext");
				fujian = 1;
			} catch (Exception e) {
				// TODO: handle exception
			}
			getImgaddress(content);
			if (number != 0) {
				lin_tupian.setVisibility(View.VISIBLE);
			} else {
				lin_tupian.setVisibility(View.GONE);
			}
			new Thread(new Runnable() { // �����߳��ϴ��ļ�
				@Override
				public void run() {
					content = getNewContent(content);
					Message msg = new Message();

					msg.what = 0;
					uiHandler.sendMessage(msg);
				}
			}).start();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (fujian == 1) {
			lin_fujian.setVisibility(View.VISIBLE);
		} else {
			lin_fujian.setVisibility(View.GONE);
		}
		if (ext.equals("mp4")) {
			text_fujian.setText("����Ƶ");
		} else if (ext.equals("mp3")) {
			text_fujian.setText("����Ƶ");
		} else {
			text_fujian.setText("���ظ���");
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
		text_tupian = (TextView) findViewById(R.id.text_tupian);
		text_fujian = (TextView) findViewById(R.id.text_fujian);
		lin_fujian = (LinearLayout) findViewById(R.id.lin_fujian);
		lin_tupian = (LinearLayout) findViewById(R.id.lin_tupian);
		number = 0;
		lin_fujian.setOnClickListener(this);
		lin_tupian.setOnClickListener(this);
		lin_tupian.setVisibility(View.GONE);
		lin_fujian.setVisibility(View.GONE);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		// BtnAnswer.setOnClickListener(this);
		ImageBack.setOnClickListener(this);
		ListData.setOnItemClickListener(this);
		// TextWarning.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			// Intent intent = new Intent();
			// intent.setClass(getApplicationContext(), imageshow.class);
			// startActivity(intent);
			break;
		case R.id.lin_tupian:
			// finish();
			if (true) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("number", number);
				intent.putExtras(bundle);
				intent.setClass(getApplicationContext(), imageshow.class);
				startActivity(intent);
			}

			break;
		case R.id.lin_fujian:
			// finish();
			if (true) {
				int classify = 0;
				filePath = "";
				filePath = URLcontainer.urlip + "upload" + filePath;
				if (ext.equals("mp4")) {
					classify = 3;
				} else if (ext.equals("mp3")) {
					classify = 2;
				}
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

	// public static void get(String args) throws IOException {
	//// Validate.isTrue(args.length == 1, "usage: supply url to fetch");
	// String url = args;
	// print("Fetching %s...", url);
	//
	// Document doc = Jsoup.connect(url).get();
	// Elements links = doc.select("a[href]");
	// Elements media = doc.select("[src]");
	// Elements imports = doc.select("link[href]");
	//
	// print("\nMedia: (%d)", media.size());
	// for (Element src : media) {
	// if (src.tagName().equals("img"))
	// print(" * %s: <%s> %sx%s (%s)",
	// src.tagName(), src.attr("abs:src"), src.attr("width"),
	// src.attr("height"),
	// trim(src.attr("alt"), 20));
	// else
	// print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
	// }
	//
	// print("\nImports: (%d)", imports.size());
	// for (Element link : imports) {
	// print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"),
	// link.attr("rel"));
	// }
	//
	// print("\nLinks: (%d)", links.size());
	// for (Element link : links) {
	// print(" * a: <%s> (%s)", link.attr("abs:href"), trim(link.text(), 35));
	// }
	// }
	//
	// private static void print(String msg, Object... args) {
	// System.out.println(String.format(msg, args));
	// }
	//
	// private static String trim(String s, int width) {
	// if (s.length() > width)
	// return s.substring(0, width-1) + ".";
	// else
	// return s;
	// }
	// public static List getImgStr(String htmlStr) {
	// String img = "";
	// Pattern p_image;
	// Matcher m_image;
	// List pics = new ArrayList();
	// String regEx_img = "]*?>";
	// p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
	// m_image = p_image.matcher(htmlStr);
	// while (m_image.find()) {
	// img = img + "," + m_image.group();
	// Matcher m =
	// Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
	// while (m.find()) {
	// pics.add(m.group(1));
	// }
	// Log.e("getImgStr", img);
	// }
	// for (int i = 0; i < pics.size(); i++) {
	// Log.e("pics",""+ pics.get(i));
	// }
	// return pics;
	// }
	/**
	 * @param s
	 * @return ���ͼƬ
	 */
	public static List<String> getImg(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "src=\"(.*?)\"";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			Log.e("list.get(i) ", "" + list.get(i).indexOf("data:image"));
			if (list.get(i).indexOf("data:image") >= 0) {
				Log.e("list.get(i) remove ", "" + list.get(i).indexOf("data:image"));

				list.remove(i);
				i--;
			}
		}
		return list;
	}

	/**
	 * ���ش���ͼƬ��ַ������
	 * 
	 * @param tar
	 * @return
	 */
	public static String[] getImgaddress(String tar) {
		List<String> imgList = getImg(tar);

		String res[] = new String[imgList.size()];

		if (imgList.size() > 0) {
			for (int i = 0; i < imgList.size(); i++) {
				int begin = imgList.get(i).indexOf("\"") + 1;
				int end = imgList.get(i).lastIndexOf("\"");
				String url[] = imgList.get(i).substring(begin, end).split("/");
				Log.e("url", imgList.get(i).substring(begin, end));

				res[i] = imgList.get(i).substring(begin, end);
			}
		} else {
		}

		for (int i = 0; i < res.length; i++) {
			Log.e("res", res[i]);
			Editor edit = PreUserInfo.edit();
			number++;
			edit.putString("image" + i, res[i]);
			edit.commit();
		}
		return res;
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
