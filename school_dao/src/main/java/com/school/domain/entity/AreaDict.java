package com.school.domain.entity;

import lombok.Data;

/**
 *
 * <b>Description:行政区划字典pojo.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:AreaDict</b>
 * <br><b>Date:</b> 07/06/2018 14:38
 */
@Data
public class AreaDict {

    /**
     * 行政区ID.
     */
    private Long id;

    /**
     * 行政区名.
     */
    private String name;

    /**
     * 上级行政区ID.
     */
    private Long parentId;
}
