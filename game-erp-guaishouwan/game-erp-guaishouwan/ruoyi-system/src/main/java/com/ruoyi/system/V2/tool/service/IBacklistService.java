package com.ruoyi.system.V2.tool.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.dto.thirdparty.ThirdPartyRecycleReq;
import com.ruoyi.system.pay.domain.PayRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IBacklistService
{
    /**
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Backlist selectBacklistById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param backlist 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Backlist> selectBacklistList(Backlist backlist, PageDomain pageDomain);
    /**
     * 查询【请填写功能名称】列表
     *
     * @param backlist 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Backlist> selectMyBacklistList(Backlist backlist);

    /**
     * 新增【请填写功能名称】
     *
     * @param backlist 【请填写功能名称】
     * @return 结果
     */
    public int insertBacklist(Backlist backlist);

    /**
     * 修改【请填写功能名称】
     *
     * @param backlist 【请填写功能名称】
     * @return 结果
     */
    public int updateBacklist(Backlist backlist);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteBacklistByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBacklistById(Long id);

    List<Backlist> allList(Backlist backlist);

    public int insertH5Backlist(Backlist backlist);

    public int insertH5BacklistNew(Backlist backlist);

    public List<Backlist> selectListH5ForTel(String tel);

    public int insertThirdBacklist(ThirdPartyRecycleReq thirdPartyRecycleReq);

    public List<Backlist> selectThirdBacklistList(Backlist backlist);

    public Backlist getBackInfoById(Backlist backlist);

    public int editCompensateInfo(Map param);

    public Map getAccountInfo(Long id);

    int submitGoodsInfo(Long backListId);

    JSONObject createPublicGroup(Long id);

    void addGroupMember(Long id,Long cpsId);

    SysUser getOtherUserHX(String otherUserId, String from);

//   JSONObject getOtherGroupInfo(String groupId);

    void refuse(Map<String,Object> params);

    List<Backlist> kefuList(Backlist backlist);

    PayRecord payment(Backlist backlist,PayRecord payRecordReq);

    Backlist sucessOrder(PayRecord payRecord);

    Backlist queryOrderById(Backlist backlist);
}
