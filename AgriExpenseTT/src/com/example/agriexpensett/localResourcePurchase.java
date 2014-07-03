package com.example.agriexpensett;

public class localResourcePurchase {
	private int pId;
	private int resourceId;
	private String quantifier;
	private double qty;
	private double cost;
	private double qtyRemaining;
	private String type;
	
	public localResourcePurchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	public localResourcePurchase(int pId, int resourceId, String quantifier,
			double qty, double cost, double qtyRemaining,String type) {
		super();
		this.pId = pId;
		this.resourceId = resourceId;
		this.quantifier = quantifier;
		this.qty = qty;
		this.cost = cost;
		this.qtyRemaining = qtyRemaining;
		this.type=type;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getQuantifier() {
		return quantifier;
	}
	public void setQuantifier(String quantifier) {
		this.quantifier = quantifier;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getQtyRemaining() {
		return qtyRemaining;
	}
	public void setQtyRemaining(double qtyRemaining) {
		this.qtyRemaining = qtyRemaining;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		String n="purchaseId:"+pId+" resourceId:"+resourceId+" quantifier:"+quantifier+" qty:"+qty+" cost:"+cost+" remaining:"+qtyRemaining;
		return n;
	}
	
	
	
}