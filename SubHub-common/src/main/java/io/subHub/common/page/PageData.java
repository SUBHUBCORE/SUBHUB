package io.subHub.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Paging Tools
 *
 * @author By
 */
@Data
@ApiModel(value = "Paging data")
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Total number of records")
    private int total;

    @ApiModelProperty(value = "List data")
    private List<T> list;

    /**
     * page
     * @param list   Total number of records
     * @param total  List data
     */
    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }
}
