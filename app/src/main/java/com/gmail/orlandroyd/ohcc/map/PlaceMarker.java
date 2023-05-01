package com.gmail.orlandroyd.ohcc.map;

import android.content.Context;
import android.content.DialogInterface;

import com.gmail.orlandroyd.ohcc.R;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.layer.overlay.Marker;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by OrlanDroyd on 25/02/2019.
 */
public class PlaceMarker extends Marker {
    private Context ctx;
    private Place place;


    public PlaceMarker(Context ctx, LatLong latLong, Bitmap bitmap, int horizontalOffset,
                       int verticalOffset, Place place) {
        super(latLong, bitmap, horizontalOffset, verticalOffset);
        this.ctx = ctx;
        this.place = place;

    }

    @Override
    public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
        if (this.contains(layerXY, tapXY)) {
            alertDialogCreate();
            return true;
        }
        return super.onTap(tapLatLong, layerXY, tapXY);
    }


    public void alertDialogCreate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setIcon(R.drawable.img_lugar);
        builder.setTitle(place.getNombre());
        builder.setMessage("\nTelef: \n" + place.getDescripcion() + "\nHorario: \n" + ctx.getString(R.string.dias_info));
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
