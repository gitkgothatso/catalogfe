package com.payu.catalogui.domain;

public enum BookType {

    HARD_CORVER ("Hard Cover"),
    SOFT_COVER ("Soft Cover"),
    EBOOK ("eBook");

    private final String name;

    private BookType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
