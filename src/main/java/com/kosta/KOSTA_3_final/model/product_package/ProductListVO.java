package com.kosta.KOSTA_3_final.model.product_package;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class ProductListVO {
	private int packageId;
	private int packagePrice;
	private int packageType;
	private String packageName;
	private int[] productId;
	private String[] productName;
	private int[] productPrice;
	private int[] productQty;
	private int[] companyId;
	private int[] categoryId;
	private  MultipartFile[] files;
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(int packagePrice) {
		this.packagePrice = packagePrice;
	}
	public int getPackageType() {
		return packageType;
	}
	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public int[] getProductId() {
		return productId;
	}
	public void setProductId(int[] productId) {
		this.productId = productId;
	}
	public String[] getProductName() {
		return productName;
	}
	public void setProductName(String[] productName) {
		this.productName = productName;
	}
	public int[] getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int[] productPrice) {
		this.productPrice = productPrice;
	}
	public int[] getProductQty() {
		return productQty;
	}
	public void setProductQty(int[] productQty) {
		this.productQty = productQty;
	}
	public int[] getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int[] companyId) {
		this.companyId = companyId;
	}
	public int[] getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int[] categoryId) {
		this.categoryId = categoryId;
	}
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "ProductListVO [packageId=" + packageId + ", packagePrice=" + packagePrice + ", packageType="
				+ packageType + ", packageName=" + packageName + ", productId=" + Arrays.toString(productId)
				+ ", productName=" + Arrays.toString(productName) + ", productPrice=" + Arrays.toString(productPrice)
				+ ", productQty=" + Arrays.toString(productQty) + ", companyId=" + Arrays.toString(companyId)
				+ ", categoryId=" + Arrays.toString(categoryId) + ", files=" + Arrays.toString(files) + "]";
	}
}