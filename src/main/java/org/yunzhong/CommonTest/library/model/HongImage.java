package org.yunzhong.CommonTest.library.model;

public class HongImage {
    private Long id;

    private String imageDesc;

    private String imagePath;

    private Long createUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}