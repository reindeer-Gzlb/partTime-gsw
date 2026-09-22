package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.Backlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyy
 * @date 2023-05-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class H5BacklistReq extends Backlist {
    private String dxyzm;
    private String yzmRankey;
}
