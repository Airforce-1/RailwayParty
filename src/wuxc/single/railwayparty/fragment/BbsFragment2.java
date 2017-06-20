package wuxc.single.railwayparty.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import wuxc.single.railwayparty.R;
import wuxc.single.railwayparty.adapter.Bbs2Adapter;
import wuxc.single.railwayparty.model.Bbs2Model;

public class BbsFragment2 extends Fragment implements OnTouchListener, OnClickListener, OnItemClickListener {
	private ListView ListData;
	List<Bbs2Model> list = new ArrayList<Bbs2Model>();
	private static Bbs2Adapter mAdapter;
	private int firstItemIndex = 0;
	private int lastItemIndex = 0;
	private float startY = 0;
	private float startYfoot = 0;
	private boolean isRecored;
	private boolean isRecoredfoot;
	private int pageSize = 10;
	private int totalPage = 10;
	private int curPage = 1;
	private final static int RATIO = 2;
	private TextView headTextView = null;
	private View view;// ����Fragment view
	private int[] headimg = { R.drawable.p234, R.drawable.p234, R.drawable.p234, R.drawable.p234, R.drawable.p234,
			R.drawable.p234, R.drawable.p234, R.drawable.p234, R.drawable.p234, R.drawable.p234, R.drawable.p234 };

