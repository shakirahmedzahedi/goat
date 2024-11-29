package com.saz.se.goat.product;

import com.saz.se.goat.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "product_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", initialValue = 544390, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(columnDefinition = "TEXT")
    private String extraInfo;

    @Column(nullable = false)
    @NotBlank(message = "Category is required")
    private String category;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @NotNull(message = "Price is required")
    private long price;

    @Min(value = 0, message = "Discount percentage must be greater than or equal to 0")
    @Max(value = 50, message = "Discount percentage must be less than or equal to 50")
    private long discountPercentage;

    @Min(value = 0, message = "Rating must be greater than or equal to 0")
    @Max(value = 5, message = "Rating must be less than or equal to 5")
    @Column(nullable = false)
    private long rating ;

    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    @Max(value = 500, message = "Stock must be less than or equal to 500")
    @NotNull(message = "Stock is required")
    private int stock;

    @Column(nullable = false)
    @NotBlank(message = "Tags are required")
    private String tags;

    @Column(nullable = false)
    @NotBlank(message = "Brand is required")
    private String brand;

    private String size;

    private long weight;

    private String thumbnail;

    private boolean bestSeller;
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    public ProductEntity(String title, String description, String additionalInfo, String extraInfo, String category,
                         long price, long discountPercentage, long rating, int stock, String tags, String brand,
                         String size, long weight, String thumbnail)
    {
        this.title = title;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.extraInfo = extraInfo;
        this.category = category;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.tags = tags;
        this.brand = brand;
        this.size = size;
        this.weight = weight;
        this.thumbnail = thumbnail;
        this.bestSeller = false;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice (long price) {
        this.price = price;
    }

    public long getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage (long discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public long getRating() {
        return rating;
    }

    public void setRating (long rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight (long weight) {
        this.weight = weight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", rating=" + rating +
                ", stock=" + stock +
                ", tags='" + tags + '\'' +
                ", brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                ", weight=" + weight +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
