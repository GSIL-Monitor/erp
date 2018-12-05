package com.stosz.tms.service.waybill;

import com.google.common.collect.Lists;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stosz.plat.utils.BarcodeUtils;
import com.stosz.tms.model.ShippingParcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public abstract class AbstractShippingBillHandler implements ShippingBillHandler{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected BaseFont defaultFont = null;

    protected Document document = null;

    protected PdfWriter pdfWriter = null;

    protected OutputStream os = null;

    protected List<Element> elementList = Lists.newArrayList();



    public AbstractShippingBillHandler() {
        try {
            this.defaultFont = BaseFont.createFont("/pdf/ttf/msyh.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            logger.error("创建字体失败",e);
            throw new RuntimeException(e);
        }
    }

    /**
     *  该面单处理器归属code
     * @return
     */
    public  abstract String ailasCode();

    /**
     * 处理器顺序，从小到大排序
     * @return
     */
    public abstract Integer handlerOrder();

    /**
     * 校验包裹数据
     * @param parcel
     * @return
     */
    protected  abstract void  validate(ShippingParcel parcel) throws Exception;

    /**
     * 处理包裹数据,生成PDF文件并输出到指定流中
     * @param parcel
     * @return
     */
    protected  abstract void  handler(ShippingParcel parcel, OutputStream os) throws Exception;


    @Override
    public void createShippingBill(ShippingParcel parcel, OutputStream os) throws Exception {
        validate(parcel);
        handler(parcel,os);
    }

    /**
     * 创建pdf文档
     */
    protected void createDocument(){
        createDocument(PDFPageSizeConstant.BASE_SIZE,0, 0, 0, 0);
    }

    protected void createDocument(Rectangle rectangle){
         createDocument(rectangle,0, 0, 0, 0);
    }

    protected void createDocument(Rectangle rectangle,float marginLeft, float marginRight, float marginTop, float marginBottom){
        this.document = new Document(rectangle,0,0,0,0);
    }

    /**
     * 开启文档
     */
    protected void documentOpen(){
        this.document.open();
    }

    /**
     * 关闭文档
     */
    protected void documentClose(){
        this.document.close();
    }

    protected void pdfWriterClose(){
        this.pdfWriter.close();
    }

    /**
     * 初始化PDF输出流,默认输出到内存中
     * @throws DocumentException
     */
    protected void createPdfWriter() throws DocumentException {
        createPdfWriter( new ByteArrayOutputStream());
    }

    /**
     * 初始化PDF输出流，输出到指定的流中
     * @param os
     * @throws DocumentException
     */
    protected void createPdfWriter(OutputStream os) throws DocumentException {
        this.os = os;
        if (document == null)
            createDocument();;
        pdfWriter = PdfWriter.getInstance(document, os);
        documentOpen();
    }


    /**
     * 根据指定参数创建Table,宽度默认100%,每列宽度相等
     * @param cloums
     * @return
     * @throws DocumentException
     */
    protected PdfPTable createTable(int cloums) throws DocumentException {
        return createTable(cloums,100);
    }

    /**
     * 根据指定参数创建Table,每列宽度相等
     * @param cloums 列数
     * @param widthPercentage 宽度百分比
     * @return
     * @throws DocumentException
     */
    protected PdfPTable createTable(int cloums,int widthPercentage) throws DocumentException {
        float[] columnWidths = new float[cloums];

        for (int i = 0 ; i<cloums;i++){
            columnWidths[i] = 1;
        }
        return createTable(cloums,widthPercentage,columnWidths);
    }

    /**
     * 根据指定参数创建Table
     * @param cloums 列数
     * @param widthPercentage 宽度百分比
     * @param columnWidths 每列宽度占比
     * @return
     * @throws DocumentException
     */
    protected PdfPTable createTable(int cloums,int widthPercentage,float[] columnWidths) throws DocumentException {
        PdfPTable table = new PdfPTable(cloums);
        table.setWidthPercentage(widthPercentage);
        table.setWidths(columnWidths);
        table.setTotalWidth(this.document.getPageSize().getWidth()/(100/widthPercentage));
        table.setLockedWidth(true);
        elementList.add(table);
        return table;
    }



    protected Image createImage(String filePath) throws IOException, BadElementException {
        return Image.getInstance(filePath);
    }

    protected Image createImage(String filePath,int width,int heigth) throws IOException, BadElementException {
        final Image image = createImage(filePath);
        image.scaleAbsolute(width,heigth);
        return image;
    }

    protected Image createImage(byte[] fileData) throws IOException, BadElementException {
        return Image.getInstance(fileData);
    }


    protected Image createImage(byte[] fileData,int width,int heigth) throws IOException, BadElementException {
        final Image image = createImage(fileData);
        image.scaleAbsolute(width,heigth);
        return image;
    }

    /**
     * 创建条形码
     * @param code
     * @return
     * @throws IOException
     * @throws BadElementException
     */
    protected Image createBarCodeImage(String code) throws IOException, BadElementException {
        final byte[] codeImageData = BarcodeUtils.generate(code);
        return createImage(codeImageData);
    }

    protected Image createBarCodeImage(String code,int width,int heigth) throws IOException, BadElementException {
        final byte[] codeImageData = BarcodeUtils.generate(code);
        return createImage(codeImageData,width,heigth);
    }

    protected Paragraph createParagraph(String content){
        return createParagraph(content,this.defaultFont,-1.0f);
    }

    protected Paragraph createParagraph(String content,BaseFont baseFont){
        return createParagraph(content,baseFont,-1.0f);
    }

    protected Paragraph createParagraph(String content,float fontSize){
        return createParagraph(content,this.defaultFont,fontSize);
    }

    protected Paragraph createParagraph(String content,BaseFont baseFont,float fontSize){
        return createParagraph(content,baseFont,fontSize,false);
    }

    protected Paragraph createParagraph(String content,float fontSize,boolean bold){
        return createParagraph(content,this.defaultFont,fontSize,bold);
    }

    protected Paragraph createParagraph(String content,BaseFont baseFont,float fontSize,boolean bold){
        final Font font = new Font(baseFont, fontSize);
        if (bold)
            font.setStyle(Font.BOLD);
        final Paragraph paragraph = new Paragraph(content, font);

        return paragraph;
    }

    protected Paragraph createParagraph(Chunk chunk){
        return new Paragraph(chunk);
    }





    protected Chunk createChunk(String content,float thickness){
        return createChunk(content,thickness,this.defaultFont,-1f);
    }

    protected Chunk createChunk(String content,float thickness,BaseFont baseFont){
        return createChunk(content,thickness,baseFont,-1f);
    }
    protected Chunk createChunk(String content,float thickness,float fontSize){
        return createChunk(content,thickness,this.defaultFont,fontSize);
    }

    protected Chunk createChunk(String content,float thickness,BaseFont baseFont,float fontSize){
        return createChunk(content,thickness,baseFont,fontSize,false);
    }
    protected Chunk createChunk(String content,float thickness,float fontSize,boolean bold){
        return createChunk(content,thickness,this.defaultFont,fontSize,bold);
    }

    /**
     *  创建一个带下划线的块对象
     * @param content 内容
     * @param thickness 下划线的厚度
     * @param baseFont
     * @param fontSize
     * @param bold
     * @return
     */
    protected Chunk createChunk(String content,float thickness, BaseFont baseFont,float fontSize,boolean bold){
        final Font font = new Font(baseFont, fontSize);
        if (bold)
            font.setStyle(Font.BOLD);
        Chunk chunk = new Chunk(content,font);
        chunk.setUnderline(thickness,-1f);
        chunk.setTextRise(1f);
        return chunk;
    }



    protected PdfPCellBuilder createCell(Paragraph paragraph){
        return new PdfPCellBuilder(paragraph);
    }

    protected PdfPCellBuilder createCell(Image image){
        return new PdfPCellBuilder(image);
    }


    /**
     * 建造PDF文件元素
     */
    protected void buildDocumentElement(){
        elementList.forEach(e -> {
            try {
                this.document.add(e);
            } catch (DocumentException e1) {
                throw new RuntimeException(e1);
            }
        });
        documentClose();
        pdfWriterClose();
    }


    public class PdfPCellBuilder {

        private PdfPCell cell;

        public PdfPCellBuilder(Paragraph paragraph){
            this.cell = new PdfPCell(paragraph);
        }

        public PdfPCellBuilder(Image image){
            this.cell = new PdfPCell(image);
        }

        /**
         * 设置边框颜色
         * @param color
         * @return
         */
        public PdfPCellBuilder borderColor(BaseColor color){
            this.cell.setBorderColor(color);
            return this;
        }

        /**
         * 设置边框宽度
         * @return
         */
        public PdfPCellBuilder border(int border){
            this.cell.setBorder(border);
            return this;
        }

        /**
         * 设置内容横向对其方式，值应为  Element.ALIGN_LEFT/Element.ALIGN_RIGHT/Element.ALIGN_CENTER
         * @param horizontalAlignment
         * @return
         */
        public PdfPCellBuilder horizontalAlignment(int  horizontalAlignment){
            this.cell.setHorizontalAlignment(horizontalAlignment);
            return this;
        }
        /**
         * 设置内容竖向对其方式，值应为  Element.ALIGN_TOP/Element.ALIGN_BOTTOM/Element.ALIGN_MIDDLE
         * @param verticalAlignment
         * @return
         */
        public PdfPCellBuilder verticalAlignment(int  verticalAlignment){
            this.cell.setVerticalAlignment(verticalAlignment);
            return this;
        }


        /**
         * 设置内容溢出不进行换行
         * @return
         */
        public PdfPCellBuilder noWrap(boolean  noWrap){
            this.cell.setNoWrap(noWrap);
            return this;
        }


        /**
         * 设置内容最小高度
         * @return
         */
        public PdfPCellBuilder minimumHeight(float minimumHeight){
            this.cell.setMinimumHeight(minimumHeight);
            return this;
        }


        /**
         * 设置固定高度
         * @param fixedHeight
         * @return
         */
        public PdfPCellBuilder fixedHeight(float fixedHeight){
            this.cell.setFixedHeight(fixedHeight);
            return this;
        }

        /**
         * 设置内容离左边框距离宽度
         * @return
         */
        public PdfPCellBuilder paddingLeft(float paddingLeft){
            this.cell.setPaddingLeft(paddingLeft);
            return this;
        }
        /**
         * 设置内容离上边框距离宽度
         * @return
         */
        public PdfPCellBuilder paddingTop(float paddingTop){
            this.cell.setPaddingTop(paddingTop);
            return this;
        }
        /**
         * 设置内容离右边框距离宽度
         * @return
         */
        public PdfPCellBuilder paddingRight(float paddingRight){
            this.cell.setPaddingRight(paddingRight);
            return this;
        }

        /**
         * 设置内容离下边框距离宽度
         * @return
         */
        public PdfPCellBuilder paddingBottom(float paddingBottom){
            this.cell.setPaddingBottom(paddingBottom);
            return this;
        }

        /**
         * 设置内容离全部边框距离宽度
         * @return
         */
        public PdfPCellBuilder padding(float paddind){
            this.cell.setPadding(paddind);
            return this;
        }

        /**
         * 设置列的单元跨度
         * @return
         */
        public PdfPCellBuilder colspan(int colspan){
            this.cell.setColspan(colspan);
            return this;
        }
        /**
         * 设置列的行跨度
         * @return
         */
        public PdfPCellBuilder rowspan(int rowspan){
            this.cell.setRowspan(rowspan);
            return this;
        }

        /**
         * 获取列对象
         * @return
         */
        public PdfPCell cell() {
            return this.cell;
        }
    }


}
