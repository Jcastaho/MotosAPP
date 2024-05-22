package com.straccion.motos_admin.ui.inicio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.FichaTecnicaAdapters;
import com.straccion.motos_admin.adapters.ListaCompararAdapter;
import com.straccion.motos_admin.adapters.PostsAdapters;
import com.straccion.motos_admin.adapters.SliderAdapter;
import com.straccion.motos_admin.adapters.ViewPagerAdapter;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.SliderItem;
import com.straccion.motos_admin.models.ViewPagerItem;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetallesMoto extends Fragment {
    View mview;
    LinearLayout lnlMostrarManuales;
    LinearLayout lnlInformacionAdd1;
    LinearLayout lnlTextoColores;
    FrameLayout frameContenedor;
    NestedScrollView nestedScrollView;
    AppBarLayout appbarLayout;
    FloatingActionButton fabComparar;
    TextView txtExtendedComparar;
    ImageView imgExtendedFlecha;
    CardView crdArchivo1;
    CardView crdArchivo2;
    CardView crdArchivo3;
    CardView crdArchivo4;
    CardView crdArchivo5;
    CardView crdArchivo6;
    CardView cdvManuales;
    TextView txtArchivo1;
    TextView txtArchivo2;
    TextView txtArchivo3;
    TextView txtArchivo4;
    TextView txtArchivo5;
    TextView txtArchivo6;
    TextView nombreColor;


    TextView txtModelo;
    TextView txtModelo2;
    TextView txtNomMoto;
    TextView txtFabricanteMoto;
    TextView txtDescripcionMoto;
    TextView txtTituloDescripcionMoto;
    TextView txtPrecio;
    TextView txtprecioAnterior;
    LinearLayout lnlDescuento;
    ViewPager2 viewImagenesAdd;
    Handler viewImagenHandler = new Handler();

    LinearLayout lnlDatos;
    SliderView mSliderView;
    SliderAdapter mSliderAdapter;
    ViewPagerAdapter mViewPagerAdapter;
    PostProvider mPostProvider;
    ListaCompararAdapter mListaCompararAdapter;
    ImageProvider mImageProvider;
    FichaTecnicaAdapters fichaTecnicaAdapters;
    List<SliderItem> mSliderItems = new ArrayList<>();
    List<String> colores = new ArrayList<>();
    List<String> listaImagenes = new ArrayList<>();
    List<String> listaImagenesCaracteristicas = new ArrayList<>();
    List<String> listaTextoCaracteristicas = new ArrayList<>();
    List<String> imagenesColores1 = new ArrayList<>();
    List<String> imagenesColores2 = new ArrayList<>();
    List<String> imagenesColores3 = new ArrayList<>();
    List<String> imagenesColores4 = new ArrayList<>();
    List<String> imagenesColores5 = new ArrayList<>();
    List<String> imagenesColores6 = new ArrayList<>();
    List<String> archivosPDF = new ArrayList<>();
    List<String> nombreArchivosPDF = new ArrayList<>();
    List<ViewPagerItem> viewPagerImagenes = new ArrayList<>();

    String idDocument = "";
    String nombre = "";
    String fabricante = "";
    String modelo = "";
    String modelo2 = "";
    String DescripcionMoto = "";
    boolean busqueda = false;
    int precio = 0;
    int precio2 = 0;
    int nuevoValorDescuento = 0;
    boolean descuento;
    double numVisitas = 0;
    double numBusquedas = 0;
    String elemento = "";
    String archivo = "";
    int colorCirculos = 0;
    int cantArchivos = 0;


    public DetallesMoto() {
        // Required empty public constructor
    }

    public static DetallesMoto newInstance(String param1, String param2) {
        DetallesMoto fragment = new DetallesMoto();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idDocument = getArguments().getString("idDocument");
            busqueda = getArguments().getBoolean("busquedas");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_detalles_moto, container, false);

        mSliderView = mview.findViewById(R.id.imageSlider);
        appbarLayout = mview.findViewById(R.id.appbarLayout);
        nestedScrollView = mview.findViewById(R.id.nestedScrollView);
        frameContenedor = mview.findViewById(R.id.frameContenedor);
        viewImagenesAdd = mview.findViewById(R.id.viewImagenesAdd);
        txtModelo = mview.findViewById(R.id.txtModelo);
        txtModelo2 = mview.findViewById(R.id.txtModelo2);
        txtNomMoto = mview.findViewById(R.id.txtNomMoto);
        txtFabricanteMoto = mview.findViewById(R.id.txtFabricanteMoto);
        lnlDatos = mview.findViewById(R.id.lnlDatos);
        txtDescripcionMoto = mview.findViewById(R.id.txtDescripcionMoto);
        txtTituloDescripcionMoto = mview.findViewById(R.id.txtTituloDescripcionMoto);
        txtPrecio = mview.findViewById(R.id.txtPrecio);
        txtprecioAnterior = mview.findViewById(R.id.txtprecioAnterior);
        lnlDescuento = mview.findViewById(R.id.lnlDescuento);
        lnlMostrarManuales = mview.findViewById(R.id.lnlMostrarManuales);
        lnlInformacionAdd1 = mview.findViewById(R.id.lnlInformacionAdd1);
        lnlTextoColores = mview.findViewById(R.id.lnlTextoColores);
        fabComparar = mview.findViewById(R.id.fabComparar);
        txtExtendedComparar = mview.findViewById(R.id.txtExtendedComparar);
        imgExtendedFlecha = mview.findViewById(R.id.imgExtendedFlecha);
        crdArchivo1 = mview.findViewById(R.id.crdArchivo1);
        crdArchivo2 = mview.findViewById(R.id.crdArchivo2);
        crdArchivo3 = mview.findViewById(R.id.crdArchivo3);
        crdArchivo4 = mview.findViewById(R.id.crdArchivo4);
        crdArchivo5 = mview.findViewById(R.id.crdArchivo5);
        crdArchivo6 = mview.findViewById(R.id.crdArchivo6);
        cdvManuales = mview.findViewById(R.id.cdvManuales);
        txtArchivo1 = mview.findViewById(R.id.txtArchivo1);
        txtArchivo2 = mview.findViewById(R.id.txtArchivo2);
        txtArchivo3 = mview.findViewById(R.id.txtArchivo3);
        txtArchivo4 = mview.findViewById(R.id.txtArchivo4);
        txtArchivo5 = mview.findViewById(R.id.txtArchivo5);
        txtArchivo6 = mview.findViewById(R.id.txtArchivo6);
        nombreColor = mview.findViewById(R.id.nombreColor);

        mPostProvider = new PostProvider();
        mImageProvider = new ImageProvider();
        getPost();

        txtDescripcionMoto.setVisibility(View.VISIBLE);
        txtTituloDescripcionMoto.setVisibility(View.VISIBLE);
        txtModelo2.setVisibility(View.GONE);

        fichaTecnicaAdapters = new FichaTecnicaAdapters(getContext(), idDocument);
        fichaTecnicaAdapters.showData(lnlDatos);

        txtModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelo2 != "") {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                    symbols.setGroupingSeparator('.');
                    symbols.setDecimalSeparator(',');

                    DecimalFormat formato = new DecimalFormat("#,###", symbols);
                    String numeroFormato = formato.format(nuevoValorDescuento);
                    txtPrecio.setText("$" + numeroFormato);
                    txtModelo.setTextColor(Color.BLUE);
                    txtModelo2.setTextColor(Color.BLACK);
                }
            }
        });
        txtModelo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');

                DecimalFormat formato = new DecimalFormat("#,###", symbols);
                String numeroFormato = formato.format(precio2);
                txtPrecio.setText("$" + numeroFormato);
                txtModelo2.setTextColor(Color.BLUE);
                txtModelo.setTextColor(Color.BLACK);
            }
        });

        fabComparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabComparar.setVisibility(View.GONE);
                txtExtendedComparar.setVisibility(View.VISIBLE);
                imgExtendedFlecha.setVisibility(View.VISIBLE);
            }
        });
        txtExtendedComparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.card_elegir_moto);
                Button btnComparar = dialog.findViewById(R.id.btnComparar);
                Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
                ImageView imgMoto1 = dialog.findViewById(R.id.imgMoto1);
                ImageView imgMoto2 = dialog.findViewById(R.id.imgMoto2);
                TextView txtMoto1 = dialog.findViewById(R.id.txtMoto1);
                TextView txtMoto2 = dialog.findViewById(R.id.txtMoto2);
                FrameLayout contenedor = dialog.findViewById(R.id.contenedor);
                TextView txtVs = dialog.findViewById(R.id.txtVs);
                Dialog dialog2 = new Dialog(getContext());
                dialog.show();

                String moto = listaImagenes.get(0);
                Picasso.get().load(moto).into(imgMoto1);
                txtMoto1.setText(nombre);

                imgMoto2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.setContentView(R.layout.elegir_moto_comparar);
                        RecyclerView reciclerViewElegirMoto = dialog2.findViewById(R.id.reciclerViewElegirMoto);
                        LinearLayout lnlMostrarMotos = dialog2.findViewById(R.id.lnlMostrarMotos);
                        ProgressBar progressBar = dialog2.findViewById(R.id.progressBar);

                        int tiempoMostrandoProgressBar = 1200;
                        lnlMostrarMotos.setVisibility(View.VISIBLE);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                        reciclerViewElegirMoto.setLayoutManager(gridLayoutManager);


                        Query query = mPostProvider.getAll2();

                        FirestoreRecyclerOptions<PostAuteco> options = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query, PostAuteco.class).build();

                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                        mListaCompararAdapter = new ListaCompararAdapter(options, getContext(), navController, idDocument, imgMoto2, dialog2, txtMoto2);
                        reciclerViewElegirMoto.setAdapter(mListaCompararAdapter);
                        mListaCompararAdapter.startListening();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lnlMostrarMotos.setVisibility(View.GONE);
                            }
                        }, tiempoMostrandoProgressBar);

                        Window window = dialog.getWindow();

                        dialog2.show();

                    }
                });
                btnComparar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombreMoto = txtMoto2.getText().toString();
                        if (!nombreMoto.isEmpty()) {
                            //animacion a las imagenes
                            ObjectAnimator animMoto1 = ObjectAnimator.ofFloat(imgMoto1, "translationX", 0f, 460f, 80);
                            animMoto1.setDuration(500);

                            ObjectAnimator animMoto2 = ObjectAnimator.ofFloat(imgMoto2, "translationX", 0f, -460f, -80);
                            animMoto2.setDuration(500);

                            //animacion al txt VS
                            ObjectAnimator scaleX = ObjectAnimator.ofFloat(txtVs, "scaleX", 1.0f, 3.5f);
                            ObjectAnimator scaleY = ObjectAnimator.ofFloat(txtVs, "scaleY", 1.0f, 3.5f);
                            scaleX.setDuration(600);
                            scaleY.setDuration(600);

                            //"vibracion a las imagenes"
                            PropertyValuesHolder holderX1 = PropertyValuesHolder.ofFloat("translationX", -20f, 20f);
                            PropertyValuesHolder holderY1 = PropertyValuesHolder.ofFloat("translationY", -20f, 20f);

                            PropertyValuesHolder holderX2 = PropertyValuesHolder.ofFloat("translationX", -20f, 20f);
                            PropertyValuesHolder holderY2 = PropertyValuesHolder.ofFloat("translationY", -20f, 20f);


                            ObjectAnimator animTiembla1 = ObjectAnimator.ofPropertyValuesHolder(imgMoto1, holderX1, holderY1);
                            ObjectAnimator animTiembla2 = ObjectAnimator.ofPropertyValuesHolder(imgMoto2, holderX2, holderY2);


                            ScaleAnimation zoomAnimation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.758f, Animation.RELATIVE_TO_SELF, 0.85f);
                            zoomAnimation.setDuration(3000);

                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animMoto1, animMoto2, animTiembla1, animTiembla2, scaleX, scaleY);
                            contenedor.startAnimation(zoomAnimation);
                            animatorSet.start();

                            Handler handler = new Handler();
                            int delayMillis = 500;

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Bundle args = new Bundle();
                                    args.putString("idDocument", idDocument);
                                    String idNew = mListaCompararAdapter.getPostIdSeleccionado();
                                    args.putString("idDocument2", idNew);
                                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                                    navController.navigate(R.id.action_detallesMoto_to_nuevoCompararFragment, args);
                                }
                            }, delayMillis);
                        } else {
                            Toast.makeText(getContext(), "Por favor seleccione una moto a comparar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        imgExtendedFlecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabComparar.setVisibility(View.VISIBLE);
                txtExtendedComparar.setVisibility(View.GONE);
                imgExtendedFlecha.setVisibility(View.GONE);
            }
        });
        lnlMostrarManuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarManuales();
                mostrarCards();
            }
        });
        crdArchivo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(0);
            }
        });
        crdArchivo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(1);
            }
        });
        crdArchivo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(2);
            }
        });
        crdArchivo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(3);
            }
        });
        crdArchivo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(4);
            }
        });
        crdArchivo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(5);
            }
        });

        return mview;
    }

    private void downloadPdf(int identificador) {
        DownloadManager.Request descarga;
        DownloadManager manager = (DownloadManager) requireActivity().getSystemService(getContext().DOWNLOAD_SERVICE);
        switch (identificador) {
            case 0:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(0) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(0) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
            case 1:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(1) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(1) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
            case 2:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(2) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(2) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
            case 3:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(3) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(3) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
            case 4:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(4) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(4) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
            case 5:
                archivo = archivosPDF.get(identificador);
                descarga = new DownloadManager.Request(Uri.parse(archivo));
                descarga.setTitle(nombreArchivosPDF.get(5) + ".pdf");
                descarga.setDescription("Descargando archivo PDF");
                descarga.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivosPDF.get(5) + ".pdf");
                descarga.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                manager.enqueue(descarga);
                break;
        }
    }

    private void mostrarCards() {
        cantArchivos = nombreArchivosPDF.size();
        switch (cantArchivos) {
            case 0:
                crdArchivo1.setVisibility(View.GONE);
                crdArchivo2.setVisibility(View.GONE);
                crdArchivo3.setVisibility(View.GONE);
                crdArchivo4.setVisibility(View.GONE);
                crdArchivo5.setVisibility(View.GONE);
                crdArchivo6.setVisibility(View.GONE);
                break;
            case 1:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.GONE);
                crdArchivo3.setVisibility(View.GONE);
                crdArchivo4.setVisibility(View.GONE);
                crdArchivo5.setVisibility(View.GONE);
                crdArchivo6.setVisibility(View.GONE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                break;
            case 2:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.VISIBLE);
                crdArchivo3.setVisibility(View.GONE);
                crdArchivo4.setVisibility(View.GONE);
                crdArchivo5.setVisibility(View.GONE);
                crdArchivo6.setVisibility(View.GONE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                txtArchivo2.setText(nombreArchivosPDF.get(1));
                break;
            case 3:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.VISIBLE);
                crdArchivo3.setVisibility(View.VISIBLE);
                crdArchivo4.setVisibility(View.GONE);
                crdArchivo5.setVisibility(View.GONE);
                crdArchivo6.setVisibility(View.GONE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                txtArchivo2.setText(nombreArchivosPDF.get(1));
                txtArchivo3.setText(nombreArchivosPDF.get(2));
                break;
            case 4:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.VISIBLE);
                crdArchivo3.setVisibility(View.VISIBLE);
                crdArchivo4.setVisibility(View.VISIBLE);
                crdArchivo5.setVisibility(View.GONE);
                crdArchivo6.setVisibility(View.GONE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                txtArchivo2.setText(nombreArchivosPDF.get(1));
                txtArchivo3.setText(nombreArchivosPDF.get(2));
                txtArchivo4.setText(nombreArchivosPDF.get(3));
                break;
            case 5:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.VISIBLE);
                crdArchivo3.setVisibility(View.VISIBLE);
                crdArchivo4.setVisibility(View.VISIBLE);
                crdArchivo5.setVisibility(View.VISIBLE);
                crdArchivo6.setVisibility(View.GONE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                txtArchivo2.setText(nombreArchivosPDF.get(1));
                txtArchivo3.setText(nombreArchivosPDF.get(2));
                txtArchivo4.setText(nombreArchivosPDF.get(3));
                txtArchivo5.setText(nombreArchivosPDF.get(4));
                break;
            case 6:
                crdArchivo1.setVisibility(View.VISIBLE);
                crdArchivo2.setVisibility(View.VISIBLE);
                crdArchivo3.setVisibility(View.VISIBLE);
                crdArchivo4.setVisibility(View.VISIBLE);
                crdArchivo5.setVisibility(View.VISIBLE);
                crdArchivo6.setVisibility(View.VISIBLE);
                txtArchivo1.setText(nombreArchivosPDF.get(0));
                txtArchivo2.setText(nombreArchivosPDF.get(1));
                txtArchivo3.setText(nombreArchivosPDF.get(2));
                txtArchivo4.setText(nombreArchivosPDF.get(3));
                txtArchivo5.setText(nombreArchivosPDF.get(4));
                txtArchivo6.setText(nombreArchivosPDF.get(5));
        }
    }

    private void instanceSlider() {
        mSliderAdapter = new SliderAdapter(getContext(), mSliderItems, fabricante);
        mSliderView.setSliderAdapter(mSliderAdapter);
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM);//animacion del punto
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);//transiscion
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);//hacia que direccion va
        mSliderView.setIndicatorSelectedColor(Color.BLACK);//Color del punto
        mSliderView.setIndicatorUnselectedColor(Color.GRAY);//Color cuandono esta seleccionado
        mSliderView.setScrollTimeInSec(3);//tiempo
        mSliderView.setAutoCycle(true);//se le dice si quiere cambiar automaticamente
        mSliderView.startAutoCycle();


        //CASO ESPECIAL PARA EL TAMAÑO DE ESTAS MOTOS
        if (fabricante.equals("HONDA")){
            modificarTamanoVistaMotos();
        }
        if (fabricante.equals("YAMAHA")){
            modificarTamanoVistaMotos2(100);
        }
        if (fabricante.equals("SUZUKI")){
            modificarTamanoVistaMotos2(110);
        }
        if (fabricante.equals("HERO")){
            modificarTamanoVistaMotos2(0);
        }

        //imagenes caractreristicas adicionales
        if (listaTextoCaracteristicas == null || listaTextoCaracteristicas.isEmpty()) {
            mViewPagerAdapter = new ViewPagerAdapter(getContext(), viewPagerImagenes, viewImagenesAdd, null);
        } else {
            mViewPagerAdapter = new ViewPagerAdapter(getContext(), viewPagerImagenes, viewImagenesAdd, listaTextoCaracteristicas);
        }
        viewImagenesAdd.setAdapter(mViewPagerAdapter);
        viewImagenesAdd.setOffscreenPageLimit(3);
        viewImagenesAdd.setClipChildren(false);
        viewImagenesAdd.setClipToPadding(false);
        viewImagenesAdd.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });
        viewImagenesAdd.setPageTransformer(transformer);
        viewImagenesAdd.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewImagenHandler.removeCallbacks(viewpagerRunnable);
                viewImagenHandler.postDelayed(viewpagerRunnable, 5000);
            }
        });

    }

    private Runnable viewpagerRunnable = new Runnable() {
        @Override
        public void run() {
            viewImagenesAdd.setCurrentItem(viewImagenesAdd.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        viewImagenHandler.removeCallbacks(viewpagerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewImagenHandler.postDelayed(viewpagerRunnable, 2000);
    }

    private void getPost() {
        mSliderItems.clear();
        mPostProvider.getPostById(idDocument).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("imagenes")) {
                        listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                        for (String imagen : listaImagenes) {
                            SliderItem item = new SliderItem();
                            item.setImageurl(imagen);
                            mSliderItems.add(item);
                        }
                    }
                    if (documentSnapshot.contains("caracteristicasImagenes")) {
                        listaImagenesCaracteristicas = (List<String>) documentSnapshot.get("caracteristicasImagenes");
                        for (String imagenes : listaImagenesCaracteristicas) {
                            ViewPagerItem item = new ViewPagerItem();
                            item.setImageurl(imagenes);
                            viewPagerImagenes.add(item);
                        }
                    }
                    if (documentSnapshot.contains("caracteristicasTexto")) {
                        listaTextoCaracteristicas = (List<String>) documentSnapshot.get("caracteristicasTexto");
                    }

                    if (documentSnapshot.contains("colores")) {
                        colores = (List<String>) documentSnapshot.get("colores");
                        colores(colores);
                        nombreColor.setText(colores.get(0));
                    }
                    if (documentSnapshot.contains("imagenesColores1")) {
                        imagenesColores1 = (List<String>) documentSnapshot.get("imagenesColores1");
                    }
                    if (documentSnapshot.contains("imagenesColores2")) {
                        imagenesColores2 = (List<String>) documentSnapshot.get("imagenesColores2");
                    }
                    if (documentSnapshot.contains("imagenesColores3")) {
                        imagenesColores3 = (List<String>) documentSnapshot.get("imagenesColores3");
                    }
                    if (documentSnapshot.contains("imagenesColores4")) {
                        imagenesColores4 = (List<String>) documentSnapshot.get("imagenesColores4");
                    }
                    if (documentSnapshot.contains("imagenesColores5")) {
                        imagenesColores5 = (List<String>) documentSnapshot.get("imagenesColores5");
                    }
                    if (documentSnapshot.contains("imagenesColores6")) {
                        imagenesColores6 = (List<String>) documentSnapshot.get("imagenesColores6");
                    }
                    if (documentSnapshot.contains("nombreMoto")) {
                        nombre = documentSnapshot.get("nombreMoto").toString();
                    }
                    if (documentSnapshot.contains("carpeta1")) {
                        fabricante = documentSnapshot.get("carpeta1").toString();
                    }
                    if (documentSnapshot.contains("modelo")) {
                        modelo = documentSnapshot.get("modelo").toString();
                    }
                    if (documentSnapshot.contains("modelo2")) {
                        Object modelo2Objeto = documentSnapshot.get("modelo2");
                        if (modelo2Objeto != null && !modelo2Objeto.toString().isEmpty()) {
                            modelo2 = modelo2Objeto.toString();
                        }
                    }
                    if (documentSnapshot.contains("descripcion")) {
                        DescripcionMoto = documentSnapshot.get("descripcion").toString();
                    }
                    if (documentSnapshot.contains("precio")) {
                        precio = Integer.parseInt(documentSnapshot.get("precio").toString());
                    }
                    if (documentSnapshot.contains("precio2")) {
                        precio2 = Integer.parseInt(documentSnapshot.get("precio2").toString());
                    }
                    if (documentSnapshot.contains("nuevoValorDescuento")) {
                        nuevoValorDescuento = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                    }
                    if (documentSnapshot.contains("descuento")) {
                        descuento = Boolean.parseBoolean(documentSnapshot.get("descuento").toString());
                    }
                    if (documentSnapshot.contains("manualesArchivos")) {
                        archivosPDF = (List<String>) documentSnapshot.get("manualesArchivos");
                    }
                    if (documentSnapshot.contains("nombresArchivos")) {
                        nombreArchivosPDF = (List<String>) documentSnapshot.get("nombresArchivos");
                    }
                    if (documentSnapshot.contains("vistas")) {
                        numVisitas = Double.parseDouble(documentSnapshot.get("vistas").toString());
                    }
                    if (documentSnapshot.contains("busquedas")) {
                        numBusquedas = Double.parseDouble(documentSnapshot.get("busquedas").toString());
                    }
                    instanceSlider();
                    actualizarVistas();
                }
                if (nombreArchivosPDF == null || nombreArchivosPDF.isEmpty()) {
                    cdvManuales.setVisibility(View.GONE);
                }
                txtModelo.setText(modelo);
                txtFabricanteMoto.setText(fabricante);
                if (modelo2 != "") {
                    txtModelo2.setVisibility(View.VISIBLE);
                    txtModelo2.setText(modelo2);
                    txtModelo.setTextColor(Color.BLUE);
                }
                txtNomMoto.setText(nombre);
                if (DescripcionMoto.isEmpty()) {
                    txtDescripcionMoto.setVisibility(View.GONE);
                    txtTituloDescripcionMoto.setVisibility(View.GONE);
                }
                txtDescripcionMoto.setText(DescripcionMoto);
                //muestra el seperador de puntos en el precio

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');

                DecimalFormat formato = new DecimalFormat("#,###", symbols);
                String numeroFormato = formato.format(nuevoValorDescuento);
                txtPrecio.setText("$" + numeroFormato);
                if (descuento) {
                    String Formato = formato.format(precio);
                    lnlDescuento.setVisibility(View.VISIBLE);
                    txtprecioAnterior.setText("$" + Formato);
                    txtprecioAnterior.setPaintFlags(txtprecioAnterior.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    lnlDescuento.setVisibility(View.GONE);
                }
            }
        });
    }

    private void color(List<String> ArrayColores, int cont) {
        elemento = ArrayColores.get(cont).toUpperCase();
        if (elemento.startsWith("#")) {
            colorCirculos = Color.parseColor(elemento);
            lnlTextoColores.setVisibility(View.GONE);
        } else {
            Map<String, Integer> colorMap = new HashMap<>();
            colorMap.put("ROJO", R.color.red);
            colorMap.put("ROJA", R.color.red);
            colorMap.put("ROJO RACING", R.color.red);
            colorMap.put("RED", R.color.red);
            colorMap.put("ROJOBLANCO", R.color.red);
            colorMap.put("NEGRO NEBULOSA - GRIS ROJO", R.color.rojoNegro);
            colorMap.put("NEGRA - ROJA", R.color.rojoNegro);
            colorMap.put("NEGRO - GRIS ROJO", R.color.rojoNegro);
            colorMap.put("ROJO APACHE - GRIS NEGRO", R.color.rojoNegro);
            colorMap.put("NEGRO NEBULOSA - ROJO GRIS PLATA", R.color.rojoNegro);
            colorMap.put("AZUL PETROLEO - ROJO", R.color.rojoNegro);
            colorMap.put("ROJO-NEGRO", R.color.rojoNegro);
            colorMap.put("ROJONEGRO", R.color.rojoNegro);
            colorMap.put("NEGRO-ROJO", R.color.rojoNegro);
            colorMap.put("NEGROROJO", R.color.rojoNegro);
            colorMap.put("NEGRAROJA", R.color.rojoNegro);
            colorMap.put("TRICOLOR", R.color.rojoNegro);
            colorMap.put("ROJOGRISMATE", R.color.rojoNegro);

            colorMap.put("NEGRA", R.color.black);
            colorMap.put("BLACK", R.color.black);
            colorMap.put("NEGRO", R.color.black);
            colorMap.put("NEGRO NEBULOSA - GRIS DORADO", R.color.black);
            colorMap.put("NEGRO NEBULOSA", R.color.black);
            colorMap.put("NEGRA NEBULOSA", R.color.black);
            colorMap.put("NEGRO NEBULOSA - ROJO", R.color.black);
            colorMap.put("NEGRO NEBULOSA - VERDE", R.color.black);
            colorMap.put("NEGRO NEBULOSA - DORADO", R.color.black);
            colorMap.put("BLACK-RED", R.color.black);
            colorMap.put("ECOBLACK", R.color.black);
            colorMap.put("NEGRO GRIS", R.color.black);
            colorMap.put("NEGROGRIS", R.color.black);

            colorMap.put("AMARILLA", R.color.amarillo);
            colorMap.put("AMARILLO", R.color.amarillo);
            colorMap.put("YELLOW", R.color.amarillo);
            colorMap.put("NEGRO - VERDE", R.color.amarilloVerde);
            colorMap.put("GRIS CARBONO - VERDE", R.color.amarillo);

            colorMap.put("NEGRO NEBULOSA - CAMALEÓN", R.color.morado);
            colorMap.put("MORADO", R.color.morado);

            colorMap.put("AZUL PETROLEO - GRIS DORADO", R.color.azulPetroleo);
            colorMap.put("AZUL PETROLEO", R.color.azulPetroleo);
            colorMap.put("AZUL PETRÓLEO - GRIS DORADO", R.color.azulPetroleo);
            colorMap.put("AZUL MATE/NEGRO NEB - GRIS", R.color.azulPetroleo);
            colorMap.put("AZUL PETRÓLEO - GRIS ROJO", R.color.azulPetroleo);
            colorMap.put("NEGRAAZUL", R.color.azulPetroleo);
            colorMap.put("NEGRA - AZUL", R.color.azulPetroleo);
            colorMap.put("NEGROAZUL", R.color.azulPetroleo);
            colorMap.put("AZULMATE", R.color.azulPetroleo);
            colorMap.put("AZUL-ANTARTICA", R.color.azulPetroleo);

            colorMap.put("GRIS", R.color.grisOscuro);
            colorMap.put("GRAY", R.color.grisOscuro);
            colorMap.put("GRIS MATE", R.color.grisOscuro);
            colorMap.put("TOP FROST", R.color.grisOscuro);
            colorMap.put("TOP FROST - ROJO", R.color.grisOscuro);
            colorMap.put("GRIS CARBONO - AZUL MIAMI BLUE", R.color.grisOscuro);
            colorMap.put("GRIS CARBONO", R.color.grisOscuro);
            colorMap.put("TOP FROST/NEGRO NEB - GRIS", R.color.grisOscuro);
            colorMap.put("GRIS CARBONO - NEGRO DORADO", R.color.grisOscuro);
            colorMap.put("NEGRAGRIS", R.color.grisOscuro);
            colorMap.put("GRIS CALIFORNIA", R.color.grisOscuro);
            colorMap.put("NEGRO-GRIS", R.color.grisOscuro);
            colorMap.put("GRISMATE", R.color.grisOscuro);
            colorMap.put("AZULGRISMATE", R.color.grisOscuro);
            colorMap.put("GRIS PIEDRA", R.color.grisOscuro);




            colorMap.put("NARANJA", R.color.naranja);
            colorMap.put("REPSOL", R.color.naranja);

            colorMap.put("GRAY-GRAFITO", R.color.grisclaro);
            colorMap.put("TOP FROST - GRIS METALIZADO", R.color.grisclaro);
            colorMap.put("PLATA", R.color.grisclaro);

            colorMap.put("DORADO", R.color.dorado);
            colorMap.put("DORADA", R.color.dorado);
            colorMap.put("NEGRODORADO", R.color.dorado);
            colorMap.put("NEGRA MATE - DORADO", R.color.dorado);

            colorMap.put("VERDE", R.color.verde);
            colorMap.put("AZUL METALICO - GRIS VERDE", R.color.verde);
            colorMap.put("NEGRO MATE VERDE NEON", R.color.verde);
            colorMap.put("GREEN", R.color.verde);
            colorMap.put("GRISLIMA", R.color.verde);
            colorMap.put("VERDE-NEGRO", R.color.verde);


            colorMap.put("AZUL", R.color.azul);
            colorMap.put("BLUE", R.color.azul);
            colorMap.put("AZUL ARLEQUIN", R.color.azul);
            colorMap.put("AZUL MATE", R.color.azul);
            colorMap.put("NEGRO MATE AZUL MATE", R.color.azul);
            colorMap.put("AZUL MATE - GRIS VERDE", R.color.azul);
            colorMap.put("AZUL MATE - DORADO METALIZADO", R.color.azul);
            colorMap.put("AZUL ARLEQUÍN", R.color.azul);
            colorMap.put("GRIS CARBONO - AZUL", R.color.azul);
            colorMap.put("AZUL-NEGRO", R.color.azul);

            colorMap.put("BLANCO", R.color.white);
            colorMap.put("BLANCA", R.color.white);
            colorMap.put("BLANCO-AZUL", R.color.white);
            colorMap.put("WHITE-BLUE", R.color.white);
            lnlTextoColores.setVisibility(View.VISIBLE);

            Integer colorCirculo = colorMap.get(elemento);
            colorCirculos = ContextCompat.getColor(getContext(), colorCirculo);
        }


    }

    private void colores(List<String> ArrayColores) {
        CardView cardView = mview.findViewById(R.id.crdColores);
        LinearLayout linearLayout = mview.findViewById(R.id.lnlColores);

        int contador = 0;

        int baseSize = 100; // Tamaño base de los círculos
        int circleSize = 100;

        for (String color : ArrayColores) {

            if (!ArrayColores.get(contador).equals("")) {
                View colorCircle = new View(getContext());

                color(ArrayColores, contador);
                colorCircle.setId(contador);
                contador++;
                if (ArrayColores.size() <= 5) {
                    circleSize = baseSize;
                } else if (ArrayColores.size() <= 7) {
                    circleSize = baseSize - 10;
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(circleSize, circleSize);

                if (ArrayColores.size() <= 3) {
                    params.setMargins(30, 0, 50, 0);
                } else {
                    params.setMargins(15, 0, 15, 0);
                }

                colorCircle.setLayoutParams(params);
                ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
                shapeDrawable.getPaint().setColor(colorCirculos);

                if (colorCirculos == -1) {
                    shapeDrawable.getPaint().setStrokeWidth(3);
                    shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
                    shapeDrawable.getPaint().setStrokeJoin(Paint.Join.ROUND);
                    shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
                    shapeDrawable.getPaint().setColor(Color.BLACK);
                }
                colorCircle.setBackground(shapeDrawable);
                linearLayout.addView(colorCircle);
                colorCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = view.getId();
                        mostrarColores(id);
                    }
                });
            }
        }

    }

    private void mostrarColores(int id) {
        mSliderItems.clear();
        int controlador = 0;
        switch (id) {
            case 0:
                for (String imagen : listaImagenes) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    nombreColor.setText(colores.get(0));
                }
                break;
            case 1:
                for (String imagen : imagenesColores1) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 1;
                    nombreColor.setText(colores.get(1));
                }
                break;
            case 2:
                for (String imagen : imagenesColores2) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 2;
                    nombreColor.setText(colores.get(2));
                }
                break;
            case 3:
                for (String imagen : imagenesColores3) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 3;
                    nombreColor.setText(colores.get(3));
                }
                break;
            case 4:
                for (String imagen : imagenesColores4) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 4;
                    nombreColor.setText(colores.get(4));
                }
                break;
            case 5:
                for (String imagen : imagenesColores5) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 5;
                    nombreColor.setText(colores.get(5));
                }
                break;
            default:
                for (String imagen : imagenesColores6) {
                    SliderItem item = new SliderItem();
                    item.setImageurl(imagen);
                    mSliderItems.add(item);
                    controlador = 6;
                    nombreColor.setText(colores.get(6));
                }
                break;
        }
        instanceSlider();
    }

    private void modificarTamanoVistaMotos() {
        int newHeight = (int) getResources().getDimension(R.dimen.new_height);
        mSliderView.getLayoutParams().height = newHeight;
        mSliderView.requestLayout();
        frameContenedor.getLayoutParams().height = newHeight;
        frameContenedor.requestLayout();
        nestedScrollView.requestLayout();
    }

    private void modificarTamanoVistaMotos2(int agg) {
        int newHeight = (int) getResources().getDimension(R.dimen.new_height2) + agg;
        mSliderView.getLayoutParams().height = newHeight;
        mSliderView.requestLayout();
        frameContenedor.getLayoutParams().height = newHeight;
        frameContenedor.requestLayout();
        nestedScrollView.requestLayout();
    }
    private void mostrarManuales() {
        if (lnlInformacionAdd1.getVisibility() == View.VISIBLE) {
            lnlInformacionAdd1.setVisibility(View.GONE);
        } else {
            lnlInformacionAdd1.setVisibility(View.VISIBLE);
        }
    }

    private void actualizarVistas() {
        numVisitas = numVisitas + 1;
        Map<String, Object> updates = new HashMap<>();
        updates.put("vistas", numVisitas);
        if (busqueda) {
            numBusquedas = numBusquedas + 1;
            updates.put("busquedas", numBusquedas);
        }
        mPostProvider.updatePost(idDocument, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }


}