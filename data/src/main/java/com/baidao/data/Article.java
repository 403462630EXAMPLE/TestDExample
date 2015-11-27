package com.baidao.data;

import java.util.ArrayList;

/**
 * Created by rjhy on 14-12-5.
 */
public class Article {
    public long id;
    public String cmsId;
    public String title;
    public String subtitle;
    public String author;
    public String introduction;
    public String content;
    public int totalHitCount;
    public long publishTime;
    public long publishTimestamp;
    public long createTime;
    public long updateTime;
    public Attributes attributes;
    public Column column;

    public class Attributes {
        public String referencedCompanies;
        public String referencedTypes;
        public boolean publishToMine;
        public String targetAudience;
        public long authorCmsContentId;
        public String coverImageUrl;

        public String getReferencedCompanies() {
            return referencedCompanies;
        }

        public void setReferencedCompanies(String referencedCompanies) {
            this.referencedCompanies = referencedCompanies;
        }

        public String getReferencedTypes() {
            return referencedTypes;
        }

        public void setReferencedTypes(String referencedTypes) {
            this.referencedTypes = referencedTypes;
        }

        public boolean isPublishToMine() {
            return publishToMine;
        }

        public void setPublishToMine(boolean publishToMine) {
            this.publishToMine = publishToMine;
        }

        public String getTargetAudience() {
            return targetAudience;
        }

        public void setTargetAudience(String targetAudience) {
            this.targetAudience = targetAudience;
        }

        public long getAuthorCmsContentId() {
            return authorCmsContentId;
        }

        public void setAuthorCmsContentId(long authorCmsContentId) {
            this.authorCmsContentId = authorCmsContentId;
        }

        public String getCoverImageUrl() {
            return coverImageUrl;
        }

        public void setCoverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
        }
    }

    public class Column {
        public long id;
        public String name;
        public String introduction;
        public int totalSubscriptionCount;
        public int articleCount;
        public ArrayList<Tag> tags;
        public Attributes attributes;

        public class Tag {
            public long id;
            public String cmsTagId;
            public String name;
            public long createTime;
            public long updateTime;
        }

        public class Attributes {
            public String slogan;
            public String logo;
            public String title;

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getTotalSubscriptionCount() {
            return totalSubscriptionCount;
        }

        public void setTotalSubscriptionCount(int totalSubscriptionCount) {
            this.totalSubscriptionCount = totalSubscriptionCount;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public void setArticleCount(int articleCount) {
            this.articleCount = articleCount;
        }

        public ArrayList<Tag> getTags() {
            return tags;
        }

        public void setTags(ArrayList<Tag> tags) {
            this.tags = tags;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTotalHitCount() {
        return totalHitCount;
    }

    public void setTotalHitCount(int totalHitCount) {
        this.totalHitCount = totalHitCount;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public long getPublishTimestamp() {
        return publishTimestamp;
    }

    public void setPublishTimestamp(long publishTimestamp) {
        this.publishTimestamp = publishTimestamp;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
