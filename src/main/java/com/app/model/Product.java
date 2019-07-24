package com.app.model;





public class Product {
	
	private Integer id;
	private String code;
	private Double cost;
	private Double disc;
	private Double gst;
	public Product() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getDisc() {
		return disc;
	}
	public void setDisc(Double disc) {
		this.disc = disc;
	}
	public Double getGst() {
		return gst;
	}
	public void setGst(Double gst) {
		this.gst = gst;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", cost=" + cost + ", disc=" + disc + ", gst=" + gst + "]";
	}
	
	}
