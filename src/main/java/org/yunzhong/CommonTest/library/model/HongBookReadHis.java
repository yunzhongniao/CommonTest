package org.yunzhong.CommonTest.library.model;

import java.util.Date;

public class HongBookReadHis {
    private Long userId;

    private Long bookId;

    private Long bookChapterId;

    private Date readTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookChapterId() {
        return bookChapterId;
    }

    public void setBookChapterId(Long bookChapterId) {
        this.bookChapterId = bookChapterId;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }
}