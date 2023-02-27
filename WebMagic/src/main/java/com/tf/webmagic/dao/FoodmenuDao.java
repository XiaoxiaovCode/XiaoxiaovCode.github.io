package com.tf.webmagic.dao;


import com.tf.webmagic.pojo.Foodmenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodmenuDao extends JpaRepository<Foodmenu,Integer> {
}
