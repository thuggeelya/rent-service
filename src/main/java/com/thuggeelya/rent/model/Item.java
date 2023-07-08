package com.thuggeelya.rent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "item", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "condition")
    private String condition;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    @ToString.Exclude
    private Set<AppUser> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null
                && Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(serialNumber, item.serialNumber)
                && Objects.equals(cost, item.cost)
                && Objects.equals(brand, item.brand)
                && Objects.equals(description, item.description)
                && Objects.equals(condition, item.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, serialNumber, cost, brand, description, condition);
    }
}
