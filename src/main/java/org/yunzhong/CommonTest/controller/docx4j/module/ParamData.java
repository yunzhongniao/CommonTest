package org.yunzhong.CommonTest.controller.docx4j.module;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamData {
	public static class ParamRow {
		private List<String> values;

		public List<String> getValues() {
			return values;
		}

		public void setValues(List<String> values) {
			this.values = values;
		}
	}

	private String wordName;
	private Map<String, String> variables;
	private Map<String, List<ParamRow>> datas;

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public Map<String, List<ParamRow>> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, List<ParamRow>> datas) {
		this.datas = datas;
	}

	public static ParamData load(Path paramPath) {
		ParamData data = new ParamData();
		data.setWordName("wordname");

		Map<String, String> variables = new HashMap<String, String>();
		variables.put("userStat.className", "酷炫名字");
		variables.put("userStat.classvalue", "100D");
		data.setVariables(variables);

		Map<String, List<ParamRow>> datas = new HashMap<String, List<ParamRow>>();
		List<ParamRow> rows1 = new ArrayList<>();

		ParamRow row1 = new ParamRow();
		List<String> row1Values = new ArrayList<String>();
		row1Values.add("");
		row1Values.add("order");
		row1Values.add("match");
		row1Values.add("quot");
		row1.setValues(row1Values);
		rows1.add(row1);

		ParamRow row2 = new ParamRow();
		List<String> row2Values = new ArrayList<String>();
		row2Values.add("客户");
		row2Values.add("100");
		row2Values.add("50");
		row2Values.add("122");
		row2.setValues(row2Values);
		rows1.add(row2);

		ParamRow row3 = new ParamRow();
		List<String> row3Values = new ArrayList<String>();
		row3Values.add("实控组");
		row3Values.add("1000");
		row3Values.add("500");
		row3Values.add("1220");
		row3.setValues(row3Values);
		rows1.add(row3);

		ParamRow row4 = new ParamRow();
		List<String> row4Values = new ArrayList<String>();
		row4Values.add("rela-group");
		row4Values.add("100");
		row4Values.add("55");
		row4Values.add("120");
		row4.setValues(row4Values);
		rows1.add(row4);

		ParamRow row5 = new ParamRow();
		List<String> row5Values = new ArrayList<String>();
		row5Values.add("member");
		row5Values.add("200");
		row5Values.add("25");
		row5Values.add("220");
		row5.setValues(row5Values);
		rows1.add(row5);

		datas.put("成交委托表", rows1);

		List<ParamRow> rows2 = new ArrayList<>();
		ParamRow row21 = new ParamRow();
		List<String> row21Values = new ArrayList<String>();
		row21Values.add("");
		row21Values.add("销售额");
		row21.setValues(row21Values);
		rows2.add(row21);

		ParamRow row22 = new ParamRow();
		List<String> row22Values = new ArrayList<String>();
		row22Values.add("第一季度");
		row22Values.add("101");
		row22.setValues(row22Values);
		rows2.add(row22);

		ParamRow row23 = new ParamRow();
		List<String> row23Values = new ArrayList<String>();
		row23Values.add("第二季度");
		row23Values.add("31");
		row23.setValues(row23Values);
		rows2.add(row23);

		ParamRow row24 = new ParamRow();
		List<String> row24Values = new ArrayList<String>();
		row24Values.add("第三季度");
		row24Values.add("61");
		row24.setValues(row24Values);
		rows2.add(row24);

		ParamRow row25 = new ParamRow();
		List<String> row25Values = new ArrayList<String>();
		row25Values.add("第四季度");
		row25Values.add("64");
		row25.setValues(row25Values);
		rows2.add(row25);
		
		datas.put("销售额", rows2);
		data.setDatas(datas);
		return data;
	}

}
