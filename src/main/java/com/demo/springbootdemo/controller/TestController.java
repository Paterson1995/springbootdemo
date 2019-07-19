package com.demo.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.springbootdemo.mapper.TestMapper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/export_excel")
    @ResponseBody
    public ResponseEntity<byte[]> exportExcel() {

        JSONObject jsonObject = new JSONObject();
        String[][] columns = {{"xuhao", "序号"}, {"id", "商品条码"}, {"supplier_brand", "品牌"}, {"goodsInfo", "商品信息"},
                {"goods_ENstandard", "标准"}, {"goods_toothshape", "牙型"}, {"goods_norm", "规格"}, {"goods_surfacetreat", "表面处理"},
                {"goods_level", "级别"}, {"goods_isWeight", "调价属性"}, {"goods_oprice", "基价"}, {"costPrice", "成本价"}, {"baseunit", "基础单位"},
                {"smallpackageNorm", "小包装规格"}, {"goods_smallpackagenum", "小包装单位"}, {"bigpackeageNorm", "大包装规格"},
                {"goods_bigpackeagenum", "大包装单位"}, {"goods_thousandweight", "千支重"}, {"goods_threadaccuracy", "螺纹精度"},
                {"goods_mechanicalproperties", "机械性能"}, {"goods_tolerance", "公差"}, {"goods_toothdistance", "牙距"},
                {"goods_materials", "材质"}, {"goods_receiveday", "到货天数"}, {"goods_length", "长度"}};

        List<? extends Map<String, Object>> exportData = testMapper.exportExcel(jsonObject);
        Map<String, Object> map = null;
        for (int i = 0; i<exportData.size(); i++) {
            map = exportData.get(i);
            if ((Boolean) map.get("goods_isWeight") == true) {
                map.put("goods_isWeight", "重量调价");
            } else {
                map.put("goods_isWeight", "整体调价");
            }
            map.put("xuhao", i);

        }

        String excelName = "商品列表导出管理_" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + ".xlsx";
        // 下载文件路径
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = null;
        try {
            downloadFielName = new String(excelName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 通知浏览器以attachment（下载方式）打开
        headers.setContentDispositionFormData("attachment", downloadFielName);
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] body = new byte[0];
        if (exportData.size() != 0) {
            // 创建excel工作簿
            @SuppressWarnings("resource")
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 创建工作表sheet
            XSSFSheet sheet = workbook.createSheet("导出列表");

            // 单元格样式
            CellStyle style = workbook.createCellStyle();
            // 设置单元格边框及颜色
            style.setBorderBottom(BorderStyle.THIN); // 下边框
            style.setBorderLeft(BorderStyle.THIN);// 左边框
            style.setBorderTop(BorderStyle.THIN);// 上边框
            style.setBorderRight(BorderStyle.THIN);// 右边框
//		style.setWrapText(true);// 文本是否被包裹在单元格内
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
            // 写入数据
            for (int i = 0; i <= exportData.size(); i++) {
                // 创建行
                XSSFRow row = sheet.createRow(i);
                XSSFCell cell = null;
                // 插入行数据
                for (int j = 0; j < columns.length; j++) {
                    // 创建列
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    // 第一行，表头
                    if (i == 0) {
                        cell.setCellValue(columns[j][1]);
                    } else {
                        Object obj = exportData.get(i - 1).get(columns[j][0]);
                        if (obj == null)
                            continue;
                        if (obj instanceof Boolean)
                            cell.setCellValue((Boolean) obj);
                        else if (obj instanceof Integer)
                            cell.setCellValue((Integer) obj);
                        else if (obj instanceof Double)
                            cell.setCellValue((Double) obj);
                        else {
                            // 日期格式
                            if (obj instanceof Date || obj instanceof Timestamp) {
                                cell.setCellType(CellType.STRING);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                cell.setCellValue(sdf.format((Date) obj));
                            } else
                                cell.setCellValue(obj.toString());
                        }
                    }
                }
            }
            // 创建excel文件的字节数组
            ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
            try {
                workbook.write(byteArr);
                body = byteArr.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    byteArr.flush();
                    byteArr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
    }
}
