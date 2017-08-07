package wuxc.single.railwayparty.branch;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.internet.HttpGetData;
import wuxc.single.railwayparty.layout.dialogselecttwo;
import wuxc.single.railwayparty.layout.dialogtwo;

public class FragmentSendMoney extends Fragment implements OnClickListener {
	private View view;// ����Fragment view
	private TextView TextYear;
	private TextView TextTotalPay;
	private ImageView ImageLeft;
	private ImageView ImageRight;

	private Button BtnPay;
	private String idnumber;
	private String name;

	private RelativeLayout RelativeResult;
	private LinearLayout LinResult;
	private LinearLayout LinButtonResult;
	private LinearLayout Lin1;
	private LinearLayout Lin2;
	private LinearLayout Lin3;
	private LinearLayout LinNumber1;
	private LinearLayout LinNumber2;
	private LinearLayout LinNumber3;
	private LinearLayout LinNumber4;
	private LinearLayout LinNumber5;
	private LinearLayout LinNumber6;
	private LinearLayout LinNumber7;
	private LinearLayout LinNumber8;
	private LinearLayout LinNumber9;
	private LinearLayout LinNumber10;
	private LinearLayout LinNumber11;
	private LinearLayout LinNumber12;
	private ImageView ImageNumber1;
	private ImageView ImageNumber2;
	private ImageView ImageNumber3;
	private ImageView ImageNumber4;
	private ImageView ImageNumber5;
	private ImageView ImageNumber6;
	private ImageView ImageNumber7;
	private ImageView ImageNumber8;
	private ImageView ImageNumber9;
	private ImageView ImageNumber10;
	private ImageView ImageNumber11;
	private ImageView ImageNumber12;
	private ImageView ImageDot1;
	private ImageView ImageDot2;
	private ImageView ImageDot3;
	private ImageView ImageDot4;
	private ImageView ImageDot5;
	private ImageView ImageDot6;
	private ImageView ImageDot7;
	private ImageView ImageDot8;
	private ImageView ImageDot9;
	private ImageView ImageDot10;
	private ImageView ImageDot11;
	private ImageView ImageDot12;
	private boolean GetMonth = true;
	private int[] DotResourceId = { R.drawable.dot_ok, R.drawable.dot_grey };
	private int[][] NumberResourceId = {
			{ R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7,
					R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12 },
			{ R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7,
					R.drawable.r8, R.drawable.r9, R.drawable.r10, R.drawable.r11, R.drawable.r12 }, };
	// 0--�ѽ�
	// 1--δ��

	private int[] status = { 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 };
	// 0--δѡ
	// 1--��ѡ
	private int[] condition = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private double[] money = { 14, 56, 32, 23, 21, 53, 123.7, 344.7, 3454.6, 56.9, 786.9, 976.5 };
	private double totalpay = 0.0;
	private int screenwidth = 0;
	private float scale = 0;
	private float scalepx = 0;
	private float dp = 0;
	private int inityear = 2017;
	private String monthstring = "";
	private static final String[] STR_MONTH = { "-01", "-02", "-03", "-04", "-05", "-06", "-07", "-08", "-09", "-10",
			"-11", "-12", };

	// 0-����һ�������
	// 1-����һ�������

	private String ticket = "";
	private final static int GET_USER_HEAD_IMAGE = 6;
	private SharedPreferences PreUserInfo;// �洢������Ϣ
	private static final int GET_MONTH_RESULT_DATA = 1;
	private static final String GET_SUCCESS_RESULT = "success";
	private String Months = null;
	private double salary = 0;
	private String rechargeresult = "";
	private boolean canwrite = true;
	private String orderId = "";
	private static final int GET_DUE_DATA = 9;
	private String Month = "";
	private final static int GO_CHANGE_HEADIMG = 8;

	private static final String GET_FAIL_RESULT = "fail";
	private TextView text_last_month;
	private TextView text_last_time;
	private TextView text_name;
	private Button btn_ok;
	private double pMonthFaredouble;
	public Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case GET_MONTH_RESULT_DATA:
				GetDataDetailFromMonth(msg.obj);
				break;
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
		String Data = "";
		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");

