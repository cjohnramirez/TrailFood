package menu_page;

import java.sql.Date;

public class Order {
  private int orderId;
  private double orderPrice;
  private Date orderDate;

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getOrderId() {
    return orderId;
  }

  public double getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(double orderPrice) {
    this.orderPrice = orderPrice;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }
}
