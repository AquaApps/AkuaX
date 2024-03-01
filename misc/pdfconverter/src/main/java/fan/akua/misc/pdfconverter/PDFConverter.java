package fan.akua.misc.pdfconverter;

import android.graphics.pdf.PdfDocument;
import android.graphics.Canvas;
import android.print.PrintAttributes;
import android.view.View;

import androidx.annotation.NonNull;

public class PDFConverter {
    public static PdfDocument createPDFFromView(@NonNull View content) {
        return createPDFFromView(content, PrintAttributes.MediaSize.ISO_A4.getWidthMils() * 72 / 1000,
                PrintAttributes.MediaSize.ISO_A4.getHeightMils() * 72 / 1000, 1);
    }

    public static PdfDocument createPDFFromView(@NonNull View content, int pageWidth, int pageHeight, int pageNumber) {
        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber)
                .create();

        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        content.draw(canvas);

        document.finishPage(page);

        return document;
    }

}
