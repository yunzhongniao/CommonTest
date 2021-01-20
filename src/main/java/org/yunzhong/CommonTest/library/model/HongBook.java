package org.yunzhong.CommonTest.library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HongBook {
    private Long id;

    private String bookName;

    private String bookNameEn;

    private String bookAbstract;

    private String bookContent;
}