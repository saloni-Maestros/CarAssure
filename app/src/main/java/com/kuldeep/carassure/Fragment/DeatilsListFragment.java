package com.kuldeep.carassure.Fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.kuldeep.carassure.Adapter.ImageAdapter;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import static android.app.Activity.RESULT_OK;


public class DeatilsListFragment extends Fragment {
 EditText et_Name,et_Price,et_Location,et_KmDriven,et_BuyDate,et_CarType,et_Damages,et_FuelType,et_Seats,et_Average,et_EngineCC,et_ClutchFunction,
         et_BreakFunction,et_BreakConditionSummary,et_SpecialFeatureSummary,et_EngineLeakSummary,et_AirConditions,et_carnum;
ImageView iv_back,iv_upload;
TextView tv_uploadimg;
Spinner Spinner_Cartype, Spinner_Damages;
MaterialButton mbtn_post;
String User_Id = "";
ProgressBar progressBar;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;

    private static final int REQUEST_CODE_CHOOSE = 23;
    private final int FILE_REQUEST_CODE = 7777;
    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    String selectedPath1 = "NONE";
    String selectedPath2 = "NONE";

    private static final String IMAGE_DIRECTORY = "/directory";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    static final int OPEN_MEDIA_PICKER = 1;  // Request code

    List<Uri> mSelected;
    File f;

    RecyclerView recycler;
    String filePath = "";

    ArrayList<File> fileList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager1;              //image adapter, recycler view
    ImageAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_deatils_list, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        mbtn_post = view.findViewById(R.id.mbtn_post);
        iv_back = view.findViewById(R.id.iv_back);
        iv_upload = view.findViewById(R.id.iv_upload);
        tv_uploadimg = view.findViewById(R.id.tv_uploadimg);
      //  Spinner_Cartype = view.findViewById(R.id.Spinner_Cartype);
        Spinner_Damages = view.findViewById(R.id.Spinner_Damages);
        //edit texts
        et_Name = view.findViewById(R.id.et_Name);
        et_Price = view.findViewById(R.id.et_Price);
        et_Location = view.findViewById(R.id.et_Location);
        et_KmDriven = view.findViewById(R.id.et_KmDriven);
        et_BuyDate = view.findViewById(R.id.et_BuyDate);
        et_CarType = view.findViewById(R.id.et_CarType);
        et_Damages = view.findViewById(R.id.et_Damages);
        et_FuelType = view.findViewById(R.id.et_FuelType);
        et_carnum = view.findViewById(R.id.et_carnum);
        et_Seats = view.findViewById(R.id.et_Seats);
        et_Average = view.findViewById(R.id.et_Average);
        et_EngineCC = view.findViewById(R.id.et_EngineCC);
        et_ClutchFunction = view.findViewById(R.id.et_ClutchFunction);
        et_BreakFunction = view.findViewById(R.id.et_BreakFunction);
        et_BreakConditionSummary = view.findViewById(R.id.et_BreakConditionSummary);
        et_SpecialFeatureSummary = view.findViewById(R.id.et_SpecialFeatureSummary);
        et_EngineLeakSummary = view.findViewById(R.id.et_EngineLeakSummary);
        et_AirConditions = view.findViewById(R.id.et_AirConditions);
        recycler = view.findViewById(R.id.recycler);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SellFragment NAME = new SellFragment();             //fragment to fragment
                fragmentTransaction.replace(R.id.item_container, NAME);
                fragmentTransaction.commit();
            }
        });


        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(getActivity(), FilePickerActivity.class);
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
        });

        mbtn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_car(fileList);
            }
        });
        User_Id = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("frgxdfdgd", User_Id);


        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

            if (mediaFiles.size() < 0) {

                iv_upload.setVisibility(View.VISIBLE);

            } else {
                iv_upload.setVisibility(View.GONE);
            }

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {

                    filePath = mediaFiles.get(i).getPath();

                    File file = new File(filePath);
                    fileList.add(file);

                }
