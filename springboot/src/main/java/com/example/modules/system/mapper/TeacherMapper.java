package com.example.modules.system.mapper;


import com.example.core.mapper.BaseMapper;
import com.example.modules.system.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TeacherMapper接口用于定义与Teacher实体相关的数据库操作
 * 它是一个标记接口，通过@Mapper注解指示MyBatis该接口是一个映射器
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher, Long> {
    /**
     * 根据教师ID查找教师信息
     *
     * @param id 教师的唯一标识符
     * @return 返回对应的Teacher对象，如果找不到则返回null
     */
    Teacher findById(Long id);

    /**
     * 查找所有教师信息
     *
     * @return 返回一个包含所有Teacher对象的列表
     */
    List<Teacher> findAll();

    /**
     * 向数据库中插入一个新的教师记录
     *
     * @param teacher 要插入的Teacher对象
     */
    void insert(Teacher teacher);

    /**
     * 更新数据库中现有的教师记录
     *
     * @param teacher 包含更新信息的Teacher对象
     * @return 返回更新操作影响的行数
     */
    int update(Teacher teacher);

    /**
     * 根据教师ID从数据库中删除教师记录
     *
     * @param id 要删除的教师的唯一标识符
     */
    void deleteById(Long id);

    /**
     * 根据用户ID查找教师信息
     * 这个方法用于处理多对一或多对多关系，其中用户和教师之间可能存在复杂的关系
     *
     * @param userId 与教师关联的用户ID
     * @return 返回对应的Teacher对象，如果找不到则返回null
     */
    Teacher findByUserId(Long userId);

    void updateById(Long id);

}
