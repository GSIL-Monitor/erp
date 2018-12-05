package com.stosz.product.imageHashMatch.service;


import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductNewSiftImage;
import com.stosz.product.imageHashMatch.similarimage.SimilarImageSearch;
import com.stosz.product.service.CategoryService;
import com.stosz.product.service.ProductNewSiftImageService;
import com.stosz.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/6]
 */
@Service
public class HashMatchImageService {

    @Resource
    private ProductService productService;
    @Resource
    private ProductNewSiftImageService productNewSiftImageService;
    @Resource
    private CategoryService categoryService;
    @Value("${file.uploadDir}")
    private String filePath;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Async
    public void matchImageByCategory(ProductNew productNew) {
        Assert.notNull(productNew, "待比对的新品不允许为空！");
        Integer productNewId = productNew.getId();
        String imagePath = productNew.getMainImageUrl();
        Integer categoryId = productNew.getCategoryId();
        int matchCount = 0;
        Category category = categoryService.getById(categoryId);
        Assert.notNull(category, "没有找到该品类！");
        Integer parentId = category.getParentId();
        List<Product> productList = productService.findProductByCategoryId(categoryId);
        if (parentId != -1) {
            categoryId = parentId;
        }
        List<Product> productParentList = productService.findProductByCategoryId(categoryId);
        productList.addAll(productParentList);
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                String mainImageUrl = product.getMainImageUrl();
                if (StringUtils.isNotBlank(mainImageUrl)) {
                    //全部存库
                    Double similarity = SimilarImageSearch.getSimilarity(
                            filePath + imagePath, filePath + mainImageUrl);
                    if (similarity <= 0.0) {
                        continue;
                    }
                    Integer productId = product.getId();
                    ProductNewSiftImage productNewSiftImage = new ProductNewSiftImage();
                    productNewSiftImage.setProductId(productId);
                    productNewSiftImage.setProductNewId(productNewId);
                    productNewSiftImage.setSiftValue((int) (similarity * 1000));
                    productNewSiftImageService.insert(productNewSiftImage);
                    matchCount++;
                }
            }
        }
        logger.info("新品{}图片比对完成，比对结果有{}个相似产品!", productNew, matchCount);
    }


}  
