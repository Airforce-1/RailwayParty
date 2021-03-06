package wuxc.single.railwayparty.other;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import wuxc.single.railwayparty.MainActivity;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;

public class LoginActivity2 extends Activity implements OnClickListener {
	private EditText edit_username;
	private EditText edit_password;
	private Button btn_ok;
	private LinearLayout lin_remeber;
	private LinearLayout lin_forget;
	private ImageView image_selected;
	private String userName = "";
	private String password = "";
	private boolean remeber = true;
	private boolean IsLogin = true;// 防止重复点击
	private static final int GET_LOGININ_RESULT_DATA = 1;
	private static final String GET_SUCCESS_RESULT = "success";
	private String userPhoto;
	private String address;
	private String ticket;
	private String loginId;
	private String sex;
	private String sessionId;
	private String username;
	private SharedPreferences PreAccount;// 存储用户名和密码，用于自动登录
	private SharedPreferences PreUserInfo;// 存储个人信息
	private SharedPreferences PreLogin;
	private String remeberusername = "";
	private String remeberpassword = "";
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_LOGININ_RESULT_DATA:
				GetDataDetailFromLoginResultData(msg.obj);
				break;
			case 66:
				GetData(msg.obj);
				break;
			default:
				break;
			}
		}
	};

	protected void GetData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;

		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			if (Type.equals(GET_SUCCESS_RESULT)) {
				Editor edit = PreLogin.edit();
				edit.putBoolean(getTimeByCalendar() + userName, true);
				Log.e("PreLogin", "" + true);
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_login);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		edit_username = (EditText) findViewById(R.id.edit_username);
		edit_password = (EditText) findViewById(R.id.edit_password);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		lin_remeber = (LinearLayout) findViewById(R.id.lin_remeber);
		lin_forget = (LinearLayout) findViewById(R.id.lin_forget);
		image_selected = (ImageView) findViewById(R.id.image_selected);
		btn_ok.setOnClickListener(this);
		lin_remeber.setOnClickListener(this);
		lin_forget.setOnClickListener(this);
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		PreAccount = getSharedPreferences("Account", Context.MODE_PRIVATE);
		PreLogin = getSharedPreferences("Login", Context.MODE_PRIVATE);
		remeber = PreAccount.getBoolean("remeber", true);
		Log.e("remeber_2", remeber + "");
		remeberusername = PreAccount.getString("LoginId", "");
		remeberpassword = PreAccount.getString("pwd", "");
		edit_username.setText(remeberusername);
		if (remeber) {
			edit_password.setText(remeberpassword);
		} else {
			edit_password.setText("");
		}
		remeber = true;
	}

	public void GetDataDetailFromLoginResultData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;
		btn_ok.setText("登录");
		IsLogin = true;
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			if (Type.equals(GET_SUCCESS_RESULT)) {
				Data = demoJson.getString("data");
				Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
				GetDetailData(Data);
				if (true) {
					Intent intent_credits = new Intent();
					intent_credits.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent_credits);
				}
				finish();
			} else if (Type.equals("accountPwdError")) {
				Toast.makeText(getApplicationContext(), "用户名和密码不匹配", Toast.LENGTH_SHORT).show();

			} else if (Type.equals("userLocked")) {
				Toast.makeText(getApplicationContext(), "账号被禁用", Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
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

			userPhoto = demoJson.getString("userPhoto");
			address = demoJson.getString("address");
			ticket = demoJson.getString("ticket");
			loginId = demoJson.getString("loginId");
			sex = demoJson.getString("sex");
			sessionId = demoJson.getString("sessionId");
			username = demoJson.getString("username");
			WriteAccount();
			WriteUserInfo();
			if (!PreLogin.getBoolean(getTimeByCalendar() + userName, false)) {
				final ArrayList ArrayValues = new ArrayList();
				ArrayValues.add(new BasicNameValuePair("userScoreDto.inOut", "1"));
				ArrayValues.add(new BasicNameValuePair("userScoreDto.classify", "userSign"));
				ArrayValues.add(new BasicNameValuePair("userScoreDto.amount", "1"));
				ArrayValues.add(new BasicNameValuePair("userScoreDto.reason", "每日首次登录"));
				ArrayValues.add(new BasicNameValuePair("ticket", ticket));
				new Thread(new Runnable() { // 开启线程上传文件
					@Override
					public void run() {
						String LoginResultData = "";
						LoginResultData = HttpGetData.GetData("api/console/userScore/save", ArrayValues);
						Message msg = new Message();
						msg.obj = LoginResultData;
						msg.what = 66;
						uiHandler.sendMessage(msg);
					}
				}).start();
			}
			// GetAllData();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getTimeByCalendar() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		int day = cal.get(Calendar.DAY_OF_MONTH);// 获取日
		String Mon = "";
		String Day = "";
		month++;
		if (month < 10) {
			Mon = "0" + month;
		}else {
			Mon = "" + month;
		}
		if (day < 10) {
			Day = "0" + day;
		}else {
			Day=day+"";
		}
		Log.e("getTimeByCalendar", year + "-" + Mon + "-" + Day);
		return year + "-" + Mon + "-" + Day;
	}

	private void WriteUserInfo() {
		// TODO Auto-generated method stub
		Editor edit = PreUserInfo.edit();
		edit.putString("userPhoto", userPhoto);
		edit.putString("address", address);
		edit.putString("ticket", ticket);
		edit.putString("sex", sex);
		edit.putString("loginId", loginId);
		edit.putString("sessionId", sessionId);
		edit.putString("sex", sex);
		edit.commit();
	}

	private void WriteAccount() {
		// TODO Auto-generated method stub
		if (true) {
			Editor edit = PreAccount.edit();
			edit.putString("LoginId", userName);
			edit.putString("pwd", password);

			edit.putBoolean("remeber", remeber);
			Log.e("remeber_2_w", remeber + "");
			edit.putBoolean("autoLogin", true);
			edit.commit();
			// } else {
			// Editor edit = PreAccount.edit();
			// edit.putString("LoginId", null);
			// edit.putString("pwd", null);
			// edit.commit();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.btn_ok:
			userName = edit_username.getText().toString();
			password = edit_password.getText().toString();
			if (userName.equals("") || userName == null) {
				Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
			} else if (password.equals("") || password == null) {
				Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
			} else {
				if (IsLogin) {
					final ArrayList ArrayValues = new ArrayList();
					ArrayValues.add(new BasicNameValuePair("login_id", userName));
					ArrayValues.add(new BasicNameValuePair("pwd", password));
					new Thread(new Runnable() { // 开启线程上传文件
						@Override
						public void run() {
							String LoginResultData = "";
							LoginResultData = HttpGetData.GetData("api/member/login", ArrayValues);
							Message msg = new Message();
							msg.obj = LoginResultData;
							msg.what = GET_LOGININ_RESULT_DATA;
							uiHandler.sendMessage(msg);
						}
					}).start();
					btn_ok.setText("正在登录...");
					IsLogin = false;
				} else {
					Toast.makeText(getApplicationContext(), "请勿重复点击", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.lin_remeber:
			remeber = !remeber;
			if (remeber) {
				image_selected.setImageResource(R.drawable.noselected);
			} else {
				image_selected.setImageResource(R.drawable.selected);
			}
			break;
		case R.id.lin_forget:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), ResetPwdActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
