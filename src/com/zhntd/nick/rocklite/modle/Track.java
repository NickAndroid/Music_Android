package com.zhntd.nick.rocklite.modle;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * @author Nick
 * 
 */
public class Track implements Serializable {

    private static final long serialVersionUID = 10090099L;
    private String title;
    private Bitmap art;
    private String artist;
    private long id;
    private long albumId;
    private String url;

    /**
     * @return the albumId
     */
    public long getAlbumId() {
        return albumId;
    }

    /**
     * @param albumId
     *            the albumId to set
     */
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the art
     */
    public Bitmap getArt() {
        return art;
    }

    /**
     * @param art
     *            the art to set
     */
    public void setArt(Bitmap art) {
        this.art = art;
    }

    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist
     *            the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
