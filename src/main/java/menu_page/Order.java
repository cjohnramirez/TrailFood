package menu_page;

public class Order {
  private double orderPrice;
  private String orderOption;
  private double orderPaymentAmount;
  private String orderPaymentMethod;
  private String orderDate;

  public String getOrderOption() {
    return orderOption;
  }

  public void setOrderOption(String orderOption) {
    this.orderOption = orderOption;
  }

  public double getOrderPaymentAmount() {
    return orderPaymentAmount;
  }

  public void setOrderPaymentAmount(double orderPaymentAmount) {
    this.orderPaymentAmount = orderPaymentAmount;
  }

  public String getOrderPaymentMethod() {
    return orderPaymentMethod;
  }

  public void setOrderPaymentMethod(String orderPaymentMethod) {
    this.orderPaymentMethod = orderPaymentMethod;
  }

  public double getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(double orderPrice) {
    this.orderPrice = orderPrice;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }
}
