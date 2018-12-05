package com.stosz.tms.service.waybill.impl;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.service.waybill.AbstractShippingBillHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * 马来西亚GDEX物流面单第二页
 */

@Service
public class MalaysiaGDEXBillPage2Handler extends AbstractShippingBillHandler {

    @Override
    public String ailasCode() {
        return "malaysia_gdex";
    }

    @Override
    public Integer handlerOrder() {
        return 2;
    }

    @Override
    protected void validate(ShippingParcel parcel) throws Exception {
        Assert.notNull(parcel.getTrackNo(),"包裹运单号为空");
        Assert.notNull(parcel.getTelphone(),"包裹手机号为空");
        Assert.notNull(parcel.getZipcode(),"包裹邮编为空");
        Assert.notNull(parcel.getCountry(),"包裹国家为空");
        Assert.notNull(parcel.getProvince(),"包裹省份为空");
        Assert.notNull(parcel.getCity(),"包裹城市为空");
        Assert.notNull(parcel.getAddress(),"包裹详细地址为空");
        Assert.notNull(parcel.getOrderAmount(),"包裹订单金额为空");
        Assert.notNull(parcel.getProductType(),"包裹发货类型为空");
    }

    @Override
    protected void handler(ShippingParcel parcel, OutputStream os) throws Exception {
        createPdfWriter(os);

        final PdfPTable logTable = createTable(2, 100, new float[]{32, 62});
        //log列
        final Image logImage = createImage(this.getClass().getResource("/pdf/image/malaysia_gdex_log.png").getFile().toString(), 22, 15);
        logTable.addCell(createCell(logImage).border(0).paddingLeft(0).horizontalAlignment(Element.ALIGN_CENTER)
                .verticalAlignment(Element.ALIGN_MIDDLE).fixedHeight(18).cell());

        //条形码列
        final Image barCodeImage = createBarCodeImage(parcel.getTrackNo(), 57, 15);
        logTable.addCell(createCell(barCodeImage).border(0).paddingLeft(0).paddingTop(0).horizontalAlignment(Element.ALIGN_CENTER)
                .verticalAlignment(Element.ALIGN_MIDDLE).fixedHeight(18).cell());

        logTable.addCell(createCell(createParagraph("GDEX ACC 1660504",3.5f,true))
                .border(0).paddingLeft(2).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_TOP).fixedHeight(6).colspan(2).cell());


        final PdfPTable toTable = createTable(2, 100, new float[]{10, 90});

        toTable.addCell(createCell(createParagraph("TO:",3.5f))
                .noWrap(true).border(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_TOP).paddingLeft(4f).paddingTop(0).cell());

        final String address = parcel.getCountry() + " " + parcel.getProvince() + " " + parcel.getCity() + " " + parcel.getAddress();

        toTable.addCell(createCell(createParagraph("-------"+address,3.5f))
                .paddingLeft(1).fixedHeight(20).border(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_TOP).paddingTop(1).cell());

        final PdfPTable fromTable = createTable(2,100,new float[]{62,38});


        //收货人列
        fromTable.addCell(createCell(createParagraph("CONTACT: "+parcel.getFirstName()+" "+parcel.getLastName(),3.5f))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(4).paddingTop(0).paddingTop(0).cell());

        //手机号码
        fromTable.addCell(createCell(createParagraph("TEL: "+parcel.getTelphone(),3.5f))
                .border(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(0).paddingTop(0).noWrap(true).cell());

        //国家列
        fromTable.addCell(createCell(createParagraph("COUNTRY: "+parcel.getCountry(),3.5f))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingTop(0).paddingLeft(4).cell());

        //邮编列
        fromTable.addCell(createCell(createParagraph("POSTCODE: "+parcel.getZipcode(),3.5f))
                .border(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(0).paddingTop(0).noWrap(true).cell());


        final PdfPTable codTable = createTable(2, 100, new float[]{35, 75});

        codTable.addCell(createCell(createParagraph("COD MYR:",5,true))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_BOTTOM).paddingLeft(4).paddingTop(2).cell());

        codTable.addCell(createCell(createParagraph(createChunk("  " + new DecimalFormat("#.##").format(parcel.getOrderAmount().stripTrailingZeros()),
                0.1f, 8f, true)))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_BOTTOM).fixedHeight(15).paddingLeft(0).colspan(1).paddingTop(2).cell());

        final PdfPTable receiverNameTable = createTable(2, 100, new float[]{40.5f, 59.5f});

        receiverNameTable.addCell(createCell(createParagraph("RECEIVER NAME:",4))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT).paddingBottom(1)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(4).paddingTop(2).cell());

        receiverNameTable.addCell(createCell(createParagraph("____________________",4))
                .border(0).noWrap(true).paddingLeft(0).paddingTop(2).paddingBottom(1)
                .horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_BOTTOM).cell());

        final PdfPTable signatureTable = createTable(3, 100, new float[]{30, 50, 20});

        signatureTable.addCell(createCell(createParagraph("SIGNATURE:",4))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT).paddingBottom(1)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(4).paddingTop(2).cell());


        final PdfPCellBuilder cellBuilder = createCell(createParagraph("__________________________", 4))
                .border(0).noWrap(true).paddingLeft(0).paddingTop(2).fixedHeight(5).paddingBottom(1)
                .horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_BOTTOM);

        if (parcel.getProductType() == 2){
            signatureTable.addCell(cellBuilder.cell());
            signatureTable.addCell(createCell(createImage(this.getClass().getResource("/pdf/image/malaysia_gdex_t.png").getFile().toString(), 15, 15))
                    .paddingRight(5).rowspan(2).border(0).cell());
            signatureTable.addCell(createCell(createParagraph("NRC NO:",4))
                    .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT).colspan(3)
                    .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(4).paddingTop(2).cell());
        }else{
            cellBuilder.colspan(2);
            signatureTable.addCell(cellBuilder.cell());
            signatureTable.addCell(createCell(createParagraph("NRC NO:",4))
                    .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT).colspan(3).paddingBottom(0)
                    .verticalAlignment(Element.ALIGN_MIDDLE).paddingLeft(4).paddingTop(2).cell());
        }

        final PdfPTable bottomTable = createTable(3, 100, new float[]{75, 15, 10});

        bottomTable.addCell(createCell(createParagraph("DATE&TIME:",4))
                .border(0).paddingLeft(4).paddingTop(2).horizontalAlignment(Element.ALIGN_LEFT).paddingBottom(0)
                .verticalAlignment(Element.ALIGN_MIDDLE).noWrap(true).cell());

        bottomTable.addCell(createCell(createParagraph("1/1",3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT).paddingBottom(0)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());

        bottomTable.addCell(createCell(createParagraph("1",3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT).paddingBottom(0)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());




        buildDocumentElement();
    }
}
