package dk.easv.friendsv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.easv.friendsv2.Model.BEFriend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 400;
    private final static int CAPTURE_IMAGE_PERMISSION_REQUEST_CODE = 401;
    private static int RESULT_CODE = MainActivity.DETAIL_ACTIVITY_MODIFIED_RESULT_CODE;
    private final static int MAP_ACTIVITY_REQUEST_CODE = 500;
    boolean editing = false;
    private GoogleMap m_map;

    EditText etName, etPhone, etEmail, etUrl;
    CheckBox cbFavorite;
    ImageView ivImage;
    Button btnMap, btnSetHome, btnEditSave;

    BEFriend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etUrl = findViewById(R.id.etUrl);
        cbFavorite = findViewById(R.id.cbFavorite);
        ivImage = findViewById(R.id.imageView);
        btnMap = findViewById(R.id.btnMap);
        btnSetHome = findViewById(R.id.btnSetHome);
        btnEditSave = findViewById(R.id.btnEditSave);

        setGUI();
        setupListeners();
        removeKeyListeners();
    }

    private void removeKeyListeners() {
        etName.setTag(etName.getKeyListener());
        etPhone.setTag(etPhone.getKeyListener());
        etEmail.setTag(etEmail.getKeyListener());
        etUrl.setTag(etUrl.getKeyListener());

        etName.setKeyListener(null);
        etPhone.setKeyListener(null);
        etEmail.setKeyListener(null);
        etUrl.setKeyListener(null);
    }

    private void setKeyListeners() {
        etName.setKeyListener((KeyListener) etName.getTag());
        etPhone.setKeyListener((KeyListener) etPhone.getTag());
        etEmail.setKeyListener((KeyListener) etEmail.getTag());
        etUrl.setKeyListener((KeyListener) etUrl.getTag());
    }

    private void setupListeners() {
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permission = PermissionChecker.checkSelfPermission(DetailActivity.this, MediaStore.ACTION_IMAGE_CAPTURE);
                if (permission == PermissionChecker.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CAMERA}, CAPTURE_IMAGE_PERMISSION_REQUEST_CODE);
                    Log.d(TAG, "PERMISSION DENIED");
                }
            }
        });
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editing) {
                    setKeyListeners();
                    btnEditSave.setText(getString(R.string.save));
                } else {
                    removeKeyListeners();
                    btnEditSave.setText(getString(R.string.edit));
                }
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(DetailActivity.this, MapActivity.class);
                Log.d(TAG, "Detail activity will be started");
                startActivityForResult(x, MAP_ACTIVITY_REQUEST_CODE);
                Log.d(TAG, "Detail activity is started");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAPTURE_IMAGE_PERMISSION_REQUEST_CODE: {
                if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(photo);
        }
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private void setGUI() {
        friend = (BEFriend) getIntent().getSerializableExtra("friend");

        etName.setText(friend.getName());
        etPhone.setText(friend.getPhone());
        etEmail.setText(friend.getEmail());
        etUrl.setText(friend.getUrl());
        cbFavorite.setChecked(friend.isFavorite());
    }

    @Override
    public void onBackPressed() {
        if (isChanged()) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("friendOriginal", friend);
            friend = new BEFriend(
                    friend.getId(),
                    etName.getText().toString(),
                    etPhone.getText().toString(),
                    cbFavorite.isChecked(),
                    etEmail.getText().toString(),
                    etUrl.getText().toString()
            );
            returnIntent.putExtra("friendModified", friend);
            setResult(RESULT_CODE, returnIntent);
        } else {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    private boolean isChanged() {
        if (friend.getName() != etName.getText().toString() ||
                friend.getPhone() != etPhone.getText().toString() ||
                (cbFavorite.isChecked() != friend.isFavorite()) ||
                friend.getEmail() != etEmail.getText().toString() ||
                friend.getUrl() != etUrl.getText().toString()) {
            return true;
        } else {
            return false;
        }
    }
}
