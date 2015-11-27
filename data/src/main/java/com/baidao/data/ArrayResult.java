package com.baidao.data;

import java.util.List;

/**
 * Created by burizado on 14-12-2.
 */
public class ArrayResult<T> {
    public String code;
    public String errMsg;
    public String[] openCodes;
    public T detail;
    public List<T> list;
}
