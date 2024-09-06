package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ICategoryDao;
import com.app.pojos.Categories;

@Service
@Transactional
public class FarmerServiceImpl implements IFarmerService {
    
    private static final Logger logger = LoggerFactory.getLogger(FarmerServiceImpl.class);
    
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<Categories> viewCategories() {    
        logger.info("Fetching categories with type 'V'.");
        List<Categories> categories = categoryDao.findByType("V");
        logger.info("Found {} categories with type 'V'.", categories.size());
        return categories;
    }
}
