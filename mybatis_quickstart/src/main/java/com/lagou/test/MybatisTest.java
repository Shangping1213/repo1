package com.lagou.test;

import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author 尚平
 * @version 10.0
 * @motto 我亦无他 惟吾熟而
 */
public class MybatisTest {
    //快熟入门mybatis测试方法
    @Test
    public void mybatisQuickStart() throws IOException {
        //1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.执行SQL  参数:statementid:namespace.id
        List<User> users = sqlSession.selectList("userMapper.findAll");

        //5.遍历打印结果
        for (User user : users) {
            System.out.println(user);
        }

        //6.关闭资源
        sqlSession.close();
    }
    /*
        测试新增用户
     */
    @Test
    public void testSave() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        User user = new User();
        user.setUsername("jack");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("北京海淀");
        sqlSession.insert("userMapper.saveUser",user);
        /*//手动提交事务  若没有提交事务，就会产生回滚
        sqlSession.commit();*/
        sqlSession.close();

    }

    /*
        测试更新用户
     */
    @Test
    public void testUpdate() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("luck");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("北京海淀");
        sqlSession.update("userMapper.updateUser",user);

        sqlSession.commit();
        sqlSession.close();
    }
/*
    删除用户
 */
    @Test
    public void deleteUser() throws IOException {
        //1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.执行SQL
        sqlSession.delete("userMapper.deleteUser",4);
        //5.对数据库做出改变，手动提交事务
        sqlSession.commit();
        //6.关闭资源
        sqlSession.close();
    }
}
