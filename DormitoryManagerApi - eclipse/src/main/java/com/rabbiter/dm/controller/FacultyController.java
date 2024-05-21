package com.rabbiter.dm.controller;

import com.rabbiter.dm.annotation.Log;
import com.rabbiter.dm.annotation.RequirePermission;
import com.rabbiter.dm.entity.Faculty;
import com.rabbiter.dm.exception.HttpException;
import com.rabbiter.dm.service.FacultyService;
import com.rabbiter.dm.utils.HttpCode;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rabbiter
 * @date 2022-01-19
 */
@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping("/list")
    @RequirePermission(permissions = {"faculty:list"})
    public Result<List<Faculty>> list() {
        List<Faculty> list = facultyService.list();
        return Result.<List<Faculty>>ok().add(list);
    }

    @GetMapping("/listAll")
    @RequirePermission(permissions = {"faculty:list"})
    public Result<List<Faculty>> listAll() {
        List<Faculty> list = facultyService.listAll();
        return Result.<List<Faculty>>ok().add(list);
    }

    @PostMapping("/saveOrUpdate")
    @RequirePermission(permissions = {"faculty:save", "faculty:update"})
    @Log("添加或更新学院")
    public Result<?> saveOrUpdate(@RequestBody @Validated Faculty faculty) {
        if (faculty.getId() == null) {
            facultyService.insert(faculty);
        } else {
            if (faculty.getId().equals(faculty.getParentId())) {
                throw new HttpException(HttpCode.FAILED, "父节点不能为自己");
            }
            facultyService.update(faculty);
        }
        return Result.ok("操作成功");
    }

    @GetMapping("/delete")
    @RequirePermission(permissions = {"faculty:delete"})
    @Log("删除学院")
    public Result<?> delete(@RequestParam("id") Long id) {
        facultyService.delete(id);
        return Result.ok("删除成功");
    }

    @GetMapping("/query")
    @RequirePermission(permissions = {"faculty:query"})
    public Result<Faculty> query(@RequestParam("id") Long id) {
        Faculty faculty = facultyService.query(id).orElseThrow(() -> new HttpException(HttpCode.FAILED, "没有该数据"));
        return Result.<Faculty>ok().add(faculty);
    }

}
