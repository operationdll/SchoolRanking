package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.dto.SchoolDto;

/**
 * Excel数据导入
 * 
 * @author Administrator
 *
 */
public class ExcelUtil {

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public static List<SchoolDto> importData(File file) throws Exception {
		Workbook wb = null;
		List<SchoolDto> shools = new ArrayList<SchoolDto>();
		if (isExcel2007(file.getPath())) {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} else {
			wb = new HSSFWorkbook(new FileInputStream(file));
		}

		// 获取总的sheet数量
		// int numberOfSheets = wb.getNumberOfSheets();
		Sheet sheet = wb.getSheetAt(0);// 获取第一个sheet
		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String name = row.getCell(0).getStringCellValue();
			String country = row.getCell(1).getStringCellValue();
			int ranking = (int) row.getCell(2).getNumericCellValue();
			int year = (int) row.getCell(3).getNumericCellValue();
			if (name == null) {
				break;
			}
			SchoolDto schoolDto = new SchoolDto();
			schoolDto.setName(name);
			schoolDto.setCountry(country);
			schoolDto.setRanking(ranking);
			schoolDto.setYear(year);

			shools.add(schoolDto);
		}
		wb.close();
		return shools;
	}

	public static File convert(MultipartFile file) throws Exception {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static void main(String[] args) {
		File f = new File("d:/test.xlsx");
		try {
			List<SchoolDto> l = ExcelUtil.importData(f);
			System.out.println(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
