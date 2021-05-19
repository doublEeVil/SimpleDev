package com.simpledev.tools.excel_action;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestExcel {
    public static void main(String ... args) throws IOException {
        new TestExcel().action();
    }

    public void action() throws IOException {
        String filePath = "C:\\Users\\Lenovo\\Desktop\\02_大数据党支部党员信息花名册（现）.xls";
        File file = new File(filePath);
        ZipSecureFile.setMinInflateRatio(0);
        Workbook wb = WorkbookFactory.create(file);
        int sheetCnt = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCnt; i++) {
            Sheet sheet = wb.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            System.out.printf("sheetName: %s\n", sheet);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    ///
                    // System.out.printf("%s\n", cell);
                }
                int start = row.getFirstCellNum();
                int end = row.getLastCellNum();

                int rowNum = row.getRowNum();
                System.out.printf("rowNum:%s firstCellNum:%s endCellNum:%s \n", rowNum, start, end);

                if (rowNum >= 2) {
                    Cell cell = row.createCell(12);
                    cell.setCellValue(row.getCell(1).toString() + row.getCell(10));
                }
            }
        }
        wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\02_bak.xls"));
    }
}
