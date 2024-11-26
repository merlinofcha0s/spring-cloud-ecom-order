package fr.plb.ecom_order.infrastructure.secondary.repository;


import fr.plb.ecom_order.infrastructure.secondary.entity.OrderedProductEntity;
import fr.plb.ecom_order.infrastructure.secondary.entity.OrderedProductEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, OrderedProductEntityPk> {

}
