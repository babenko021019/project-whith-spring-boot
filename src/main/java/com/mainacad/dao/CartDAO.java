package com.mainacad.dao;

import com.mainacad.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartDAO extends JpaRepository<Cart, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM carts WHERE user_id = :userId AND creation_time >= :timeFrom AND creation_time <= :timeTo")
    List<Cart> getAllByUserAndPeriod(Integer userId, Long timeFrom, Long timeTo);

    @Query(nativeQuery = true, value = "SELECT * FROM carts WHERE user_id = :userId")
    List<Cart> getAllByUser(Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM carts WHERE user_id = :userId AND status=0")
    List<Cart> getByUserAndOpenStatus(Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE carts SET status = :statusOrdinal WHERE id = :cartId")
    int updateStatus(Integer cartId, int statusOrdinal);
}
