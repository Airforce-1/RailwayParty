package wuxc.single.railwayparty.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import wuxc.single.railwayparty.MainActivity;
import wuxc.single.railwayparty.R;

public class startLogoActivity extends Activity {
	private ImageView image_logo;
	private ImageView image_top;
	private LinearLayout lin_top;
	private int screenwidth = 0;
	private SharedPreferences PreGuidePage;// ����ȷ���Ƿ��ǵ�һ�ε�¼
	private SharedPreferences PreAccount;// �洢�û��������룬�����Զ���¼
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private int GuidePage = 0;// ����ҳ��ʾ��־��
	private String userName = "";
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wuxc_activity_startlogo);
		image_logo = (ImageView) findViewById(R.id.image_logo);
		image_top = (ImageView) findViewById(R.id.image_top);
		lin_top = (LinearLayout) findViewById(R.id.lin_top);
		screenwidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		int width = (int) (screenwidth * 0.9);
		RelativeLayout.LayoutParams LayoutParams = (android.widget.RelativeLayout.LayoutParams) lin_top
				.getLayoutParams();
		LayoutParams.width = width;
		lin_top.setLayoutParams(LayoutParams);

		LinearLayout.LayoutParams LayoutParams1 = (android.widget.LinearLayout.LayoutParams) image_top
				.getLayoutParams();
		LayoutParams1.width = (int) (screenwidth * 0.7);
		LayoutParams1.height = (int) (width / 6.4);
		image_top.setLayoutParams(LayoutParams1);

		RelativeLayout.LayoutParams LayoutParams2 = (android.widget.RelativeLayout.LayoutParams) image_logo
				.getLayoutParams();
		LayoutParams2.height = (int) (screenwidth * 0.4);
		LayoutParams2.width = (int) (screenwidth * 0.4);
		image_logo.setLayoutParams(LayoutParams2);
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		PreAccount = getSharedPreferences("Account", Context.MODE_PRIVATE);
		PreGuidePage = getSharedPreferences("GuidePage", Context.MODE_PRIVATE);
		GuidePage = PreGuidePage.getInt("GuidePage", 0);
		if (GuidePage == 0) {
			Intent intent = new Intent();
			intent.setClass(this, guidePageActivity.class);
			startActivity(intent);
			finish();
		} else {
			userName = PreAccount.getString("userName", "");
			password = PreAccount.getString("password", "");
			if (userName.equals("") || password.equals("")) {
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "�Զ���¼", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}

	}

}
