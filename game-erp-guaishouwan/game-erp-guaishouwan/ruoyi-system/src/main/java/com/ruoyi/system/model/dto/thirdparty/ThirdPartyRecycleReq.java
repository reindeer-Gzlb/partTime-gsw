package com.ruoyi.system.model.dto.thirdparty;

import com.ruoyi.system.model.domain.Backlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyRecycleReq extends Backlist {
    private String userName;
    private String userPassWord;
    private String otherUserId;
}
