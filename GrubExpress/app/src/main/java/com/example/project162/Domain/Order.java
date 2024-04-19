package com.example.project162.Domain;

public class Order {
    private int orderID;
    private String customerName;
    private String orderDate;
    private double orderTotal;
    private String[] orderItems;

    public String[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String[] orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Order() {
    }

    public Order(int orderID, String customerName, String orderDate, double orderTotal) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
}}
