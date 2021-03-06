/**
 * @FileName: ModelServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:20
 * Description: 模版业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Model;
import xyz.fusheng.model.core.mapper.ModelMapper;
import xyz.fusheng.model.core.service.ModelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author code-fusheng
 * [@Service]: 业务逻辑层注解 该注解可以将该类注册为 bean 注入 Spring 容器
 * 相当于xml
 * <bean id="modelService"
 *      class="xyz.fusheng.model.core.service.ModelService">
 * </bean>
 *
 * [@Resource]: (这个注解属于J2EE)，默认按照名称(byName)自动注入，名称可以通过name属性进行指定
 * - @Resource装配顺序
 * 1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
 * 2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
 * 3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
 * 4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
 *
 *
 * [@Autowried]: (这个注解属于Spring)，默认情况下要求依赖对象必须存在,按照类型(byType)自动注入
 *
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    public void save(Model model) {
        modelMapper.save(model);
    }

    @Override
    public void deleteById(Integer id) {
        modelMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        modelMapper.deleteByIds(ids);
    }

    @Override
    public void update(Model model) {
        modelMapper.update(model);
    }

    @Override
    public Model getById(Integer id) {
        return modelMapper.getById(id);
    }

    @Override
    public List<Model> getAll() {
        return modelMapper.getAll();
    }

    @Override
    public Page<Model> getByPage(Page<Model> page) {
        // 查询数据
        List<Model> modelList = modelMapper.getByPage(page);
        page.setList(modelList);
        // 查询总数
        int totalCount = modelMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void enableById(Integer id) {
        Model model = modelMapper.getById(id);
        model.setIsEnabled(StateEnums.ENABLED.getCode());
        modelMapper.updateEnable(model);
    }

    @Override
    public void disableById(Integer id) {
        Model model = modelMapper.getById(id);
        model.setIsEnabled(StateEnums.NOT_ENABLE.getCode());
        modelMapper.updateEnable(model);
    }
}
