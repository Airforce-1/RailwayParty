package wuxc.single.railwayparty.branch;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.layout.RoundImageView;
import wuxc.single.railwayparty.layout.dialogtwo;

public class PartyMembershipTransAfterActivity extends Activity implements OnClickListener {
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
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_party_membership_trans_after);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		image_headimg = (RoundImageView) findViewById(R.id.image_headimg);
		text_name = (TextView) findViewById(R.id.text_name);
		text_party_name = (TextView) findViewById(R.id.text_party_name);
		text_party_address = (TextView) findViewById(R.id.text_party_address);
		text_party_time = (TextView) findViewById(R.id.text_party_time);
 
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
 
		settext();
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
		default:
			break;
		}
	}

	public void showAlertDialog() {

		dialogtwo.Builder builder = new dialogtwo.Builder(this);
		builder.setMessage("�Ƿ�ȷ�Ͻ����Ĺ�ϵת��\n" + Str_target);
		builder.setTitle("��ϵת��ȷ��");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

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
