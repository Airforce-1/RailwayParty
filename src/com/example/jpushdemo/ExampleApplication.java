package com.example.jpushdemo;

import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * һ�㽨�����Զ��� Application �����ʼ����Ҳ�������� Activity �
 */
public class ExampleApplication extends Application {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onCreate() {
		Logger.d(TAG, "[ExampleApplication] onCreate");
		super.onCreate();

		JPushInterface.setDebugMode(true); // ���ÿ�����־,����ʱ��ر���־
		JPushInterface.init(this); // ��ʼ�� JPush
		Set<String> tagSet = new LinkedHashSet<String>();
		tagSet.add("ZT001");
		JPushInterface.setTags(getApplicationContext(), 0, tagSet);
	}
}
