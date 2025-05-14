package menu_page;

public class OrderItem {
  private String orderItemName;
  private int quantity;
  private double price;

  public String getOrderItemName() {
    return orderItemName;
  }

  public void setOrderItemName(String orderItemName) {
    this.orderItemName = orderItemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
