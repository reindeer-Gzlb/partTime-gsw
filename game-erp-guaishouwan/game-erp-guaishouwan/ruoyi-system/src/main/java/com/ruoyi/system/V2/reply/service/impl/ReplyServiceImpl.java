package com.ruoyi.system.V2.reply.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.V2.reply.mapper.ReplyMapper;
import com.ruoyi.system.V2.reply.service.IReplyService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Reply;
import com.ruoyi.system.service.ISysRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 快捷回复Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-01
 */
@Service
public class ReplyServiceImpl implements IReplyService
{
    @Resource
    private ReplyMapper replyMapper;


    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private ISysRoleService roleService;

    /**
     * 查询快捷回复
     * 
     * @param id 快捷回复主键
     * @return 快捷回复
     */
    @Override
    public Reply selectReplyById(Long id)
    {
        return replyMapper.selectReplyById(id);
    }

    /**
     * 查询快捷回复列表
     * 
     * @param reply 快捷回复
     * @return 快捷回复
     */
    @Override
    public List<Reply> selectReplyList(Reply reply)
    {
        if (!reply.isAll()){
            reply.setCreateId(SecurityUtils.getUserId());
        }

        return replyMapper.selectReplyList(reply);
    }

    /**
     * 新增快捷回复
     * 
     * @param reply 快捷回复
     * @return 结果
     */
    @Override
    public int insertReply(Reply reply)
    {
        reply.setCreateTime(DateUtils.getNowDate());
        reply.setCreateId(SecurityUtils.getUserId());
        return replyMapper.insertReply(reply);
    }

    /**
     * 修改快捷回复
     * 
     * @param reply 快捷回复
     * @return 结果
     */
    @Override
    public int updateReply(Reply reply)
    {
        return replyMapper.updateReply(reply);
    }

    /**
     * 批量删除快捷回复
     * 
     * @param ids 需要删除的快捷回复主键
     * @return 结果
     */
    @Override
    public int deleteReplyByIds(Long[] ids)
    {
        return replyMapper.deleteReplyByIds(ids);
    }

    /**
     * 删除快捷回复信息
     * 
     * @param id 快捷回复主键
     * @return 结果
     */
    @Override
    public int deleteReplyById(Long id)
    {
        return replyMapper.deleteReplyById(id);
    }

    /**
     * 查询快捷回复语
     * @param reply
     * @return
     */
    @Override
    public List<Reply> allList(Reply reply) {

        /**
         * 1、销售客服  包含销售客服权限的用户
         * 2、通用设置  每次查询都会出现
         * 3、供货客服  包含供货客服权限的用户
         * 4、普通用户  c端客服
         * 5、包赔商
         */

//        List<String> typeList = new ArrayList<>();
//        //查询用户角色
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        boolean b = SysUser.isAdmin(loginUser.getUserId());
//        boolean b1 = true;
//        if (!b){
//            typeList.add("commonCustomer");
//            Set<String> collect = roleService.selectRolePermissionByUserId(loginUser.getUserId());
//            SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUserId());
//            if (CollectionUtils.isNotEmpty(collect)){
//                if (collect.contains("salesCustomer")){
//                    b1 = false;
//                    //销售权限
//                    typeList.add("saleCustomer");
//                }else if (collect.contains("supplyCustomer")){
//                    //号商权限
//                    typeList.add("supplyCustomer");
//                }
//            if (Objects.nonNull(sysUser)){
//                Integer userNewType = sysUser.getUserNewType();
//                if (Objects.equals(4,userNewType) || collect.contains("baopei")){
//                    //包赔商
//                    typeList.add("underwriter");
//                }
//            }
//            }else {
//                b1 = false;
//                //普通用户
//                typeList.add("ordinaryUser");
//            }
//        }
//
//        if (b1){
//            reply.setCreateId(loginUser.getUserId());
//        }
//        reply.setTypeList(typeList);

        List<String> typeList = new ArrayList<>();
        typeList.add("1");
        typeList.add(SecurityUtils.getUserId().toString());


        reply.setTypeList(typeList);

        List<Reply> replies = replyMapper.selectReplyList(reply);


        return replies;
    }
}
