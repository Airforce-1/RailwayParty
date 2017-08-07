package wuxc.single.railwayparty.branch;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Window;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.layout.PartViewforage;
import wuxc.single.railwayparty.layout.PartViewforlevel;

public class Statisticsforlevel extends Activity implements OnClickListener {

	private int[] data = new int[] { 0, 0, 0, 0, 0, 0, 0, 400, 500, 200, 100, 800, 600, 400, 500, 200, 100, 800, 600,
			400, 500, 200, 100, 800 };
	private PartViewforlevel histogramView;
	public static int total = 0;
	private String[] ySteps = new String[] { "", "", "", "", "" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wuxc_activity_statistics_for_level);
		ImageView image_back = (ImageView) findViewById(R.id.image_back);
		image_back.setOnClickListener(this);
		String Data = null;
		// String pager = null;
		try {
			Intent intent = this.getIntent(); // ��ȡ���е�intent����
			Bundle bundle = intent.getExtras(); // ��ȡintent�����bundle����
			String data1 = bundle.getString("data");
			JSONObject demoJson = new JSONObject(data1);
			String Type = demoJson.getString("type");
			total = 0;
			Data = demoJson.getString("datas");
			if (Type.equals("success")) {
				JSONArray jArray = null;

				jArray = new JSONArray(Data);
				JSONObject json_data = null;
				if (jArray.length() == 0) {
					Toast.makeText(getApplicationContext(), "������", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					int temp = 0;
					if (jArray.length() < 6) {
						temp = jArray.length();
					} else {
						temp = 6;
					}
					for (int i = 0; i < temp; i++) {
						json_data = jArray.getJSONObject(i);

						if (json_data.getString("title").equals("����")) {
							data[0] = json_data.getInt("num");
						} else if (json_data.getString("title").equals("����")) {
							data[1] = json_data.getInt("num");
						} else if (json_data.getString("title").equals("��ר")) {
							data[2] = json_data.getInt("num");
						} else if (json_data.getString("title").equals("��ר")) {
							data[3] = json_data.getInt("num");
						} else if (json_data.getString("title").equals("����")) {
							data[4] = json_data.getInt("num");
						} else {
							data[5] = json_data.getInt("num");
						}

					}
					for (int i = 0; i < temp; i++) {
						if (data[i] > total) {
							total = data[i];
						}
					}
					total = total + 40;
					ySteps[0] = "" + total / 40 * 40;
					ySteps[1] = "" + total / 40 * 30;
					ySteps[2] = "" + total / 40 * 20;
					ySteps[3] = "" + total / 40 * 10;
					ySteps[4] = "" + total / 40 * 0;

					histogramView = (PartViewforlevel) this.findViewById(R.id.histogram);

					histogramView.setProgress(data, ySteps);
				}

			} else {
				Toast.makeText(getApplicationContext(), "���ݴ���", Toast.LENGTH_SHORT).show();
			 
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;

		default:
			break;
		}
	}

}
