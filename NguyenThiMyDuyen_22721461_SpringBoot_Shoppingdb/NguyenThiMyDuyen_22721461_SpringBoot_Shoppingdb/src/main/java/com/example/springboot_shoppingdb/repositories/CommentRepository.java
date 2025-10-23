package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // tìm kiếm comment theo id sản phẩm
    List<Comment> findByProductId(Integer productId);
}
