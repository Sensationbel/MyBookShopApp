package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagsDto {

    private String genresName;
    private String slug;
    private TagsClass tagsClass;

    @Getter
    public enum TagsClass {
        TAGLG (" Tag_lg"),
        TAGMD (" Tag_md"),
        TAG (""),
        TAGSM (" Tag_sm"),
        TAGXS(" Tag_xs");

        private final String className;

        TagsClass(String className) {
           this.className=className;
        }

        @Override
        public String toString() {
            return className;
        }
    }
}
