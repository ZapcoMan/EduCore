package com.example.modules.system.service;

import com.example.core.service.BaseService;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 管理员服务接口，定义了管理员相关的操作
 */
public interface AdminService extends BaseService<Admin, Integer> {
    /**
     * 添加管理员
     *
     * @param admin 要添加的管理员对象
     */
    void add(Admin admin);

    /**
     * 更新管理员信息
     *
     * @param admin 要更新的管理员对象，包含管理员的新信息
     */
    void update(Admin admin);

    /**
     * 根据ID删除管理员
     *
     * @param id 管理员的ID
     */
    void deleteById(Integer id);

    /**
     * 批量删除管理员
     *
     * @param list 包含多个管理员对象的列表，用于批量删除
     */
    void deleteBatch(List<Admin> list);

    /**
     * 根据ID查询管理员
     *
     * @param id 管理员的ID
     * @return 返回查询到的管理员对象，如果没有找到则返回null
     */
    Admin selectById(String id);

    /**
     * 查询所有管理员
     *
     * @return 返回包含所有管理员对象的列表
     */
    List<Admin> selectAll();

    /**
     * 分页查询管理员
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param admin    用于查询条件的管理员对象，可以为空
     * @return 返回包含分页信息的PageInfo对象
     */
    PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize, Admin admin);

    /**
     * 管理员登录
     *
     * @param account 包含管理员登录信息的账户对象
     * @return 返回登录成功的管理员对象，如果登录失败则返回null
     */
    Admin login(Account account);

    /**
     * 更新管理员密码
     *
     * @param account 包含新密码的账户对象
     */
    void updatePassword(Account account);
}
