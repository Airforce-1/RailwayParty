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

public class mottoActivity extends Activity implements OnClickListener, OnTouchListener {
	private EditText editSign;
	private TextView textChar;
	private TextView textSave;
	private static final int MAX_COUNT = 15;
	private ImageView imageBack;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private String Sign = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_mottochange);
		// edit_motto
		// text_save
		// text_char
		// image_back
		editSign = (EditText) findViewById(R.id.edit_motto);
		editSign.addTextChangedListener(mTextWatcher);
		editSign.setSelection(editSign.length()); // ������ƶ����һ���ַ�����
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
			editStart = editSign.getSelectionStart();
			editEnd = editSign.getSelectionEnd();

			// ��ȥ������������������ջ���
			editSign.removeTextChangedListener(mTextWatcher);

			// ע������ֻ��ÿ�ζ�������EditText�������󳤶ȣ����ܶ�ɾ���ĵ����ַ��󳤶�
			// ��Ϊ����Ӣ�Ļ�ϣ������ַ����ԣ�calculateLength�������᷵��1
			while (calculateLength(s.toString()) > MAX_COUNT) { // �������ַ������������ƵĴ�Сʱ�����нضϲ���
				s.delete(editStart - 1, editEnd);
				editStart--;
				editEnd--;
			}
			editSign.setText(s);
			editSign.setSelection(editStart);

			// �ָ�������
			editSign.addTextChangedListener(mTextWatcher);

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
				len += 0.5;
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
		return calculateLength(editSign.getText().toString());
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
			Sign = editSign.getText().toString();
			if (Sign.equals("")) {
				Toast.makeText(getApplicationContext(), "������ǩ��", Toast.LENGTH_SHORT).show();

			} else {
				writeSign();
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
		Sign = PreUserInfo.getString("sign", "");
		editSign.setText(Sign);
	}

	private void writeSign() {
		// TODO Auto-generated method stub
		Editor edit = PreUserInfo.edit();
		edit.putString("sign", Sign);
		edit.commit();
	}

}
