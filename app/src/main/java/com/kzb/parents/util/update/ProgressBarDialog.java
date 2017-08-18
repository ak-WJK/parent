package com.kzb.parents.util.update;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kzb.parents.R;


public class ProgressBarDialog extends Dialog {

	private int marginNum = 100;
	private TextView contentTV;
	private ImageView dialogImage;
	private ImageView dialog_refresh_image;
	private TextView dialogTitle;
	private TextView dialog_downloaded_size;
	private TextView dialog_file_size;
	private Button enterBN;
	private Button canelBN;
	private ProgressBar progressBar;
	public LinearLayout button_LL;
	public View.OnClickListener onBackPressLister;

	public ProgressBarDialog(Context context) {
		super(context, R.style.dialogFull);
		setDefaultParam();
	}

	public ProgressBarDialog(Context context, int theme) {
		super(context, theme);
		setDefaultParam();
	}

	public ProgressBarDialog(Context context, int theme, int marginNum) {
		this(context, theme);
		this.marginNum = marginNum;
	}

	public void setDefaultParam() {
		setContentView(R.layout.normal_dialog_progress);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		LayoutParams lay = getWindow().getAttributes();
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		lay.width = dm.widthPixels - marginNum;

		// 赋值
		contentTV = (TextView) getWindow().findViewById(R.id.dialog_content);
		dialogImage = (ImageView) getWindow().findViewById(R.id.dialog_image);
		dialog_refresh_image = (ImageView)getWindow().findViewById(R.id.dialog_refresh_image);
		dialogTitle = (TextView) getWindow().findViewById(R.id.dialog_title);
		dialog_file_size = (TextView)getWindow().findViewById(R.id.dialog_file_size);
		dialog_downloaded_size = (TextView)getWindow().findViewById(R.id.dialog_downloaded_size);
		progressBar = (ProgressBar)getWindow().findViewById(R.id.progress_horizontal);
		button_LL = (LinearLayout)getWindow().findViewById(R.id.button_LL);
		enterBN = (Button) getWindow().findViewById(R.id.enter);
		enterBN.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ProgressBarDialog.this.hide();
			}
		});
		canelBN = (Button) getWindow().findViewById(R.id.canel);
		canelBN.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ProgressBarDialog.this.hide();
			}
		});
	}

	public void setContent(String content) {
		contentTV.setText(content);
	}

	public void showDialog() {
		this.show();
	}

	public void hideDialog() {
		this.hide();
	}

	public void setEnterOnClickListener(View.OnClickListener onClickListener) {
		this.getEnterBN().setOnClickListener(onClickListener);
	}
	public void setCanelOnClickListener(View.OnClickListener onClickListener) {
		this.getCanelBN().setOnClickListener(onClickListener);
	}
	
	public void setBackPressListener(View.OnClickListener onClickListener) {
		this.onBackPressLister=onClickListener;
	}
	// getting,setting
	public TextView getContentTV() {
		return contentTV;
	}

	public Button getEnterBN() {
		return enterBN;
	}

	public Button getCanelBN() {
		return canelBN;
	}

	public ImageView getDialogImage() {
		return dialogImage;
	}

	public TextView getDialogTitle() {
		return dialogTitle;
	}

	public ImageView getDialog_refresh_image() {
		return dialog_refresh_image;
	}

	public ProgressBar getProgressBar(){
		return progressBar;
	}
	
	
	public TextView getDialog_downloaded_size() {
		return dialog_downloaded_size;
	}

	public void setDialog_downloaded_size(TextView dialog_downloaded_size) {
		this.dialog_downloaded_size = dialog_downloaded_size;
	}

	public TextView getDialog_file_size() {
		return dialog_file_size;
	}

	public void setDialog_file_size(TextView dialog_file_size) {
		this.dialog_file_size = dialog_file_size;
	}

	@Override
	public void onBackPressed() {
		if(this.onBackPressLister!=null){
			this.onBackPressLister.onClick(null);
		}
	}
}