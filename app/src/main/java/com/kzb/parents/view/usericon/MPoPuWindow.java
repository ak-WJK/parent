package com.kzb.parents.view.usericon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.main.fragment.FourthFragment;
import com.kzb.parents.main.fragment.FourthFragment.Type;

import java.io.File;


//点击头像切换弹出的Popu
public class MPoPuWindow extends PopupWindow implements OnClickListener {

    public Context mContext;

    private Type type;

    public Activity mActivity;

    private File file;
    private Uri ImgUri;

    private TextView mTakePhoto, mAlbumPhoto, mCancel;
    private FourthFragment fourthFragment;

    public MPoPuWindow(Context context, Activity mActivity, FourthFragment fourthFragment) {
        this.fourthFragment = fourthFragment;
        initView(context);
        this.mActivity = mActivity;
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_icon_popu,
                null);
        setContentView(v);

        mTakePhoto = (TextView) v.findViewById(R.id.photo_take);
        mAlbumPhoto = (TextView) v.findViewById(R.id.photo_album);
        mCancel = (TextView) v.findViewById(R.id.photo_cancel);

        mTakePhoto.setOnClickListener(this);
        mAlbumPhoto.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        // 刷新状态
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popuwindow_from_bottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x50000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();

        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.photo_take:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                ImgUri = Uri.fromFile(file);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, ImgUri);
                fourthFragment.startActivityForResult(intent1, 1);

                type = Type.CAMERA;
                if (listener != null) {
                    listener.getType(type);
                    listener.getImgUri(ImgUri, file);
                }

                this.dismiss();
                break;
            case R.id.photo_album:
                Intent intent2 = new Intent("android.intent.action.PICK");
                intent2.setType("image/*");
                fourthFragment.startActivityForResult(intent2, 2);
                type = Type.PHONE;
                if (listener != null) {
                    listener.getType(type);
                }
                this.dismiss();
                break;
            case R.id.photo_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public void onPhoto(Uri uri, int outputX, int outputY) {
        Intent intent = null;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);

//        if (type.equals(Type.CAMERA)) {
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }

        intent.putExtra("return-data", true);
//        intent.putExtra("circleCrop", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        fourthFragment.startActivityForResult(intent, 3);
    }

    public interface onGetTypeClckListener {
        void getType(Type type);

        void getImgUri(Uri ImgUri, File file);
    }

    private onGetTypeClckListener listener;

    public void setOnGetTypeClckListener(onGetTypeClckListener listener) {
        this.listener = listener;
    }

}
