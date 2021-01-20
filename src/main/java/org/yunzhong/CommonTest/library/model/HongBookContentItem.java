package org.yunzhong.CommonTest.library.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongBookContentItem {
    private int number;
    private String name;
    private List<HongBookContentItem> childern;
}
