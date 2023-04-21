package com.saul.examen;

import static com.saul.examen.BdSupermercado.tblname;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.listasimple, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    final Model model=modelArrayList.get(position);
    byte[]image=model.getFoto();
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.txtname.setText(model.getNombre());
        holder.txtpass.setText(model.getPrecio());
        holder.txtpermiso.setText(model.getCodigo());
        //flow menu
        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.editar_menu:
                                //codigo
                                Bundle bundle=new Bundle();
                                bundle.putInt("id_usuario", model.getId());
                                bundle.putByteArray("foto",model.getFoto());
                                bundle.putString("nombre", model.getNombre());
                                bundle.putString("precio",model.getPrecio());
                                bundle.putString("codigo", model.getCodigo());
                                Intent intent=new Intent(context,MainActivity.class);
                                intent.putExtra("userdata",bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.eliminar_menu:
                                //codigo
                                BdSupermercado supermercadoSql= new BdSupermercado(context);
                                sqLiteDatabase = supermercadoSql.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(tblname,"id_usuario="+model.getId(), null);
                                if(recdelete!=-1){
                                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                                    modelArrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //mostrar menu
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView txtname, txtpass, txtpermiso;
    ImageButton flowmenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.viewfoto);
            txtname= itemView.findViewById(R.id.txt_nombre);
            txtpass= itemView.findViewById(R.id.txt_pass);
            txtpermiso= itemView.findViewById(R.id.txt_permiso);
            flowmenu=itemView.findViewById(R.id.flowmenu);
        }
    }

//

}
