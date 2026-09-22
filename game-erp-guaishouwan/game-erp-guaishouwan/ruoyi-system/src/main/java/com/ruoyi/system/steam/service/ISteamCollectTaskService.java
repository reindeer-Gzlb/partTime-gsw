package com.ruoyi.system.steam.service;

import java.util.List;

import com.ruoyi.system.steam.domain.SteamCollectTask;

/**
 * steam任务执行Service接口
 *
 * @author ruoyi
 * @date 2025-03-21
 */
public interface ISteamCollectTaskService {
    /**
     * 查询steam任务执行
     *
     * @param id steam任务执行主键
     * @return steam任务执行
     */
    public SteamCollectTask selectSteamCollectTaskById(Long id);

    /**
     * 查询steam任务执行列表
     *
     * @param steamCollectTask steam任务执行
     * @return steam任务执行集合
     */
    public List<SteamCollectTask> selectSteamCollectTaskList(SteamCollectTask steamCollectTask);
    /**
     * 查询steam任务执行列表
     *
     * @return steam任务执行集合
     */
    public List<Long> selectSteamCollectTaskIds();

    /**
     * 新增steam任务执行
     *
     * @param steamCollectTask steam任务执行
     * @return 结果
     */
    public int insertSteamCollectTask(SteamCollectTask steamCollectTask);

    /**
     * 修改steam任务执行
     *
     * @param steamCollectTask steam任务执行
     * @return 结果
     */
    public int updateSteamCollectTask(SteamCollectTask steamCollectTask);

    /**
     * 回收
     *
     * @param steamCollectTask
     * @return
     */
    int backList(SteamCollectTask steamCollectTask);

    /**
     * 重新校验
     *
     * @param
     * @return
     */
    int restart(Long id);

    int restartTask(Long id);

    /**
     * 批量删除steam任务执行
     *
     * @param ids 需要删除的steam任务执行主键集合
     * @return 结果
     */
    public int deleteSteamCollectTaskByIds(Long[] ids);

    /**
     * 删除steam任务执行信息
     *
     * @param id steam任务执行主键
     * @return 结果
     */
    public int deleteSteamCollectTaskById(Long id);


}
