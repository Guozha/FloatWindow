package com.example.floatwindow;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatWindowBigView extends LinearLayout{

	public static int viewWidth;
	public static int viewHeight;
	
	private List<String> menus;
	private GridMenuAdapter gridMenuAdapter;
	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.float_window_big_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		GridView gridMenu = (GridView) view.findViewById(R.id.float_window_grid_menu);
		Button back = (Button) view.findViewById(R.id.back);
		/*close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyWindowManager.removeBigWindow(getContext());
				MyWindowManager.removeSmallWindow(getContext());
				Intent intent = new Intent(getContext(), FloatWindowService.class);
				context.stopService(intent);
			}
		});*/
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyWindowManager.removeBigWindow(getContext());
				MyWindowManager.createSmallWindow(getContext());
			}
		});
		
		menus = new ArrayList<String>();
		for(int i = 0; i < 10; i++){
			menus.add("²Ëµ¥" + i);
		}
		gridMenuAdapter = new GridMenuAdapter(context, menus);
		gridMenu.setAdapter(gridMenuAdapter);
	}

	
	class GridMenuAdapter extends BaseAdapter{
		private List<String> menus;
		private LayoutInflater inflater;
		public GridMenuAdapter(Context context, List<String> menus){
			this.menus = menus;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return menus.size();
		}

		@Override
		public Object getItem(int index) {
			return menus.get(index);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.window_grid_menu_cell, null);
				holder = new ViewHolder();
				holder.menuText = (TextView) convertView.findViewById(R.id.menu_text);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.menuText.setText(menus.get(position));
			return convertView;
		}
	}
	
	class ViewHolder{
		public TextView menuText;
	}
}
