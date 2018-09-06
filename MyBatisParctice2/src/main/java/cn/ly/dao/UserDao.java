package cn.ly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import cn.ly.po.User;


public interface UserDao {
	public List<User> findUsers();

}
