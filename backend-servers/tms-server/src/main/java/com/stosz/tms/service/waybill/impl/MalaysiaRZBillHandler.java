package com.stosz.tms.service.waybill.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.service.waybill.AbstractShippingBillHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  马来西亚RZ物流面单
 */
@Service
public class MalaysiaRZBillHandler extends AbstractShippingBillHandler {

    @Override
    public String ailasCode() {
        return "malaysia_rz";
    }

    @Override
    public Integer handlerOrder() {
        return 1;
    }


    @Override
    public void validate(ShippingParcel parcel) throws Exception {
        Assert.notNull(parcel.getTrackNo(),"包裹运单号为空");
        Assert.notNull(parcel.getTelphone(),"包裹手机号为空");
        Assert.notNull(parcel.getZipcode(),"包裹邮编为空");
        Assert.notNull(parcel.getCountry(),"包裹国家为空");
        Assert.notNull(parcel.getProvince(),"包裹省份为空");
        Assert.notNull(parcel.getCity(),"包裹城市为空");
        Assert.notNull(parcel.getAddress(),"包裹详细地址为空");
        Assert.notNull(parcel.getOrderAmount(),"包裹订单金额为空");
    }

    @Override
    protected void handler(ShippingParcel parcel, OutputStream os) throws Exception {
        createPdfWriter(os);

        final PdfPTable logTable = createTable(2, 100, new float[]{43, 57});
        //log列
        final Image logImage = createImage(   this.getClass().getResource("/pdf/image/malaysia_rz_logo.png").getFile().toString(), 41, 21);
        logTable.addCell(createCell(logImage).border(0).paddingLeft(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).cell());

        //条形码列
        final Image barCodeImage = createBarCodeImage(parcel.getTrackNo(), 54, 15);
        logTable.addCell(createCell(barCodeImage).border(0).paddingLeft(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).cell());

        final PdfPTable table = createTable(2, 100);


        //名称列
        table.addCell(createCell(createParagraph("Name:"+parcel.getFirstName()+" "+parcel.getLastName(),5))
                    .borderColor(BaseColor.BLACK).paddingLeft(1).horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
                    .noWrap(true).colspan(2).cell());

        //手机号码列
        table.addCell(createCell(createParagraph("Tel:"+parcel.getTelphone(),5))
            .borderColor(BaseColor.BLACK).paddingLeft(1).horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
            .noWrap(true).cell());

        //邮编列
        table.addCell(createCell(createParagraph("Post Code:"+parcel.getZipcode(),5))
                .borderColor(BaseColor.BLACK).paddingLeft(1).horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
                .noWrap(true).cell());

        final String address = parcel.getCountry() + " " + parcel.getProvince() + " " + parcel.getCity() + " " + parcel.getAddress();

        //地址列
        table.addCell(createCell(createParagraph("Add:\r\n   "+address,5)).
                 colspan(2).fixedHeight(24).borderColor(BaseColor.BLACK).paddingLeft(1)
                 .horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_TOP).cell());

        //CS列
        table.addCell(createCell(createParagraph("CS:customerservice@malaysiacuckoo.com",4))
                .borderColor(BaseColor.BLACK).padding(0.5f).colspan(2).noWrap(true).minimumHeight(6)
                .horizontalAlignment(Element.ALIGN_CENTER).verticalAlignment(Element.ALIGN_MIDDLE).cell());


        final PdfPTable codTable = createTable(2, 100, new float[]{6.5f, 3.5f});

        codTable.addCell(createCell(createParagraph("COD：RM "+
                new DecimalFormat("#.##").format(parcel.getOrderAmount().stripTrailingZeros()),8,true))
                .borderColor(BaseColor.BLACK).paddingLeft(1).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).noWrap(true).cell());
        codTable.addCell(createCell(createParagraph("Deliver by:",4,true))
                .borderColor(BaseColor.BLACK).paddingLeft(1).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_TOP).noWrap(true).cell());

        //TODO 后续更改，值为sku和货位号
        codTable.addCell(createCell(createParagraph("69100012330\r\n\r\nAI-02-04",4))
                .border(0).paddingLeft(5).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).cell());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        codTable.addCell(createCell(createParagraph(df.format(new Date())+"\r\n\r\n"+"1/1",4))
                .border(0).paddingTop(0).paddingRight(5).horizontalAlignment(Element.ALIGN_RIGHT)
                .verticalAlignment(Element.ALIGN_MIDDLE).cell());

        buildDocumentElement();
    }
}
