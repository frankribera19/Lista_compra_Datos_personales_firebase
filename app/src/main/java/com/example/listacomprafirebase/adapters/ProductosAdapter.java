package com.example.listacomprafirebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listacomprafirebase.R;
import com.example.listacomprafirebase.modelos.Producto;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {
    private List<Producto> objects;
    private int resource;
    private Context context;
    private DatabaseReference refDatabase;

    public ProductosAdapter(List<Producto> objects, int resource, Context context, DatabaseReference refDatabase) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.refDatabase = refDatabase;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(resource, null);
        productoView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblNombre.setText(producto.getNombre());
        holder.lblCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.lblPrecio.setText(String.valueOf(producto.getPrecioMoneda()));
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.remove(producto);
                //notifyItemRemoved(holder.getAdapterPosition());
                refDatabase.setValue(objects);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ProductoVH extends RecyclerView.ViewHolder{

        ImageView btnEliminar;
        TextView lblNombre;
        TextView lblCantidad;
        TextView lblPrecio;

        public ProductoVH(@NonNull View itemView) {
            super(itemView);

            btnEliminar = itemView.findViewById(R.id.btnDeleteCard);
            lblNombre = itemView.findViewById(R.id.lblNombreCard);
            lblCantidad = itemView.findViewById(R.id.lblCantidadCard);
            lblPrecio = itemView.findViewById(R.id.lblPrecioCard);
        }
    }
}
