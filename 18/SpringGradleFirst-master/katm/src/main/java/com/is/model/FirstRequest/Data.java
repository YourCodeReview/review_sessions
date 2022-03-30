package com.is.model.FirstRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pHead", "pCode", "pLegal", "pClaimId", "pReportId", "pReportFormat" })
public class Data {
	
	@JsonProperty("pHead")
	private String pHead;
	@JsonProperty("pCode")
	private String pCode;
	@JsonProperty("pLegal")
	private String pLegal;
	@JsonProperty("pClaimId")
	private String pClaimId;
	@JsonProperty("pReportId")
	private Integer pReportId;
	@JsonProperty("pReportFormat")
	private Integer pReportFormat;
	
	public Data() {
		super();
	}

	public Data(String pHead, String pCode, String pLegal, String pClaimId,
			Integer pReportId, Integer pReportFormat) {
		super();
		this.pHead = pHead;
		this.pCode = pCode;
		this.pLegal = pLegal;
		this.pClaimId = pClaimId;
		this.pReportId = pReportId;
		this.pReportFormat = pReportFormat;
	}

	@JsonProperty("pHead")
	public String getpHead() {
		return pHead;
	}

	@JsonProperty("pHead")
	public void setpHead(String pHead) {
		this.pHead = pHead;
	}

	@JsonProperty("pCode")
	public String getpCode() {
		return pCode;
	}

	@JsonProperty("pCode")
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	@JsonProperty("pLegal")
	public String getpLegal() {
		return pLegal;
	}

	@JsonProperty("pLegal")
	public void setpLegal(String pLegal) {
		this.pLegal = pLegal;
	}

	@JsonProperty("pClaimId")
	public String getpClaimId() {
		return pClaimId;
	}

	@JsonProperty("pClaimId")
	public void setpClaimId(String pClaimId) {
		this.pClaimId = pClaimId;
	}

	@JsonProperty("pReportId")
	public Integer getpReportId() {
		return pReportId;
	}

	@JsonProperty("pReportId")
	public void setpReportId(Integer pReportId) {
		this.pReportId = pReportId;
	}

	@JsonProperty("pReportFormat")
	public Integer getpReportFormat() {
		return pReportFormat;
	}

	@JsonProperty("pReportFormat")
	public void setpReportFormat(Integer pReportFormat) {
		this.pReportFormat = pReportFormat;
	}
	
	

}
