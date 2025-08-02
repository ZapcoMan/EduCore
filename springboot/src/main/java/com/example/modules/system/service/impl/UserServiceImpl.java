package com.example.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.example.common.exception.CustomerException;
import com.example.common.utils.TokenUtils;
import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.modules.system.mapper.UserMapper;
import com.example.modules.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserMapper> implements UserService {

    @Resource
    UserMapper userMapper;
    // 日志对象，用于记录系统日志
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    public UserServiceImpl(UserMapper mapper) {
        super(mapper);
    }

    /**
     * 添加新用户
     *
     * @param user 待添加的用户信息
     * @throws CustomerException 如果用户名已存在，则抛出异常
     */
    public void add(User user) {
        // 根据新的账号查询数据库  是否存在同样账号的数据
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new CustomerException("账号重复");
        }
        // 默认密码
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(DigestUtil.md5Hex("123456"));
        } else {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        }
        // 如果未设置用户名，则使用账号作为用户名
        if (StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }
        // 设置用户角色为普通用户
        user.setRole("USER");
        // 插入用户数据到数据库
        userMapper.insert(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 需要更新的用户信息
     */
    public void update(User user) {
        // 根据用户ID更新用户信息
        userMapper.updateById(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    public void deleteById(Integer id) {
        // 根据用户ID删除用户
        userMapper.deleteById(Long.valueOf(id));
    }

    /**
     * 批量删除用户
     *
     * @param list 需要删除的用户列表
     */
    public void deleteBatch(List<User> list) {
        // 遍历用户列表，逐个删除
        for (User user : list) {
            this.deleteById(user.getId());
        }
    }


    /**
     * 根据Long类型ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户对象，如果找到匹配的用户则返回用户信息，否则返回null
     */
    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }


    /**
     * 根据Integer类型ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户对象，如果找到匹配的用户则返回用户信息，否则返回null
     */
    @Override
    public User selectById(Integer id) {
        return null;
    }


    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    public List<User> selectAll() {
        // 查询所有用户数据
        return userMapper.selectAll(null);
    }

    /**
     * @param id
     */
    @Override
    public void updateById(Long id) {

    }

    /**
     * 分页查询用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param user     查询条件
     * @return 分页后的用户列表
     */
    public PageInfo<User> selectPage(Integer pageNum, Integer pageSize, User user) {
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        // 根据条件查询用户数据
        List<User> list = userMapper.selectAll(user);
        // 返回分页信息
        return PageInfo.of(list);
    }

    /**
     * 注册新用户
     *
     * @param user 待注册的用户信息
     * @throws CustomerException 如果用户名已存在，则抛出异常
     */
    public void register(User user) {
        // 调用添加用户方法实现注册
        this.add(user);
    }

    /**
     * 用户登录
     *
     * @param account 用户账号信息
     * @return 登录成功的用户信息
     * @throws CustomerException 如果账号不存在或密码错误，则抛出异常
     */
    public User login(Account account) {
        // 验证账号是否存在
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (dbUser == null) {
            throw new CustomerException("账号不存在");
        }

        // 获取输入的密码和数据库中的密码
        String InputPassWord = account.getPassword();
        String dbAdminPassword = dbUser.getPassword();
        // 生成输入密码的摘要
        String inputHash = DigestUtil.md5Hex(InputPassWord);
        // 验证密码是否匹配
        boolean isValid = dbAdminPassword.equals(inputHash);
        log.info("密码验证" + isValid + "一致");
        // 验证密码是否正确
        if (!isValid) {
            log.warn("账号或密码错误");
            throw new CustomerException("账号或密码错误");
        }
        // 创建token并返回给前端
        String token = TokenUtils.createToken(dbUser.getId() + "-" + "USER", dbUser.getPassword());
        dbUser.setToken(token);
        // 返回登录成功的用户信息
        return dbUser;

    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    public User selectById(String id) {
        // 根据用户ID查询用户信息
        return userMapper.selectById(Long.valueOf(id));
    }

    /**
     * 更新用户密码
     *
     * @param account 包含原密码和新密码的账号信息
     * @throws CustomerException 如果新密码和旧密码一致或原密码错误，则抛出异常
     */
    public void updatePassword(Account account) {
        //判断新密码和旧密码是否相等
        if (!account.getNewpassword().equals(account.getNew2password())) {
            throw new CustomerException("500", "你两次输入的密码不一致");
        }
        //判断原密码是否正确
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && !account.getPassword().equals(currentUser.getPassword())) {
            throw new CustomerException("500", "原密码输入错误");
        }
        //开始更新密码


        // 根据当前用户的ID从数据库中选择用户信息
        User user = null;
        if (currentUser != null) {
            user = userMapper.selectById(Long.valueOf(currentUser.getId().toString()));
        }

        // 设置用户的密码为新的密码
        if (user != null) {
            user.setPassword(DigestUtil.md5Hex(account.getNewpassword()));
        }

        // 更新数据库中的用户信息
        userMapper.updateById(user);

    }

    /**
     * 插入用户信息
     *
     * @param user 要插入的用户对象
     */
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }


    /**
     * 根据ID更新用户信息
     *
     * @param user 包含更新信息的用户对象
     */
    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }


    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    @Override
    public void deleteById(Long id) {
        userMapper.deleteById( id);
    }


}