			if (Type.equals(GET_SUCCESS_RESULT)) {
				orderId = "";
				orderId = demoJson.getString("orderId");
				totalpay = demoJson.getDouble("payMoney");
				if (!orderId.equals("")) {
					rechargeresult = "success";
				} else {
					rechargeresult = "";
				}
				// showalert();
			} else if (Type.equals(GET_FAIL_RESULT)) {
				Toast.makeText(getActivity(), "����������ʧ��", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "���ݸ�ʽУ��ʧ��", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void GetDataDetailFromMonth(Object obj) {

		// TODO Auto-generated method stub
		String Type = null;
		String Data = null;

		try {
			JSONObject demoJson = new JSONObject(obj.toString());
			Type = demoJson.getString("type");
			Data = demoJson.getString("data");
			if (Type.equals(GET_SUCCESS_RESULT)) {
				GetDetailData(Data);
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

		String months = null;
		try {
			JSONObject demoJson = new JSONObject(data);
			name = demoJson.getString("name");
			months = demoJson.getString("months");
			// salary = demoJson.getDouble("salary");
			// for (int i = 0; i < 12; i++) {
			// money[i] = salary;
			// }
			Months = months;

			GetMonth(months);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void GetMonth(String months) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 12; i++) {
			status[i] = 0;
		}
		JSONArray jArray;
		try {
			jArray = new JSONArray(months);

			for (int i = 0; i < jArray.length(); i++) {

				String month = jArray.getString(i);
				for (int j = 0; j < 12; j++) {
					if (month.equals(inityear + STR_MONTH[j])) {
						status[j] = 1;
					}
				}
			}
			ShowResult();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.wuxc_fragment_send_money, container, false);
			initview(view);
			setonclicklistener();
			setlayoutheight();
			setinitview();
			PreUserInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
			ReadTicket();
		}

		return view;
	}

	private void ReadTicket() {
		// TODO Auto-generated method stub
		ticket = PreUserInfo.getString("ticket", "");
		idnumber = PreUserInfo.getString("icardNo", "");
		text_name = (TextView) view.findViewById(R.id.text_name);
		text_name.setText(PreUserInfo.getString("userName", ""));
		text_last_month.setText("��ֹ���ڣ�" + PreUserInfo.getString("pFareEndMonth", ""));
		text_last_time.setText("ÿ�µ��ѣ�" + PreUserInfo.getString("pMonthFare", ""));
		try {
			pMonthFaredouble = Double.parseDouble(PreUserInfo.getString("pMonthFare", ""));
			for (int i = 0; i < money.length; i++) {
				money[i] = pMonthFaredouble;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// LoginId = PreUserInfo.getString("userName", "");

	}

	private void setinitview() {
		// TODO Auto-generated method stub
		RelativeResult.setVisibility(View.INVISIBLE);
		LinResult.setVisibility(View.INVISIBLE);
		LinButtonResult.setVisibility(View.INVISIBLE);
	}

	private void setlayoutheight() {
		// TODO Auto-generated method stub
		screenwidth = getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth();
		DisplayMetrics mMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		scale = getResources().getDisplayMetrics().density;
		dp = screenwidth / scale + 0.5f;
		scalepx = screenwidth / dp;
		int height = (int) ((screenwidth - 67 * scalepx) / 4);
		LinearLayout.LayoutParams layoutParams1 = (android.widget.LinearLayout.LayoutParams) Lin1.getLayoutParams();
		layoutParams1.height = height;
		Lin1.setLayoutParams(layoutParams1);
		layoutParams1 = (android.widget.LinearLayout.LayoutParams) Lin2.getLayoutParams();
		layoutParams1.height = height;
		Lin2.setLayoutParams(layoutParams1);
		layoutParams1 = (android.widget.LinearLayout.LayoutParams) Lin3.getLayoutParams();
		layoutParams1.height = height;
		Lin3.setLayoutParams(layoutParams1);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub

		TextYear.setOnClickListener(this);
		TextTotalPay.setOnClickListener(this);
		ImageLeft.setOnClickListener(this);
		ImageRight.setOnClickListener(this);

		BtnPay.setOnClickListener(this);

		RelativeResult.setOnClickListener(this);
		LinResult.setOnClickListener(this);
		LinButtonResult.setOnClickListener(this);
		Lin1.setOnClickListener(this);
		Lin2.setOnClickListener(this);
		Lin3.setOnClickListener(this);
		LinNumber1.setOnClickListener(this);
		LinNumber2.setOnClickListener(this);
		LinNumber3.setOnClickListener(this);
		LinNumber4.setOnClickListener(this);
		LinNumber5.setOnClickListener(this);
		LinNumber6.setOnClickListener(this);
		LinNumber7.setOnClickListener(this);
		LinNumber8.setOnClickListener(this);
		LinNumber9.setOnClickListener(this);
		LinNumber10.setOnClickListener(this);
		LinNumber11.setOnClickListener(this);
		LinNumber12.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	private void initview(View view) {
		// TODO Auto-generated method stub
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		TextYear = (TextView) view.findViewById(R.id.text_year);
		TextTotalPay = (TextView) view.findViewById(R.id.text_total_pay);
		ImageLeft = (ImageView) view.findViewById(R.id.image_left);
		ImageRight = (ImageView) view.findViewById(R.id.image_right);
		text_last_month = (TextView) view.findViewById(R.id.text_last_month);
		text_last_time = (TextView) view.findViewById(R.id.text_last_time);
		BtnPay = (Button) view.findViewById(R.id.btn_pay);
		RelativeResult = (RelativeLayout) view.findViewById(R.id.relative_result);
		LinResult = (LinearLayout) view.findViewById(R.id.lin_result);
		LinButtonResult = (LinearLayout) view.findViewById(R.id.lin_button_result);
		Lin1 = (LinearLayout) view.findViewById(R.id.lin_1);
		Lin2 = (LinearLayout) view.findViewById(R.id.lin_2);
		Lin3 = (LinearLayout) view.findViewById(R.id.lin_3);
		LinNumber1 = (LinearLayout) view.findViewById(R.id.lin_number_1);
		LinNumber2 = (LinearLayout) view.findViewById(R.id.lin_number_2);
		LinNumber3 = (LinearLayout) view.findViewById(R.id.lin_number_3);
		LinNumber4 = (LinearLayout) view.findViewById(R.id.lin_number_4);
		LinNumber5 = (LinearLayout) view.findViewById(R.id.lin_number_5);
		LinNumber6 = (LinearLayout) view.findViewById(R.id.lin_number_6);
		LinNumber7 = (LinearLayout) view.findViewById(R.id.lin_number_7);
		LinNumber8 = (LinearLayout) view.findViewById(R.id.lin_number_8);
		LinNumber9 = (LinearLayout) view.findViewById(R.id.lin_number_9);
		LinNumber10 = (LinearLayout) view.findViewById(R.id.lin_number_10);
		LinNumber11 = (LinearLayout) view.findViewById(R.id.lin_number_11);
		LinNumber12 = (LinearLayout) view.findViewById(R.id.lin_number_12);
		ImageNumber1 = (ImageView) view.findViewById(R.id.image_number_1);
		ImageNumber2 = (ImageView) view.findViewById(R.id.image_number_2);
		ImageNumber3 = (ImageView) view.findViewById(R.id.image_number_3);
		ImageNumber4 = (ImageView) view.findViewById(R.id.image_number_4);
		ImageNumber5 = (ImageView) view.findViewById(R.id.image_number_5);
		ImageNumber6 = (ImageView) view.findViewById(R.id.image_number_6);
		ImageNumber7 = (ImageView) view.findViewById(R.id.image_number_7);
		ImageNumber8 = (ImageView) view.findViewById(R.id.image_number_8);
		ImageNumber9 = (ImageView) view.findViewById(R.id.image_number_9);
		ImageNumber10 = (ImageView) view.findViewById(R.id.image_number_10);
		ImageNumber11 = (ImageView) view.findViewById(R.id.image_number_11);
		ImageNumber12 = (ImageView) view.findViewById(R.id.image_number_12);
		ImageDot1 = (ImageView) view.findViewById(R.id.image_dot_1);
		ImageDot2 = (ImageView) view.findViewById(R.id.image_dot_2);
		ImageDot3 = (ImageView) view.findViewById(R.id.image_dot_3);
		ImageDot4 = (ImageView) view.findViewById(R.id.image_dot_4);
		ImageDot5 = (ImageView) view.findViewById(R.id.image_dot_5);
		ImageDot6 = (ImageView) view.findViewById(R.id.image_dot_6);
		ImageDot7 = (ImageView) view.findViewById(R.id.image_dot_7);
		ImageDot8 = (ImageView) view.findViewById(R.id.image_dot_8);
		ImageDot9 = (ImageView) view.findViewById(R.id.image_dot_9);
		ImageDot10 = (ImageView) view.findViewById(R.id.image_dot_10);
		ImageDot11 = (ImageView) view.findViewById(R.id.image_dot_11);
		ImageDot12 = (ImageView) view.findViewById(R.id.image_dot_12);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lin_number_1:
			if (status[0] == 0) {
				Toast.makeText(getActivity(), "1�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[0] == 0) {
					condition[0] = 1;
					ImageDot1.setImageResource(R.drawable.dot_red);
				} else {
					condition[0] = 0;
					ImageDot1.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_2:
			if (status[1] == 0) {
				Toast.makeText(getActivity(), "2�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[1] == 0) {
					condition[1] = 1;
					ImageDot2.setImageResource(R.drawable.dot_red);
				} else {
					condition[1] = 0;
					ImageDot2.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_3:
			if (status[2] == 0) {
				Toast.makeText(getActivity(), "3�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[2] == 0) {
					condition[2] = 1;
					ImageDot3.setImageResource(R.drawable.dot_red);
				} else {
					condition[2] = 0;
					ImageDot3.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_4:
			if (status[3] == 0) {
				Toast.makeText(getActivity(), "4�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[3] == 0) {
					condition[3] = 1;
					ImageDot4.setImageResource(R.drawable.dot_red);
				} else {
					condition[3] = 0;
					ImageDot4.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_5:
			if (status[4] == 0) {
				Toast.makeText(getActivity(), "5�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[4] == 0) {
					condition[4] = 1;
					ImageDot5.setImageResource(R.drawable.dot_red);
				} else {
					condition[4] = 0;
					ImageDot5.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_6:
			if (status[5] == 0) {
				Toast.makeText(getActivity(), "6�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[5] == 0) {
					condition[5] = 1;
					ImageDot6.setImageResource(R.drawable.dot_red);
				} else {
					condition[5] = 0;
					ImageDot6.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_7:
			if (status[6] == 0) {
				Toast.makeText(getActivity(), "7�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[6] == 0) {
					condition[6] = 1;
					ImageDot7.setImageResource(R.drawable.dot_red);
				} else {
					condition[6] = 0;
					ImageDot7.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_8:
			if (status[7] == 0) {
				Toast.makeText(getActivity(), "8�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[7] == 0) {
					condition[7] = 1;
					ImageDot8.setImageResource(R.drawable.dot_red);
				} else {
					condition[7] = 0;
					ImageDot8.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_9:
			if (status[8] == 0) {
				Toast.makeText(getActivity(), "9�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[8] == 0) {
					condition[8] = 1;
					ImageDot9.setImageResource(R.drawable.dot_red);
				} else {
					condition[8] = 0;
					ImageDot9.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_10:
			if (status[9] == 0) {
				Toast.makeText(getActivity(), "10�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[9] == 0) {
					condition[9] = 1;
					ImageDot10.setImageResource(R.drawable.dot_red);
				} else {
					condition[9] = 0;
					ImageDot10.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_11:
			if (status[10] == 0) {
				Toast.makeText(getActivity(), "11�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[10] == 0) {
					condition[10] = 1;
					ImageDot11.setImageResource(R.drawable.dot_red);
				} else {
					condition[10] = 0;
					ImageDot11.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;
		case R.id.lin_number_12:
			if (status[11] == 0) {
				Toast.makeText(getActivity(), "12�µ����ѽ�", Toast.LENGTH_SHORT).show();
			} else {
				if (condition[11] == 0) {
					condition[11] = 1;
					ImageDot12.setImageResource(R.drawable.dot_red);
				} else {
					condition[11] = 0;
					ImageDot12.setImageResource(R.drawable.dot_grey);
				}
				ShowPayMoney();
			}
			break;

		case R.id.image_left:
			inityear = inityear - 1;
			GetMonth(Months);
			break;
		case R.id.image_right:
			inityear = inityear + 1;
			GetMonth(Months);
			break;
		case R.id.text_year:

			break;
		case R.id.btn_pay:
			if (monthstring.equals("")) {
				Toast.makeText(getActivity(), "��δѡ��Ҫ֧�����·�", Toast.LENGTH_SHORT).show();

			} else if (canwrite) {
				selecttype();
			} else {
				Toast.makeText(getActivity(), "�����ظ��ύ", Toast.LENGTH_SHORT).show();

			}

			break;
		case R.id.btn_ok:

			ShowResult();

			GetMonthData(idnumber);
			break;
		default:
			break;
		}
	}

	private void GetMonthData(String idnumber) {
		// TODO Auto-generated method stub
		final ArrayList ArrayValues = new ArrayList();
		ArrayValues.add(new BasicNameValuePair("idCardNo", idnumber));
		ArrayValues.add(new BasicNameValuePair("ticket", "" + ticket));
		new Thread(new Runnable() { // �����߳��ϴ��ļ�
			@Override
			public void run() {
				String LoginResultData = "";
				LoginResultData = HttpGetData.GetData("api/pb/common/queryFare", ArrayValues);
				Message msg = new Message();
				msg.obj = LoginResultData;
				msg.what = GET_MONTH_RESULT_DATA;
				uiHandler.sendMessage(msg);
			}
		}).start();
	}

	private void selecttype() {

		dialogselecttwo.Builder builder = new dialogselecttwo.Builder(getActivity());
		builder.setMessage("��ѡ��ɷѷ�ʽ");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("���Ͻɷ�", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showAlertDialog();
			}

		});

		builder.setNegativeButton("���½ɷ�", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showAlert();
			}
		});

		builder.create().show();

	}

	public void showAlertDialog() {

		dialogtwo.Builder builder = new dialogtwo.Builder(getActivity());
		builder.setMessage("����֧��" + totalpay + "Ԫ\n���ڽ���" + inityear + "��" + monthstring + "�·ݵ���");
		builder.setTitle("�ɷ�ȷ��");
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

	public void showAlert() {

		dialogtwo.Builder builder = new dialogtwo.Builder(getActivity());
		builder.setMessage("��������������½ɷ�");
		builder.setTitle("��ܰ��ʾ");
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

	// private void GetMonth(String months) {
	// // TODO Auto-generated method stub
	// for (int i = 0; i < 12; i++) {
	// status[i] = 0;
	// }
	// // JSONArray jArray;
	// // try {
	// // jArray = new JSONArray(months);
	// //
	// // for (int i = 0; i < jArray.length(); i++) {
	// //
	// // String month = jArray.getString(i);
	// // for (int j = 0; j < 12; j++) {
	// // if (month.equals(inityear + STR_MONTH[j])) {
	// // status[j] = 1;
	// // }
	// // }
	// // }
	// // ShowResult();
	// // } catch (JSONException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// TextYear.setText("" + inityear);
	// setImage();
	// }

	private void ShowResult() {
		// TODO Auto-generated method stub
		RelativeResult.setVisibility(View.VISIBLE);
		LinResult.setVisibility(View.VISIBLE);
		LinButtonResult.setVisibility(View.VISIBLE);
		btn_ok.setVisibility(View.GONE);

		text_last_time.setVisibility(View.GONE);
		text_last_month.setVisibility(View.GONE);
		TextYear.setText("" + inityear);
		setImage();
	}

	public void setImage() {
		// TODO Auto-generated method stub
		ImageView[] ImageNumber = { ImageNumber1, ImageNumber2, ImageNumber3, ImageNumber4, ImageNumber5, ImageNumber6,
				ImageNumber7, ImageNumber8, ImageNumber9, ImageNumber10, ImageNumber11, ImageNumber12 };
		ImageView[] ImageDot = { ImageDot1, ImageDot2, ImageDot3, ImageDot4, ImageDot5, ImageDot6, ImageDot7, ImageDot8,
				ImageDot9, ImageDot10, ImageDot11, ImageDot12 };
		for (int i = 0; i < 12; i++) {
			ImageNumber[i].setImageResource(NumberResourceId[status[i]][i]);
			ImageDot[i].setImageResource(DotResourceId[status[i]]);
		}

	}

	private void ShowPayMoney() {
		// TODO Auto-generated method stub
		double result = 0;
		monthstring = "";
		Month = "";
		for (int i = 0; i < 12; i++) {
			if (condition[i] == 1) {
				result = result + money[i];
				monthstring = monthstring + (i + 1) + "��";
				Month = Month + (inityear + STR_MONTH[i]) + ",";
			}

		}
		if (!monthstring.equals("")) {
			String b[] = monthstring.split("");
			monthstring = "";
			for (int i = 0; i < b.length - 1; i++) {
				monthstring = monthstring + b[i];
			}
			String c[] = Month.split("");
			Month = "";
			for (int i = 0; i < c.length - 1; i++) {
				Month = Month + c[i];
			}
		}

		totalpay = result;
		TextTotalPay.setText("����" + result + "Ԫ");
	}
}
