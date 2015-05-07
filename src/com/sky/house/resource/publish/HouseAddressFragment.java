package com.sky.house.resource.publish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.eroad.base.BaseFragment;
import com.sky.house.R;
/**
 * 填写房源地址
 * @author skypan
 *
 */
public class HouseAddressFragment extends BaseFragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mDetailTitlebar.setTitle("房源地址");
		mDetailTitlebar.setRightButton1("确定", new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_house_address, container, false);
		return view;
	}

}