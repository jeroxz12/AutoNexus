


/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.entities.Car;

import java.util.List;

/*
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.modelTextView.setText("Modelo: " +car.getModel());
        holder.brandTextView.setText("Marca: " + car.getBrand());
        holder.priceTextView.setText("Precio: $" +car.getPrice().toString());
        holder.yearTextView.setText("Año: "+ car.getYear().toString());
        holder.colorTextView.setText("Color: " + car.getColor());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        TextView modelTextView;
        TextView brandTextView;
        TextView priceTextView;
        TextView yearTextView;
        TextView colorTextView;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            colorTextView = itemView.findViewById(R.id.colorTextView);
        }
    }
}*/

package com.example.acn4av_jeronimo_lago.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.activities.ConcesionariaDetailActivity;
import com.example.acn4av_jeronimo_lago.activities.ConcesionariasActivity;
import com.example.acn4av_jeronimo_lago.controller.CarController;
import com.example.acn4av_jeronimo_lago.entities.Car;

import java.util.ArrayList;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder> {

    ArrayList<Car> carList;
    Integer concessionaryId;

    public CarListAdapter(ArrayList<Car> carList, Integer concessionaryId) {
        this.carList= carList;
        this.concessionaryId = concessionaryId;
    }

    @NonNull
    @Override
    public CarListAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_auto,null,false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListAdapter.CarViewHolder holder, int position) {
        Car car = carList.get(position);
        if (car.getExpanded() == null) car.setExpanded(false);
        boolean isExpanded = car.getExpanded();
        holder.modelTextView.setText(car.getBrand() +" "+car.getModel());
        holder.deleteCar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Eliminar auto")
                    .setMessage("¿Está seguro que desea eliminar el auto?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            CarController controller = new CarController(view.getContext());
                            controller.open();
                            if(controller.deleteCar(car.getId())) {
                                Toast.makeText(view.getContext(), "Auto eliminado correctamente", Toast.LENGTH_SHORT).show();
                               carList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                               Intent launch = new Intent(view.getContext(), ConcesionariaDetailActivity.class);
                                launch.putExtra("ID", concessionaryId);

                                view.getContext().startActivity(launch);
                            } else {
                                Toast.makeText(view.getContext(), "Error al eliminar el auto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        TextView modelTextView;
        ImageButton deleteCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            deleteCar = itemView.findViewById(R.id.btnEliminarAuto);
            itemView.setOnClickListener( view -> {
                Car car = carList.get(getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(car.getModel())
                        .setMessage("Marca: " + car.getBrand() + "\n" +
                                "Precio: $" + car.getPrice() + "\n" +
                                "Año: " + car.getYear() + "\n" +
                                "Color: " + car.getColor())
                        .setPositiveButton("Cerrar", null)
                        .show();
            });
        }

    }
}

