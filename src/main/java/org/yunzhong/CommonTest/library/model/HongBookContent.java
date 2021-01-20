package org.yunzhong.CommonTest.library.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongBookContent {

    private int bookId;
    private List<HongBookContentItem> contents;
}
