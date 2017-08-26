package wuxc.single.railwayparty.my;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;

public class NewpwdActivity extends Activity implements OnClickListener {
	private EditText edit_old;
	private EditText edit_new;
	private EditText edit_new1;
	private Button btn_ok;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private String ticket = "";
	private static final int GET_DUE_DATA = 6;
	private TextView TextArticle;
	private TextView TextVideo;
	private int type = 2;
	private String classify = "";
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

			if (Type.equals("success")) {
				Toast.makeText(getApplicationContext(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "�޸�ʧ�ܣ�������", Toast.LENGTH_SHORT).show();
			}
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
		setContentView(R.layout.wuxc_activity_newpwd);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		edit_old = (EditText) findViewById(R.id.edit_old);
		edit_new = (EditText) findViewById(R.id.edit_new);
		edit_new1 = (EditText) findViewById(R.id.edit_new1);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		PreUserInfo = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ticket = PreUserInfo.getString("ticket", "");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.btn_ok:
			String old = edit_old.getText().toString();
			String newpwd = edit_new.getText().toString();
			String newpwd1 = edit_new1.getText().toString();
			if (old.equals("") || old == null) {
				Toast.makeText(getApplicationContext(), "�����������", Toast.LENGTH_SHORT).show();
			} else if (newpwd.equals("") || newpwd == null) {
				Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

			} else if (newpwd1.equals("") || newpwd1 == null) {
				Toast.makeText(getApplicationContext(), "��ȷ��������", Toast.LENGTH_SHORT).show();

			} else if (!newpwd1.equals(newpwd)) {
				Toast.makeText(getApplicationContext(), "�������벻һ��", Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(getApplicationContext(), "�����޸�", Toast.LENGTH_SHORT).show();
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
				// ArrayValues.add(new BasicNameValuePair("chn", "dyq"));
				// chn = "dyq";
				ArrayValues.add(new BasicNameValuePair("actionType", "password"));
				ArrayValues.add(new BasicNameValuePair("newPassword", edit_new.getText().toString()));
				ArrayValues.add(new BasicNameValuePair("code", "" + edit_old.getText().toString()));
				// ArrayValues.add(new
				// BasicNameValuePair("orgUserExtDto.mobile", "" +
				// Str_text_phone));
				// ArrayValues.add(new BasicNameValuePair("classify", "" +
				// classify));

				new Thread(new Runnable() { // �����߳��ϴ��ļ�
					@Override
					public void run() {
						String DueData = "";
						DueData = HttpGetData.GetData("api/member/modifyPassword", ArrayValues);
						Message msg = new Message();
						msg.obj = DueData;
						msg.what = GET_DUE_DATA;
						uiHandler.sendMessage(msg);
					}
				}).start();
			}
			break;
		default:
			break;
		}
	}
}
