package com.example.modules.system.service;

import com.example.core.service.BaseService;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户服务接口，定义了对用户信息进行增删查改及相关操作的方法
 */
public interface UserService extends BaseService<User, Integer> {
    /**
     * 添加新用户
     *
     * @param user 待添加的用户对象，包含用户基本信息
     */
    void add(User user);

    /**
     * 更新用户信息
     *
     * @param user 需要更新的用户对象，包含用户最新的基本信息
     */
    void update(User user);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID，用于定位要删除的用户
     */
    void deleteById(Integer id);

    /**
     * 查询所有用户信息
     *
     * @return 返回包含所有用户信息的列表
     */
    List<User> selectAll();

    /**
     * 分页查询用户信息
     *
     * @param pageNum  当前页码，用于指定查询的起始位置
     * @param pageSize 每页大小，用于指定每页包含的记录数
     * @param user     查询条件，用于指定筛选用户的条件
     * @return 返回分页信息对象，包含当前页用户信息列表及分页详情
     */
    PageInfo<User> selectPage(Integer pageNum, Integer pageSize, User user);

    /**
     * 用户注册
     *
     * @param user 待注册的用户对象，包含用户基本信息
     */
    void register(User user);

    /**
     * 用户登录
     *
     * @param account 包含用户登录凭证的对象，如用户名和密码
     * @return 返回登录成功的用户对象，若登录失败则返回null
     */
    User login(Account account);

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID，用于定位用户
     * @return 返回查询到的用户对象，若未找到则返回null
     */
    User selectById(String id);

    /**
     * 更新用户密码
     *
     * @param account 包含用户登录凭证的对象，用于验证用户身份
     */
    void updatePassword(Account account);

    void insert(User user);

    void deleteById(Long aLong);
}
