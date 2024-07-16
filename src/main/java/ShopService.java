import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrderOpt = productRepo.getProductById(productId);
            Product productToOrder = productToOrderOpt.orElseThrow(() -> new RuntimeException("Product not found"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        List<Order> orders = orderRepo.getOrders();
        List<Order> filteredOrders = orders.stream().filter(order -> order.status() == status).collect(Collectors.toList());
        if(!filteredOrders.isEmpty()){
            return filteredOrders;
        }
        return null;
    }
}
