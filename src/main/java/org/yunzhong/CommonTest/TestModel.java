package org.yunzhong.CommonTest;

public class TestModel {
	private String name;
	private String id;
	private String timestamp;

	public void print() {
		System.out.println("name:" + name + ", id:" + id + ", time:" + timestamp);
	}

	public void setValue(String name, String id, String timestamp) {
		this.name = name;
		this.id = id;
		this.timestamp = timestamp;
	}

	public void setValueRandom(String name, String id, String timestamp) throws Exception {
		this.name = name;
		this.id = id;
		this.timestamp = timestamp;
		if (this.id.endsWith("100")) {
			throw new Exception("数字结尾是100." + this.id);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
