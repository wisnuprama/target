package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class UpdateInfo {

    @SerializedName("ID")
    private Integer mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("URL")
    private String mURL;

    @SerializedName("author")
    private Author mAuthorName;

    @SerializedName("content")
    private String mContent;

    @SerializedName("modified")
    private OffsetDateTime mModified;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    public Author getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(Author authorName) {
        mAuthorName = authorName;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public OffsetDateTime getModified() {
        return mModified;
    }

    public void setModified(String modified) {
        mModified = OffsetDateTime.parse(modified);
    }

    static class Author {
        @SerializedName("first_name")
        String firstName;
        @SerializedName("last_name")
        String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
