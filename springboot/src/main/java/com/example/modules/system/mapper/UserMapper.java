package com.example.modules.system.mapper;

import com.example.core.mapper.BaseMapper;
import com.example.modules.system.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 用户数据访问层接口
 * 该接口定义了对用户表进行数据库操作的方法，包括查询、插入、更新和删除操作
 */
@Mapper
public interface UserMapper extends BaseMapper<User, Long> {

    /**
     * 查询所有用户信息
     * 此方法根据传入的用户对象条件，查询符合条件的所有用户
     *
     * @param user 用户对象，包含查询条件
     * @return 用户列表，包含所有符合条件的用户
     */
    List<User> selectAll(User user);

    /**
     * 插入用户信息
     * 此方法将一个用户对象插入到数据库的用户表中
     *
     * @param user 要插入的用户对象
     */
    void insert(User user);

    /**
     * 根据用户名查询用户信息
     * 使用SQL语句通过用户名查询用户表中的记录，并返回对应的用户对象
     *
     * @param username 用户名，查询用户的唯一标识
     * @return 用户对象，如果找到匹配的用户，否则返回null
     */
    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);

    /**
     * 更新用户信息
     * 此方法根据用户对象中的id属性，更新数据库中对应的用户记录
     *
     * @param user 要更新的用户对象，包含用户的新信息
     */
    void updateById(User user);

    /**
     * 根据用户ID删除用户信息
     * 使用SQL语句根据用户ID删除用户表中的记录
     *
     * @param id 用户ID，要删除用户的唯一标识
     */
    @Delete("delete from `user` where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据用户ID查询用户信息
     * 使用SQL语句根据用户ID查询用户表中的记录，并返回对应的用户对象
     *
     * @param id 用户ID，查询用户的唯一标识
     * @return 用户对象，如果找到匹配的用户，否则返回null
     */
    @Select("select * from `user` where id = #{id}")
    User selectById(Long id);

    /**
     * 更新用户密码
     * 根据用户名更新用户的密码信息
     *
     * @param username    用户名
     * @param newpassword 新密码
     */
    void updatePassword(String username, String newpassword);

    /**
     * 查询所有用户信息
     *
     * @return 用户列表，包含所有用户信息
     */
    List<User> selectAll();
}
