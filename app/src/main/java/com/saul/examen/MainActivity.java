package com.saul.examen;

import static com.saul.examen.BdSupermercado.tblname;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
BdSupermercado bdSupermercado;
SQLiteDatabase sqLiteDatabase;
private ImageView foto;
EditText nombre, permiso, pass;
Button guardar, listar, editar;
int id=0;
public static final int CAMERA_REQUEST=100;
public static final int STORAGE_REQUEST=101;
String[]cameraPermission;
String[]storagePermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bdSupermercado=new BdSupermercado(this);
        findid();
        insertar();
        selecImagen();
        editar();
    }

    private void editar() {
        if (getIntent().getBundleExtra("userdata") != null) {
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id_usuario");
            nombre.setText(bundle.getString("nombre"));
            pass.setText(bundle.getString("precio"));
            permiso.setText(bundle.getString("codigo"));
            //Enviar imagen a actualizar
            byte[] bytes = bundle.getByteArray("foto");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            foto.setImageBitmap(bitmap);
            editar.setVisibility(View.VISIBLE);
            guardar.setVisibility(View.GONE);
        }
    }

    private void selecImagen() {
        foto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int foto=0;
                if(foto==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if(foto==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
    }
    @RequiresApi(api= Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {

        CropImage.activity().start(this);

    }

    @RequiresApi(api= Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void insertar() {
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString()!=null){
                ContentValues cv= new ContentValues();
                cv.put("foto",ImageViewToByte(foto));
                cv.put("nombre", nombre.getText().toString());
                cv.put("precio", pass.getText().toString());
                cv.put("codigo",permiso.getText().toString());
                sqLiteDatabase=bdSupermercado.getWritableDatabase();
                Long recinsert=sqLiteDatabase.insert(tblname,null,cv);
                if(recinsert!=null){
                    Toast.makeText(MainActivity.this, "Producto guardado", Toast.LENGTH_SHORT).show();
                    foto.setImageResource(R.drawable.photo);
                    nombre.setText("");
                    pass.setText("");
                    permiso.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                }
            } else {
                    Toast.makeText(MainActivity.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Listausuario.class));
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("nombre", nombre.getText().toString());
                cv.put("precio", pass.getText().toString());
                cv.put("codigo", permiso.getText().toString());
                cv.put("foto", ImageViewToByte(foto));

                sqLiteDatabase = bdSupermercado.getWritableDatabase();
                long rededit = sqLiteDatabase.update(tblname, cv, "id_usuario=" + id, null);
                if (rededit != -1) {
                    Toast.makeText(MainActivity.this, "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                    guardar.setVisibility(View.VISIBLE);
                    editar.setVisibility(View.GONE);
                    foto.setImageResource(R.drawable.photo);
                    nombre.setText("");
                    pass.setText("");
                    permiso.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//convertir imagen a bytes
    private byte[] ImageViewToByte(ImageView foto) {
        if(foto!=null){
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;} else {
            Toast.makeText(this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void findid() {
        foto=findViewById(R.id.imagen);
        guardar=findViewById(R.id.btnguardar);
        editar=findViewById(R.id.btneditar);
        listar=findViewById(R.id.btnlistar);
        nombre=findViewById(R.id.txtnombre);
        permiso=findViewById(R.id.txtpermiso);
        pass=findViewById(R.id.txtpass);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Active los permisos de camara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Active los permisos de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

        }
    }
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(foto);
            }
        }
    }

}