package cn.ly.test;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import cn.ly.dao.UserDao;
import cn.ly.po.User;


public class UserDaoTest {
	/**
	 * 1、在mybatis中，一级缓存(SqlSession)是自动存在的,
	 * 除非关闭SqlSession,那么一级缓存就不存在
	 * 2、二级缓存（SqlSessionFactory），是需要我们手动去设置的
	 * 1）：要在sqlMapConfig.xml（Mybatis全局配置文件中配置）
	 * 是否开启二级缓存
	 * <settings>
	 *		<setting name="cacheEnabled" value="true"/>
	 * </settings>
	 * 2)在需要使用缓存的查询上进行配置
	 * 在相应的mapper配置文件中
	 * <mapper namespace="emp.mapper">下配置<cache></cache>
	 * 就说明这个mapper配置文件中的所有查询都使用二级缓存
	 * 可以在需要缓存的查询上<select>里配置useCache="true"表示该查询使用二级缓存
	 * 配置useCache="false"表示该查询不使用二级缓存
	 * 3)二级缓存必须要对查询返回的实体做一个序列化
	 * 只需要实现 Serializable接口
	 */
	
	private SqlSessionFactory factory;
	@Before
	public void setUp() throws Exception{
		String resource = "SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);
	}
	 
	@Test
	public void testFindUsers1() throws SQLException{//一级缓存
		SqlSession sqlSession = factory.openSession();
		UserDao ud = sqlSession.getMapper(UserDao.class);
		List<User> list = ud.findUsers(); 
		System.out.println(list);
		System.out.println("*******************************************");
		list = ud.findUsers();
		System.out.println(list); //可以看出这里是直接从缓存中查询而没有清空缓存
		sqlSession.close(); //close代表清空一级缓存并关掉sqlsession
		System.out.println("*******************************************");
	}
	
	/**
	 * 二级缓存
	 * 可以看到最后一个输出从新获取sqlSession对象再去查询同一条数据的时候并没有提交查询
	 * 这个方法只提交了一次查询，查了三次数据
	 * @throws SQLException
	 */
	@Test
	public void testFindUsers2() throws SQLException{
		SqlSession sqlSession = factory.openSession();
		UserDao ud = sqlSession.getMapper(UserDao.class);
		List<User> list = ud.findUsers();
		System.out.println(list);
		System.out.println("*******************************************");
		list = ud.findUsers();
		System.out.println(list); 
		sqlSession.close(); 
		System.out.println("*******************************************");
		sqlSession = factory.openSession();
		ud = sqlSession.getMapper(UserDao.class);
		List<User> list2 = ud.findUsers();
		System.out.println(list2);
	}
	
}
