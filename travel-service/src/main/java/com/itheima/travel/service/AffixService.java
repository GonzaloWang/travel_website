package com.itheima.travel.service;

import com.itheima.travel.pojo.Affix;
import com.itheima.travel.req.AffixVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description：文件上传业务
 */
public interface AffixService {

    /**
     * @Description 文件上传
     * @param multipartFile 上传对象
     * @param affixVo 附件对象
     * @return
     */
    AffixVo upLoad(MultipartFile multipartFile,
                   AffixVo affixVo) throws IOException;

    /**
     * @Description 为上传绑定对应的业务Id
     * @param  affixVo 附件对象
     * @return
     */
    Boolean bindBusinessId(AffixVo affixVo);

    /**
     * @Description 按业务ID查询附件
     * @param  affixVo 附件对象
     * @return
     */
    List<AffixVo> findAffixByBusinessId(AffixVo affixVo);
}
