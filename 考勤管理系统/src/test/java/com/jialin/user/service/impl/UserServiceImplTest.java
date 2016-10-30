package com.jialin.user.service.impl;

import com.jialin.basic.entity.PageModel;
import com.jialin.test.basic.AbstractTestCase;
import com.jialin.user.entity.UserEntity;
import com.jialin.user.service.IUserService;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by JiaLin on 2014/5/26.
 */
public class UserServiceImplTest extends AbstractTestCase{

    @Resource(name="userService")
    private IUserService userService;

    @Before
    public void setUp() throws Exception {

        userService.save(new UserEntity("ee","aa",10));
        userService.save(new UserEntity("ef","aa",10));
        userService.save(new UserEntity("eg","aa",10));

    }

    @Test
    public void testGetUser() throws Exception {
        UserEntity userEntity=new UserEntity();
        userEntity.setName("ee");
        userEntity.setPassword("aa");
        UserEntity userEntity1=userService.getUser(userEntity);
        assertEquals("ee",userEntity1.getName());
        assertEquals("aa",userEntity1.getPassword());

        UserEntity userEntity2=new UserEntity();
        userEntity2.setName("ff");
        userEntity2.setPassword("aa");

        assertNull(userService.getUser(userEntity2));

    }

    @Test
    public void testQueryUsers() throws Exception {
        PageModel<UserEntity> pageModel=initPageMode();
        UserEntity userEntity=new UserEntity();
        userEntity.setName("e");

        PageModel<UserEntity> pageModel1=userService.queryUsers(userEntity,pageModel);
        assertEquals(2,pageModel1.getList().size());
        assertEquals(3,(long)pageModel1.getTotalRecords());
        assertEquals("name",pageModel1.getOrderField());
        assertEquals("DESC",pageModel1.getOrderDirection());


    }

    private PageModel<UserEntity> initPageMode() {
        PageModel<UserEntity> pageModel=new PageModel<UserEntity>();
        pageModel.setNumPerPage(2);
        pageModel.setPageNum(1);

        return pageModel;
    }
}
