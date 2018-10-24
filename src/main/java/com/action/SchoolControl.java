package com.action;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dto.SchoolDto;
import com.service.SchoolService;

import util.ExcelUtil;
import util.PaginationUtil;

@Controller
@RequestMapping(value = "/school")
public class SchoolControl {

	public static Logger log = Logger.getLogger(SchoolControl.class);

	@Autowired
	private SchoolService schoolService;

	/**
	 * 初始化页面
	 */
	@RequestMapping(value = "/init.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String init() {
		return "school";
	}

	/**
	 * 导入excel页面
	 */
	@RequestMapping(value = "/initXSL.do", method = { RequestMethod.GET })
	public String initXSL() {
		return "schoolXSL";
	}

	/**
	 * 登录验证方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = { RequestMethod.POST })
	public void login(HttpServletRequest req, HttpServletResponse response, Model model, String userName,
			String password) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			String code = "{code:0}";
			if (!"admin".equals(userName)) {
				code = "{code:1}";
			} else if (!"admin123".equals(password)) {
				code = "{code:2}";
			}
			out = response.getWriter();
			out.print(JSON.parse(code));
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("login报错:" + e);
		}
	}

	/**
	 * 获取信息
	 * 
	 * @param req
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getSchoolList.do", method = { RequestMethod.GET })
	public void getSchoolList(HttpServletRequest req, HttpServletResponse response, Model model, String schoolType,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", schoolType);
		map.put("name", name);
		PaginationUtil<SchoolDto> pageUtil = new PaginationUtil<SchoolDto>(schoolService.selectSchools(map));
		map = new HashMap<String, Object>();
		map.put("datas", pageUtil.subList(1));
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(map));
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("SchoolControl->getSchoolList报错:" + e);
		}
	}

	/**
	 * 获取更多信息
	 * 
	 * @param req
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getMore.do", method = { RequestMethod.GET })
	public void getMore(HttpServletRequest req, HttpServletResponse response, Model model, Integer page, String type,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("start", (page - 1) * PaginationUtil.pageSize);
		map.put("end", PaginationUtil.pageSize);
		map.put("datas", schoolService.selectSchools(map));
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(map));
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("SchoolControl->getMore报错:" + e);
		}
	}

	/**
	 * 获取类型信息
	 * 
	 * @param req
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getTypes.do", method = { RequestMethod.GET })
	public void getTypes(HttpServletRequest req, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", schoolService.getTypes());
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(map));
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("SchoolControl->getTypes报错:" + e);
		}
	}

	/**
	 * 增加信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/addItem.do", method = { RequestMethod.POST })
	public void addItem(HttpServletRequest req, HttpServletResponse response, String ranking, String name,
			String country, int year, String type) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			SchoolDto schoolDto = new SchoolDto();
			schoolDto.setRanking(ranking);
			schoolDto.setName(URLDecoder.decode(name, "UTF-8"));
			schoolDto.setCountry(URLDecoder.decode(country, "UTF-8"));
			schoolDto.setYear(year);
			schoolDto.setType(type);
			int num = schoolService.insertSchool(schoolDto);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->addItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 删除信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/delItem.do", method = { RequestMethod.DELETE })
	public void delItem(HttpServletRequest req, HttpServletResponse response, int id) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			int num = schoolService.deleteSchool(id);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->delItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 修改信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/updItem.do", method = { RequestMethod.PUT })
	public void updItem(HttpServletRequest req, HttpServletResponse response, int id, String ranking, String name,
			String country, int year, String type) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			SchoolDto schoolDto = new SchoolDto();
			schoolDto.setRanking(ranking);
			schoolDto.setName(URLDecoder.decode(name, "UTF-8"));
			schoolDto.setCountry(URLDecoder.decode(country, "UTF-8"));
			schoolDto.setYear(year);
			schoolDto.setId(id);
			schoolDto.setType(type);
			int num = schoolService.updateSchool(schoolDto);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->updItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 上传excel
	 */
	@RequestMapping(value = "/upload.do", method = { RequestMethod.POST })
	public void upload(HttpServletRequest req, HttpServletResponse response,
			@RequestParam("filename") MultipartFile file) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			File f = ExcelUtil.convert(file);
			List<SchoolDto> schools = ExcelUtil.importData(f);
			f.delete();
			boolean bol = true;
			for (SchoolDto schoolDto : schools) {
				int num = schoolService.insertSchool(schoolDto);
				if (num == 0) {
					bol = false;
				}
			}
			response.setContentType("text/html; charset=UTF-8"); // 转码
			out.flush();
			out.println("<script>");
			if (bol) {
				out.println("alert('导入成功！');");
			} else {
				out.println("alert('导入失败！');");
			}
			out.println("window.location = '/school/initXSL.do';");// init.do
			out.println("</script>");
		} catch (Exception e) {
			log.error("SchoolControl->upload报错:" + e.toString());
			response.setContentType("text/html; charset=UTF-8"); // 转码
			out.flush();
			out.println("<script>");
			out.println("alert('导入失败！');");
			out.println("window.location = '/school/initXSL.do';");// init.do
			out.println("</script>");
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
}
