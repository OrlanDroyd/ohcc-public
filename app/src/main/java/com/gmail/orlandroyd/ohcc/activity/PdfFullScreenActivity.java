package com.gmail.orlandroyd.ohcc.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.util.ResourcesUtil;

import androidx.appcompat.app.AppCompatActivity;

public class PdfFullScreenActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressDialog dialog;
    private String TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_full_screen);
        TITLE = getIntent().getStringExtra("TITLE");
        int mode = getIntent().getIntExtra("MODE", -1);
        String asset;

        if (mode == 0) {//Books -> Index
            int[] pages = ResourcesUtil.getPagesByTitle(TITLE);
            asset = ResourcesUtil.getAssetByTitleIndex(TITLE);
            loadPDF(asset, pages);
        } else if (mode == 1) {// Library
            asset = ResourcesUtil.getAssetByTitle(TITLE);
            loadPDF(asset);
        } else {//Error
            Toast.makeText(this, "ERROR al abrir el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPDF(String asset) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.setCancelable(false);
        dialog.show();

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(asset)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        dialog.dismiss();
                    }
                })
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .scrollHandle(new DefaultScrollHandle(getApplicationContext())) // make the  scroll indicator visible
                .load();
    }

    private void loadPDF(String asset, int[] pages) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.setCancelable(false);
        dialog.show();

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(asset)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        dialog.dismiss();
                    }
                })
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .scrollHandle(new DefaultScrollHandle(getApplicationContext())) // make the  scroll indicator visible
                .pages(pages)
                .load();

    }
}
