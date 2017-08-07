package wuxc.single.railwayparty.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.model.ExamTopicModel;

public class ExamDetailActivity extends Activity implements OnClickListener {
	private TextView text_topic_main;
	private Button btn_last;
	private Button btn_next;
	private int[] er = { R.drawable.er1, R.drawable.er2, R.drawable.er3, R.drawable.er4, R.drawable.er5, R.drawable.er6,
			R.drawable.er7, R.drawable.er8, R.drawable.er9, R.drawable.er10, R.drawable.er11, R.drawable.er12,
			R.drawable.er13, R.drawable.er14, R.drawable.er15, R.drawable.er16, R.drawable.er17, R.drawable.er18,
			R.drawable.er19, R.drawable.er20 };
	private LinearLayout lin_a;
	private ImageView image_a;
	private TextView topic_a;
	private LinearLayout lin_b;
	private ImageView image_b;
	private TextView topic_b;
	private LinearLayout lin_c;
	private ImageView image_c;
	private TextView topic_c;
	private LinearLayout lin_d;
	private ImageView image_d;
	private TextView topic_d;
	private List<ExamTopicModel> list = new ArrayList<ExamTopicModel>();
	private int Number = 1;
	private TextView text_topic_detail;
	private ImageView image_number;
	private String Id;
	private String ticket = "";
	private static final String GET_SUCCESS_RESULT = "success";
	private static final String GET_FAIL_RESULT = "fail";
	private static final int GET_DUE_DATA = 6;
	private int topicnumber = 2;
	private int[] user;
	private String answer = "";
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxc_activity_exam_result_detail);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		text_topic_main = (TextView) findViewById(R.id.text_topic_main);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		lin_a = (LinearLayout) findViewById(R.id.lin_a);
		image_a = (ImageView) findViewById(R.id.image_a);
		topic_a = (TextView) findViewById(R.id.topic_a);
		lin_b = (LinearLayout) findViewById(R.id.lin_b);
		image_b = (ImageView) findViewById(R.id.image_b);
		topic_b = (TextView) findViewById(R.id.topic_b);
		lin_c = (LinearLayout) findViewById(R.id.lin_c);
		image_c = (ImageView) findViewById(R.id.image_c);
		topic_c = (TextView) findViewById(R.id.topic_c);
		lin_d = (LinearLayout) findViewById(R.id.lin_d);
		image_d = (ImageView) findViewById(R.id.image_d);
		topic_d = (TextView) findViewById(R.id.topic_d);
		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		lin_a.setOnClickListener(this);
		lin_b.setOnClickListener(this);
		lin_c.setOnClickListener(this);
		lin_d.setOnClickListener(this);
		Intent intent = this.getIntent(); // ��ȡ���е�intent����
		Bundle bundle = intent.getExtras(); // ��ȡintent�����bundle����
		Number = bundle.getInt("Number");
		Id = bundle.getString("keyid");
		ticket = bundle.getString("ticket");
		user = bundle.getIntArray("user");
		answer = bundle.getString("answer");
		Log.e("answer", answer);
		text_topic_detail = (TextView) findViewById(R.id.text_topic_detail);
		image_number = (ImageView) findViewById(R.id.image_number);
		if (Number == 1) {
			btn_last.setVisibility(View.GONE);
		} else {
			btn_last.setVisibility(View.VISIBLE);
		}
		if (Number == 20) {
			btn_next.setVisibility(View.GONE);
		} else {
			btn_next.setVisibility(View.VISIBLE);
		}
		// GetDataList();
		// showTop();
		// showtopic();
		GetData();
	}

	private void GetData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		ArrayValues.add(new BasicNameValuePair("datakey", "" + Id));
		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String DueData = "";
				DueData = HttpGetData.GetData("api/pubshare/testPaper/getPaper", ArrayValues);
				Message msg = new Message();
				msg.obj = DueData;
				msg.what = GET_DUE_DATA;
				uiHandler.sendMessage(msg);
			}
		}).start();

	}

	protected void GetDataDueData(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;
		String pager = null;
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			Data = demoJson.getString("data");
			if (Type.equals(GET_SUCCESS_RESULT)) {

				GetDataList(Data);
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

	private void GetDataList(String data) {

		try {

			JSONObject json_data = null;

			json_data = new JSONObject(data);

			String Subs = json_data.getString("subs");
			getdata(Subs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getdata(String subs) {
		// TODO Auto-generated method stub
		list.clear();
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(subs);
			JSONObject json_data = null;
			if (jArray.length() == 0) {
				Toast.makeText(getApplicationContext(), "������", Toast.LENGTH_SHORT).show();

			} else {
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);

					// json_data = json_data.getJSONObject("data");
					ExamTopicModel listinfo = new ExamTopicModel();

					listinfo.setId(json_data.getString("keyid"));
					listinfo.setTopic(json_data.getString("title"));
					listinfo.setScore(json_data.getInt("score"));
					listinfo.setDetail("������" + json_data.getString("analysis"));
					JSONArray jArray1 = new JSONArray(json_data.getString("subs"));
					listinfo.setAString("");
					listinfo.setBString("");
					listinfo.setCString("");
					listinfo.setDString("");
					listinfo.setAid("");
					listinfo.setBid("");
					listinfo.setCid("");
					listinfo.setDid("");
					String anwser = "";
					anwser = json_data.getString("answer");
					listinfo.setRightAnswer(0);
					// listinfo.setUserAnswer(user[i]);
					if (anwser.equals("A")) {
						listinfo.setRightAnswer(1);
					} else if (anwser.equals("B")) {
						listinfo.setRightAnswer(2);
					} else if (anwser.equals("C")) {
						listinfo.setRightAnswer(3);
					} else if (anwser.equals("D")) {
						listinfo.setRightAnswer(4);
					}
					for (int j = 0; j < jArray1.length(); j++) {
						JSONObject json_data1 = jArray1.getJSONObject(j);
						if (j == 0) {
							listinfo.setAString(json_data1.getString("code") + "��" + json_data1.getString("content"));
							listinfo.setAid(json_data1.getString("keyid"));
						} else if (j == 1) {
							listinfo.setBString(json_data1.getString("code") + "��" + json_data1.getString("content"));
							listinfo.setBid(json_data1.getString("keyid"));
						} else if (j == 2) {
							listinfo.setCString(json_data1.getString("code") + "��" + json_data1.getString("content"));
							listinfo.setCid(json_data1.getString("keyid"));
						} else if (j == 3) {
							listinfo.setDString(json_data1.getString("code") + "��" + json_data1.getString("content"));
							listinfo.setDid(json_data1.getString("keyid"));
						}
					}
					list.add(listinfo);

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int j = 0; j < list.size(); j++) {
			// Log.e("answer", answer);
			ExamTopicModel examTopicModel = list.get(j);
			String answer = getuseranswer(examTopicModel.getId());
			Log.e("answer", answer + "wuxc");
			if (answer.equals("A")) {

				examTopicModel.setUserAnswer(1);
			} else if (answer.equals("B")) {

				examTopicModel.setUserAnswer(2);
			} else if (answer.equals("C")) {

				examTopicModel.setUserAnswer(3);
			} else if (answer.equals("D")) {

				examTopicModel.setUserAnswer(4);
			} else {
				list.remove(j);
				j--;
			}
		}

		topicnumber = list.size();
		showTop();
		showtopic();
	}

	private String getuseranswer(String aid) {
		// TODO Auto-generated method stub
		String temp = "";
		try {

			JSONObject jsonObject = new JSONObject(answer);
			Log.e("jsonObject", "" + jsonObject);
			try {
				temp = jsonObject.getString(aid);
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return temp;
	}

	private void showtopic() {
		ExamTopicModel examTopicModel = list.get(Number - 1);
		if (examTopicModel.getAid().equals("")) {
			lin_a.setVisibility(View.GONE);
		} else {
			lin_a.setVisibility(View.VISIBLE);
		}
		if (examTopicModel.getBid().equals("")) {
			lin_b.setVisibility(View.GONE);
		} else {
			lin_b.setVisibility(View.VISIBLE);
		}
		if (examTopicModel.getCid().equals("")) {
			lin_c.setVisibility(View.GONE);
		} else {
			lin_c.setVisibility(View.VISIBLE);
		}
		if (examTopicModel.getDid().equals("")) {
			lin_d.setVisibility(View.GONE);
		} else {
			lin_d.setVisibility(View.VISIBLE);
		}
		topic_a.setText(examTopicModel.getAString());
		topic_b.setText(examTopicModel.getBString());
		topic_c.setText(examTopicModel.getCString());
		topic_d.setText(examTopicModel.getDString());

		topic_a.setTextColor(Color.parseColor("#000000"));
		topic_b.setTextColor(Color.parseColor("#000000"));
		topic_c.setTextColor(Color.parseColor("#000000"));
		topic_d.setTextColor(Color.parseColor("#000000"));
		text_topic_main.setText(examTopicModel.getTopic());
		text_topic_detail.setText(examTopicModel.getDetail());
		SpannableStringBuilder style = new SpannableStringBuilder(examTopicModel.getDetail());
		style.setSpan(new ForegroundColorSpan(Color.parseColor(getString(R.color.brown))), 0, 3,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		text_topic_detail.setText(style);
		image_a.setImageResource(R.drawable.icon_radio1);
		image_b.setImageResource(R.drawable.icon_radio1);
		image_c.setImageResource(R.drawable.icon_radio1);
		image_d.setImageResource(R.drawable.icon_radio1);
		if (examTopicModel.getUserAnswer() == 0) {

		} else if (examTopicModel.getUserAnswer() == 1) {
			image_a.setImageResource(R.drawable.icon_radio);
			topic_a.setTextColor(Color.parseColor("#cc0502"));
		} else if (examTopicModel.getUserAnswer() == 2) {
			image_b.setImageResource(R.drawable.icon_radio);
			topic_b.setTextColor(Color.parseColor("#cc0502"));
		} else if (examTopicModel.getUserAnswer() == 3) {
			image_c.setImageResource(R.drawable.icon_radio);
			topic_c.setTextColor(Color.parseColor("#cc0502"));
		} else if (examTopicModel.getUserAnswer() == 4) {
			image_d.setImageResource(R.drawable.icon_radio);
			topic_d.setTextColor(Color.parseColor("#cc0502"));
		}
		if (examTopicModel.getRightAnswer() == 0) {

		} else if (examTopicModel.getRightAnswer() == 1) {
			topic_a.setTextColor(Color.parseColor(getString(R.color.brown)));
		} else if (examTopicModel.getRightAnswer() == 2) {
			topic_b.setTextColor(Color.parseColor(getString(R.color.brown)));
		} else if (examTopicModel.getRightAnswer() == 3) {
			topic_c.setTextColor(Color.parseColor(getString(R.color.brown)));
		} else if (examTopicModel.getRightAnswer() == 4) {
			topic_d.setTextColor(Color.parseColor(getString(R.color.brown)));
		}
	}

	private void GetDataList() {
		// TODO Auto-generated method stub
		list.clear();
		for (int i = 0; i < 20; i++) {
			ExamTopicModel examTopicModel = new ExamTopicModel();
			examTopicModel.setAString("A����Ա��ڶ���ͳ�ŵ��ŵ�ƶ�");
			examTopicModel.setBString("B����Աʾ���ں͵�Ա�������ƶ�");
			examTopicModel.setCString("C����Ա�����ϸڡ���������ƶ�");
			examTopicModel.setDString("D����Ա������������ֱ����ϵ����Ⱥ���ƶ�");
			examTopicModel.setTopic(i + "����ѡ�⡿��չ����ѧһ��������Բ�ͬȺ�ڵ�Աʵ��������������Ҫ����ô���ڴ��ڵ�λ�ͷ�����ҵ�У��ص���ʵ��  ����");
			examTopicModel.setUserAnswer((int) (Math.random() * 4 + 1));
			examTopicModel.setRightAnswer((int) (Math.random() * 4 + 1));
			examTopicModel.setDetail("��������չ����ѧһ��������Բ�ͬȺ�ڵ�Աʵ��������������Ҫ����ô���ڴ��ڵ�λ�ͷ�����ҵ�У��ص���ʵ��Ա�����ϸڡ���������ƶ�");
			list.add(examTopicModel);
		}
	}

	private void showTop() {
		// TODO Auto-generated method stub
		image_number.setImageResource(er[Number - 1]);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;

		case R.id.btn_last:
			if (Number == 1) {

			} else {
				Number--;
				if (Number == 1) {
					btn_last.setVisibility(View.GONE);
				} else {
					btn_last.setVisibility(View.VISIBLE);
				}
				if (Number == topicnumber) {
					btn_next.setVisibility(View.GONE);
				} else {
					btn_next.setVisibility(View.VISIBLE);
				}
				showtopic();
				showTop();
			}
			break;
		case R.id.btn_next:
			if (Number == topicnumber) {
				// Toast.makeText(getApplicationContext(), "���Ĵ����ύ",
				// Toast.LENGTH_SHORT).show();
			} else {
				ExamTopicModel examTopicModelnext = list.get(Number - 1);
				if (examTopicModelnext.getUserAnswer() != 0) {
					Number++;
					if (Number == topicnumber) {
						btn_next.setVisibility(View.GONE);
					} else {
						btn_next.setVisibility(View.VISIBLE);
					}
					if (Number == 1) {
						btn_last.setVisibility(View.GONE);
					} else {
						btn_last.setVisibility(View.VISIBLE);
					}
					showtopic();
					showTop();
				} else {
					// Toast.makeText(getApplicationContext(), "������δ����",
					// Toast.LENGTH_SHORT).show();
				}

			}
			break;
		default:
			break;
		}
	}
}
