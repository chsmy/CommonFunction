package com.chs.commonfunction.camera;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chs.commonfunction.R;
import com.chs.commonfunction.utils.FileUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CameraActivity extends Activity {

	private Uri uritempFile;
	private final int REQUESTCODE_TAKE = 1;
	private final int REQUESTCODE_PICK = 2;
	private final int REQUESTCODE_CUTTING = 3;
	private String headPath = "";
	@Bind(R.id.rl_main)
	RelativeLayout rl_main;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_camera);
		ButterKnife.bind(this);
		findViewById(R.id.take_picture).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new TakePhotoPop(CameraActivity.this,rl_main);
			}
		});
	}

	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch (requestCode) {
			 case REQUESTCODE_TAKE:// 调用相机拍照
				 if (resultCode == RESULT_OK) {
					 File temp = new File(headPath);
					 startPhotoZoom(Uri.fromFile(temp));
				 }
				 break;
			 case REQUESTCODE_PICK:// 直接从相册获取
				 try {
					 startPhotoZoom(data.getData());
				 } catch (NullPointerException e) {
					 e.printStackTrace();// 用户点击取消操作
				 }
				 break;
			 case REQUESTCODE_CUTTING:// 取得裁剪后的图片
				 if (data != null) {
					 setPicToView(data);
				 }
				 break;
		 }
	        super.onActivityResult(requestCode, resultCode, data);

	    }
	public class TakePhotoPop extends PopupWindow {

		public TakePhotoPop(Context mContext, View parent) {
			View view = View.inflate(mContext, R.layout.add_photo_pupwindow, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));

			setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
			setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			TextView tv_take_photo = (TextView) view.findViewById(R.id.tv_take_photo);
			TextView tv_pick_photo = (TextView) view.findViewById(R.id.tv_pick_photo);
			TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			tv_take_photo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					takePhoto();
					dismiss();
				}
			});
			tv_pick_photo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					doPickPhotoFromGallery();
					dismiss();
				}
			});
			tv_cancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}
	public void takePhoto() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File vFile = new File(Environment.getExternalStorageDirectory()
				+ "/bxt_image/", String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		if (!vFile.exists()) {
			File vDirPath = vFile.getParentFile();
			vDirPath.mkdirs();
		} else {
			if (vFile.exists()) {
				vFile.delete();
			}
		}
		headPath = vFile.getPath();
		Uri cameraUri = Uri.fromFile(vFile);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
		startActivityForResult(openCameraIntent, REQUESTCODE_TAKE);
	}

	protected void doPickPhotoFromGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, REQUESTCODE_PICK);
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
		uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	String urlpath;//裁剪后的图片路径

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
//        Bundle extras = picdata.getExtras();
//        if (extras != null) {
		// 取得SDCard图片路径做显示
//            Bitmap photo = extras.getParcelable("data");
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
			urlpath = FileUtil.saveFile(this, System.currentTimeMillis()+".jpg", bitmap);
			Drawable drawable = new BitmapDrawable(null, bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//            urlpath = FileUtil.saveFile(this, "temphead.jpg", photo);
		try {
			postFile(urlpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
//        }
	}

	/**
	 * 上传file
	 * @param path
	 * @throws Exception
     */
	public void postFile(String path) throws Exception {
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
        //开始上传
		} else {
			Log.i("tag", "no file");
		}
	}
}
