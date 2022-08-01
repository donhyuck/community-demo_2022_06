package com.ldh.example.webdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	private int goodRP;
	private int badRP;
	
	private String extra__writerName;

}
