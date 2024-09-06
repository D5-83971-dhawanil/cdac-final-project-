package com.app.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.ICartDao;
import com.app.dao.IRegisterDao;
import com.app.dao.IRoleDao;
import com.app.dto.UserSiginResponse;
import com.app.dto.UsersDto;
import com.app.pojos.Cart;
import com.app.pojos.UserRole;
import com.app.pojos.Users;

@Service
@Transactional
public class RegisterServiceImpl implements IRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IRegisterDao registerDao;

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IRoleDao roleDao;

    @Override
    public String registerUser(UsersDto userDto) {
        logger.info("Registering user with details: {}", userDto);
        Users user = mapper.map(userDto, Users.class);
        user.setRole(roleDao.findByRoleName(userDto.getRole()));
        Cart transientCart = new Cart(user);
        user.setCart(transientCart);
        Users persistentUser = registerDao.save(user);
        logger.info("User registered successfully with ID: {}", persistentUser.getId());
        return "Users registered successfully :- " + persistentUser; // and an empty cart is created for user
    }

    @Override
    public Users validateUser(String mob, String pwd) {
        logger.info("Validating user with mobile number: {}", mob);
        Users user = registerDao.findByMobilenoAndPassword(mob, pwd);
        if (user != null) {
            logger.info("User validated successfully with mobile number: {}", mob);
        } else {
            logger.warn("Validation failed for mobile number: {}", mob);
        }
        return user;
    }

    @Override
    public UserSiginResponse getBloggerUserdetails(long buid) {
        logger.info("Fetching details for blogger user with ID: {}", buid);
        Users user = registerDao.findById(buid)
            .orElseThrow(() -> {
                logger.error("No such user exists with ID: {}", buid);
                return new ResourceNotFoundException("No such user exists");
            });
        UserSiginResponse response = mapper.map(user, UserSiginResponse.class);
        logger.info("Fetched blogger user details for ID: {}", buid);
        return response;
    }
}
