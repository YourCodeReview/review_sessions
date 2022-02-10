package com.is.model.SecondRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pHead", "pCode", "pToken", "pClaimId", "pReportFormat" })
public class Data {

	@JsonProperty("pHead")
	private String pHead;
	@JsonProperty("pCode")
	private String pCode;
	@JsonProperty("pToken")
	private String pToken;
	@JsonProperty("pClaimId")
	private String pClaimId;
	@JsonProperty("pReportFormat")
	private Integer pReportFormat;

	@JsonProperty("pHead")
	public String getPHead() {
		return pHead;
	}

	@JsonProperty("pHead")
	public void setPHead(String pHead) {
		this.pHead = pHead;
	}

	@JsonProperty("pCode")
	public String getPCode() {
		return pCode;
	}

	@JsonProperty("pCode")
	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	@JsonProperty("pToken")
	public String getPToken() {
		return pToken;
	}

	@JsonProperty("pToken")
	public void setPToken(String pToken) {
		this.pToken = pToken;
	}

	@JsonProperty("pClaimId")
	public String getPClaimId() {
		return pClaimId;
	}

	@JsonProperty("pClaimId")
	public void setPClaimId(String pClaimId) {
		this.pClaimId = pClaimId;
	}

	@JsonProperty("pReportFormat")
	public Integer getPReportFormat() {
		return pReportFormat;
	}

	@JsonProperty("pReportFormat")
	public void setPReportFormat(Integer pReportFormat) {
		this.pReportFormat = pReportFormat;
	}

}
