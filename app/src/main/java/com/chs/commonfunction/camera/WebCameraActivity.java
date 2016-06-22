package com.chs.commonfunction.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chs.commonfunction.R;
import com.chs.commonfunction.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 从webview中调用系统拍照和相册
 *
 * 由于没有web界面  这里看不到效果  代码测试有效
 */
public class WebCameraActivity extends Activity {

	private Uri uritempFile;
	private final int REQUESTCODE_TAKE = 1;
	private final int REQUESTCODE_PICK = 2;
	private final int REQUESTCODE_CUTTING = 3;
	private String headPath = "";
	@Bind(R.id.rl_main)
	RelativeLayout rl_main;
	@Bind(R.id.webview)
	WebView webView;
	@Bind(R.id.progressBar)
	ProgressBar progressBar;
	private ValueCallback<Uri> mUploadFile;
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_camera);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		String url = getIntent().getStringExtra("url");
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.GONE);
			}
		});
		webView.setWebChromeClient(new WebChromeClient()
		{
			//5.0以上
			@Override
			@SuppressLint("NewApi")
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
				if (mUploadCallbackAboveL != null) {
					mUploadCallbackAboveL.onReceiveValue(null);
				}
				mUploadCallbackAboveL = filePathCallback;
				new TakePhotoPop(WebCameraActivity.this, rl_main);
				return true;
			}
			// Andorid 4.1+
			public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
			{
				openFileChooser(uploadFile,acceptType);
			}

			// Andorid 3.0 +
			public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType)
			{
				mUploadFile = uploadFile;
				new TakePhotoPop(WebCameraActivity.this, rl_main);
			}

			// Android 3.0
			public void openFileChooser(ValueCallback<Uri> uploadFile)
			{
				openFileChooser(uploadFile,"");
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
			Uri uri = Uri.fromFile(file);
			if(mUploadFile!=null){
				mUploadFile.onReceiveValue(uri);
				mUploadFile = null;
			}
			if(mUploadCallbackAboveL!=null){
				mUploadCallbackAboveL.onReceiveValue(new Uri[]{uri});
				mUploadCallbackAboveL = null;
			}
		} else {
			Log.i("TAG", "no file");
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
