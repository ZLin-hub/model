package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elasticsearch.action.search.SearchResponse;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.List;

/**
 * @FileName: ArticleService
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:04
 * @version: 1.0
 * Description: 文章业务逻辑接口
 */

public interface ArticleService extends IService<Article> {

    /**
     * 保存文章 并且更新分类数
     * @param article
     * @return
     */
    void saveArticleAndUpdateCategory(Article article);

    /**
     * 修改文章 并且更新分类数
     * @param article
     */
    void updateArticleAndCategory(Article article);

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<ArticleVo> getByPage(Page<ArticleVo> page);

    /**
     * 查询所有 ArticleVo
     * @return
     */
    List<ArticleVo> getList();

    /**
     * 根据id查询 ArticleVo
     * @return
     */
    ArticleVo getById(Long id);

    /**
     * 根据id逻辑删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 启用
     * @param id
     */
    void enableById(Long id);

    /**
     * 弃用
     * @param id
     */
    void disableById(Long id);

    /**
     * 根据id阅读
     * @param id
     * @return
     */
    ArticleVo readById(Long id);

}
