package com.simpledev.tools.excel_action;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DoExcel {
    public static void main(String ... args) throws Exception {
        new DoExcel().test();
    }

    public void action() throws IOException {
        String outFilePath = "C:\\Users\\Lenovo\\Desktop\\444\\1.xlsx";
        Workbook outWb = WorkbookFactory.create(new File(outFilePath));
        outWb.createSheet();

        String inFileRootPath = "C:\\Users\\Lenovo\\Desktop\\赣州市劳务实名制管理子系统信息";
        File dir = new File(inFileRootPath);
        File[] inFiles = dir.listFiles();
        String val;
        int rowCnt = 0;
        Set<String> set = new HashSet<>(20_0000);
        for (File file : inFiles) {
            ZipSecureFile.setMinInflateRatio(0);
            Workbook wb = WorkbookFactory.create(file);
            int sheetCnt = wb.getNumberOfSheets();
            for (int i = 0; i < sheetCnt; i++) {
                Sheet sheet = wb.getSheetAt(i);
                //String sheetName = sheet.getSheetName();
                //System.out.printf("sheetName: %s\n", sheet);
                int cnt = 0;
                for (Row row : sheet) {
                    cnt++;
                    if (cnt <= 3) continue;
                    val = row.getCell(3).getStringCellValue();
                    if (set.contains(val)) {
                        continue;
                    }
                    set.add(val);
                    outWb.getSheetAt(0).createRow(rowCnt++).createCell(0).setCellValue(val);
                }
            }
            wb.close();
        }

        outWb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\444\\2.xlsx"));

    }

    public void action2() throws Exception {

        // 未接种
        String jzInfoFilePath = "C:\\Users\\Lenovo\\Desktop\\0916\\工地实名\\ls20210916-未接种.xlsx";
        ZipSecureFile.setMinInflateRatio(0);
        Workbook jzWb = WorkbookFactory.create(new File(jzInfoFilePath));
        int sheetCnt = jzWb.getNumberOfSheets();

        Map<String, Row> jzMap = new HashMap<>(50000);
        for (int i = 0; i < sheetCnt; i++) {
            Sheet sheet = jzWb.getSheetAt(i);
            //String sheetName = sheet.getSheetName();
            //System.out.printf("sheetName: %s\n", sheet);
            int cnt = 0;
            for (Row row : sheet) {
                cnt++;
                if (cnt <= 1) {
                    continue;
                }
                jzMap.put(row.getCell(0).getStringCellValue(), row);
            }
        }

        // 写数据
        String inFileRootPath = "C:\\Users\\Lenovo\\Desktop\\赣州市劳务实名制管理子系统信息 - 副本";
        File dir = new File(inFileRootPath);
        File[] inFiles = dir.listFiles();
        String val;
        int rowCnt = 0;
        for (File file : inFiles) {
            ZipSecureFile.setMinInflateRatio(0);
            Workbook wb = WorkbookFactory.create(file);
            int sheetCnt1 = wb.getNumberOfSheets();
            for (int i = 0; i < sheetCnt1; i++) {
                Sheet sheet = wb.getSheetAt(i);
                //String sheetName = sheet.getSheetName();
                //System.out.printf("sheetName: %s\n", sheet);
                int cnt = 0;
                for (Row row : sheet) {
                    cnt++;
                    if (cnt < 3) continue;
                    if (cnt == 3) {
                        row.createCell(9).setCellValue("是否接种疫苗");
                        row.createCell(10).setCellValue("健康码-联系方式");
                        row.createCell(11).setCellValue("健康码-县");
                        row.createCell(12).setCellValue("健康码-镇");
                        row.createCell(13).setCellValue("健康码-村居");
                        row.createCell(14).setCellValue("健康码-详细地址");

                        row.createCell(15).setCellValue("网格化-联系方式");
                        row.createCell(16).setCellValue("网格化-县");
                        row.createCell(17).setCellValue("网格化-镇");
                        row.createCell(18).setCellValue("网格化-村居");
                        row.createCell(19).setCellValue("网格化-详细地址");
                        row.createCell(20).setCellValue("网格化-未接种原因");
                        continue;
                    }

                    String id = row.getCell(3).getStringCellValue();
                    if (jzMap.containsKey(id)) {
                        row.createCell(9).setCellValue("未接种");

                        int add = 0;
                        for (int k = 0; k <= 10 ; k++) {
                            if (k == 5) {
                                add = 1;
                            }
                            row.createCell(10+k).setCellValue(jzMap.get(id).getCell(7+k+add) == null ? "" : jzMap.get(id).getCell(7+k+add).getStringCellValue());
                        }

                    } else {
                        row.createCell(9).setCellValue("已接种");
                    }

                }
            }

            wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\0916\\11\\" + file.getName()));
            wb.close();
        }

        jzWb.close();

    }

    public void action3() throws Exception {
        String inFileRootPath = "C:\\Users\\Lenovo\\Desktop\\0916\\工地整理明细";
        File dir = new File(inFileRootPath);
        File[] inFiles = dir.listFiles();
        String val;
        int rowCnt = 0;
        for (File file : inFiles) {
            ZipSecureFile.setMinInflateRatio(0);
            Workbook wb = WorkbookFactory.create(file);
            int sheetCnt1 = wb.getNumberOfSheets();
            for (int i = 0; i < sheetCnt1; i++) {
                Sheet sheet = wb.getSheetAt(i);
                int cnt = 0;

                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);
                    cnt++;
                    if (cnt <= 3) continue;
                    val = row.getCell(9).getStringCellValue();
                    if (val.equals("已接种")) {
                        sheet.removeRow(row);
                    }
                }
            }
            wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\0916\\工地整理明细2\\" + file.getName()));
        }
    }

    public void test() throws Exception {
        String path = "C:\\Users\\Lenovo\\Desktop\\0916\\test.xlsx";
        ZipSecureFile.setMinInflateRatio(0);
        Workbook wb = WorkbookFactory.create(new File(path));
        int sheetCnt1 = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCnt1; i++) {
            Sheet sheet = wb.getSheetAt(i);
            //sheet.removeRow(sheet.getRow(1));
            Row row = sheet.getRow(1);
            sheet.shiftRows(row.getRowNum()+1, sheet.getLastRowNum()+1, -1, true, false);

        }
        wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\0916\\test1.xlsx"));
    }
}