/*
                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String strCaption = edtCaption.getText().toString().trim();
                        if (strCaption.equals("")) {
                            Toast.makeText(getActivity(), "please enter caption", Toast.LENGTH_SHORT).show();
                        } else {
                            addProduct(fileList, strCaption);
                        }

                    }
                });
*/

                adapter = new ImageAdapter(fileList, getActivity());
                layoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true);
                recycler.setLayoutManager(layoutManager1);
                recycler.setAdapter(adapter);


            }
        }

    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == getActivity().RESULT_OK
                    && null != data) {

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){
                    Uri mImageUri=data.getData();
                    Cursor cursor = getActivity(). getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
*/
  /*  public void showPictureDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
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
    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }


    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

                    iv_upload.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            iv_upload.setImageBitmap(thumbnail);
            saveImage(thumbnail);

            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath() + "/path"},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("fvbcbv", "File Saved::---&gt;" + f.getAbsolutePath() + "/path");

            return f.getAbsolutePath() + "/path";
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }*/

    public void add_car(ArrayList<File> fileList){
        progressBar.setVisibility(View.VISIBLE);
        Log.e("fgfggb", f +"");
        AndroidNetworking.initialize(getContext());
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
        AndroidNetworking.initialize(getContext(), okHttpClient);
        AndroidNetworking.upload(Api.add_car)
                .addMultipartParameter("user_id", User_Id)
                .addMultipartParameter("name",et_Name.getText().toString().trim())
                .addMultipartParameter("car_number",et_carnum.getText().toString().trim())
                .addMultipartParameter("price",et_Price.getText().toString().trim())
                .addMultipartParameter("location",et_Location.getText().toString().trim())
                .addMultipartParameter("km_driven",et_KmDriven.getText().toString().trim())
                .addMultipartParameter("buy_date",et_BuyDate.getText().toString().trim())
                .addMultipartParameter("car_type",et_CarType.getText().toString().trim())
                .addMultipartParameter("fuel_type",et_FuelType.getText().toString().trim())
                .addMultipartParameter("sitting_capicity",et_Seats.getText().toString().trim())
                .addMultipartParameter("average_of_car_in_km",et_Average.getText().toString().trim())
                .addMultipartParameter("engine_in_cc",et_EngineCC.getText().toString().trim())
                .addMultipartParameter("clutch_function",et_ClutchFunction.getText().toString().trim())
                .addMultipartParameter("brake_function",et_BreakFunction.getText().toString().trim())
                .addMultipartParameter("brake_condition_summary", et_BreakConditionSummary.getText().toString().trim())
                .addMultipartParameter("special_feature_summary",et_SpecialFeatureSummary.getText().toString().trim())
                .addMultipartParameter("engine_leak_summary",et_EngineLeakSummary.getText().toString().trim())
                .addMultipartParameter("air_conditioning",et_AirConditions.getText().toString().trim())
                .addMultipartFileList("image[]", fileList)
                .setTag("add_car")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("jkdmcsjdksd", "onResponse: " +response.toString());
                 try {
                     if (response.getString("message").equals("Added Successfully")){
                         Toast.makeText(getContext(), ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                         JSONObject jsonObject = new JSONObject(response.getString("data"));
                         jsonObject.getString("user_id");
                         jsonObject.getString("id");
                         et_Name.setText(jsonObject.getString("name"));
                         et_Price.setText(jsonObject.getString("price"));
                         et_Location.setText(jsonObject.getString("location"));
                         et_KmDriven.setText(jsonObject.getString("km_driven"));
                         et_BuyDate.setText(jsonObject.getString("buy_date"));
                         et_CarType.setText(jsonObject.getString("car_type"));
                         et_FuelType.setText(jsonObject.getString("fuel_type"));
                         et_Seats.setText(jsonObject.getString("sitting_capicity"));
                         et_Average.setText(jsonObject.getString("average_of_car_in_km"));
                         et_EngineCC.setText(jsonObject.getString("engine_in_cc"));
                         et_ClutchFunction.setText(jsonObject.getString("clutch_function"));
                         et_BreakFunction.setText(jsonObject.getString("brake_function"));
                         et_BreakConditionSummary.setText(jsonObject.getString("brake_condition_summary"));
                         et_SpecialFeatureSummary.setText(jsonObject.getString("special_feature_summary"));
                         et_EngineLeakSummary.setText(jsonObject.getString("engine_leak_summary"));
                         et_AirConditions.setText(jsonObject.getString("air_conditioning"));
                       //  SharedHelper.putkey(getContext(), APPCONSTANT.CAR_ID,jsonObject.getString("price"));
                         JSONArray jsonArray = new JSONArray(jsonObject.getString("image"));
                         for (int j = 0; j < jsonArray.length(); j++) {
                             JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                             Glide.with(getContext()).load(jsonObject1.getString("path") + jsonObject1.getString("image"))
                                     // .placeholder(R.drawable.user_icon).override(250, 250)
                                     .diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_upload);
                         }

                         FragmentManager fragmentManager = getFragmentManager();
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         SellFragment NAME = new SellFragment();    //jis fragment me jana hai
                         fragmentTransaction.replace(R.id.item_container, NAME);
                         fragmentTransaction.commit();

                         FragmentManager fragmentManager1 = getFragmentManager();
                         FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                         LatestFragment NAME1 = new LatestFragment();    //jis fragment me jana hai
                         fragmentTransaction.replace(R.id.item_container, NAME);
                         fragmentTransaction.commit();

                     } else {
                         Toast.makeText(getContext(), ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                     }

                 } catch (Exception e){
                     progressBar.setVisibility(View.GONE);
                     Log.e("jvjmkvf", "onResponse: " +e.getMessage());

                 }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("jhnjnhcdj", "onError: " +anError);

                    }
                });
    }
}