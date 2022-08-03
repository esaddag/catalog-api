package com.example.catalogapi.utils;


import com.example.catalogapi.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK;


@Slf4j
public class ExcelUtils {

    private XSSFWorkbook workbook;
    private XSSFSheet worksheet;

    public List<Product> importExcel(MultipartFile readExcelDataFile){
        List<Product> tempProductList = new ArrayList<>();
        try {
            workbook = new XSSFWorkbook(readExcelDataFile.getInputStream());
        } catch (IOException e) {
            log.error("Hatalı excel dosyası!");
        }
         worksheet = workbook.getSheetAt(2);
        int i=1;
        try{

            for(i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                Product tempProduct;

                XSSFRow row = worksheet.getRow(i);

                try{
                    tempProduct = new Product(
                            row.getCell(0, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(1, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(2, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(3, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(4, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(5, CREATE_NULL_AS_BLANK).toString(),
                            row.getCell(6, CREATE_NULL_AS_BLANK).getStringCellValue(),
                            row.getCell(7, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(8, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(9, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(10, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(11, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(12, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(13, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(14, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            row.getCell(15, CREATE_NULL_AS_BLANK).getNumericCellValue(),
                            "",
                            0
                    );
                    tempProductList.add(tempProduct);
                }catch (Exception e){
                    log.error("Hatalı ürün bilgisi: " + row.getCell(5));
                }
            }

        }catch(Exception e){
            log.error("line"+i);
            e.printStackTrace();
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempProductList;
    }

    /*public void exportExcel(List<Product> productList, OutputStream outputStream){
        workbook = new XSSFWorkbook();
        int rowCount = 0;

        worksheet = workbook.createSheet("Ürünler");

        Row headerRow = worksheet.createRow(0);
        rowCount++;
        createCell(headerRow, 0, "No");
        createCell(headerRow, 1, "Kategori");
        createCell(headerRow, 2, "Alt Kategori");
        createCell(headerRow, 3, "Üretici");
        createCell(headerRow, 4, "Ürün Grubu");
        createCell(headerRow, 5, "Ürün Tipi");
        createCell(headerRow, 6, "Ürün Adı");
        createCell(headerRow, 7, "Ürün Kodu");
        createCell(headerRow, 8, "Ölçü");
        createCell(headerRow, 9, "Renk");
        createCell(headerRow, 10, "Giriş Fiyatı");
        createCell(headerRow, 11, "Eklenme Tarihi");
        createCell(headerRow, 12, "Güncellenme Tarihi");

        for(Product product: productList){
            Row row = worksheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, product.getId());
            createCell(row, columnCount++, product.getCategory());
            createCell(row, columnCount++, product.getSubCategory());
            createCell(row, columnCount++, product.getManufacturer());
            createCell(row, columnCount++, product.getProductGroup());
            createCell(row, columnCount++, product.getProductType());
            createCell(row, columnCount++, product.getProductName());
            createCell(row, columnCount++, product.getProductCode());
            createCell(row, columnCount++, product.getSize());
            createCell(row, columnCount++, product.getColor());
            createCell(row, columnCount++, product.getPurchasePrice());
            createCell(row, columnCount++, product.getEntryTime());
            createCell(row, columnCount, product.getUpdateTime());

        }

        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createCell(Row row, int columnCount, Object value) {
        worksheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else if (value instanceof Timestamp) {
            cell.setCellValue((Timestamp) value);
        }else {
            cell.setCellValue((String) value);
        }
    }*/
}
