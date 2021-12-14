package com.simpledev.tools.excel_action;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;

public class DoExcel2 {
    public static void main(String ... args) throws Exception {
        new DoExcel2().start();
    }

    public void start() throws Exception {

        File outFile = new File("C:\\Users\\Lenovo\\Desktop\\ga\\1.xlsx");
        ZipSecureFile.setMinInflateRatio(0);
        Workbook outWb = WorkbookFactory.create(outFile);
        outWb.createSheet();



        String inFileRootPath = "C:\\Users\\Lenovo\\Desktop\\0922";
        File dir = new File(inFileRootPath);
        File[] inFiles = dir.listFiles();
        String val;


        Workbook wb1 = WorkbookFactory.create(new File("C:\\Users\\Lenovo\\Desktop\\0922\\1.xlsx"));
        Workbook wb2 = WorkbookFactory.create(new File("C:\\Users\\Lenovo\\Desktop\\0922\\2.xlsx"));
        Workbook wb3 = WorkbookFactory.create(new File("C:\\Users\\Lenovo\\Desktop\\0922\\3.xlsx"));

        int j=0;

        for (File file : inFiles) {
            System.out.println("---1--" + file.getName());
            ZipSecureFile.setMinInflateRatio(0);
            //Workbook wb = WorkbookFactory.create(file);
//            int sheetCnt1 = wb.getNumberOfSheets();
//
//            for (int i = 0; i < sheetCnt1; i++) {
//                Sheet sheet = wb.getSheetAt(i);
//                System.out.println("===2" + sheet.getSheetName());
//                int cnt = 0;
//                for (Row row : sheet) {
//                    cnt++;
//                    if (cnt == 1) continue;
//                    Cell cell = row.getCell(1);
//                    val = cell.getStringCellValue();
//                    outWb.getSheetAt(0).createRow(j).createCell(0);
//                    outWb.getSheetAt(0).getRow(j).getCell(0).setCellValue(val);
//                    j++;
//                }
//            }
//            wb.close();
        }
        outWb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\ga\\2.xls"));
        outWb.close();
    }
}
