package wuxc.single.railwayparty.branch;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.layout.RoundImageView;
import wuxc.single.railwayparty.layout.dialogtwo;

public class PartyMembershipTransActivity extends Activity implements OnClickListener {
	private RoundImageView image_headimg;
	private TextView text_name;
	private TextView text_party_name;
	private TextView text_party_address;
	private TextView text_party_time;
	private Button btn_ok;
	private String Str_name = "������";
	private String Str_party_name = "����һ�ֵ�һ��֧��";
	private String Str_party_address = "����ʡ������������·1��";
	private String Str_party_time = "2017��5��";
	private String Str_target;
	private TextView text_target;
	private LinearLayout lin_rule;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private String ToId;
	private String myid;
	private int ticket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_party_membership_trans);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		image_headimg = (RoundImageView) findViewById(R.id.image_headimg);
		text_name = (TextView) findViewById(R.id.text_name);
		text_party_name = (TextView) findViewById(R.id.text_party_name);
		text_party_address = (TextView) findViewById(R.id.text_party_address);
		text_party_time = (TextView) findViewById(R.id.text_party_time);
		text_target = (TextView) findViewById(R.id.text_target);
		lin_rule = (LinearLayout) findViewById(R.id.lin_rule);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		text_target.setOnClickListener(this);
		lin_rule.setOnClickListener(this);
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		ReadTicket();
		settext();
	}

	private void ReadTicket() {
		// TODO Auto-generated method stub
		Str_name = PreUserInfo.getString("userName", "");
		Str_party_name = PreUserInfo.getString("deptName", "");
		Str_party_address = PreUserInfo.getString("address", "");
		Str_party_time = PreUserInfo.getString("pInTime", "");
		myid = PreUserInfo.getString("deptId", "");
		ticket = PreUserInfo.getInt("ticket", 0);
	}

	private void settext() {
		// TODO Auto-generated method stub
		text_name.setText(Str_name);
		text_party_name.setText("����֯���ƣ�" + Str_party_name);
		text_party_address.setText("����֯��ַ��" + Str_party_address);
		text_party_time.setText("ת�뱾��֯ʱ�䣺" + Str_party_time);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		Bundle bundle = data.getExtras();
		switch (requestCode) {

		case 1:
			if (!(data == null)) {
				Str_target = bundle.getString("Name");
				ToId = bundle.getString("ToId");
				text_target.setText(Str_target);
			}

			break;

		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.btn_ok:
			showAlertDialog();
			break;
		case R.id.text_target:
			Intent intent_membership = new Intent();
			intent_membership.setClass(getApplicationContext(), PartyBranchDataListActivity.class);
			startActivityForResult(intent_membership, 1);
			break;
		case R.id.lin_rule:
			Intent intent_rule = new Intent();
			intent_rule.setClass(getApplicationContext(), PartyBranchDataListActivity.class);
			startActivity(intent_rule);
			break;
		default:
			break;
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
		ArrayValues.add(new BasicNameValuePair("relationChangeDto.fromOrg", myid));
		ArrayValues.add(new BasicNameValuePair("relationChangeDto.toOrg", ToId));

		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/pb/relationChange/save", ArrayValues);

			}
		}).start();

	}

	public void showAlertDialog() {

		dialogtwo.Builder builder = new dialogtwo.Builder(this);
		builder.setMessage("�Ƿ�ȷ�Ͻ����Ĺ�ϵת��\n" + Str_target);
		builder.setTitle("��ϵת��ȷ��");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent_membership = new Intent();
				intent_membership.setClass(getApplicationContext(), PartyMembershipTransAfterActivity.class);
				startActivity(intent_membership);
				GetData();

			}
		});

		builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();

	}
}
