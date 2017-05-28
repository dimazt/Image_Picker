package com.dimazt;

import android.app.Activity;
import android.app.ActionBar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.content.*;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.widget.*;
import android.text.Editable;
import com.dimazt.GraphicsUtil;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

@SuppressLint(value={"CommitPrefEdits"})
public class ImagePicker
extends Activity {

	Button done;
	Bitmap bitmap, bitmap2, bitmap3;
	Bundle bnd;
    String abUri ,cUri, rUri, logoUri;
    ImageView photoKTA, photoCircle, photoRounded, logo;
	Button photoC;
    Uri uri, crUri;
	File dir, dir2;
	final int CROP_PHOTO = 2;
	public static final String picKTA = "photoKTA";
	public static final String Photo = "photoKTAc";
    Animation animation;
	private Animation inAnim, outAnim;
	SharedPreferences.Editor editor;
	
    
    private ViewFlipper mHeaderLogo;
	private Spinner sp;
    private View mHeader;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;

   
    private SpannableString mSpannableString;

    private TypedValue mTypedValue = new TypedValue();
	
	
	

	private void photoCrop() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(this.uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("return-data", true);
            this.startActivityForResult(intent, 2);
            return;
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText((Context)this, (CharSequence)"Sorry.. Your device not suport crop picture", (int)1).show();
            return;
        }
	}
	private void photoCropC() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(this.uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            intent.putExtra("return-data", true);
            this.startActivityForResult(intent, 3);
            return;
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText((Context)this, (CharSequence)"Sorry.. Your device not suport crop picture", (int)1).show();
            return;
        }
	}
	private void photoCropR() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(this.uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 10);
            intent.putExtra("aspectY", 4);
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            intent.putExtra("return-data", true);
            this.startActivityForResult(intent, 6);
            return;
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText((Context)this, (CharSequence)"Sorry.. Your device not suport crop picture", (int)1).show();
            return;
        }
	}

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1 && intent != null) {
			this.uri = intent.getData();
			this.photoCrop();
			return;
		}
		if (n == 4 && intent != null){
			this.uri = intent.getData();
			this.photoCropC();
			return;
		}
		if (n == 5 && intent != null){
			this.uri = intent.getData();
			this.photoCropR();
			return;
		}
		if (n == 7 && intent != null) {   
			this.uri = Uri.parse((String)intent.getDataString());
			this.logo.setImageURI(this.uri);
			SharedPreferences.Editor editor = this.getSharedPreferences("EvoPrefsFile", 0).edit();
			editor.putString("logoKTA", this.uri.toString());
			editor.commit();
			Intent intent2 = new Intent();
			intent2.setAction("com.dhian.KTA.CHANGE_LOGO_KTA");
			intent2.putExtra("LOGO", this.uri.toString());
			this.sendBroadcast(intent2);
			return;
			}
	    if (n == 2) {
            if (intent == null) return;
            {
				Bitmap bitmap = (Bitmap)intent.getExtras().getParcelable("data");
                this.bnd = new Bundle();
                this.bnd.putParcelable("BITMAP_1", (Parcelable)bitmap);
                this.photoKTA.setImageBitmap(bitmap);
				
                setPhotoKTA();
				return;
			}								
		}
            if (n == 3){
				if (intent == null) return;
            {
				Bitmap bitmap = (Bitmap)intent.getExtras().getParcelable("data");
				this.bnd = new Bundle();
				this.bnd.putParcelable("BITMAP_2", (Parcelable)bitmap);
				GraphicsUtil graphicsUtil = new GraphicsUtil();
                this.photoCircle.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
				
				setPhotoCircle();
				return;
	
		}
		}
			if (n != 6 || intent == null) return;
			{
				Bitmap bitmap = (Bitmap)intent.getExtras().getParcelable("data");
				this.bnd = new Bundle();
				this.bnd.putParcelable("BITMAP_3", (Parcelable)bitmap);
				RoundedUtils roundedUtil = new RoundedUtils();
				this.photoRounded.setImageBitmap(roundedUtil.getRoundedCornerBitmap(bitmap, 16));
				setPhotoRounded();
				return;
			}
			}
		
			
			
		
		
    

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
		
		
        this.setContentView(getID("dimazt_editor","layout"));
		this.photoKTA = (ImageView)this.findViewById(getID("photo","id"));
		this.photoCircle = (ImageView)this.findViewById(getID("photoCircle","id"));
		this.photoRounded = (ImageView)this.findViewById(getID("photoRounded","id"));
		this.logo = (ImageView)this.findViewById(getID("logo","id"));
		this.photoC = (Button)this.findViewById(getID("photoSet","id"));
	    SharedPreferences sharedPreferences2 = this.getSharedPreferences("EvoPrefsFile", 0);
		
		this.abUri = sharedPreferences2.getString("photoKTA", "null");
        if (this.abUri == "null") {
            this.photoKTA.setImageResource(getID("nia_default_kta","drawable"));
        } else {
            this.photoKTA.setImageURI(Uri.parse((String)this.abUri));
        }
		SharedPreferences sharedPreferences = this.getSharedPreferences("EvoPrefsFile",0);
		this.cUri = sharedPreferences.getString("photoKTAc","null");
		if (this.cUri == "null") {
            this.photoCircle.setImageResource(getID("default_circle","drawable"));
        } else {
            this.photoCircle.setImageURI(Uri.parse((String)this.cUri));
        }
		SharedPreferences sharedPreferences3 = this.getSharedPreferences("EvoPrefsFile",0);
		this.rUri = sharedPreferences3.getString("photoKTAr","null");
		if (this.rUri == "null"){
			this.photoRounded.setImageResource(getID("default_rounded","drawable"));
		} else {
			this.photoRounded.setImageURI(Uri.parse((String)this.rUri));
		}
		SharedPreferences sharedPreferences4 = this.getSharedPreferences("EvoPrefsFile",0);
		this.logoUri = sharedPreferences4.getString("logoKTA", "null");
		if (this.logoUri == "null"){
			this.logo.setImageResource(getID("default_logo","drawable"));
		} else {
			this.logo.setImageURI(Uri.parse((String)this.logoUri));
		}
    
		this.photoKTA.setOnClickListener((View.OnClickListener)new View.OnClickListener(){

											  public void onClick(View view) {
												  Intent intent = new Intent();
												  intent.setType("image/*");
												  intent.setAction("android.intent.action.GET_CONTENT");
												  ImagePicker.this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Select Picture"), 1);
											  }
										  });
	    this.photoKTA.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener(){
			public boolean onLongClick(View view){
				setPhotoKTA();
				Toast.makeText(getApplicationContext(),
				"Foto SQUARE Telah Di Set Sebagai Foto KTA", 2000).show();
				return (true);
				
			}
		});
		this.photoCircle.setOnClickListener((View.OnClickListener)new View.OnClickListener(){

											 public void onClick(View view) {
												 Intent intent = new Intent();
												 intent.setType("image/*");
												 intent.setAction("android.intent.action.GET_CONTENT");
												 ImagePicker.this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Select Picture"), 4);
											 }
										 });
		this.photoCircle.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener(){
												 public boolean onLongClick(View view){
													 setPhotoCircle();
													 Toast.makeText(getApplicationContext(),
																	"Foto CIRCLE Telah Di Set Sebagai Foto KTA", 2000).show();
													 return (true);

												 }
											 });
					 
							
		this.photoRounded.setOnClickListener((View.OnClickListener)new View.OnClickListener(){

												public void onClick(View view) {
													Intent intent = new Intent();
													intent.setType("image/*");
													intent.setAction("android.intent.action.GET_CONTENT");
													ImagePicker.this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Select Picture"), 5);
												}
											});
		this.photoRounded.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener(){
			public boolean onLongClick(View view){
				setPhotoRounded();
				Toast.makeText(getApplicationContext(),
				"Foto ROUNDED Telah Di Set Sebagai Foto KTA", 2000).show();
				return (true);
				
			}
		});
		this.logo.setOnClickListener((View.OnClickListener)new View.OnClickListener(){

												public void onClick(View view) {
													Intent intent = new Intent();
													intent.setType("image/*");
													intent.setAction("android.intent.action.GET_CONTENT");
													ImagePicker.this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Select Picture"), 7);
												}
											});
		
										
		
		inAnim = AnimationUtils.loadAnimation(this, getID("from_middle","anim"));
		outAnim = AnimationUtils.loadAnimation(this, getID("to_middle","anim"));
		
		
		List<String> list = new ArrayList<String>();
        list.add("Square");
        list.add("Circle");
        list.add("Rounded");
		list.add("Logo");
        
		sp = (Spinner)findViewById(getID("spinner1","id"));
	
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        sp.setAdapter(dataAdapter);
		SharedPreferences shp = this.getSharedPreferences("EvoPrefsFile", 0);
		
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
										   int arg2, long arg3) {
					final ViewFlipper vf = (ViewFlipper)findViewById(getID("viewFlipper","id"));
					vf.setInAnimation(inAnim);
					vf.setOutAnimation(outAnim);
					String s=((TextView)arg1).getText().toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
					((TextView) arg0.getChildAt(0)).setShadowLayer(3, 2, 2, (Color.RED));
					
					if(s.equals("Square"))
						vf.setDisplayedChild(0);
						
					if(s.equals("Circle"))
						vf.setDisplayedChild(1);
					
					if(s.equals("Rounded"))
						vf.setDisplayedChild(2);
					
					if(s.equals("Logo"))
						vf.setDisplayedChild(3);
						
					
						
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			
			
		mHeader = findViewById(getID("header","id"));
   
    
		}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
		
	



		
	

	private void setPhotoKTA() {
	    bitmap = ((BitmapDrawable)ImagePicker.this.photoKTA.getDrawable()).getBitmap();
		File dir = new File(ImagePicker.this.getApplicationContext().getCacheDir(), "photo.png");
		try {
	        FileOutputStream fileOutputStream = new FileOutputStream(dir);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch (IOException e) {
		}
		do {
			Uri uri = Uri.parse((String)("file://" + (Object)dir));
			SharedPreferences.Editor editor = ImagePicker.this.getSharedPreferences("EvoPrefsFile", 0).edit();
			editor.putString("photoKTA", uri.toString());
			editor.commit();
			Intent intent = new Intent();
			intent.setAction("com.dhian.KTA.CHANGE_PHOTO_KTA");
			intent.putExtra("KTAkey", uri.toString());
			ImagePicker.this.sendBroadcast(intent);
			Toast.makeText(getApplicationContext(),
			"Foto SQUARE Telah Di Set Sebagai Foto KTA", 2000).show();
			
			return;
		
		} while (true);
	}
	private void setPhotoCircle() {
	    bitmap2 = ((BitmapDrawable)ImagePicker.this.photoCircle.getDrawable()).getBitmap();
		File dir2 = new File(ImagePicker.this.getApplicationContext().getCacheDir(), "photoc.png");
		try {
	        FileOutputStream fileOutputStream2 = new FileOutputStream(dir2);
			bitmap2.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream2);
			fileOutputStream2.flush();
			fileOutputStream2.close();
		}
		catch (IOException a) {
		}
		do{
			Uri uri2 = Uri.parse((String)("file://" + (Object)dir2));
			SharedPreferences.Editor editor2 = ImagePicker.this.getSharedPreferences("EvoPrefsFile", 0).edit();
			editor2.putString("photoKTAc", uri2.toString());
			editor2.commit();
			Intent intent6 = new Intent();
			intent6.setAction("com.dhian.KTA.CHANGE_PHOTO_KTA");
			intent6.putExtra("KTAkey", uri2.toString());
			ImagePicker.this.sendBroadcast(intent6);
			Toast.makeText(getApplicationContext(),
						   "Foto CIRCLE Telah Di Set Sebagai Foto KTA", 2000).show();
			
			return;
		
		}while (true);
	}
	private void setPhotoRounded() {
	    bitmap2 = ((BitmapDrawable)ImagePicker.this.photoRounded.getDrawable()).getBitmap();
		File dir2 = new File(ImagePicker.this.getApplicationContext().getCacheDir(), "photor.png");
		try {
	        FileOutputStream fileOutputStream2 = new FileOutputStream(dir2);
			bitmap2.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream2);
			fileOutputStream2.flush();
			fileOutputStream2.close();
		}
		catch (IOException a) {
		}
		do{
			Uri uri2 = Uri.parse((String)("file://" + (Object)dir2));
			SharedPreferences.Editor editor2 = ImagePicker.this.getSharedPreferences("EvoPrefsFile", 0).edit();
			editor2.putString("photoKTAr", uri2.toString());
			editor2.commit();
			Intent intent8 = new Intent();
			intent8.setAction("com.dhian.KTA.CHANGE_PHOTO_KTA");
			intent8.putExtra("KTAkey", uri2.toString());
			ImagePicker.this.sendBroadcast(intent8);
			Toast.makeText(getApplicationContext(),
						   "Foto ROUNDED Telah Di Set Sebagai Foto KTA", 2000).show();
			
			return;

		}while (true);
	}

	


	public int getID(String name, String Type) {
		return getBaseContext().getResources().getIdentifier(name, Type, getBaseContext().getPackageName());
	}

}