	private RelativeLayout rel_1;
	private TextView text_1;
	private TextView text_line_1;
	private RelativeLayout rel_2;
	private TextView text_2;
	private TextView text_line_2;
	private RelativeLayout rel_3;
	private TextView text_3;
	private TextView text_line_3;
	private RelativeLayout rel_4;
	private TextView text_4;
	private TextView text_line_4;
	private int screenwidth = 0;
	private float scale = 0;
	private float scalepx = 0;
	private float dp = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.wuxc_fragment_bbs_2, container, false);
			initview(view);
			setonclicklistener();
			setheadtextview();
			initcolor();
			getdatalist(curPage);
		}

		return view;

	}

	private void initcolor() {
		// TODO Auto-generated method stub
		clearcolor();
		text_1.setTextColor(Color.parseColor("#cc0502"));
		text_line_1.setBackgroundColor(Color.parseColor("#cc0502"));

	}

	private void clearcolor() {
		// TODO Auto-generated method stub
		text_1.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_1.setBackgroundColor(Color.parseColor("#00000000"));
		text_2.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_2.setBackgroundColor(Color.parseColor("#00000000"));
		text_3.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_3.setBackgroundColor(Color.parseColor("#00000000"));
		text_4.setTextColor(Color.parseColor("#7d7d7d"));
		text_line_4.setBackgroundColor(Color.parseColor("#00000000"));
	}

	private void initview(View view2) {
		// TODO Auto-generated method stub
		screenwidth = getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth();
		DisplayMetrics mMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		scale = getActivity().getResources().getDisplayMetrics().density;
		// Log.e("mMetrics", mMetrics.toString() + "scale=" + scale + "0.5f"
		// +
		// 0.5f);
		dp = screenwidth / scale + 0.5f;
		scalepx = screenwidth / dp;
		ListData = (ListView) view.findViewById(R.id.list_data);
		rel_1 = (RelativeLayout) view.findViewById(R.id.rel_1);
		text_1 = (TextView) view.findViewById(R.id.text_1);
		text_line_1 = (TextView) view.findViewById(R.id.text_line_1);
		rel_1.setOnClickListener(this);
		rel_2 = (RelativeLayout) view.findViewById(R.id.rel_2);
		text_2 = (TextView) view.findViewById(R.id.text_2);
		text_line_2 = (TextView) view.findViewById(R.id.text_line_2);
		rel_2.setOnClickListener(this);
		rel_3 = (RelativeLayout) view.findViewById(R.id.rel_3);
		text_3 = (TextView) view.findViewById(R.id.text_3);
		text_line_3 = (TextView) view.findViewById(R.id.text_line_3);
		rel_3.setOnClickListener(this);
		rel_4 = (RelativeLayout) view.findViewById(R.id.rel_4);
		text_4 = (TextView) view.findViewById(R.id.text_4);
		text_line_4 = (TextView) view.findViewById(R.id.text_line_4);
		rel_4.setOnClickListener(this);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		ListData.setOnItemClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		float tempY = event.getY();
		float tempyfoot = event.getY();
		firstItemIndex = ListData.getFirstVisiblePosition();
		lastItemIndex = ListData.getLastVisiblePosition();
		// Toast.makeText(getActivity(), " lastItemIndex" +
		// lastItemIndex, Toast.LENGTH_SHORT).show();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			if (!isRecored && (firstItemIndex == 0)) {
				isRecored = true;
				startY = tempY;
			}
			int temp = 1;
			temp = (lastItemIndex) % pageSize;
			if (!isRecoredfoot && (temp == 0)) {
				isRecoredfoot = true;
				startYfoot = tempyfoot;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			isRecored = false;
			isRecoredfoot = false;
			break;

		default:
			break;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			ListData.setPadding(0, 0, 0, 0);
			if (tempY - startY < 400) {
				ListData.setPadding(0, -100, 0, 0);
			} else {
				curPage = 1;
				Toast.makeText(getActivity(), "����ˢ��", Toast.LENGTH_SHORT).show();
				getdatalist(pageSize);
			}
			int temp = 1;
			temp = (lastItemIndex) % pageSize;
			// temp = 0;
			if (temp == 0 && (startYfoot - tempyfoot > 400)) {
				curPage++;
				if (curPage > totalPage) {
					Toast.makeText(getActivity(), " û�и�����", Toast.LENGTH_SHORT).show();
					// // listinfoagain();
				} else {
					getdatalist(pageSize);
					Toast.makeText(getActivity(), "���ڼ�����һҳ", Toast.LENGTH_SHORT).show();
				}

			} else {

			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecored && tempY > startY) {
				ListData.setPadding(0, (int) ((tempY - startY) / RATIO - 100), 0, 0);
			}
			if (isRecoredfoot && startYfoot > tempyfoot) {
				// footTextView.setVisibility(View.VISIBLE);
				ListData.setPadding(0, -100, 0, (int) ((startYfoot - tempyfoot) / RATIO));
			}
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// recommendModel data = list.get(position - 1);
		// Intent intent = new Intent();
		// intent.setClass(getActivity(), SpecialDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("Title", data.getTitle());
		// bundle.putString("detail", data.getDetail());
		// bundle.putString("Time", data.getTime());
		// bundle.putString("Name", "����");
		// intent.putExtras(bundle);
		// startActivity(intent);
		Toast.makeText(getActivity(), "�����" + position + "��" + "item", Toast.LENGTH_SHORT).show();
	}

	private void setheadtextview() {
		headTextView = new TextView(getActivity());
		headTextView.setGravity(Gravity.CENTER);
		headTextView.setMinHeight(100);
		headTextView.setText("����ˢ��...");
		headTextView.setTypeface(Typeface.DEFAULT_BOLD);
		headTextView.setTextSize(15);
		headTextView.invalidate();
		ListData.addHeaderView(headTextView, null, false);
		ListData.setPadding(0, -100, 0, 0);
		ListData.setOnTouchListener(this);
	}

	private void getdatalist(int arg) {
		if (arg == 1) {
			list.clear();
		}
		// TODO Auto-generated method stub

		try {

			for (int i = 0; i < 10; i++) {

				Bbs2Model listinfo = new Bbs2Model();
				listinfo.setTime("3��10��");
				listinfo.setTitle("���ϸ�Ա");
				listinfo.setContent("������ȷ������׼��������Ϊ�淶���������ͨ�����¡�Ϊ���������ʡ�");
				listinfo.setImageurl(headimg[i]);
				listinfo.setHeadimgUrl("");
				listinfo.setLabel("2:57");
				listinfo.setWidth((int) (screenwidth - 80 * scalepx));
				list.add(listinfo);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (arg == 1) {
			go();
		} else {
			mAdapter.notifyDataSetChanged();
		}

	}

	protected void go() {
		ListData.setPadding(0, -100, 0, 0);
		mAdapter = new Bbs2Adapter(getActivity(), list, ListData);
		ListData.setAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rel_1:
			clearcolor();
			text_1.setTextColor(Color.parseColor("#cc0502"));
			text_line_1.setBackgroundColor(Color.parseColor("#cc0502"));

			break;
		case R.id.rel_2:
			clearcolor();
			text_2.setTextColor(Color.parseColor("#cc0502"));
			text_line_2.setBackgroundColor(Color.parseColor("#cc0502"));

			break;
		case R.id.rel_3:
			clearcolor();
			text_3.setTextColor(Color.parseColor("#cc0502"));
			text_line_3.setBackgroundColor(Color.parseColor("#cc0502"));

			break;
		case R.id.rel_4:
			clearcolor();
			text_4.setTextColor(Color.parseColor("#cc0502"));
			text_line_4.setBackgroundColor(Color.parseColor("#cc0502"));

			break;
		default:
			break;
		}
	}
}
