package com.kuldeep.carassure.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.kuldeep.carassure.Adapter.ImageAdapter;
import com.kuldeep.carassure.Adapter.ImageAdharAdapter;
import com.kuldeep.carassure.Model.UserTypeModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignUpActivity extends Activity {
    TextView text_login,tv_uploadfrontimg;
    MaterialButton mt_submit;
    Spinner spin;
    ImageView showpassword,iv_FrontAdharupload,iv_BackAdharupload,iv_Panupload,iv_uploadGst;
    EditText edt_username,edit_displayname,edit_email,edt_pwd,edit_mob,edt_adharcard,edt_Pancard,edt_GST;
    CheckBox checkbox;
    ProgressBar progressBar;

    private static final String IMAGE_DIRECTORY = "/directory";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private final int GALLERY1 = 3;
    private final int CAMERA1 = 4;
    private final int GALLERY2 = 5;
    private final int CAMERA2 = 6;
    private final int GALLERY3 = 7;
    private final int CAMERA3 = 8;

    String strPan = "";
    String strGst = "";
    String strFront = "", strBack = "";

    File f, F1 , F2, F3;

    RecyclerView recycler;
    String filePath = "";
    ArrayList<File> fileList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager1;
    List<File> finalList;                                  //multiple image
    ImageAdharAdapter adapter;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private final int FILE_REQUEST_CODE = 7777;
    List<Uri> mSelected;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edt_Pancard  = findViewById(R.id.edt_Pancard);
        edt_adharcard  = findViewById(R.id.edt_adharcard);
        edt_GST  = findViewById(R.id.edt_GST);
        tv_uploadfrontimg = findViewById(R.id.tv_uploadfrontimg);
        recycler = findViewById(R.id.recycler);
        text_login=findViewById(R.id.text_login);
        mt_submit=findViewById(R.id.mt_submit);
        edit_mob = findViewById(R.id.edit_mob);
        edt_username=findViewById(R.id.edt_username);
        edit_email=findViewById(R.id.edit_email);
        edt_pwd=findViewById(R.id.edt_pwd);
        checkbox=findViewById(R.id.checkbox);
        showpassword=findViewById(R.id.showpassword);
        iv_BackAdharupload = findViewById(R.id.iv_BackAdharupload);
        iv_FrontAdharupload =  findViewById(R.id.iv_FrontAdharupload);
        iv_Panupload = findViewById(R.id.iv_Panupload);
        iv_uploadGst = findViewById(R.id.iv_uploadGst);
        progressBar=findViewById(R.id.progressBar);



        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_username.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (edit_mob.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (edit_email.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (edt_pwd.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                } else if (edt_adharcard.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Aadhar Card Number", Toast.LENGTH_SHORT).show();
                } else if (strFront.equals("")){
                    Toast.makeText(SignUpActivity.this, "upload your aadhar Front image", Toast.LENGTH_SHORT).show();
                } else if (strBack.equals("")){
                    Toast.makeText(SignUpActivity.this, "upload your aadhar Back image", Toast.LENGTH_SHORT).show();
                } else if (edt_Pancard.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter your Pan Card Number", Toast.LENGTH_SHORT).show();
                } else if (strPan.equals("")){
                    Toast.makeText(SignUpActivity.this, "upload your Pan Card image", Toast.LENGTH_SHORT).show();
                } else if (edt_GST.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter your GST Number", Toast.LENGTH_SHORT).show();
                } else {
                    signup(fileList);
                }

            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            finish();
            }
        });
       /* iv_Adharupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(SignUpActivity.this, FilePickerActivity.class);
                    intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .setShowVideos(true)
                            .enableImageCapture(true)
                            .setMaxSelection(8)
                            .setSkipZeroSizeFiles(true)
                            .build());
                    startActivityForResult(intent, FILE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });*/
        iv_Panupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showPictureDialog2();
            }
        });
        iv_uploadGst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog3();
            }
        });
        iv_BackAdharupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog1();
            }
        });
        iv_FrontAdharupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    }
    public void showPictureDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }

            }
        });

        builder.show();
    }
    public void showPictureDialog1() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery1();
                        break;

                    case 1:
                        captureFromCamera1();
                        break;
                }

            }
        });

        builder.show();
    }
    public void showPictureDialog2() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery2();
                        break;

                    case 1:
                        captureFromCamera2();
                        break;
                }

            }
        });

        builder.show();
    }
    public void showPictureDialog3() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery3();
                        break;

                    case 1:
                        captureFromCamera3();
                        break;
                }

            }
        });

        builder.show();
    }
    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }

    public void choosePhotoFromGallery1() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY1);
    }
    public void choosePhotoFromGallery2() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY2);
    }
    public void choosePhotoFromGallery3() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY3);
    }
    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }

    public void captureFromCamera1() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA1);
    }
    public void captureFromCamera2() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA2);
    }
    public void captureFromCamera3() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            Log.e("fgjhkj","1");
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    strFront = String.valueOf(f);
                    iv_FrontAdharupload.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Log.e("fgjhkj","2");

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            iv_FrontAdharupload.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            strFront = String.valueOf(f);
            Toast.makeText(SignUpActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == GALLERY1) {
            Log.e("fgjhkj","3");

            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), contentURI);
                    String path = saveImage1(bitmap1);
                    strBack = String.valueOf(F1);
                    iv_BackAdharupload.setImageBitmap(bitmap1);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == CAMERA1) {
            Log.e("fgjhkj","4");

            Bitmap thumbnail2 = (Bitmap) data.getExtras().get("data");
            iv_BackAdharupload.setImageBitmap(thumbnail2);
            strBack = String.valueOf(F1);
            saveImage1(thumbnail2);
        }
        else if (requestCode == GALLERY2) {
            Log.e("fgjhkj","3");

            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), contentURI);
                    String path = saveImage2(bitmap2);
                    strPan = String.valueOf(F2);
                    iv_Panupload.setImageBitmap(bitmap2);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == CAMERA2) {
            Log.e("fgjhkj","4");

            Bitmap thumbnail2 = (Bitmap) data.getExtras().get("data");
            iv_Panupload.setImageBitmap(thumbnail2);
            strPan = String.valueOf(F2);
            saveImage2(thumbnail2);
        }
        else if (requestCode == GALLERY3) {
            Log.e("fgjhkj","3");

            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap3 = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), contentURI);
                    String path = saveImage3(bitmap3);
                    strGst = String.valueOf(F3);
                    iv_uploadGst.setImageBitmap(bitmap3);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == CAMERA3) {
            Log.e("fgjhkj","4");

            Bitmap thumbnail2 = (Bitmap) data.getExtras().get("data");
            iv_uploadGst.setImageBitmap(thumbnail2);
            strGst = String.valueOf(F3);
            saveImage3(thumbnail2);
        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SignUpActivity.this,
                    new String[]{f.getPath() + "/path"},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("fhfhfh", "File Saved::---&gt;" + f.getAbsolutePath() + "/path");

            return f.getAbsolutePath() + "/path";
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public String saveImage1(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            F1 = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            F1.createNewFile();
            FileOutputStream fo = new FileOutputStream(F1);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SignUpActivity.this,
                    new String[]{F1.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("fhfhfh", "File Saved::---&gt;" + F1.getAbsolutePath());
            Log.e("fhfhfh", "File Saved::---&gt;" + F1.getAbsolutePath() + "/path");

            // return F1.getAbsolutePath();
            return F1.getAbsolutePath() + "/path";

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public String saveImage2(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            F2 = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            F2.createNewFile();
            FileOutputStream fo = new FileOutputStream(F2);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SignUpActivity.this,
                    new String[]{F2.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("fhfhfh", "File Saved::---&gt;" + F2.getAbsolutePath());
            Log.e("fhfhfh", "File Saved::---&gt;" + F2.getAbsolutePath() + "/path");

            // return F1.getAbsolutePath();
            return F2.getAbsolutePath() + "/path";

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public String saveImage3(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            F3 = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            F3.createNewFile();
            FileOutputStream fo = new FileOutputStream(F3);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SignUpActivity.this,
                    new String[]{F3.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("fhfhfh", "File Saved::---&gt;" + F3.getAbsolutePath());
            Log.e("fhfhfh", "File Saved::---&gt;" + F3.getAbsolutePath() + "/path");

            // return F1.getAbsolutePath();
            return F3.getAbsolutePath() + "/path";

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


  /*  public void usertype(){
        AndroidNetworking.post(Api.user_type)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("fdskjgl", response.toString());
                        userTypeModelArrayList= new ArrayList<>();
                        Log.e("uygyggv", "onRespons " + response.toString());
                        Arr_usertypeID = new ArrayList<>();
                        Arr_usertypeName = new ArrayList<>();
                        Arr_usertypeID.add("0");
                        Arr_usertypeName.add("Select");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Arr_usertypeID.add(jsonObject.getString("id"));
                                Arr_usertypeName.add(jsonObject.getString("user_type"));
                            }

                            ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_usertypeName);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin.setAdapter(aa);

                            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String usertypeId = Arr_usertypeID.get(position);
                                    ((TextView) spin.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spin.getChildAt(0)).setTextSize(15);

                                    if (usertypeId.equals("0")){

                                    }else {

                                        UserTypeId=usertypeId;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }*/

    public void signup(ArrayList<File> fileList){
        progressBar.setVisibility(View.VISIBLE);
        Log.e("fhdfhfhh", f + "");
        Log.e("fhdfhfhh", F1 + "");
        Log.e("fhdfhfhh", F2 + "");
        Log.e("fhdfhfhh", F3 + "");

        AndroidNetworking.upload(Api.signup)
                .addMultipartParameter("username",edt_username.getText().toString().trim())
                .addMultipartParameter("mobile_no",edit_mob.getText().toString().trim())
                .addMultipartParameter("email",edit_email.getText().toString().trim())
                .addMultipartParameter("password",edt_pwd.getText().toString().trim())
                .addMultipartParameter("adhar_card_no", edt_adharcard.getText().toString().trim())
                .addMultipartParameter("pan_card", edt_Pancard.getText().toString().trim())
                .addMultipartParameter("gst_no", edt_GST.getText().toString().trim())
              .addMultipartFile("adhar_front_image", f)
             .addMultipartFile("adhar_backend_image", F1)
           .addMultipartFile("pan_img", F2)
          .addMultipartFile("gst_img", F3)
                .setPriority(Priority.HIGH)
                .setTag("signup")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dsjhkjghks",response.toString());
                        try {
                            if (response.getString("result").equals("Registration Successfull")){
                                Toast.makeText(SignUpActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.user_Id,response.getString("id"));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.NAME,response.getString("name"));
                                SharedHelper.putkey(SignUpActivity.this, APPCONSTANT.EMAIL_ID,response.getString("email"));
                                Log.e("dfgdfdf", response.getString("display_name") );
                                Log.e("dfgdfdf", response.getString("email"));
                            }else {
                                Toast.makeText(SignUpActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("djkhgf", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sflkhl", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}