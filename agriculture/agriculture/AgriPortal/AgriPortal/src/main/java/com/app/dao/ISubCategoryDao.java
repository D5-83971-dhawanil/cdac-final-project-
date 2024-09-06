package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.SubCategories;

public interface ISubCategoryDao extends JpaRepository<SubCategories, Long> {
//	
//	@Modifying
//	@Query("delete from SubCategories c where c.id = :id")
//	void deleteById(Long id);
}
