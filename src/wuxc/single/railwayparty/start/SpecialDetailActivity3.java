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

public class SpecialDetailActivity3 extends Activity implements OnClickListener, OnItemClickListener {
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
		setContentView(R.layout.special_detail_activity4);
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
		// detail = " <div class=card_content
		// id=\"js-card-content\"><p>����֯��ϵת�Ӿ���ָ��Ա������������ξ���ѧϰ�������ԭ���뿪ԭ���ڵػ�λ���Լ����ʱ�������������ϣ��ҵص�ȽϹ̶��ģ�������֯ͬ�⣬������֯����ת�ӵ�һ�ֵ�����ʽ��</p></div>
		// <div class=intro_button></div> </div> <div class=\"starVideo
		// starVideo1\" id=starVideo> </div> </div> <div class=\"entry-detail\">
		// <div class=\"entry-basic\"> <div id=\"basic-info\"> <a
		// name=uni_baseinfo></a> <h2 class=titleH2><span
		// class=h2mark></span>������Ϣ</h2> <div class=\"card-list-box\"> <ul
		// class=cardlist> <li> <div class=\"cardlist-con\"> <p
		// class=\"cardlist-name\" title=\"��������\">��������</p> <p
		// class=\"cardlist-value\" title=\"����֯��ϵת��\">����֯��ϵת��</p> </div> </li>
		// <li> <div class=\"cardlist-con\"> <p class=\"cardlist-name\"
		// title=\"��������\">��������</p> <p class=\"cardlist-value\" title=\"Transfer
		// relationship between the party organization\">Transfer relationship
		// between the party organization</p> </div> </li> <li> <div
		// class=\"cardlist-con\"> <p class=\"cardlist-name\" title=\"�� Ա\">��
		// Ա</p> <p class=\"cardlist-value\" title=\"��Ա\">��Ա</p> </div> </li>
		// </ul> <ul class=cardlist> <li> <div class=\"cardlist-con\"> <p
		// class=\"cardlist-name\" title=\"ԭ ��\">ԭ ��</p> <p
		// class=\"cardlist-value\" title=\"�����������ξ���ѧϰ�����\">�����������ξ���ѧϰ�����</p>
		// </div> </li> <li> <div class=\"cardlist-con\"> <p
		// class=\"cardlist-name\" title=\"�� ��\">�� ��</p> <p
		// class=\"cardlist-value\" title=\"������ʽ\">������ʽ</p> </div> </li> </ul>
		// </div> </div> </div><div class=\"entry-before\"> </div> <div
		// class=\"entry-catalogue\"> <div class=\"entry-mod-catalogue\"> <div
		// class=catalogModWrap> <span class=h2mark></span> <table
		// class=catalogMod id=\"J-ext-mod-topcatalog\"> <tr>
		// <th><span><em></em>Ŀ¼</span></th> <td class=listTd> <div><em>1</em><a
		// title=\"����\" href=\"#5400047-5637620-1\">����</a></div>
		// <div><em>2</em><a title=\"ת�ư취\"
		// href=\"#5400047-5637620-2\">ת�ư취</a></div> </td><td class=listTd>
		// <div><em>3</em><a title=\"ת�Ӳ���\"
		// href=\"#5400047-5637620-3\">ת�Ӳ���</a></div> <div><em>4</em><a
		// title=\"ת��Ҫ��\" href=\"#5400047-5637620-4\">ת��Ҫ��</a></div> </td><td
		// class=listTd> <div><em>5</em><a title=\"ע������\"
		// href=\"#5400047-5637620-5\">ע������</a></div> </td> </tr> </table>
		// </div> </div> </div><div class=\"entry-body\"> </div> <div
		// class=\"entry-content\"> <div id=\"lemma-main\"
		// class=\"lemma-meaning\" data-sub=\"#5400047-5637620-0\"> <div
		// class=\"main_content_text cl\" id=\"main-content-text\"> <h2> <a
		// name=\"5400047-5637620-1\"></a> <a class=conArrow href=\"#\"
		// data-logid=\"h2-title\">�۵�</a> <span class=\"opt js-edittext\"> <a
		// class=edit href=\"/create/edit/?eid=5400047&sid=5637620&secid=1\"
		// data-log=\"edit-title\"><i class=ico></i>�༭����</a></span> <b
		// class=title>����</b></h2> <div class=\"sonConBox \"> <div
		// class=h2_content>
		// <p>����֯��ϵת�Ӿ���ָ��Ա������������ξ���ѧϰ�������ԭ���뿪ԭ���ڵػ�λ���Լ����ʱ�������������ϣ��ҵص�ȽϹ̶��ģ�������֯ͬ�⣬������֯����ת�ӵ�һ�ֵ�����ʽ��<a
		// href=\"https://p1.ssl.qhmsg.com/t01e96705a39cf9bdfb.jpg\"
		// class=\"show-img layoutright\" data-type=\"gallery\"><span
		// class=\"show-img-hd\" style=\"width:220px;height:310px;\"><img
		// id=\"img_6923201\" title=\"����֯��ϵת��\" alt=\"����֯��ϵת��\"
		// data-img=\"mod_img\"
		// data-src=\"https://p1.ssl.qhmsg.com/dr/220__/t01e96705a39cf9bdfb.jpg\"
		// style=\"width:220px;height:310px;\" /></span><span
		// class=\"show-img-bd\">����֯��ϵת��</span></a></p> </div> </div> <h2> <a
		// name=\"5400047-5637620-2\"></a> <a class=conArrow href=\"#\"
		// data-logid=\"h2-title\">�۵�</a> <span class=\"opt js-edittext\"> <a
		// class=edit href=\"/create/edit/?eid=5400047&sid=5637620&secid=2\"
		// data-log=\"edit-title\"><i class=ico></i>�༭����</a></span> <b
		// class=title>ת�ư취</b></h2> <div class=\"sonConBox \"> <div
		// class=h2_content> <p>1����Ա������������ξ���ѧϰ��<a target=\"_blank\"
		// href=\"/doc/633426-670384.html\">����񹤾���</a>������ԭ���뿪ԭ���ڵػ�λ���Լ����ʱ�������������ϣ��ҵص�ȽϹ̶��ģ�������֯ͬ�⣬Ӧ���涨ת�Ƶ�Ա��ʽ��֯��ϵ������д��Ա��֯��ϵ�����ţ���</p><p>2����ȫ����Χ�ڿ���ֱ���໥ת�Ƶ���֯��ϵ�ĵ���֯</p><p>��1���ؼ����ؼ����ϵط�������ί��֯������2������ֱ�����ظ����š�������һ��ظ����ż�����һ����������Ļ��ص�ί����3��ʡ��<a
		// target=\"_blank\"
		// href=\"/doc/5411133-5649231.html\">������</a>��ֱϽ��ֱ�����ص�ί����ί����֯������4��ʡ����������ֱϽ�и�����ίԱ����֯������������5���½�<a
		// target=\"_blank\"
		// href=\"/doc/3954608-4149845.html\">��������</a>�������β���֯������ũҵʦ������֣����β�����������6���й������ž�ʦ���ã����൱��ʦ�������β�������֯���ţ���7����·ϵͳ�ĸ���·�ֵ�ί��֯����</p><p>3��<a
		// target=\"_blank\"
		// href=\"/doc/2495086-2636695.html\">��ת</a>���˵�Ա��֯��ϵת�ݵ�<a
		// target=\"_blank\"
		// href=\"/doc/2042470-2161163.html\">����ԭ��</a></p><p>����ʵ�˹�����λ�ģ�Ӧ��ʱ����Ա��֯��ϵת����ȥ��λ����֯����ĳЩԭ����δ��ʵ�����ģ��ɽ���Ա��֯��ϵת����ĸ����ż��ס�صĽֵ���������֯����Ա����ʵ��λ�����н����¹�ϵ�͵������ϵ��ݱ������������������£�����������������ת���ò��ŵģ���Щ���ŵĵ���֯��߱�������������ͬ���ط���ίͬ�⣬Ҳ���Խ�����Щ��Ա����֯��ϵ�������ݲ�ͬ�������֯��Щ��Ա����֯���</p><p>4��<a
		// target=\"_blank\"
		// href=\"/doc/6753720-6968297.html\">����ר��ҵ��</a>��Ա��֯��ϵת�ݵĻ���ԭ��</p><p>�Ѿ���ʵ�˹�����λ�ģ�Ӧ����Ա��֯��ϵ��ʱת����ȥ��λ����֯����Ա����ʵ������λ�����н����¹�ϵ�͵������ϵ��ݱ������������������£��Ͷ��������������˲Ž���<a
		// target=\"_blank\"
		// href=\"/doc/4989151-5212831.html\">�������</a>�ģ���Щ�����ĵ���֯��߱���������ͬ���ط���ίͬ�⣬���Խ����ⲿ�ֵ�Ա����֯��ϵ�������ݲ�ͬ�������֯��Щ��Ա����֯�������ĳЩԭ��һʱ��������ʵ������λ�ģ��ɽ��䵳Ա��֯��ϵת�Ƶ����˻�ĸ��ס�صĽֵ���������֯��</p><p>5��<a
		// target=\"_blank\"
		// href=\"/doc/4061200-4259603.html\">�����ݸɲ�</a>��ְ����Ա��֯��ϵת�ݵ�ԭ��</p><p>��1�������ݸɲ�����֯��ϵһ�㱣����ԭ��λ���ɵ����齨��֧������2���͵ذ��õ����ݹ��˺���ְ�ɲ������˵�Ա����<a
		// target=\"_blank\"
		// href=\"/doc/7890462-8164557.html\">������֯��ϵ</a>һ��Ӧת����ס�����ֵ�����壩����֯����3���׵ذ��õ������ݡ���ְ�ɲ������˵�Ա���䵳����֯��ϵӦת�Ƶ����ܰ��õ����ֵ����磨�򣩻�嵳��֯����4�������ݸɲ������˵�Ա�򿴲���̽����Ů�����������ʱ�䳬�����������ϵģ����ڵ�λ�ĵ���֯Ӧ�����ǿ��ߵ�Ա<a
		// target=\"_blank\"
		// href=\"/doc/5417618-5655766.html\">֤����</a>��������λ������ĵ���֯Ӧ���ղ�������μ�<a
		// target=\"_blank\"
		// href=\"/doc/6751690-6966255.html\">������֯����</a>����5���ɲ������˵�Ա���ݡ����ݡ���ְ������Ƹ����һ��λ���������ʱ���ڰ������ϣ�Ӧ���䵳Ա��֯��ϵת�Ƶ��µĹ�����λ����֯��</p>
		// </div> </div> <h2> <a name=\"5400047-5637620-3\"></a> <a
		// class=conArrow href=\"#\" data-logid=\"h2-title\">�۵�</a> <span
		// class=\"opt js-edittext\"> <a class=edit
		// href=\"/create/edit/?eid=5400047&sid=5637620&secid=3\"
		// data-log=\"edit-title\"><i class=ico></i>�༭����</a></span> <b
		// class=title>ת�Ӳ���</b></h2> <div class=\"sonConBox \"> <div
		// class=h2_content> <p>1����Ա�����ڵ�֧��ͬ�⣬�ɵ�֧��������֧������һ����ί��<a
		// target=\"_blank\"
		// href=\"/doc/6231415-6444748.html\">����ί</a>������֯��ϵ�����š�</p><p>2����Ա��֧�������Ľ����ŵ���һ����ί������ί������ί������ί����ʵ�󣬸���֧�������Ľ����ţ������ֲ�ͬ���������Ӧ�Ľ����š���1�������Աת���õ�ί������ί������������֧�����򿪳��ӵ�ί������ί����ת��֧���Ľ����ţ���2�������Աת������������ί������ί�����򿪳������ڵ�ί������ί����ת�뵳ί������ί���Ľ����ţ���Ա�ֽ����ţ���ת�뵳ί������ί��������3�������Աת�������йص�λ���򿪳��ӵ�ί������ί����<a
		// target=\"_blank\"
		// href=\"/doc/6777372-6993228.html\">��ί��֯��</a>�Ľ����š�</p><p>3����Ա�ֵ�ί������ί����������֯��ϵ�����ŵ���ί��֯������ί��֯������ʵ�󣬸��ݵ�ί������ί�������Ľ������������������ί��֯����������Ӧ��ת��Ȩ�޵�ί��֯���ŵĽ����ţ�</p><p>4����Ա����ί��֯��������֯��ϵ�����ŵ�������Ӧ��ת��Ȩ�޵ĵ�ί��֯���ţ�����֯���ſ�������֯���ŵ�ת�뵳ί������ί������֯��ϵ�����ţ�</p><p>5����Ա��������ת��Ȩ�޵�ί��֯���Ž����ŵ�ת�뵳ί������ί�����õ�ί������ί�����ݽ���������������ӵ�ί������ί����ת��֧���Ľ����ţ�</p><p>6����Ա��ת�뵳ί������ί�������Ľ��ܵ�ת��֧��������</p>
		// </div> </div> <h2> <a name=\"5400047-5637620-4\"></a> <a
		// class=conArrow href=\"#\" data-logid=\"h2-title\">�۵�</a> <span
		// class=\"opt js-edittext\"> <a class=edit
		// href=\"/create/edit/?eid=5400047&sid=5637620&secid=4\"
		// data-log=\"edit-title\"><i class=ico></i>�༭����</a></span> <b
		// class=title>ת��Ҫ��</b></h2> <div class=\"sonConBox \"> <div
		// class=h2_content> <p>1�����ߵ�Ա��֯��ϵ������Ҫʹ��ͳһʽ����\"<a target=\"_blank\"
		// href=\"/doc/1475840-1560614.html\">�й�������</a>��Ա��֯��ϵ������\"���øֱʻ�ë����д���ּ�Ҫ���������Ϳ�ġ�Ӧ�ڽ����źʹ����ע����Ч�ڡ���Ա��֯��ϵ�����ű���Ӹǹ��£����Ӹ�<a
		// target=\"_blank\"
		// href=\"/doc/6306155-6519706.html\">�����</a>�������ŵ���Ч�ڿɸ��ݾ������ȷ����һ�㲻Ӧ����3���¡���Ա��֯��ϵ�������ɵ�Ա�Լ�Я��������Я���ģ�Ӧ�ɻ�Ҫ��ͨ���Ҫ����ת�ݡ�</p><p>2����Ա��֯��ϵ������һ����ʧ��Ҫ��ʱ�����ڵ�λ����֯��������ת����֯��ϵ�ĵ�ί��֯���ű��棬����֯���Ž�����顣���ȷϵ���˲�������������ת����֯��ϵ�ĵ���֯���Բ�ת��������֪ͨ<a
		// target=\"_blank\"
		// href=\"/doc/321648-340666.html\">���յ�λ</a>����֯��ԭ���������ϡ��Զ�ʧ�����ŵĵ�Ա��Ӧ��������������������صĻ�Ҫ�����ʵ��ĵ��ʹ��֡�</p><p>3����ԱӦ��ʱת�Ƶ���֯��ϵ���Թ��ڵĵ�Ա��֯��ϵ�����ţ���֯����Ҫ�����˽⡣���������ɲ���ʱת����֯��ϵ��Ҫ��������������ͽ��������г��������²��μ���֯����ģ����յ��¹涨�������ѵ��������ڵĵ�Ա��֯��ϵ���������ϣ��ɿ��������ŵ�λ���в�ת��</p><p>4����ί�ڵ�Ա��֯��ϵת�ӹ�����Ҫ��ʱ���õ�Ա������ת�ӹ�����ת����λҪ��ʱ��ת����Ա�ĵ�����ʱ����ת�뵥λ��ת�뵥λҪ������ת����λ��ϵ��</p><p>��ת��Ա��֯��ϵ����Ϊ��Ա��֯��ϵ�����ź͵�Ա֤�������֡�</p><p>1����Ա��֯��ϵ������</p><p>(1)��Ա��֯��ϵ�����ŵĺ���</p><p>��Ա��֯��ϵ�������ǵ�Ա�䶯��֯��ϵ��ƾ֤����Ա������λ�����仯�����ѧϰ��<a
		// target=\"_blank\"
		// href=\"/doc/6291470-6504975.html\">����ʱ��</a>�������������ҵص�ȽϹ̶��ģ�Ӧ���涨ת����ʽ��֯��ϵ������д��Ա��֯��ϵ�����š���Ա��֯��ϵת���󣬵�Ա�ڵ���֯�е�������ϵ�漴�����仯����ԱӦ��ת�뵥λ����֯�μӵ�����֯������ɵ��ѡ�</p><p>(2)��ת��Ա��֯��ϵ�����ŵ�<a
		// target=\"_blank\"
		// href=\"/doc/631461-668300.html\">һ�����</a></p><p>�ٵ�Ա��ת����֯��ϵʱ��������֯��׼���ɰ���Ա��֯��ϵת��������</p><p>�ڵ�Ա��ת����֯��ϵʱ��Ӧ�������ڵ�֧������֤�����ɵ�Ա���˳�֤�����ϼ���ί��֯���Ű���ת��������</p><p>����֯���ſ��ߵ�Ա��֯��ϵ������Ҫʹ��ͳһ��ʽ�ġ�<a
		// target=\"_blank\"
		// href=\"/doc/164159-173484.html\">�й���������Ա</a>��֯��ϵ�����š�����ë�ʻ�ֱ���д���ּ�Ҫ���������Ϳ�ġ���Ϳ�ĸ�������ӸǸ����¡�Ҫд����Աת���ͽ��յ�λ��ȫ�ƣ�Ҫ�ô�д��ע�����ѽ������·ݡ�Ҫ���ݱ������˵�ʵ������ڽ����źʹ����ע��<a
		// target=\"_blank\"
		// href=\"/doc/3933015-4127503.html\">��Ч����</a>��һ�㲻Ӧ���������¡���Ա��֯��ϵ�����ű���Ӹǹ��£����ڽ����źʹ�������Ӳ�λ�Ӹ�����¡�ÿ�Ž�����ԭ����ֻ��һ��ʹ�á��������������֯������ϵ�䶯��Ҫ����ת�Ƶ�Ա��֯��ϵ��������ʹ��һ�Ž����ţ���Ӧ�����������Ӹ���֯���ŵ�ӡ�¡�</p><p>�ܵ�Ա��֯��ϵ�������ɵ�Ա�Լ�Я�����Լ�����Я���ģ�Ӧ�ɻ�Ҫ��ͨ���Ҫ����ת�ݡ�</p><p>�ݵ�Ա�����ڽ�����ע������Ч���ڰ�����֯��ϵ��ת������ת����λ�ĵ���֯Ӧ���𶽴ٵ�Ա����ת����֯��ϵ������Щû���������ɲ����ڵ�ָ����λ��ת����֯��ϵ�ĵ�Ա��Ӧ��������������������������Բ������ģ�ԭ���ڵ�λ����֯Ӧ��������赳�ʹ��֡������ų�����Ч���������µ�Ա���޹ʲ�������֯��ϵ��ת�����ģ�Ӧ�����¹涨�������ѵ�����</p><p>�޸�����ί��֯�����ڽ��յ�Ա��֯��ϵ������ʱ��Ҫ�������˶ԣ��Բ����Ͻ�ת����Ҫ��ĵ�Ա��֯��ϵ�����ţ�Ҫ�˸���Ա���ˣ����䰴�涨���°����ת������</p><p>�ߵ�Ա��֯������һ����ʧ��Ҫ���������ڵ�λ�ĵ���֯��������ת����֯��ϵ�ĵ�ί��֯���ű��档����֯Ӧ�Զ�ʧ�����ŵ����������飬��ȷϵ���˲�����ʧ������������ת�ƹ�ϵ�ĵ���֯���Բ�ת��������֪ͨ�µ�λ����֯��ԭ���������ϡ�</p><p>2����Ա֤����</p><p>(1)��Ա֤���ŵ���;</p><p>��Ա֤�����ǵ�Ա��ʱ����μӵ�����֯�����ƾ֤������Ա��ʱ��֯��ϵƾ֤����Ա��ʱ���ʱ���ֵ�Ա֤������֤���䵳Ա��ݡ���Ա֤����һ��ֻ���ڵ�Ա��ʱ�������������ʹ�á��ֵ�Ա֤���ŵ���ʽ��Ա���䵳��֯��ϵû�д�ԭ���ڵĵ���֯ת�Ƴ�ȥ������ԭ��λ�μӵ�����֯������ɵ��Ѻ����б��Ȩ��<a
		// target=\"_blank\"
		// href=\"/doc/9062166-9393195.html\">ѡ��Ȩ�ͱ�ѡ��Ȩ</a>��</p><p>(2)���ߵ�Ա֤���ŵ�Ҫ��</p><p>���ߵ�Ա֤���ţ�Ӧ�ɵ�֧��д��֤�����ɻ��㵳ί����������ߵ�Ա֤���ţ�Ӧ��ͳ��ӡ�Ƶ�ʽ����֤����Ӧд����Ա���������Ա�ְ������ʽ��Ա����<a
		// target=\"_blank\"
		// href=\"/doc/334625-354439.html\">Ԥ����Ա</a>����ע��֤���ŵ�ʹ����Ч���ޡ���Ա֤����Ӧͳһ��ţ��Ӹǻ��㵳ί���¡���Ա֤����һ���ɱ������Եݽ���ȥ�ĵ���֯��Ҳ�����ɿ��ߵ�Ա֤���ŵĻ��㵳ί�ĳ���</p>
		// </div> </div> <h2> <a name=\"5400047-5637620-5\"></a> <a
		// class=conArrow href=\"#\" data-logid=\"h2-title\">�۵�</a> <span
		// class=\"opt js-edittext\"> <a class=edit
		// href=\"/create/edit/?eid=5400047&sid=5637620&secid=5\"
		// data-log=\"edit-title\"><i class=ico></i>�༭����</a></span> <b
		// class=title>ע������</b></h2> <div class=\"sonConBox \"> <div
		// class=h2_content> <p>��Ա��֯��ϵ�����ţ��ǵ�Ա����<a target=\"_blank\"
		// href=\"/doc/6499632-6713347.html\">��ݵ�֤��</a>����ԱӦ�������浳Ա��֯��ϵ�����ţ�����ʱ����ת��������</p><p>һ����Ա��ʱ�������һ��λȥ����ѧϰ��ʱ�������������ڵģ�Ӧ�֡��й���������Ա֤���š�������ʱ��֯��ϵ����֤����Ա��ݣ�����֯��ϵû��ת�ƣ�����ԭ��λ�μӵ�����֯������ɵ��Ѻ����б��Ȩ��ѡ��Ȩ�ͱ�ѡ��Ȩ��</p><p>������Ա������λ�����仯�����ѧϰ������ʱ�������������ϣ��ҵص�ȽϹ̶��ģ�Ӧ�֡�<a
		// target=\"_blank\"
		// href=\"/doc/5376128-5612242.html\">�й���������Ա��֯��ϵ������</a>��ת����֯��ϵ����Ա��֯��ϵת������������ϵ�漴�����仯����ԱӦ��ת�뵥λ����֯�μӵ�����֯������ɵ��Ѻ����б��Ȩ��ѡ��Ȩ�ͱ�ѡ��Ȩ��</p><p>����������������������ڣ����������û�й̶��ص���ʱ�޷�ת����֯��ϵ�ĵ�Ա���ɳ֡�<a
		// target=\"_blank\"
		// href=\"/doc/1920113-2031430.html\">������Ա</a>�֤����������ڵػ�λ�ĵ���֯�μ�<a
		// target=\"_blank\"
		// href=\"/doc/5488265-5726177.html\">��������</a>�����ɵ��ѣ��������б��Ȩ��ѡ��Ȩ�ͱ�ѡ��Ȩ����������Ա�֤���ɻ��㵳ί���𷢷š�����������Ա�ĵ���֯�Գ��С�������Ա�֤����������Ա��Ӧ����֤��ʱ���ղ�������뵳֧������С�飬ͬʱ���ϼ�����֯������</p><p>�ġ�<a
		// target=\"_blank\"
		// href=\"/doc/6808494-7025447.html\">��������Ա</a>�еĵ�Ա���͵ذ��ò���ԭ��λ����ģ���֯��ϵ����ԭ��λ�������䵳Ա�����Ķ��٣�������������Ա��֧������С�飻�׵ذ��õģ�����͵ذ��ã�������ȷ���ԭ�򲻱�μ�ԭ��λ��������ģ�Ӧ����֯��ϵת����ס�ص���֯��</p><p>�塢����ר��ҵ������ת�����еĵ�Ա������ʵ�˹�����λ�ģ�Ӧ��ʱ����֯��ϵת����ȥ��λ����֯����ĳЩԭ��һʱ��������ʵ������λ�ģ��ɽ�����֯��ϵת�����ˡ���ż��ĸ��ס�ص���֯��</p><p>����ͣн��ְ��Ա�еĵ�Ա��Ӧ�ڰ���ͣн��ְ������ʱ����֯��ϵת����ס�ص���֯��</p><p>�ߡ���Ա��˽�¶��ڳ�����������<a
		// target=\"_blank\"
		// href=\"/doc/220144-232878.html\">�Էѳ�����ѧ</a>��Ӧ��������������������֯��ϵ������ԭ��λ����֯�������������ӵĵ�Ա��Ӧ����ֹͣ����������</p><p>�ˡ�Ԯ����Ա�еĵ�Ա��֯��ϵ�����ɳ���λͳһת�ơ���֧�����ߵ�Ա��֯��ϵ������ʱ������ע����Ա�ľ���ȥ��������������������ʡ���У�������������ίԱ����֯��Э����ͳһת���⽻�����β��������⽻��ת���ҹ�פ��ʹ�ݵĵ���ίԱ�ᡣ</p><p>�š��ָ�������ͬ־����ڻָ�����֮ǰ�ѵ����˹�����Ҫ�������ָ����������ĵ���֯���ߵ�Ա��֯��ϵ�����ţ��������㵳ί����Ա��֯��ϵת��������</p><p>ʮ����Ա��֧����Χ�ڵ�����������ʱ�䶯��С�飬���ؿ��ߵ�Ա��֯��ϵ�����ţ�Ҳ���ؿ��ߵ�Ա֤���š�</p><p>ʮһ����֧������д��Ա��֯��ϵ������ʱ��Ӧ�ڽ����źʹ����ע����Ч���ޣ����ڽ����źʹ�������Ӳ��ּӸ�ӡ�£������ŵ���Ч��һ�㲻����15�졣</p><p>ʮ���������Ա���������µ�λ����֧�����ڿ�����֯��ϵ�����ź�����������������Ҫ�Ӹ�ӡ�¡���ʽ��Ա��Ԥ����ԱҪ�ֱ𿪾���֯��ϵ�����š�</p><p>ʮ����<a
		// target=\"_blank\"
		// href=\"/doc/1935272-2047449.html\">��ֱ����</a>����֧����Ա��ϵ��ת�ƣ����뾭��ϵͳ��ί����ת��������</p><p>ʮ�ġ���Աת����֯��ϵ��Ӧ�ɱ�������Я����֯��ϵ�����Ű���<a
		// target=\"_blank\"
		// href=\"/doc/5703890-5916607.html\">�����Լ�</a>Я���ģ�Ӧ�ɵ��ڽ�ͨ���Ҫ�������ݡ�</p><p>ʮ�塢��Ա��֯��ϵ�����Ż�֤����һ����ʧ��Ӧ��ʱ�����ڵ�λ����֯���棬����֯Ӧ������飬��ȷϵ���˲�����ʧ��Ӧд��������֣�����������ת����֯��ϵ�ĵ���֯���Բ�ת��������֪ͨ���յ�λ����֯��ԭ�����Ż�֤�������ϡ�</p><p>ʮ������Ա�Դ���֯��ϵӦ��ʱת�ơ�������Щ���������ɣ�����ʱת����֯��ϵ�����¹��ڵģ�Ӧ����������������������г��������²��μӵ�����֯����ģ��������ѵ�����</p>
		// </div> </div> </div> </div> </div><div class=\"entry-after\"> <div
		// id=characterRelationship style=\"display:none;padding:0 30px\"></div>
		// </div> <div class=\"entry-belong\"> <div class=\"reinforce cl\">
		// </div> </div> </div> </div> <div class=\"boxinner-r\"> <span
		// class=\"dw-ad-box js-wd-ad-box\" style=\"display:none\"> <a
		// href=\"###\" target=_blank id=dwAdBox> </a> <span
		// class=\"ico-adclose\"></span> </span> <div id=\"card-image\"> <div
		// class=img> <a data-type=gallery
		// href=\"https://p1.ssl.qhmsg.com/dr/270_500_/t01bfa90a36e26476f3.jpg\"
		// target=_blank> <img id=\"js-entry-image\" class=pic width=270
		// src=\"https://p1.ssl.qhmsg.com/dr/270_500_/t01bfa90a36e26476f3.jpg?size=268x200\"
		// alt=\"����֯��ϵת��\" title=\"\"> </a> </div> <div class=desc> <a
		// id=entry_search_url
		// href=\"http://image.so.com/i?src=360baike_sidepicmore&q=%E5%85%9A%E7%BB%84%E7%BB%87%E5%85%B3%E7%B3%BB%E8%BD%AC%E6%8E%A5\"
		// target=_blank>����֯��ϵת��</a> </div> <div class=bline1></div> <div
		// class=bline2></div> </div> <div id=weiboshow></div> <div
		// class=right_high_top></div> <div class=\"doc-interest\"
		// id=baike_interests> <h2></h2> <div class=\"cont items\"> <div
		// class=\"cont-img\"> <a href=\"###\" target=_blank> <img> </a> </div>
		// </div> </div> <div class=\"doc-rightslide\"
		// id='js-doc-rightslide'></div> <div id=\"J-ext-mod-nlpmodule-2\"
		// style=\"display:none\"> ";
		// if (chn.equals("wsdx")) {
		webView = (android.webkit.WebView) findViewById(R.id.webview);
		// StringBuilder sb = new StringBuilder();
		// sb.append(detail);
		Log.e("here", "here");
		// webView.loadUrl("http://ww.baidu.com");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.loadUrl("http://211.149.216.199:9050/console/pb/orgTree/orgTree");
		// webView.loadDataWithBaseURL(URLcontainer.urlip, detail, "text/html",
		// "utf-8", null);

		// } else {
		// GetData();
		// }

		// detail=getNewContent(detail);
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
