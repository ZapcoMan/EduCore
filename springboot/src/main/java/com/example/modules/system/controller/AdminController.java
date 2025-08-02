package com.example.modules.system.controller;

import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.Admin;
import com.example.modules.system.service.impl.AdminServiceImpl;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器类
 * 处理与管理员相关的HTTP请求，包括增删改查等操作
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController<Admin, Integer> {

    @Resource
    private AdminServiceImpl adminServiceImpl;

    protected AdminController(BaseService<Admin, Integer> baseService) {
        super(baseService);
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员对象，包含要添加的管理员信息
     * @return R<Void> 表示添加结果的响应对象
     */
    @ApiOperation("添加管理员")
    @AuditLogRecord(action = "添加管理员", resource = "管理员")
    @PostMapping("/add")
    public R<Void> add(@RequestBody Admin admin) {
        adminServiceImpl.add(admin);
        return R.ok();
    }

    /**
     * 更新管理员信息
     *
     * @param admin 管理员对象，包含要更新的管理员信息
     * @return R<Void> 表示更新结果的响应对象
     */
    @ApiOperation("更新管理员信息")
    @AuditLogRecord(action = "更新管理员信息", resource = "管理员")
    @PutMapping("/update")
    public R<Void> update(@RequestBody Admin admin) {
        adminServiceImpl.update(admin);
        return R.ok();
    }

    /**
     * 根据ID删除管理员
     *
     * @param id 要删除的管理员ID
     * @return R<Void> 表示删除结果的响应对象
     */
    @ApiOperation("根据ID删除管理员")
    @AuditLogRecord(action = "删除管理员", resource = "管理员")
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        adminServiceImpl.deleteById(id);
        return R.ok();
    }

    /**
     * 批量删除管理员
     *
     * @param list 管理员对象列表，包含要删除的管理员信息
     * @return R<Void> 表示批量删除结果的响应对象
     */
    @ApiOperation("批量删除管理员")
    @DeleteMapping("/deleteBatch")
    @AuditLogRecord(action = "批量删除管理员", resource = "管理员")
    public R<Void> deleteBatch(@RequestBody List<Admin> list) {
        adminServiceImpl.deleteBatch(list);
        return R.ok();
    }

    /**
     * 查询所有管理员
     *
     * @return R<List<Admin>> 包含所有管理员信息的响应对象
     */
    @ApiOperation("查询所有管理员")
    @AuditLogRecord(action = "查询所有管理员", resource = "管理员")
    @GetMapping("/selectAll")
    public R<List<Admin>> selectAll() {
        return R.success(adminServiceImpl.selectAll());
    }

    /**
     * 分页查询管理员
     *
     * @param pageNum 页码，默认值为1
     * @param pageSize 每页大小，默认值为10
     * @param admin 管理员查询条件对象
     * @return R<Object> 包含分页查询结果的响应对象
     */
    @ApiOperation("分页查询管理员")
    @AuditLogRecord(action = "分页查询管理员", resource = "管理员")
    @GetMapping("/selectPage")
    public R<Object> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                Admin admin) {
        return R.success(adminServiceImpl.selectPage(pageNum, pageSize, admin));
    }
}
