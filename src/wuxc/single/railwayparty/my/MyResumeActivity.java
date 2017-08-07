package wuxc.single.railwayparty.my;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;

public class MyResumeActivity extends Activity implements OnClickListener {
	private String Str_text_name = "李超";
	private String Str_text_sex = "男";
	private String Str_text_id_number = "622331196011146661";
	private String Str_text_motto = "点击输入您的宣言";
	private String Str_text_phone = "点击绑定电话号码";
	private String Str_text_level = "本科";
	private String Str_text_party_time = "2014-10-01";
	private String Str_text_party_age = "1年";
	private String Str_text_branch = "五公司第一党支部";
	private String Str_text_statue = "政工干部";
	private String Str_text_partymoney_endtime = "2017-10-09";
	private TextView text_ok;
	private TextView text_name;
	private TextView text_sex;
	private TextView text_id_number;
	private TextView text_motto;
	private TextView text_phone;
	private TextView text_level;
	private TextView text_party_time;
	private TextView text_party_age;
	private TextView text_branch;
	private TextView text_statue;
	private TextView text_partymoney_endtime;
	private SharedPreferences PreUserInfo;// 存储个人信息
	private String ticket = "";
	private String Str_text_username = "李超";
	private String Str_text_party_time_yes = "无数据";
	private EditText text_username;
	private TextView text_party_time_yes;
	private static final int GET_DUE_DATA = 6;
	private TextView TextArticle;
	private TextView TextVideo;
	private int type = 2;
	private String userid;
	private String classify = "";
	private int creditsym = 0;
	private static final int GET_LOGININ_RESULT_DATA = 1;
	private static final String GET_SUCCESS_RESULT = "success";
	private static final int GET_VERSION_RESULT = 5;
	public Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_DUE_DATA:
				GetDataDueData(msg.obj);
				break;
			case GET_LOGININ_RESULT_DATA:
				GetDataDetailFromLoginResultData(msg.obj);
				break;
			default:
				break;
			}
		}
	};

	public void GetDataDetailFromLoginResultData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;

		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			if (Type.equals(GET_SUCCESS_RESULT)) {
				Data = demoJson.getString("data");
				// Toast.makeText(getApplicationContext(), "登陆成功",
				// Toast.LENGTH_SHORT).show();
				GetDetailData(Data);
				// finish();
			} else if (Type.equals("accountPwdError")) {
				// Toast.makeText(getApplicationContext(), "用户名和密码不匹配",
				// Toast.LENGTH_SHORT).show();

			} else if (Type.equals("userLocked")) {
				// Toast.makeText(getApplicationContext(), "账号被禁用",
				// Toast.LENGTH_SHORT).show();

			} else {
				// Toast.makeText(getApplicationContext(), "登陆失败",
				// Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void GetDetailData(String data) {
		// TODO Auto-generated method stub
		try {
			JSONObject demoJson = new JSONObject(data);

			Editor edit = PreUserInfo.edit();
			edit.putString("deptId", demoJson.getString("deptId"));
			edit.putString("deptName", demoJson.getString("deptName"));
			edit.putString("companyId", demoJson.getString("companyId"));
			edit.putString("companyName", demoJson.getString("companyName"));
			edit.putString("userPhoto", demoJson.getString("userPhoto"));
			edit.putString("userName", demoJson.getString("userName"));
			edit.putString("sex", demoJson.getString("sex"));
			edit.putString("loginId", demoJson.getString("loginId"));
			edit.putString("memberLevel", demoJson.getString("memberLevel"));
			edit.putString("memberLevelDesc", demoJson.getString("memberLevelDesc"));
			edit.putString("address", demoJson.getString("address"));
			edit.putString("permisons", demoJson.getString("permisons"));
			edit.putString("userLevel", demoJson.getString("userLevel"));
			edit.putString("position", demoJson.getString("position"));
			edit.putString("sign", demoJson.getString("sign"));
			edit.putString("hobby", demoJson.getString("hobby"));
			edit.putString("balance", demoJson.getString("balance"));
			edit.putString("cashBalance", demoJson.getString("cashBalance"));
			edit.putString("realName", demoJson.getString("realName"));
			edit.putString("cityCode", demoJson.getString("cityCode"));
			edit.putString("mobile", demoJson.getString("mobile"));
			edit.putString("alipayAccount", demoJson.getString("alipayAccount"));
			edit.putString("alipayRealname", demoJson.getString("alipayRealname"));
			edit.putString("renzhengName", demoJson.getString("renzhengName"));
			edit.putString("renzheng", demoJson.getString("renzheng"));
			edit.putString("icardNo", demoJson.getString("icardNo"));
			edit.putString("sixin", demoJson.getString("sixin"));
			edit.putString("guanzhu", demoJson.getString("guanzhu"));
			edit.putString("fensi", demoJson.getString("fensi"));
			edit.putString("shoucang", demoJson.getString("shoucang"));
			edit.putString("other1", demoJson.getString("other1"));
			edit.putString("other2", demoJson.getString("other2"));
			edit.putString("other3", demoJson.getString("other3"));
			edit.putString("pAge", demoJson.getString("pAge"));
			edit.putString("pInTime", demoJson.getString("pInTime"));
			edit.putString("pFormalTime", demoJson.getString("pFormalTime"));
			edit.putString("pMonthFare", demoJson.getString("pMonthFare"));
			edit.putString("pFareEndTime", demoJson.getString("pFareEndTime"));
			edit.putString("pUser", demoJson.getString("pUser"));
			edit.putString("pAllowOnLinFare", demoJson.getString("pAllowOnLinFare"));
			edit.commit();
			// GetAllData();
			ReadTicket();
			settext();
		} catch (

		JSONException e) {
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
			// pager = demoJson.getString("pager");

			if (Type.equals("success")) {
				Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				if (creditsym == 0) {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), CreditsRuleActivity.class);
					startActivity(intent);
					Editor edit = PreUserInfo.edit();
					edit.putInt("creditsym", 1);
					edit.commit();
				}
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "修改失败，请重试", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.wuxc_activity_myresume);
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		text_ok = (TextView) findViewById(R.id.text_ok);
		text_ok.setOnClickListener(this);
		text_name = (TextView) findViewById(R.id.text_name);
		text_sex = (TextView) findViewById(R.id.text_sex);
		text_id_number = (TextView) findViewById(R.id.text_id_number);
		text_motto = (TextView) findViewById(R.id.text_motto);
		text_phone = (TextView) findViewById(R.id.text_phone);
		text_level = (TextView) findViewById(R.id.text_level);
		text_party_time = (TextView) findViewById(R.id.text_party_time);
		text_party_age = (TextView) findViewById(R.id.text_party_age);
		text_branch = (TextView) findViewById(R.id.text_branch);
		text_statue = (TextView) findViewById(R.id.text_statue);
		text_partymoney_endtime = (TextView) findViewById(R.id.text_partymoney_endtime);
		text_username = (EditText) findViewById(R.id.text_username);
		text_party_time_yes = (TextView) findViewById(R.id.text_party_time_yes);
		text_motto.setOnClickListener(this);
		text_phone.setOnClickListener(this);
		PreUserInfo = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		getdata();

	}

	private void getdata() {
		// TODO Auto-generated method stub
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ticket = PreUserInfo.getString("ticket", "");
		final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("queryUserId", userid));
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		new Thread(new Runnable() { // 开启线程上传文件
			@Override
			public void run() {
				String LoginResultData = "";
				LoginResultData = HttpGetData.GetData("api/member/getUserInfo", ArrayValues);
				Message msg = new Message();
				msg.obj = LoginResultData;
				msg.what = GET_LOGININ_RESULT_DATA;
				uiHandler.sendMessage(msg);
			}
		}).start();
	}

	private void ReadTicket() {
		// TODO Auto-generated method stub
		creditsym = PreUserInfo.getInt("creditsym", 0);
		ticket = PreUserInfo.getString("ticket", "");
		Str_text_name = PreUserInfo.getString("realName", "无数据");
		userid = PreUserInfo.getString("loginId", "");
		Str_text_sex = PreUserInfo.getString("sex", "male");
		if (Str_text_sex.equals("male")) {
			Str_text_sex = "男";
		} else {
			Str_text_sex = "女";
		}
		Str_text_id_number = PreUserInfo.getString("icardNo", "无数据");
		Str_text_motto = PreUserInfo.getString("sign", "无数据");
		Str_text_phone = PreUserInfo.getString("mobile", "点击绑定手机号码");
		Str_text_level = PreUserInfo.getString("education", "本科");
		if (Str_text_level.equals("2")) {
			Str_text_level = "大专";
		} else if (Str_text_level.equals("3")) {
			Str_text_level = "高中";
		} else if (Str_text_level.equals("4")) {
			Str_text_level = "本科";
		} else if (Str_text_level.equals("5")) {
			Str_text_level = "大学";
		} else if (Str_text_level.equals("6")) {
			Str_text_level = "中专";
		} else if (Str_text_level.equals("7")) {
			Str_text_level = "中央党校大专";
		} else if (Str_text_level.equals("8")) {
			Str_text_level = "初中";
		} else if (Str_text_level.equals("9")) {
			Str_text_level = "技校";
		} else if (Str_text_level.equals("10")) {
			Str_text_level = "职高";
		} else if (Str_text_level.equals("11")) {
			Str_text_level = "研究生";
		} else if (Str_text_level.equals("12")) {
			Str_text_level = "中央党校大学";
		} else if (Str_text_level.equals("13")) {
			Str_text_level = "大学普通班";
		} else if (Str_text_level.equals("14")) {
			Str_text_level = "其他";
		} else if (Str_text_level.equals("15")) {
			Str_text_level = "小学";
		}
		Str_text_party_time = PreUserInfo.getString("pInTime", "无数据");
		Str_text_party_age = PreUserInfo.getString("pAge", "无数据") + "年";
		Str_text_branch = PreUserInfo.getString("deptName", "无数据");
		Str_text_statue = PreUserInfo.getString("position", "一般党员");
		if (Str_text_statue.equals("1")) {
			Str_text_statue = "政工干部";
		} else if (Str_text_statue.equals("2")) {
			Str_text_statue = "一般党员";
		} else if (Str_text_statue.equals("3")) {
			Str_text_statue = "公司领导";
		} else {
			Str_text_statue = "一般党员";
		}
		Str_text_party_time_yes = PreUserInfo.getString("pFormalTime", "无数据");
		Str_text_partymoney_endtime = PreUserInfo.getString("pFareEndTime", "无数据");
		Str_text_username = PreUserInfo.getString("userName", "无数据");
	}

	private void settext() {
		// TODO Auto-generated method stub
		text_name.setText(Str_text_name);
		text_sex.setText(Str_text_sex);
		text_id_number.setText(Str_text_id_number);
		text_motto.setText(Str_text_motto);
		text_phone.setText(Str_text_phone);
		text_level.setText(Str_text_level);
		text_party_time.setText(Str_text_party_time);
		text_party_age.setText(Str_text_party_age);
		text_branch.setText(Str_text_branch);
		text_statue.setText(Str_text_statue);
		text_partymoney_endtime.setText(Str_text_partymoney_endtime);
		text_username.setText(Str_text_username);
		text_party_time_yes.setText(Str_text_party_time_yes);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		Bundle bundle = data.getExtras();
		switch (requestCode) {

		case 1:
			if (!(data == null)) {
				ReadSign();
				settext();
			}

			break;
		case 2:
			if (!(data == null)) {
				ReadPhone();
				settext();
			}

			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void ReadSign() {
		// TODO Auto-generated method stub
		Str_text_motto = PreUserInfo.getString("Sign", "");

	}

	private void ReadPhone() {
		// TODO Auto-generated method stub

		Str_text_phone = PreUserInfo.getString("PhoneNumber", "");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.text_motto:
			Intent intent_sign = new Intent();
			intent_sign.setClass(getApplicationContext(), mottoActivity.class);
			startActivityForResult(intent_sign, 1);
			break;
		case R.id.text_phone:
			Intent intent_phone = new Intent();
			intent_phone.setClass(getApplicationContext(), phoneActivity.class);
			startActivityForResult(intent_phone, 2);
			break;
		case R.id.text_ok:
			final ArrayList ArrayValues = new ArrayList();
			// ArrayValues.add(new BasicNameValuePair("ticket", ticket));
			// ArrayValues.add(new BasicNameValuePair("applyType", "" + 2));
			// ArrayValues.add(new BasicNameValuePair("helpSType", "" + type));
			// ArrayValues.add(new BasicNameValuePair("modelSign",
			// "KNDY_APPLY"));
			// ArrayValues.add(new BasicNameValuePair("curPage", "" + curPage));
			// ArrayValues.add(new BasicNameValuePair("pageSize", "" +
			// pageSize));
			// final ArrayList ArrayValues = new ArrayList();
			ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
			// chn = GetChannelByKey.GetSign(PreALLChannel, "职工之家");
			// ArrayValues.add(new BasicNameValuePair("chn", "dyq"));
			// chn = "dyq";
			ArrayValues.add(new BasicNameValuePair("orgUserExtDto.user_name", text_username.getText().toString()));
			ArrayValues.add(new BasicNameValuePair("orgUserExtDto.sign", "" + Str_text_motto));
			ArrayValues.add(new BasicNameValuePair("orgUserExtDto.mobile", "" + Str_text_phone));
			// ArrayValues.add(new BasicNameValuePair("classify", "" +
			// classify));

			new Thread(new Runnable() { // 开启线程上传文件
				@Override
				public void run() {
					String DueData = "";
					DueData = HttpGetData.GetData("api/member/saveMemberInfo", ArrayValues);
					Message msg = new Message();
					msg.obj = DueData;
					msg.what = GET_DUE_DATA;
					uiHandler.sendMessage(msg);
				}
			}).start();
			break;
		default:
			break;
		}
	}
}
