package com.ruoyi.system.model.dto.thirdparty;

import com.ruoyi.system.model.domain.Krecycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyKrecycleReq extends Krecycle {
    private String userName;
    private String userPassWord;
    private String otherUserId;
}
