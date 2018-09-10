package com.example.smartserve.keymortgageapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartserve.keymortgageapp.Adapters.ImagesAdapter;
import com.example.smartserve.keymortgageapp.Models.ImagesModel;
import com.example.smartserve.keymortgageapp.Util.ApiClient;
import com.example.smartserve.keymortgageapp.Util.ApiService;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;
import com.example.smartserve.keymortgageapp.Util.SessionManager;
import com.example.smartserve.keymortgageapp.Util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocActivity extends AppCompatActivity {
    ImageView back;
    Button submit;
    ImageView upload;
    private String userChoosenTask;
    SessionManager sessionManager;
    public static final String KEY_USERID= "userID";
    byte[] test;
    int ImageId;
    ProgressDialog progressDialog;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    RecyclerView recyclerView;
    static int i = 0;
    ImagesAdapter imagesAdapter;
    ArrayList<ImagesModel> imageList;
    Bitmap thumbnail,bm;
    File destination;
    Uri fileUri;
    Uri mCapturedImageURI;
    ByteArrayOutputStream byteArrayOutputStream = null;
    HashMap<String, String> user;
    String keyuserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_doc);
        progressDialog =new ProgressDialog(this);
//        sessionManager= new SessionManager(this);
//        sessionManager.createLoginSession(User_ID,KEY_USERID);
        sessionManager =new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();

        //phoneNumber = user.get("phoneNumber");
        keyuserId = user.get("userID");
        back =(ImageView)findViewById(R.id.back);
        upload =(ImageView)findViewById(R.id.upload);
        submit =(Button)findViewById(R.id.submit);
        recyclerView =(RecyclerView)findViewById(R.id.recycler);
        imageList=new ArrayList<>();
        imagesAdapter = new ImagesAdapter(imageList,asyncResult_addNewConnection1);

        recyclerView.setAdapter(imagesAdapter);

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImages();
            }
        });
    }


    AsyncResult<ImagesModel > asyncResult_addNewConnection1 = new AsyncResult<ImagesModel>() {
        @Override
        public void success(ImagesModel  click) {
            ImageId=click.getIid();

            for(int j = 0; j < imageList.size(); j++)
            {


                if(click.getBm().equals(imageList.get(j).getBm())){
                    //found, delete.
                    imageList.remove(j);
                    break;
                }

            }
            imagesAdapter.notifyDataSetChanged();
        }
        @Override
        public void error(String error) {

        }
    };
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(UploadDocActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image File name");
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intent, REQUEST_CAMERA);

    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                test = onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                test = onCaptureImageResult(data);
        }
    }

  private byte[] onCaptureImageResult(Intent data) {
    //  Uri selectedImageUri = data.getData();
   //   selectedImagePath = getRealPathFromURI(selectedImageUri);
     // thumbnail = (Bitmap) data.getExtras().get("data");
      try {

          thumbnail =  MediaStore.Images.Media.getBitmap(getContentResolver(), mCapturedImageURI);

      } catch (IOException e) {
          e.printStackTrace();
      }
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


     // String filePath = getRealPathFromURIPath(data, UploadDocActivity.this);
      String filePath = getRealPathFromURI(mCapturedImageURI);
      destination= new File(filePath);
      long length = destination.length();
      length = length/1024;
      if(length<=1024){
          thumbnail.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
      }else {
         // bm =  getResizedBitmap(bm,500);
        //  bm.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
          thumbnail = Bitmap.createScaledBitmap(thumbnail, (int) (thumbnail.getWidth() * 0.8), (int) (thumbnail.getHeight() * 0.8), true);
      }
      ImagesModel hero = new ImagesModel(i,thumbnail,destination);

      imageList.add(hero);
      i++;
      imagesAdapter.notifyDataSetChanged();
      return byteArrayOutputStream.toByteArray();
  }
    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e)
        {
            return contentUri.getPath();
        }
    }

    @SuppressWarnings("deprecation")
    private byte[] onSelectFromGalleryResult(Intent data) {
        ByteArrayOutputStream bytes=null;

        bm=null;
        Bitmap bitmap;
        Bundle extras = data.getExtras();

        Uri uri = data.getData();

        if (data != null) {
            try {
              //  InputStream stream = getContentResolver().openInputStream(
                       // data.getData());
             //   bitmap = BitmapFactory.decodeStream(stream);
            //    Uri uri=  FileProvider.getUriForFile(UploadDocActivity.this, getApplicationContext().getApplicationContext().getPackageName() + ".provider", createImageFile());
              //  Uri uri= getImageUri(UploadDocActivity.this,bitmap);
                //Uri uri = data.getData();
                if(uri!=null){

                }else{
                    if(extras.keySet().contains("data"))
                    {
                        Bitmap pictTaken = null ;

                     //   pictTaken = (Bitmap) extras.get("data");

                        uri = getIntent().getData();
                    }
                }

                String filePath = getRealPathFromURI_API19( UploadDocActivity.this,uri);
                File file = new File(filePath);
                long length = file.length();
                length = length/1024;
              //  System.out.println("File Path : " + file.getPath() + ", File size : " + length +" KB");

                destination= new File(filePath);

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                bytes = new ByteArrayOutputStream();
             //   bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                if(length<=1024){
                    bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                }else {
                    bm =  getResizedBitmap(bm,500);
                    bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                  //  bm.compress(Bitmap.CompressFormat.JPEG, 150, bytes);
               //     bm = Bitmap.createScaledBitmap(bm, (int) (bm.getWidth() * 0.8), (int) (bm.getHeight() * 0.8), true);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ImagesModel hero = new ImagesModel(i,bm,destination);
        imageList.add(hero);
        i++;
        imagesAdapter.notifyDataSetChanged();
        // ivImage.setImageBitmap(bm);
        return bytes.toByteArray();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                wholeID = DocumentsContract.getDocumentId(uri);
            }

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return  getRealPathFromURI_BelowAPI11(context,uri);
        }

    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private String getRealPathFromURIPath(Intent data, Activity activity) {
 /*                                               ;
        Uri contentURI= data.getData();
        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
*/


        Uri selectedImage = data.getData();
        String thePath1 ="No_path";
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(selectedImage, filePath,
                null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        thePath1 = c.getString(columnIndex);
        c.close();
        return  thePath1;
    }
    public void setImages(){
       showProgressDialog();
        ApiService service = ApiClient.createService(ApiService.class);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", keyuserId);

        //longitude

        RequestBody requestBody1 = null;

        for (int i = 0, size = imageList.size(); i < size; i++) {
            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), imageList.get(i).getFilePath());
            builder.addFormDataPart("doc_images" + "[" + i + "]", imageList.get(i).getFilePath().getName(), requestBody1);
            //builder.addFormDataPart("files",imageList.get(i).getFilePath().getName(),RequestBody.create(MediaType.parse("image/*"), requestBody1);
        }
        MultipartBody requestBody = builder.build();
        Call<ResponseBody> call = service.postMeme(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()) {

                    Log.i("Response", response.body().toString());
                    JSONObject jsonObject= null;
                    try {
                        String AllresponseData = response.body().string();
                        jsonObject = new JSONObject(AllresponseData);
                        String status = jsonObject.getString("status");
                        String resposne_message = jsonObject.getString("message");

                        Toast.makeText(getBaseContext(),resposne_message,Toast.LENGTH_SHORT).show();
                        //String resposne_sucess = jsonObject.getString("status");
                       // JSONObject resposne_userId = jsonObject.getJSONObject("data");
                      //  JSONArray heroArray = resposne_userId.getJSONArray("images");


                       Intent intent =new Intent(UploadDocActivity.this,UserDashboardActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                hideProgressDialog();
                Log.e("Response", response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               hideProgressDialog();
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }


}
