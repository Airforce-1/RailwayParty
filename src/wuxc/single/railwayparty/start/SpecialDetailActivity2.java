package wuxc.single.railwayparty.start;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.internet.URLcontainer;
import wuxc.single.railwayparty.layout.RoundImageView;

public class SpecialDetailActivity2 extends Activity implements OnClickListener, OnItemClickListener {
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
	private String ticket="";
	private String chn;
	private String userPhoto;
	private String LoginId;
	private WebView webView;
	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private TextView text_detail;
	private String content;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
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
		setContentView(R.layout.special_detail_activity3);
		// Intent intent = this.getIntent(); // ��ȡ���е�intent����
		// Bundle bundle = intent.getExtras(); // ��ȡintent�����bundle����
		// Name = bundle.getString("Name");
		// Title = bundle.getString("Title");
		// Time = bundle.getString("Time");
		// Id = bundle.getString("Id");
		//
		// chn = bundle.getString("chn");
		// try {
		// detail = bundle.getString("detail");
		// ticket = bundle.getInt("ticket");
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		initview();
		setonclicklistener();
		setlistheight(0);
		settext();
		starttimedelay();
		TextName.setText("���ߣ�" + "ϵͳ����Ա");
		TextTime.setText("2017");
		String html = "<html>" + "<body>" + "<table>" + "<tr>" + "<td>�ɶ��츮</td>" + "</tr>" + "</table>" + "</body>"
				+ "</html>";
		text_detail.setText("ժҪ��" + detail);
		detail = "<p align=\"center\"><strong><span style=\"font-size: 16px\">��Ա��֯��ϵת�ӵĻ���֪ʶ</span></strong></p><p><strong><span style=\"font-size: 16px\">1��ʲô�ǵ�Ա��֯��ϵ��</span></strong></p><p><span style=\"font-size: 16px\">������Ա��֯��ϵ��ָ��Ա�Ե��Ļ�����֯��������ϵ����Ա��֯��ϵ�ĸ����й��������֮�֣�������������ʽ��֯��ϵ����ʱ��֯��ϵ�����������ָ��ʽ��֯��ϵ��</span></p><p><strong><span style=\"font-size: 16px\">2����Ա��֯��ϵ�����ȷ���ģ�</span></strong></p><p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; �����¡��ڰ����涨��ÿ����Ա������ְ��ߵͣ���������뵳��һ��֧����С��������ض���֯���μӵ�����֯������ܵ�����Ⱥ�ڵļල��</span></p><p><span style=\"font-size: 16px\">�����뵳����һ������׼�뵳���������뵳�ĵ���֯�Ͱ�����뵳��һ��������֯���Ӵ˾�ȷ����������֯��ϵ����Ա����֯��ϵһ��ȷ������Ա�Ϳ��Զ��ұ���μӸ���֯������������л���������</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">3��ΪʲôҪת�Ƶ�Ա��֯��ϵ��</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ����ʵ�����У���Ա��ѧϰ��������λ�����������Ǿ��������䶯�ģ������ת�Ƶ�Ա��֯��ϵ���ͻ��γɵ�Ա���뵳��֯�Ľ����������ල�ͷ����״����ֻ��ͨ��ת�Ƶ�Ա��֯��ϵ�����ܱ�֤��Ա���۵��������֯������Ч�ذ�������֯������</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">4��ʲô�������Ҫת�Ƶ�Ա��֯��ϵ��</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ��Ա��<strong>��ѧ����ҵ����ҵ</strong>�����ݡ����ۡ����ۡ����������ѧϰ���񹤡������Լ�����ԭ����Ҫ��һ����λ���������һ����λ�����ʱ������֯Ӧ�������йع涨����Ӧ�ظı䵳Ա����֯������ϵ��������֯��ϵ��ԭ�����ڵĻ��㵳��֯ת�Ƶ���ȥ��λ������Ļ��㵳��֯��</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">5��ת�Ƶ�Ա��֯��ϵ�ܵ�Ҫ����ʲô��</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; <strong>����Ա��ȥ��λ�ѽ�������֯�ģ�</strong>Ӧ�����䵳Ա��֯��ϵת�Ƶ���λ����֯��<strong>��λδ��������֯�ģ�Ӧ�����䵳Ա��֯��ϵת�Ƶ���λ���ڵص���֯��λ������ҵ���ܲ��ŵ���֯��Ҳ����ת�Ƶ���Ա���˾�ס�ص���֯���ؼ������������£��Ͷ��������������˲ţ��Ͷ��������������֯</strong>��</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">6��&nbsp;��У��ҵ����Ա���ת�Ƶ�Ա��֯��ϵ</span></strong></p>"
				+ "<p align=\"left\"><span style=\"font-size: 16px\">��������������֯�������ڽ�һ����ǿ��Ա��֯��ϵ���������������鷢�ۣ��������ݣ����ţ��涨����У��ҵ����Ա���Ѿ���ʵ������λ�ģ�Ӧ����Ա��֯��ϵ��ʱת�Ƶ���ȥ��λ����֯����δ��ʵ������λ�ģ��ɽ���Ա��ϵת�Ƶ����˻�ĸ��ס�صĽֵ���������֯��Ҳ����ͬ����ת�Ƶ��������������£��Ͷ��������������˲ţ��Ͷ��������������֯��</span></p>"
				+ "<p>&nbsp;</p>"
				+ "<p style=\"text-align: center\"><strong><span style=\"font-size: 16px\">��Ա��֯��ϵת�ӵĳ�������</span></strong></p>"
				+ "<p><strong><span style=\"font-size: 16px\">1����Ա��֯��ϵ�����¹�ϵ����һ����</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ��ͨ������£���Ա��֯��ϵ�����¹�ϵ��һ�µģ��絳�����ء����ӡ����󲿷ֹ�����ҵ��ҵ��λ�ȡ�����һЩ������û�о�ҵ�ı�ҵ�������۾��ˣ���Ա��֯��ϵ��ʱת���������˲��г�����֯�����Ÿĸ￪�ŵز������룬�Ͷ����ɼƻ����õĵ�λ�������г����õ������ת�䣬��ҵ��ʽ�������Ͷ���ϵ�����¹�ϵ�����˷��롣��Ա��֯��ϵ�������Ͷ���ϵһ�������¹�ϵ������������¹�ϵһ�����Ͷ���ϵ�����������ǹ���ҵ��������ҵ�Ͳ��ֹ�������ҵ��λ�ȡ�</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">2����˭����ת�Ƶ�Ա��֯��ϵ������</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ת�Ƶ�Ա��֯��ϵҪ��<strong>��Ա�������Ի��ɵ���֯����</strong>��������������˽��ί�������ر��ǵ�����Ա���졣</span></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; </span><strong><span style=\"font-size: 16px\">ע����������ԭ��ȷʵ��Ҫί�����ˣ�������Ա���⣩�����ɴ���Ա����������дǩ�ֵ�ί����ǰ������</span></strong></p>"
				+ "<p><strong><span style=\"font-size: 16px\">3����Ա��֯��ϵ������̧ͷ��ʲô����̧ͷ��</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; �����ŵڶ���̧ͷҪ����ת�ӵ�Ա��ʽ��֯��ϵȨ�ޣ���дӦת���Ӧ����ĵ���֯ȫ�ƻ��׼��ơ�������֯���ŵĿ�����ί��֯����������δ����֯���ŵĿ�����ί���ܿ������졣����ʹ��&times;&times;ʡί��&times;&times;��ί��&times;&times;��ί�ļ�ƣ���������Ҫʹ��&times;&times;��������ί����Ͻ��Ҫʹ��&times;&times;��&times;&times;��ί�ļ�ơ�</span></p>"
				+ "<p><span style=\"font-size: 16px\">�����ŵ�����̧ͷӦ��д��Աԭ���ڵ�֧����һ�����㵳ίȫ�ƻ��׼��ơ�</span></p>"
				+ "<p><span style=\"font-size: 16px\">4����Ա��֯��ϵ���������ɺδ�ȥ�δ�����ô���ת�����㵳��֯���ƣ�</span></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; �ɺδ�ȥ�δ���Ӧ��д�ɺε�λ���ס��ȥ�ε�λ���ס�ء���λ����Ӧ��дȫ�ƻ��׼��ƣ���ס��Ӧ��ʡ����������ֱϽ�У����أ��ؼ��С������ݣ����أ���Ͻ�����ؼ��С������أ������򣨽ֵ�����д���壨��������</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">5������֯��ϵ�����ŵ���Ч�ڣ�</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ��Ч������������ͨתһ����������7�죬��������10�죻ȫ��ͨתһ��30�죬<strong>����ѧУ�żٵ����������಻����90��</strong>���������ӦΪ���߽����Ż򿪾߻�ִ�ĵ��գ�������ǰ���Ӻ���д��</span></p>"
				+ "<p><strong><span style=\"font-size: 16px\">6��ת�ӵ���֯��ϵ�жԵ�Ա��Ҫ���ǣ�</span></strong></p>"
				+ "<p><span style=\"font-size: 16px\">����������������ѧϰ�������ԭ���뿪ԭ���ڵ���֯��<strong>Ҫ��ʱת�Ƶ�Ա��֯��ϵ<strong>����ҵ��Уǰ����������</strong>��</strong>��<strong>�涨ʱ���ڣ���Ч���ڣ�</strong>����ȥ�ط���λ����֯������</span></p>"
				+ "<p><span style=\"font-size: 16px\">����������������������ʱ��ϳ����޹̶��ص�ģ�Ӧ��ͨ���ʵ���ʽ������ԭ���ڵ���֯������ϵ���㱨����ڼ���й���������չ涨���ɵ��ѡ�</span></p>"
				+ "<p><span style=\"font-size: 16px\">����������</span><strong><span style=\"font-size: 16px\">���û���������ɣ����������²��μӵ�����֯����򲻽��ɵ��ѣ�������������Ĺ������ͱ���Ϊ�������ѵ���֧�����Ӧ�������������ĵ�Ա�����������ϼ�����֯��׼��</span></strong></p>"
				+ "<p><strong><span style=\"font-size: 16px\">7������֯��ϵ�������뵳��֯���ϣ��뵳־Ը��ȣ��Ƿ���һ�� </span></strong></p>"
				+ "<p><strong><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp; ��һ����</span></strong><span style=\"font-size: 16px\">ӦѧУͳһҪ�󣬵���֯����������ͳһ�鵵���������µ��������浵����ǲ����Ӧ��λ������֯��ϵ������Ӧǰ�����������죩��λ���ˣ���ĸ���������ڻ��㵳��֯��</span></p><p><span style=\"font-size: 16px\">&nbsp;&nbsp;&nbsp;&nbsp; ���磺�ڱ�����ҵ����������ڣ��������ֻ�ʡ���������˲����ģ�����֯����Ҳһ�����ڻ������˲����ģ�������֯��ϵת��������λ���ˣ���ĸ���������ڻ��㵳��֯��</span></p>";
		// if (chn.equals("wsdx")) {
		webView = (android.webkit.WebView) findViewById(R.id.webview);
		// StringBuilder sb = new StringBuilder();
		// sb.append(detail);
		Log.e("here", "here");
		// webView.loadUrl("http://ww.baidu.com");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.loadDataWithBaseURL(URLcontainer.urlip, detail, "text/html", "utf-8", null);
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
		// } else {
		// GetData();
		// }

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
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(data);

			content = demoJson.getString("content");

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
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
