package com.is;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Res {

    private int code;
    private String name;
    private Param[] params;

}
