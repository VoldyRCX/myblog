package com.voldy.myblog.controller;

import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.service.impl.CatalogService;
import com.voldy.myblog.service.impl.UserService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.utils.OwnerJudge;
import com.voldy.myblog.vo.CatalogVO;
import com.voldy.myblog.vo.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * TODO
 * 分类控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/8
 **/
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private OwnerJudge ownerJudge;

    /**
     * 获取分类
     * @param username
     * @return
     */
    @GetMapping
    public ModelAndView listComments(@RequestParam(value = "username") String username) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        boolean isOwner = ownerJudge.isOwner(user);

        ModelAndView mav = new ModelAndView("userspace/u :: #catalogRepleace");

        mav.addObject("isCatalogsOwner", isOwner);
        mav.addObject("catalogs", catalogs);
        return mav;
    }

    /**
     * 修改分类
     * @param catalogVO
     * @return
     */
    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")//权限判断
    public ResponseEntity<ResponseVO> create(@RequestBody CatalogVO catalogVO) {
        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功!", null));
    }

    /**
     * 删除分类
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")//权限判断
    public ResponseEntity<ResponseVO> delete(String username, @PathVariable("id") Long id) {
        try {
            catalogService.removeCatalog(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", null));
    }

    /**
     * 获取分类编辑页面
     * @return
     */
    @GetMapping("/edit")
    public ModelAndView getCatalogEdit() {
        Catalog catalog = new Catalog(null, null);
        ModelAndView mav = new ModelAndView("userspace/catalogedit");
        mav.addObject("catalog", catalog);
        return mav;
    }

    /**
     * 根据Id获取分类
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView getCatalogById(@PathVariable("id") Long id) {
        Catalog catalog = catalogService.getCatalogById(id);
        ModelAndView mav = new ModelAndView("userspace/catalogedit");
        mav.addObject("catalog",catalog);
        return mav;
    }

}
