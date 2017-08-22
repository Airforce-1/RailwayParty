package wuxc.single.railwayparty.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;

public class phoneActivity extends Activity implements OnClickListener, OnTouchListener {
	private EditText editphone;
	private TextView textChar;
	private TextView textSave;
	private static final int MAX_COUNT = 11;
	private ImageView imageBack;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private String phone = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_phone);
		// edit_motto
		// text_save
		// text_char
		// image_back
		editphone = (EditText) findViewById(R.id.edit_motto);
		editphone.addTextChangedListener(mTextWatcher);
		editphone.setSelection(editphone.length()); // ������ƶ����һ���ַ�����
		textChar = (TextView) findViewById(R.id.text_char);
		textSave = (TextView) findViewById(R.id.text_save);
		imageBack = (ImageView) findViewById(R.id.image_back);
		textSave.setOnClickListener(this);
		textSave.setOnTouchListener(this);
		imageBack.setOnClickListener(this);
		PreUserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

		readinfo();
	}

	private TextWatcher mTextWatcher = new TextWatcher() {

		private int editStart;

		private int editEnd;

		public void afterTextChanged(Editable s) {
			editStart = editphone.getSelectionStart();
			editEnd = editphone.getSelectionEnd();

			// ��ȥ������������������ջ���
			editphone.removeTextChangedListener(mTextWatcher);

			// ע������ֻ��ÿ�ζ�������EditText�������󳤶ȣ����ܶ�ɾ���ĵ����ַ��󳤶�
			// ��Ϊ����Ӣ�Ļ�ϣ������ַ����ԣ�calculateLength�������᷵��1
			while (calculateLength(s.toString()) > MAX_COUNT) { // �������ַ������������ƵĴ�Сʱ�����нضϲ���
				s.delete(editStart - 1, editEnd);
				editStart--;
				editEnd--;
			}
			editphone.setText(s);
			editphone.setSelection(editStart);

			// �ָ�������
			editphone.addTextChangedListener(mTextWatcher);

			setLeftCount();
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

	};

	/**
	 * ����������ݵ�������һ������=����Ӣ����ĸ��һ�����ı��=����Ӣ�ı�� ע�⣺�ú����Ĳ������ڶԵ����ַ����м��㣬��Ϊ�����ַ������������1
	 * 
	 * @param c
	 * @return
	 */
	private long calculateLength(CharSequence c) {
		double len = 0;
		for (int i = 0; i < c.length(); i++) {
			int tmp = (int) c.charAt(i);
			if (tmp > 0 && tmp < 127) {
				len += 1;
			} else {
				len++;
			}
		}
		return Math.round(len);
	}

	/**
	 * ˢ��ʣ����������,���ֵ����΢����140���֣���������200����
	 */
	private void setLeftCount() {
		textChar.setText(String.valueOf((MAX_COUNT - getInputCount())));
	}

	/**
	 * ��ȡ�û�����ķ�����������
	 * 
	 * @return
	 */
	private long getInputCount() {
		return calculateLength(editphone.getText().toString());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_save:
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				textSave.setTextColor(Color.parseColor("#FF7F24"));
			} else if (action == MotionEvent.ACTION_UP) {
				textSave.setTextColor(Color.parseColor("#ffffff"));
			} else if (action == MotionEvent.ACTION_CANCEL) {
				textSave.setTextColor(Color.parseColor("#ffffff"));
			}
			break;

		default:
			break;
		}
		return false;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.text_save:
			phone = editphone.getText().toString();
			if (phone.equals("")) {
				Toast.makeText(getApplicationContext(), "������ǩ��", Toast.LENGTH_SHORT).show();

			} else {
				writephone();
				Intent intent = new Intent();
				setResult(0, intent);
				finish();
			}
			break;

		default:
			break;
		}
	}

	private void readinfo() {
		// TODO Auto-generated method stub
		phone = PreUserInfo.getString("PhoneNumber", "");
		editphone.setText(phone);
	}

	private void writephone() {
		// TODO Auto-generated method stub
		Editor edit = PreUserInfo.edit();
		edit.putString("PhoneNumber", phone);
		edit.commit();
	}

}
