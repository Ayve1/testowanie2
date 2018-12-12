package MK.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers_orders")
public class CustomerOrder {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal payment;
    private LocalDate date;
    private int numberOfItems;
    private long productId;
    private long customerId;

    @ManyToMany(mappedBy = "customerOrders", fetch = FetchType.EAGER)
    private Set<Product> products;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_orders",
            joinColumns = @JoinColumn(name = "customers_order_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )

    private Set<Customer> customers;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int n) {
        this.numberOfItems = n;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal p) {
        this.payment = p;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

}
