package com.ruoyi.system.model.dto.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyQuotationReq {
    private String userName;
    private String userPassWord;
    private String otherUserId;
    private Long backId;
}
