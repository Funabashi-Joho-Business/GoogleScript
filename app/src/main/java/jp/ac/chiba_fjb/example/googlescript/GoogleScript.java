package jp.ac.chiba_fjb.example.googlescript;


import android.app.Activity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.script.Script;
import com.google.api.services.script.model.ExecutionRequest;
import com.google.api.services.script.model.Operation;

import java.io.IOException;
import java.util.List;


public class GoogleScript extends GoogleAccount
{
	private static final String[] SCOPES = {"https://www.googleapis.com/auth/drive"};
	private Activity mContext;
	private Script mService;
	private boolean mDebug = false;

	private static HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer) {
		return new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest httpRequest)
					throws IOException {
				requestInitializer.initialize(httpRequest);
				httpRequest.setReadTimeout(380000);
			}
		};
	}

	public GoogleScript(Activity activity, String[] scope) {
		super(activity,scope);
		//Activityの保存
		mContext = activity;

		HttpTransport transport = AndroidHttp.newCompatibleTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		mService = new Script.Builder(
			                             transport, jsonFactory, setHttpTimeout(getCredential()))
			           .setApplicationName("Google Apps Script Execution API")
			           .build();
	}
	public void setDebug(boolean flag){
		mDebug = flag;
	}
	public Operation callScript(String scriptId, String apiKey, String name, List<Object> params) throws IOException {
		ExecutionRequest request = new ExecutionRequest().setFunction(name);
		if (params != null)
			request.setParameters(params);
		request.setDevMode(mDebug);//デベロッパーモード
		Operation op = mService.scripts().run(scriptId, request).setKey(apiKey).execute();
		return op;

	}



}