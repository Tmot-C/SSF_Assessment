package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vttp.batch5.ssf.noticeboard.utils.Constants;

public class Notice {
    
    @NotNull(message = "Title required.")
    @Size(min=3, max=128, message = "Notice title must be between 3 to 128 characters long.")
    private String title;

    @NotEmpty(message = "Email required.")
    @Email(message = "Must be valid email format: <emailname>@<domain_name>")
    private String poster;

    @NotNull(message = "Date required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future (message = "Date of publishing must be in the future")
    private Date postDate; 

    @NotNull(message = "Select at least 1 category for your notice.")
    private List<String> categories;
    
    @NotEmpty(message = "Message required.")
    private String text;


    public Notice() {
    }
    
    public Notice(
            @NotNull(message = "Title required.") @Size(min = 3, max = 128, message = "Notice title must be between 3 to 128 characters long.") String title,
            @NotEmpty(message = "Email required.") @Email(message = "Must be valid email format: <emailname>@<domain_name>") String poster,
            @NotNull(message = "Date required.") @Future(message = "Date of publishing must be in the future") Date postDate,
            @NotNull(message = "Select at least 1 category for your notice.") List<String> categories,
            @NotEmpty(message = "Message required.") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }





    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getPoster() {
        return poster;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }


    public Date getPostDate() {
        return postDate;
    }


    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    public List<String> getCategories() {
        return categories;
    }


    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return title + Constants.delimiter + poster + Constants.delimiter + postDate + Constants.delimiter 
                + categories + Constants.delimiter  + text;
    }

    

    

    

}
