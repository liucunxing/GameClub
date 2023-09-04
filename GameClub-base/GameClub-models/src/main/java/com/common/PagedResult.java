package com.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T> {
    private List<T> data;
    private long total;
    private int pageIndex;
    private int pageSize;
}
