package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.ApiReqRec;
import com.ruoyi.system.model.domain.SupplyInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * api请求记录Service接口
 *
 * @author ruoyi
 * @date 2022-10-27
 */
public interface IApiReqRecService {
    /**
     * 查询api请求记录
     *
     * @param id api请求记录主键
     * @return api请求记录
     */
    public ApiReqRec selectApiReqRecById(Long id);

    /**
     * 查询api请求记录列表
     *
     * @param apiReqRec api请求记录
     * @return api请求记录集合
     */
    public List<ApiReqRec> selectApiReqRecList(ApiReqRec apiReqRec);

    /**
     * 新增api请求记录
     *
     * @param apiReqRec api请求记录
     * @return 结果
     */
    public int insertApiReqRec(ApiReqRec apiReqRec);

    /**
     * 修改api请求记录
     *
     * @param apiReqRec api请求记录
     * @return 结果
     */
    public int updateApiReqRec(ApiReqRec apiReqRec);

    /**
     * 批量删除api请求记录
     *
     * @param ids 需要删除的api请求记录主键集合
     * @return 结果
     */
    public int deleteApiReqRecByIds(Long[] ids);

    /**
     * 删除api请求记录信息
     *
     * @param id api请求记录主键
     * @return 结果
     */
    public int deleteApiReqRecById(Long id);

    void saveInRec(String toJSONString, String toJSONString1, HttpServletRequest request, String add);

    void saveInRec(String req, String res, HttpServletRequest request, String type,
                   SupplyInfo supplyInfo,Long goodsId);
}
