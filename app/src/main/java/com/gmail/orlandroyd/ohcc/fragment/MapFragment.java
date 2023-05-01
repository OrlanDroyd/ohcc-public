package com.gmail.orlandroyd.ohcc.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.AuthenticationActivity;
import com.gmail.orlandroyd.ohcc.map.MyMarker;
import com.gmail.orlandroyd.ohcc.map.Place;
import com.gmail.orlandroyd.ohcc.map.PlaceMarker;
import com.gmail.orlandroyd.ohcc.map.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class MapFragment extends Fragment {
    private View rootView;
    private LocationManager locManager;
    private MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;
    private String provider, RUTA;
    private Location loc;
    private MyMarker marker;
    private Double longitud, latitud;

    /**
     * COORDENADAS
     */
    private static final double[] latCentroHistorico = {21.374382, 21.375023, 21.375860, 21.376819, 21.376569, 21.378256, 21.380511, 21.381778, 21.382986, 21.383928, 21.385035, 21.385817, 21.385357, 21.384989, 21.386285, 21.386268, 21.387645, 21.387687, 21.388492, 21.388134, 21.389999, 21.390082, 21.402746, 21.403098, 21.400439, 21.400518, 21.396012, 21.396032, 21.391197, 21.390425, 21.389573, 21.389193, 21.388015, 21.387931, 21.387291, 21.387114, 21.384001, 21.384262, 21.377295, 21.375345, 21.374129, 21.371524, 21.369718, 21.370645, 21.371084, 21.375414, 21.374635, 21.375654, 21.374223, 21.373743, 21.374382};
    private static final double[] lonCentroHistorico = {-77.926724, -77.927099, -77.925640, -77.926147, -77.926523, -77.927458, -77.926392, -77.928111, -77.927758, -77.930456, -77.931771, -77.931483, -77.928806, -77.927053, -77.926563, -77.922528, -77.922075, -77.920652, -77.921128, -77.918795, -77.918458, -77.917670, -77.918013, -77.913332, -77.913318, -77.914946, -77.914703, -77.911323, -77.911189, -77.912652, -77.912062, -77.910843, -77.911230, -77.910303, -77.910525, -77.909179, -77.909829, -77.911685, -77.914254, -77.909051, -77.910232, -77.906716, -77.907276, -77.910299, -77.910525, -77.916296, -77.918120, -77.918946, -77.924347, -77.924570, -77.926724};
    private static final double[] latPatrimonio = {21.37934, 21.37973, 21.38070, 21.38074, 21.38075, 21.38087, 21.38136, 21.38161, 21.38316, 21.38481, 21.38476, 21.38313, 21.38344, 21.38330, 21.38331, 21.38262, 21.38088, 21.38022, 21.38004, 21.38009, 21.38002, 21.37995, 21.37951, 21.37927, 21.37884, 21.37836, 21.37801, 21.37780, 21.37746, 21.37722, 21.37700, 21.37639, 21.37620, 21.37607, 21.37596, 21.37563, 21.37539, 21.37504, 21.37458, 21.37492, 21.37515, 21.37486, 21.37528, 21.37539, 21.37664, 21.37896, 21.37919, 21.37945, 21.37945, 21.37934};
    private static final double[] lonPatrimonio = {-77.92439, -77.92434, -77.92413, -77.92184, -77.92125, -77.92085, -77.91978, -77.91960, -77.91930, -77.91904, -77.91856, -77.91795, -77.91650, -77.91651, -77.91601, -77.91590, -77.91557, -77.91533, -77.91525, -77.91484, -77.91479, -77.91426, -77.91428, -77.91431, -77.91449, -77.91461, -77.91430, -77.91428, -77.91483, -77.91472, -77.91470, -77.91534, -77.91576, -77.91589, -77.91575, -77.91595, -77.91616, -77.91666, -77.91804, -77.91832, -77.91863, -77.91985, -77.92007, -77.91962, -77.92004, -77.92134, -77.92139, -77.92224, -77.92299, -77.92439};

    /**
     * LocationListener -> Para que el GPS funcione.
     */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            localizacion();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    private ArrayList<PlaceMarker> markers = new ArrayList<>();

    /**
     * PERMISOS -> Métodos necesarios para que en versiones de android superiores a 5.0 se garanticen los permisos de:
     * - WRITE_EXTERNAL_STORAGE
     * - READ_EXTERNAL_STORAGE
     * - ACCESS_FINE_LOCATION
     * - ACCESS_LOCATION_EXTRA_COMMANDS
     * - ACCESS_COARSE_LOCATION
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                boolean yes = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        yes = false;
                        break;
                    }
                }
                if (yes) {
                    initialize();
                } else {
                    getActivity().finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * PERMISOS
     */
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    }

    /**
     * PERMISOS
     *
     * @return
     */
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    private void initialize() {
        /**
         * Init el FloatingActionButton de la ubicacion
         */
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitud != null && longitud != null) {
                    /**
                     * si se ha encontrado la lat y long, se mueve el foco a la posicion obtenida del GPS
                     */
                    byte zoom = mapView.getModel().mapViewPosition.getZoomLevel();
                    mapView.getModel().mapViewPosition.animateTo(new LatLong(latitud, longitud));
                    mapView.getModel().mapViewPosition.setZoomLevel(zoom);
                } else {
                    /**
                     * si lat y long no se han encontrado, se muestra el 'message2' de la carpeta 'values.strings.xml'
                     */
                    FancyToast.makeText(getContext(), "No se ha encontrado su posición...", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }
        });
        /**
         * cargar el mapa de las assets
         */
        loadMap();
        /**
         * Init el mapa
         */
        mapView = rootView.findViewById(R.id.mapView);
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.getModel().mapViewPosition.setZoomLevel((byte) 13);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 11);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 19);
        // centrar el mapa en CMG
        mapView.getModel().mapViewPosition.setCenter(new LatLong(21.3919882, -77.919945));

        /**
         * asignar atributos a la vista para que el mapa funcione
         */
        tileCache = AndroidUtil.createTileCache(getContext(), "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor());
        tileRendererLayer = new TileRendererLayer(tileCache,
                mapView.getModel().mapViewPosition, false, AndroidGraphicFactory.INSTANCE);
        tileRendererLayer.setMapFile(new File(RUTA));
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        /**
         * GPS
         */
        locManager =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locManager.getBestProvider(criteria, true);

        LocationProvider locationProvider = locManager.getProvider(provider);
        if (locationProvider == null)
            provider = LocationManager.GPS_PROVIDER;
        /**
         * int markers
         */
        //initMarkers();
        /**
         * añadir los markers
         */
//        addListMap();
        /**
         * add polygon 1
         */
        addPolygon(latCentroHistorico, lonCentroHistorico, 70, 33, 150, 243, Style.FILL);
//        addPolygon(latCentroHistorico, lonCentroHistorico, 100, 33, 150, 243, Style.STROKE);
        /**
         * add polygon 2
         */
        addPolygon(latPatrimonio, lonPatrimonio, 70, 233, 30, 99, Style.FILL);
        addPolygon(latPatrimonio, lonPatrimonio, 100, 233, 30, 99, Style.STROKE);

        localizacion();
        /**
         * Dialogo para encender el gps
         */
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Desea activar el GPS?").setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //click en si
                            FancyToast.makeText(getContext(), "Click en Ubicación", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        //click en no
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * add polygon
     */
    private void addPolygon(double[] lat, double[] lon, int alpha, int red, int green, int blue, Style style) {
        Paint paintStroke = Utils.createPaint(AndroidGraphicFactory.INSTANCE
                        .createColor(alpha, red, green, blue), 3,
                style);
        paintStroke.setStrokeWidth(3);
        Polyline line = new Polyline(paintStroke, AndroidGraphicFactory.INSTANCE);

        List<LatLong> geoPoints = line.getLatLongs();

        for (int i = 0; i < lat.length; i++) {
            geoPoints.add(new LatLong(lat[i], lon[i]));
        }

        mapView.getLayerManager().getLayers().add(line);
    }

    /**
     * Añadir los marcadores
     */
    private void initMarkers() {
        Place place1 = new Place(getString(R.string.info1), getString(R.string.phone), 21.378902, -77.9184368);

        PlaceMarker placeMarker1 = new PlaceMarker(getContext(), new LatLong(place1.getLatitud(), place1.getLongitud()), AndroidGraphicFactory.convertToBitmap(getResources().getDrawable(R.drawable.img_lugar)), 0, 0, place1);

        markers.add(placeMarker1);
    }

    /**
     * añadir los markers
     */
    private void addListMap() {
        for (int i = 0; i < markers.size(); i++) {
            mapView.getLayerManager().getLayers().add(markers.get(i));
        }
    }

    /**
     * metodo para añadir el marcador de mi posicion
     */
    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        loc = locManager.getLastKnownLocation(provider);

        if (marker != null) {
            mapView.getLayerManager().getLayers().remove(marker);
        }
        if (loc != null) {
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();

            marker = new MyMarker(getContext(), new LatLong(latitud, longitud), AndroidGraphicFactory.convertToBitmap(getResources().getDrawable(R.drawable.img_ubicacion)), 0, 0);

            if (mapView != null) {
                mapView.getLayerManager().getLayers().add(marker);
            }
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 1, locationListener);
    }

    /**
     * metodo para cargar el mapa de la carpeta assets y copiarla al almazanamientos del dispositivo
     */
    private void loadMap() {
        AssetManager am = getActivity().getAssets();
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/mapa/");
            if (!dir.exists()) {
                dir.mkdir();
            }
            // se remueve el .mp3 por .map
            String fileName = "cuba";
            RUTA = Environment.getExternalStorageDirectory() + "/mapa/" + fileName + ".map";
            File destinationFile = new File(RUTA);
            if (!destinationFile.exists()) {
                InputStream in = am.open(fileName + ".mp3");
                FileOutputStream f = new FileOutputStream(destinationFile);
                byte[] buffer = new byte[1024];
                int len1;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
            }
        } catch (Exception e) {
            FancyToast.makeText(getContext(), "ERROR AL CARGAR LOS DATOS: " + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        AndroidGraphicFactory.createInstance(getActivity().getApplication());
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        /**
         * Revisa la version de android del dispositivo y muestra los mensajes de
         * confirmación si es superior a 5.0 (LOLLIPOP_MR1)
         */
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            } else {
                initialize();
            }
        } else {
            initialize();
        }
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login_main) {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
