package com.stosz.tms.service.waybill.impl;

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
 * 马来西亚GDEX物流面单第一页
 */
@Service
public class MalaysiaGDEXBillPage1Handler extends AbstractShippingBillHandler {
    @Override
    public String ailasCode() {
        return "malaysia_gdex";
    }

    @Override
    public Integer handlerOrder() {
        return 1;
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


        final PdfPTable table = createTable(2,100,new float[]{55,45});

        table.addCell(createCell(createParagraph("GDEX ACC 1660504",3.5f,true))
                .border(0).paddingLeft(2).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_TOP).fixedHeight(6).colspan(2).cell());

        //收件人列
        table.addCell(createCell(createParagraph("FROM:gdex LOGISTICS",3.5f))
                .horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
                .border(0).paddingLeft(4).paddingTop(0).noWrap(true).cell());

        table.addCell(createCell(createParagraph("T: 075522310815",3.5f))
                .horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
                .border(0).paddingLeft(0).paddingTop(0).noWrap(true).cell());

        table.addCell(createCell(createParagraph("NO.1,Yanshan Road,Songgang,Baoan,Shenzhen,China",3.6f,true))
                .noWrap(true).colspan(2).horizontalAlignment(Element.ALIGN_CENTER).verticalAlignment(Element.ALIGN_MIDDLE)
                .border(0).paddingLeft(3f).paddingTop(0).cell());

        table.addCell(createCell(createParagraph("CS：custmoerservice@malaysiacuckoo.com",4,true))
                .noWrap(true).colspan(2).horizontalAlignment(Element.ALIGN_LEFT).verticalAlignment(Element.ALIGN_MIDDLE)
                .border(0).paddingLeft(4f).paddingTop(0).cell());


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


        //金额表格
        final PdfPTable codTable = createTable(3, 100, new float[]{30, 50, 20});

        codTable.addCell(createCell(createParagraph("COD MYR:",5,true))
            .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
            .verticalAlignment(Element.ALIGN_BOTTOM).paddingLeft(4).paddingTop(2).cell());


        final PdfPCellBuilder amountCellBuilder = createCell(createParagraph(createChunk("  " + new DecimalFormat("#.##").format(parcel.getOrderAmount().stripTrailingZeros()),
                0.1f, 8f, true)))
                .border(0).noWrap(true).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_BOTTOM).fixedHeight(15).paddingLeft(0).colspan(1).paddingTop(2);


        if (parcel.getProductType() == 2){
            codTable.addCell(amountCellBuilder.cell());
            codTable.addCell(createCell(createImage(this.getClass().getResource("/pdf/image/malaysia_gdex_t.png").getFile().toString(), 15, 15))
                    .paddingRight(5).border(0).cell());
        }else{
            amountCellBuilder.colspan(2);
            codTable.addCell(amountCellBuilder.cell());
        }

        //TODO 后续进行修改，值为旧订单号，如为空则不显示
        String oldOrderId = " 111111";

        codTable.addCell(createCell(createParagraph(oldOrderId,3))
                .border(0).paddingLeft(15).paddingTop(1).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).colspan(3).cell());


        final PdfPTable bottomTable = createTable(5, 100, new float[]{15, 35, 25, 15, 10});

        bottomTable.addCell(createCell(createParagraph("PSC:1",3))
                .border(0).paddingLeft(4).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());

        //TODO 后续修改，使用货位号
        bottomTable.addCell(createCell(createParagraph("PK4564684654654",3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).noWrap(true).paddingBottom(0).cell());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        bottomTable.addCell(createCell(createParagraph(df.format(new Date()),3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());


        bottomTable.addCell(createCell(createParagraph("1/1",3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());

        bottomTable.addCell(createCell(createParagraph("1",3))
                .border(0).paddingTop(0).horizontalAlignment(Element.ALIGN_LEFT)
                .verticalAlignment(Element.ALIGN_MIDDLE).paddingBottom(0).cell());


        buildDocumentElement();
    }
}
