package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: ArticleController
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:01
 * @version: 1.0
 * Description: 文章控制类
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章 - 增
     * @param article
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Article article){
        article.setArticleAuthor(SecurityUtil.getUserName());
        articleService.save(article);
        return new Result<>("操作成功: 添加文章！");
    }

    /**
     * 根据id删除文章 - 删
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id){
        articleService.deleteById(id);
        return new Result<>("操作成功: 删除文章！");
    }

    /**
     * 修改文章
     * @param article
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Article article){
        articleService.updateById(article);
        return new Result<>("操作成功: 更新文章!");
    }

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/getByPage")
    public Result<Page<ArticleVo>> getByPage(@RequestBody Page<ArticleVo> page){
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if(StringUtils.isNotBlank(sortColumn)){
            // 文章标题
            String[] sortColumns = {"article_title", "article_author", "good_count", "read_count", "collection_count", "comment_count", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = articleService.getByPage(page);
        return new Result<>("操作成功: 分页查询文章！", page);
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Integer id) {
        articleService.enableById(id);
        return new Result<>("操作成功: 启用文章!");
    }

    /**
     * 弃用
     * @param id
     * @return
     */
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Integer id) {
        articleService.disableById(id);
        return new Result<>("操作成功: 弃用文章！");
    }

}