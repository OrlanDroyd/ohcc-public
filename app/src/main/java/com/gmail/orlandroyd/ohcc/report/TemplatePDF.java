package com.gmail.orlandroyd.ohcc.report;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.gmail.orlandroyd.ohcc.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OrlanDroyd on 25/05/19
 */
public class TemplatePDF {
    private static final String TAG = "TemplatePDF";
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument(String fileName) {
        createFile(fileName);
        try {
            document = new Document(PageSize.LETTER.rotate());
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        } catch (Exception e) {
            Log.e(TAG, "openDocument: ", e);
        }
    }

    private void createFile(String fileName) {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "ReportesOHCC");
        if (!folder.exists())
            folder.mkdir();
        pdfFile = new File(folder, fileName + ".pdf");
    }

    public void closeDocument() {
        document.close();
    }

    public void addMetaData(String tile, String subject, String author) {
        document.addTitle(tile);
        document.addTitle(subject);
        document.addTitle(author);
    }

    public void addTitles(String title, String subtitle, String date) throws DocumentException {
        paragraph = new Paragraph();
        addChild(new Paragraph(title, fTitle));
        addChild(new Paragraph(subtitle, fSubTitle));
        addChild(new Paragraph("Generado: " + date, fHighText));
        paragraph.setSpacingAfter(30);
        document.add(paragraph);
    }

    private void addChild(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void createTable(String[] header, ArrayList<String[]> body) throws DocumentException {
        paragraph = new Paragraph();
        paragraph.setFont(fText);
        PdfPTable pdfPTable = new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(10);
        PdfPCell pdfPCell;
        int indexC = 0;
        while (indexC < header.length) {
            pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GRAY);
            pdfPTable.addCell(pdfPCell);
        }
        for (int indexR = 0; indexR < body.size(); indexR++) {
            String[] row = body.get(indexR);
            for (indexC = 0; indexC < header.length; indexC++) {
                pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
            }
        }
        paragraph.add(pdfPTable);
        document.add(paragraph);
    }

    public void appViewPdf(Activity activity) {
        if (pdfFile.exists()) {
            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(), "No tienes una app para visualizar PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity.getApplicationContext(), "El archivo no se encontró", Toast.LENGTH_SHORT).show();

        }
    }
}
