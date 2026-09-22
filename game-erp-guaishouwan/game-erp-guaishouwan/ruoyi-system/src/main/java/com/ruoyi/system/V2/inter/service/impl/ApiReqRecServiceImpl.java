package com.ruoyi.system.V2.inter.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.mapper.ApiReqRecMapper;
import com.ruoyi.system.V2.inter.service.IApiReqRecService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.ApiReqRec;
import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.model.domain.UserKey;
import com.ruoyi.system.model.service.IUserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * api请求记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-27
 */
@Service
public class ApiReqRecServiceImpl implements IApiReqRecService {
    @Autowired
    private ApiReqRecMapper apiReqRecMapper;

    @Autowired
    private IUserKeyService userKeyService;

    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 查询api请求记录
     *
     * @param id api请求记录主键
     * @return api请求记录
     */
    @Override
    public ApiReqRec selectApiReqRecById(Long id) {
        return apiReqRecMapper.selectApiReqRecById(id);
    }

    /**
     * 查询api请求记录列表
     *
     * @param apiReqRec api请求记录
     * @return api请求记录
     */
    @Override
    public List<ApiReqRec> selectApiReqRecList(ApiReqRec apiReqRec) {
        List<ApiReqRec> apiReqRecs = apiReqRecMapper.selectApiReqRecList(apiReqRec);
        for (ApiReqRec reqRec : apiReqRecs) {
            if (Objects.nonNull(reqRec.getCreateId())) {
                SysUser sysUser = sysUserMapper.selectUserById(reqRec.getCreateId());
                if (Objects.nonNull(sysUser)) {
                    reqRec.setUserName(sysUser.getNickName());
                }
            }
        }
        return apiReqRecs;
    }

    /**
     * 新增api请求记录
     *
     * @param apiReqRec api请求记录
     * @return 结果
     */
    @Override
    public int insertApiReqRec(ApiReqRec apiReqRec) {
        apiReqRec.setCreateTime(DateUtils.getNowDate());
        return apiReqRecMapper.insertApiReqRec(apiReqRec);
    }

    /**
     * 修改api请求记录
     *
     * @param apiReqRec api请求记录
     * @return 结果
     */
    @Override
    public int updateApiReqRec(ApiReqRec apiReqRec) {
        apiReqRec.setUpdateTime(DateUtils.getNowDate());
        return apiReqRecMapper.updateApiReqRec(apiReqRec);
    }

    /**
     * 批量删除api请求记录
     *
     * @param ids 需要删除的api请求记录主键
     * @return 结果
     */
    @Override
    public int deleteApiReqRecByIds(Long[] ids) {
        return apiReqRecMapper.deleteApiReqRecByIds(ids);
    }

    /**
     * 删除api请求记录信息
     *
     * @param id api请求记录主键
     * @return 结果
     */
    @Override
    public int deleteApiReqRecById(Long id) {
        return apiReqRecMapper.deleteApiReqRecById(id);
    }

    @Override
    public void saveInRec(String req, String res, HttpServletRequest request, String type) {

        ApiReqRec apiReqRec = new ApiReqRec();

        Long aLong = bookingId(request);
        String author = request.getHeader(Constant.author);

        apiReqRec.setReq(req);
        apiReqRec.setCreateTime(new Date());
        apiReqRec.setCreateId(aLong);

        apiReqRec.setAuthor(author);
        apiReqRec.setUserId(aLong);
        apiReqRec.setReqServer(request.getServerName());

        apiReqRec.setRes(res);
        String remortIP = getRemortIP(request);

        apiReqRec.setReqIp(remortIP);
        apiReqRec.setType(type);


        apiReqRecMapper.insertApiReqRec(apiReqRec);

    }

    @Override
    public void saveInRec(String req, String res, HttpServletRequest request, String type,
                          SupplyInfo supplyInfo, Long goodsId) {
        ApiReqRec apiReqRec = new ApiReqRec();

//        Long aLong = bookingId(request);
//        String author = request.getHeader(Constant.author);
        if (supplyInfo != null) {
            apiReqRec.setCreateId(supplyInfo.getUserId());
            apiReqRec.setUserId(supplyInfo.getId());
        }
        apiReqRec.setGoodsId(goodsId);
        apiReqRec.setReq(req);
        apiReqRec.setCreateTime(new Date());
        apiReqRec.setReqServer(request.getServerName());
        apiReqRec.setRes(res);
        String remortIP = getRemortIP(request);
        apiReqRec.setReqIp(remortIP);
        apiReqRec.setType(type);

        apiReqRecMapper.insertApiReqRec(apiReqRec);
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }


    private Long bookingId(HttpServletRequest request) {

        String author = request.getHeader(Constant.author);
        if (!StringUtils.isEmpty(author)) {
            UserKey userKey = userKeyService.getAuthor(author);
            return userKey.getUserId();
        }
        return null;
    }
}
