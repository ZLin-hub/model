/**
 * @FileName: ModelController
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:21
 * Description: 模版控制类
 */
package xyz.fusheng.model.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Model;
import xyz.fusheng.model.core.service.ModelService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/model")
@Api(tags = "常规模版", value = "普通模版操作管理接口")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * 模版类型的控制类 下面实现最基础的 层删改查 分页（模糊查询、条件查询、排序、时间区间删选）
     */

    /**
     * 保存 - 增
     * @param model
     * @return
     */
    @ApiOperation(value = "添加常规模版", notes = "添加常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/save','model:list:add')")
    @PostMapping("/save")
    @Log(title = "添加常规模版", businessType = BusinessType.INSERT)
    public Result<Object> save(@RequestBody Model model){
        modelService.save(model);
        return new Result<>("操作成功: 添加模版！");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除单个常规模版", notes = "根据id删除常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/delete','model:list:delete')")
    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable("id") Integer id){
        modelService.deleteById(id);
        return new Result<>("操作成功: 删除模版!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除常规模版", notes = "根据ids批量删除常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/deleteByIds','model:list:delete')")
    @PutMapping("/deleteByIds")
    @Log(title = "删除常规模版", businessType = BusinessType.DELETE)
    public Result<Object> deleteByIds(@RequestBody List<Integer> ids){
        modelService.deleteByIds(ids);
        return new Result<>("操作成功: 批量删除模版！");
    }

    /**
     * 修改
     * @param model
     * @return
     */
    @ApiOperation(value = "修改常规模版", notes = "根据id修改常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/update','model:list:update')")
    @PutMapping("/update")
    @Log(title = "修改常规模版", businessType = BusinessType.UPDATE)
    public Result<Object> update(@RequestBody Model model){
        modelService.update(model);
        return new Result<>("操作成功: 修改模版!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "查询单个常规模版", notes = "根据id查询常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/get','model:list:info')")
    @GetMapping("/get/{id}")
    public Result<Model> get(@PathVariable("id") Integer id){
        Model model = modelService.getById(id);
        return new Result<>("操作成功: 查询模版！",model);
    }

    /**
     * 查询所有
     * @return
     */
    @ApiOperation(value = "查询所有常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/list','model:list')")
    @GetMapping("/list")
    public Result<List<Model>> list(){
        List<Model> modelList = modelService.getAll();
        return new Result<>("操作成功: 查询模版列表！", modelList);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @ApiOperation(value = "分页查询常规模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/getByPage','model:list')")
    @PostMapping("/getByPage")
    public Result<Page<Model>> getByPage(@RequestBody Page<Model> page){
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 模型名称， 创建时间， 更新时间
            String[] sortColumns = {"model_name", "created_time", "update_time"};
            // Arrays.asList() 方法使用
            // 1. 该方法是将数组转换成list。 Json 数据格式中的 排序列为数组形式，此处需要转换成 List数据形式
            // 2. 该方法不适用于剧本数据类型（byte,short,int,long,float,double,boolean）
            // 3. 不支持add和remove方法
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"参数错误！");
            }
        }
        page = modelService.getByPage(page);
        return new Result<>("操作成功: 分页查询模版！", page);
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @ApiOperation(value = "启用模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/enable','model:list:enable')")
    @PutMapping("/enable/{id}")
    @Log(title = "启用常规模版", businessType = BusinessType.ENABLE)
    public Result<Object> enable(@PathVariable("id") Integer id) {
        modelService.enableById(id);
        return new Result<>("操作成功: 启用模版！");
    }

    /**
     * 弃用
     * @param id
     * @return
     */
    @ApiOperation("弃用模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/model/disable','model:list:disable')")
    @PutMapping("/disable/{id}")
    @Log(title = "弃用常规模版", businessType = BusinessType.DISABLE)
    public Result<Object> disable(@PathVariable("id") Integer id) {
        modelService.disableById(id);
        return new Result<>("操作成功: 弃用模版！");
    }


}
