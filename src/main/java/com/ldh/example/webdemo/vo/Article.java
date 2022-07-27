package com.ldh.example.webdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	private String body;
	private int hitCount;
	private int goodRP;
	private int badRP;

	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;

	public String getForPrintRegDate_Type1() {
		return regDate.substring(2, 16).replace(" ", "</br>");
	}

	public String getForPrintUpdateDate_Type1() {
		return updateDate.substring(2, 16).replace(" ", "</br>");
	}

	public String getForPrintRegDate_Type2() {
		return regDate.substring(2, 16);
	}

	public String getForPrintUpdateDate_Type2() {
		return updateDate.substring(2, 16);
	}
}
