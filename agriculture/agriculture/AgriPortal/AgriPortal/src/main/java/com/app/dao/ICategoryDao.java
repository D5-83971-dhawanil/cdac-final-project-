package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Categories;
import com.app.pojos.SubCategories;

public interface ICategoryDao extends JpaRepository<Categories, Long> {
	
	@Query(value = "select * from categories where type=\"1\";", nativeQuery = true)
	List<Categories> viewAllCategories();

	List<Categories> findByType(String string);

	Categories findBySubCategoriesCategoriesId(long id);
	
//	@Query(value = "select * from sub_categories where sbt_id in (select id from categories where type =\"1\");", nativeQuery = true)
//	List<SubCategories> viewAllSubcategories();
	
	
}
