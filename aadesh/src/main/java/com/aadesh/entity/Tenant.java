package com.aadesh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tenants")
@NamedQueries({ @NamedQuery(name = "com.aadesh.entity.Tenant.findAll", query = "SELECT t FROM Tenant t"),
		@NamedQuery(name = "com.aadesh.entity.Tenant.findById", query = "SELECT t FROM Tenant t where t.id = :id"),
		@NamedQuery(name = "com.aadesh.entity.Tenant.findByEmail", query = "SELECT t FROM Tenant t where t.tenantEmail = :tenantEmail"),
		@NamedQuery(name = "com.aadesh.entity.Tenant.findByPhone", query = "SELECT t FROM Tenant t where t.tenantPhone = :tenantPhone")
		})

public class Tenant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "tenant_email")
	private String tenantEmail;
	
	@Column(name = "tenant_firstName", nullable = false)
	private String tenantFirstName;
	
	@Column(name = "tenant_middleName", nullable = false)
	private String tenantMiddleName;
	
	@Column(name = "tenant_lastName", nullable = false)
	private String tenantLastName;
	
	@Column(name = "tenant_phone")
	private String tenantPhone;
	
	@Column(name = "tenant_landmark", nullable = false)
	private String tenantLandmark;
	
	@Column(name = "tenant_city", nullable = false)
	private String tenantCity;
	
	@Column(name = "tenant_bankAcNo", nullable = false)
	private String tenantBankAcNo;

	@Column(name = "tenant_bankName", nullable = false)
	private String tenantBankName;
	
	@Column(name = "tenant_bankBranch", nullable = false)
	private String tenantBankBranch;

	@Column(name = "tenant_ifscCode", nullable = false)
	private String tenantIfscCode;
	
	@Column(name = "tenant_pan", nullable = false)
	private String tenantPan;
	
	@Column(name = "tenant_status")
	private String tenantStatus;
	
	@Column(name = "tenant_parent", nullable = false)
	private String tenantParent;
	
	@Column(name = "tenant_joining_date", nullable = false)
	private String tenantJoiningDate;
	
	@Column(name = "tenant_total_amount", nullable = false)
	private long tenantTotalAmount;
	
	@Column(name = "tenant_paid_amount", nullable = false)
	private long tenantPaidAmount;
	
	@Column(name = "tenant_pswd")
	private String tenantPswd;
	
	@Column(name = "tenant_stage")
	private long tenant_stage;
	
	@Column(name = "tenant_requied_id")
	private long tenantRequiedId;
	
	@Column(name = "tenant_sponsor")
	private long tenantSponsor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantFirstName() {
		return tenantFirstName;
	}

	public void setTenantFirstName(String tenantFirstName) {
		this.tenantFirstName = tenantFirstName;
	}

	public String getTenantMiddleName() {
		return tenantMiddleName;
	}

	public void setTenantMiddleName(String tenantMiddleName) {
		this.tenantMiddleName = tenantMiddleName;
	}

	public String getTenantLastName() {
		return tenantLastName;
	}

	public void setTenantLastName(String tenantLastName) {
		this.tenantLastName = tenantLastName;
	}

	public String getTenantPhone() {
		return tenantPhone;
	}

	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}

	public String getTenantLandmark() {
		return tenantLandmark;
	}

	public void setTenantLandmark(String tenantLandmark) {
		this.tenantLandmark = tenantLandmark;
	}

	public String getTenantCity() {
		return tenantCity;
	}

	public void setTenantCity(String tenantCity) {
		this.tenantCity = tenantCity;
	}

	public String getTenantBankAcNo() {
		return tenantBankAcNo;
	}

	public void setTenantBankAcNo(String tenantBankAcNo) {
		this.tenantBankAcNo = tenantBankAcNo;
	}

	public String getTenantBankName() {
		return tenantBankName;
	}

	public void setTenantBankName(String tenantBankName) {
		this.tenantBankName = tenantBankName;
	}

	public String getTenantBankBranch() {
		return tenantBankBranch;
	}

	public void setTenantBankBranch(String tenantBankBranch) {
		this.tenantBankBranch = tenantBankBranch;
	}

	public String getTenantIfscCode() {
		return tenantIfscCode;
	}

	public void setTenantIfscCode(String tenantIfscCode) {
		this.tenantIfscCode = tenantIfscCode;
	}

	public String getTenantPan() {
		return tenantPan;
	}

	public void setTenantPan(String tenantPan) {
		this.tenantPan = tenantPan;
	}

	public String getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	public String getTenantParent() {
		return tenantParent;
	}

	public void setTenantParent(String tenantParent) {
		this.tenantParent = tenantParent;
	}

	public String getTenantJoiningDate() {
		return tenantJoiningDate;
	}

	public void setTenantJoiningDate(String tenantJoiningDate) {
		this.tenantJoiningDate = tenantJoiningDate;
	}

	public long getTenantTotalAmount() {
		return tenantTotalAmount;
	}

	public void setTenantTotalAmount(long tenantTotalAmount) {
		this.tenantTotalAmount = tenantTotalAmount;
	}

	public long getTenantPaidAmount() {
		return tenantPaidAmount;
	}

	public void setTenantPaidAmount(long tenantPaidAmount) {
		this.tenantPaidAmount = tenantPaidAmount;
	}

	public String getTenantPswd() {
		return tenantPswd;
	}

	public void setTenantPswd(String tenantPswd) {
		this.tenantPswd = tenantPswd;
	}

	public long getTenant_stage() {
		return tenant_stage;
	}

	public void setTenant_stage(long tenant_stage) {
		this.tenant_stage = tenant_stage;
	}

	public long getTenantRequiedId() {
		return tenantRequiedId;
	}

	public void setTenantRequiedId(long tenantRequiedId) {
		this.tenantRequiedId = tenantRequiedId;
	}

	public long getTenantSponsor() {
		return tenantSponsor;
	}

	public void setTenantSponsor(long tenantSponsor) {
		this.tenantSponsor = tenantSponsor;
	}

}
