package cn.app.bt.pet.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.customview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PullListAdapter extends BaseAdapter {
	
	private Context context;
	private List<String> data;
	
	public PullListAdapter(Context context) {
		super();
		this.context = context;
	}
	
	public void setData(List<String> data) {
		this.data = data;
	}
	
	public void addData(List<String> data) {
		if (null == this.data) {
			this.data = new ArrayList<String>();
		}
		this.data.addAll(data);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_pull_listview, null);
		}
		final TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(data.get(position));
		return convertView;
	}
}
