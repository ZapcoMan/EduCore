package com.example.modules.system.mapper;

import com.example.core.mapper.BaseMapper;
import com.example.modules.system.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AdminMapper接口定义了管理员数据访问层的方法
 * 这些方法用于对管理员表进行基本的CRUD操作
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin, Integer> {

    /**
     * 查询所有管理员信息
     *
     * @param admin 一个Admin对象，用于传递查询条件（本例中未使用）
     * @return 包含所有管理员的列表
     */
    List<Admin> selectAll(Admin admin);

    /**
     * 插入一个新的管理员记录
     *
     * @param admin 要插入的管理员对象，包含管理员的详细信息
     */
    void insert(Admin admin);

    /**
     * 根据用户名查询管理员信息
     *
     * @param username 管理员的用户名，用于查询
     * @return 对应用户名的管理员对象，如果不存在则返回null
     */
    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);

    /**
     * 更新指定管理员的信息
     *
     * @param admin 包含更新信息的管理员对象，必须包含管理员的ID
     */
    void updateById(Admin admin);

    /**
     * 根据ID删除管理员记录
     *
     * @param id 要删除的管理员的ID
     */
    @Delete("delete from `admin` where id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据ID查询管理员信息
     *
     * @param id 管理员的ID，用于查询
     * @return 对应ID的管理员对象，如果不存在则返回null
     */
    @Select("select * from `admin` where id = #{id}")
    Admin selectById(String id);

//    @Select("select * from `admin` where id = #{id}")
//    Admin selectById(Long id);

    void updateById(Long id);
}

