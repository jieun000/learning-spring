package com.computer.kukje.fullstack.vo;

import lombok.Data;

@Data
public class ChildrenVO {
    private int cno;
    private String name;
    private int fno; // Foregign Key
}
// mysql - ChildrenVO - ChildrenMapper(인터페이스) - xml - controller - html