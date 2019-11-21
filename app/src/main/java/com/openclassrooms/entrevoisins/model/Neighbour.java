package com.openclassrooms.entrevoisins.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Model object representing a Neighbour
 */
public class Neighbour implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Neighbour> CREATOR = new Parcelable.Creator<Neighbour>() {
        @Override
        public Neighbour createFromParcel(Parcel in) {
            return new Neighbour(in);
        }

        @Override
        public Neighbour[] newArray(int size) {
            return new Neighbour[size];
        }
    };
    /**
     * Identifier
     */
    private Integer id;
    /**
     * Full name
     */
    private String name;
    /**
     * Avatar
     */
    private String avatarUrl;
    /**
     * Favorite
     */
    private Boolean isFavorite = false;

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param avatarUrl
     */

    public Neighbour(Integer id, String name, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    protected Neighbour(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        avatarUrl = in.readString();
        byte isFavoriteVal = in.readByte();
        isFavorite = isFavoriteVal == 0x02 ? null : isFavoriteVal != 0x00;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        this.isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbour neighbour = (Neighbour) o;
        return Objects.equals(id, neighbour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(avatarUrl);
        if (isFavorite == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isFavorite ? 0x01 : 0x00));
        }
    }
}