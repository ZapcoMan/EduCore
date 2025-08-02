package com.example.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.example.common.exception.CustomerException;
import com.example.common.utils.TokenUtils;
import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.Admin;
import com.example.modules.system.mapper.AdminMapper;
import com.example.modules.system.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Integer, AdminMapper> implements AdminService {

    @Resource
    AdminMapper adminMapper;
    // 日志对象，用于记录系统日志
    private static final Log log = LogFactory.getLog(AdminServiceImpl.class);

    public AdminServiceImpl(AdminMapper mapper) {
        super(mapper);
    }

    /**
     * 添加管理员账户
     *
     * @param admin 待添加的管理员对象，包含用户名和密码等信息
     * @throws CustomerException 如果用户名已存在，则抛出此异常
     */
    @Override
    public void add(Admin admin) {
        // 根据新的账号查询数据库  是否存在同样账号的数据
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (dbAdmin != null) {
            throw new CustomerException("账号重复");
        }
        // 默认密码
        if (StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword(DigestUtil.md5Hex("123456"));
        }
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    /**
     * 更新管理员信息
     *
     * @param admin 包含更新信息的管理员对象
     */
    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 根据ID删除管理员账户
     *
     * @param id 要删除的管理员账户ID
     */
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    /**
     * 批量删除管理员账户
     *
     * @param list 包含多个待删除的管理员对象的列表
     */
    @Override
    public void deleteBatch(List<Admin> list) {
        for (Admin admin : list) {
            this.deleteById(admin.getId());
        }
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param id 要查询的管理员账户ID
     * @return 查询到的管理员对象
     */
    public Admin selectById(String id) {
        return adminMapper.selectById(id);
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param id 要查询的管理员账户ID
     * @return 查询到的管理员对象
     */
    @Override
    public Admin selectById(Long id) {
        return adminMapper.selectById(String.valueOf(id));
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param id 要查询的管理员账户ID
     * @return 查询到的管理员对象
     */
    @Override
    public Admin selectById(Integer id) {
        return adminMapper.selectById(String.valueOf(id));
    }

    /**
     * 查询所有管理员信息
     *
     * @return 包含所有管理员对象的列表
     */
    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll(null);
    }

    /**
     * 更新管理员信息
     *
     * @param admin 包含更新信息的管理员对象
     */
    @Override
    public void updateById(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 插入管理员信息
     *
     * @param admin 待添加的管理员对象
     */
    @Override
    public void insert(Admin admin) {
        adminMapper.insert(admin);
    }

    /**
     * 根据ID更新管理员信息
     *
     * @param id 要更新的管理员ID
     */
    @Override
    public void updateById(Long id) {
        adminMapper.updateById(id);
    }

    /**
     * 分页查询管理员信息
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param admin    用于查询的管理员对象，可以为null
     * @return 包含分页信息的PageInfo对象
     */
    @Override
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize, Admin admin) {
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * 管理员登录方法
     *
     * @param account 包含用户名和密码的账户信息
     * @return 登录成功的管理员对象
     * @throws CustomerException 如果账号不存在或密码错误，则抛出此异常
     */
    @Override
    public Admin login(Account account) {
        // 验证账号是否存在
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (dbAdmin == null) {
            throw new CustomerException("账号不存在");
        }

        // 获取输入的密码和数据库中的密码
        String inputPassword = account.getPassword();
        String dbAdminPassword = dbAdmin.getPassword();

        // 生成输入密码的摘要
        String inputHash = DigestUtil.md5Hex(inputPassword);
        // 验证密码是否匹配
        boolean isValid = dbAdminPassword.equals(inputHash);
        log.info("密码验证" + isValid + "一致");

        // 如果密码不正确，抛出异常
        if (!isValid) {
            throw new CustomerException("账号或密码错误");
        }

        // 创建token并返回给前端
        String token = TokenUtils.createToken(dbAdmin.getId() + "-" + "ADMIN", dbAdmin.getPassword());
        dbAdmin.setToken(token);

        // 返回登录成功的管理员对象
        return dbAdmin;
    }

    /**
     * 更新用户密码
     * 此方法首先检查用户输入的新密码和确认密码是否一致，然后验证当前输入的原密码是否正确，
     * 最后在验证通过后更新数据库中的密码信息
     *
     * @param account 包含用户输入的原密码、新密码和确认密码的账户对象
     * @throws CustomerException 如果新密码和确认密码不一致，或者原密码错误，抛出自定义异常
     */
    @Override
    public void updatePassword(Account account) {
        // 判断新密码和确认密码是否相等
        if (!account.getNewpassword().equals(account.getNew2password())) {
            throw new CustomerException("500", "你两次输入的密码不一致");
        }

        // 判断原密码是否正确
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && !account.getPassword().equals(currentUser.getPassword())) {
            throw new CustomerException("500", "原密码输入错误");
        }

        // 开始更新密码
        // MD5 加密
        account.setNewpassword(DigestUtil.md5Hex(account.getNewpassword()));
        Admin admin = null;
        if (currentUser != null) {
            admin = adminMapper.selectById(currentUser.getId().toString());
        }
        if (admin != null) {
            admin.setPassword(account.getNewpassword());
        }
        adminMapper.updateById(admin);
    }
}
