package cn.app.bt.pet.http;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.app.bt.pet.util.AbstractActivity;

import com.example.customview.R;

public class HttpActivity extends AbstractActivity {

	private String cookie;
	
	private Button mStep1;
	private Button mStep2;
	private Button mStep3;
	
	private EditText mCookie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);
		
		init();
	}
	
	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		mStep1 = (Button) findViewById(R.id.btn1);
		mStep2 = (Button) findViewById(R.id.btn2);
		mStep3 = (Button) findViewById(R.id.btn3);
		
		mCookie = (EditText) findViewById(R.id.cookie);
	}

	@Override
	public void initViewData() {

	}

	@Override
	public void initViewListener() {
		mStep1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						step1();
					}
				}).start();
			}
		});

		mStep2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						step2();
					}
				}).start();
			}
		});

		mStep3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						step3();
					}
				}).start();
			}
		});


	}
	
	private void step1() {
		try {
			HttpURLConnection con = openConnection("http://192.168.1.104:8080/eshop/app/findverifyName.action?name=870451599@qq.com");

			Map<String, List<String>> headers = con.getHeaderFields();
			for (Entry<String, List<String>> header : headers.entrySet()) {
				if ("Set-Cookie".equals(header.getKey())) {
					System.out.println("--> " + header.getKey() + " " + header.getValue().get(0) + " <--");
					cookie = header.getValue().get(0).split("\\;")[0];
				}
			}
			System.out.println(streamToString(con.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void step2() {
		try {
			HttpURLConnection con = openConnection("http://192.168.1.104:8080/eshop/app/findverifyEmail.action");
			con.addRequestProperty("Cookie", cookie);
			
			Map<String, List<String>> headers = con.getHeaderFields();
			for (Entry<String, List<String>> header : headers.entrySet()) {
				if ("Set-Cookie".equals(header.getKey())) {
					System.out.println("--> " + header.getKey() + " " + header.getValue().get(0) + " <--");
				}
			}
			System.out.println(streamToString(con.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void step3() {
		DataOutputStream dos = null;

		try {
			HttpURLConnection con = openConnection("http://192.168.1.104:8080/eshop/app/findverify.action");
			
			con.addRequestProperty("Cookie", cookie);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			dos = new DataOutputStream(con.getOutputStream());
			dos.writeBytes("verifyCode=" + mCookie.getText().toString());
			
			System.out.println(streamToString(con.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(dos);
		}
	}
	
	private HttpURLConnection openConnection(String urlVal) throws IOException {
		URL url = new URL(urlVal);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		return con;
	}
	
	private String streamToString(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			int oneByte = -1;
			while (-1 != (oneByte = is.read())) {
				os.write(oneByte);
			}
			return os.toString();
		} finally {
			closeStream(os);
			closeStream(is);
		}
	}
	
	private void closeStream(Closeable stream) {
		if (null == stream) {
			return;
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
