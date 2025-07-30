package com.example.modules.system.mapper;


import com.example.modules.system.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StudentMapper接口用于定义与学生数据相关的数据库操作
 * 它是一个标记接口，通过@Mapper注解标识，用于让MyBatis框架识别
 */
@Mapper
public interface StudentMapper {
    /**
     * 根据学生ID查找学生信息
     *
     * @param id 学生的唯一标识符
     * @return 返回对应ID的学生对象，如果找不到则返回null
     */
    Student findById(Long id);

    /**
     * 查找所有学生信息
     *
     * @return 返回一个包含所有学生对象的列表
     */
    List<Student> findAll();

    /**
     * 插入一个新的学生记录
     *
     * @param student 要插入的学生对象
     * @return 返回插入操作影响的行数
     */
    int insert(Student student);

    /**
     * 更新一个学生记录
     *
     * @param student 要更新的学生对象
     * @return 返回更新操作影响的行数
     */
    int update(Student student);

    /**
     * 根据学生ID删除学生记录
     *
     * @param id 学生的唯一标识符
     * @return 返回删除操作影响的行数
     */
    int deleteById(Long id);

    /**
     * 根据用户ID查找学生信息
     * 这个方法的存在是因为学生可能与用户系统关联，用户的ID可以映射到特定的学生
     *
     * @param userId 用户的唯一标识符
     * @return 返回对应用户ID的学生对象，如果找不到则返回null
     */
    Student findByUserId(Long userId);
}
