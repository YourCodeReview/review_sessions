package com.is;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ProcDesc {

    private int id;
    private String proc_name;
    private String url_name;
    private String proc_remark;
    private String proc_type;
    private String metod_type;
    private String return_type;
    private int state;
    private String err_msg;


}
