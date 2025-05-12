package com.androidlearning.retrofitandpaging.model.pojo;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.androidlearning.BR;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import lombok.Data;


/**
 * when POJO class extends BaseObservable, it enable automatic Data Binding
 * and make it easier to bind data between data object and the UI components in layout file
 */
@Data
public class PMovie extends BaseObservable {
    /**
     * Convert JSON to POJO class using tools
     * https://www.jsonschema2pojo.org/
     */

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * To display an img with Glide <base_url: https://image.tmdb.org/t/p/w500/> + <posterPath>
     * use @BindingAdapter
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    /**
     * @BindingAdapter({"posterPath"}): an annotation used to define custom attribute and methods
     * for data binding
     * when annotate a method with data binding or binding adapter, its telling that data binding
     * framework how to set or update a custom attribute or property of an UI element in the layout file
     */
    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView iv, String path) {
        String base_img_url = "https://image.tmdb.org/t/p/w500/";
        String imgUrl = base_img_url + path;
        Glide.with(iv.getContext())
                .load(imgUrl)
                .into(iv);
    }

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    /**
     * in order to tell Android studio that the TextView is linked to this variable
     * use @Bindable to getters and add notify property change in setters
     */
    @SerializedName("title")
    @Expose
    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    /**
     * BR: stand for Binding Resources
     *
     * - an automatically generated class by Android Data Binding library, it contains integer constants
     * that represent the binding expressions used in the layout files
     *
     * Any UI elements bound to this property will automatically update to reflect the new value
     */
    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @Bindable
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        notifyPropertyChanged(BR.voteAverage);
    }

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Override for MovieComparator
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)return false;
        return o == this;
    }
}
