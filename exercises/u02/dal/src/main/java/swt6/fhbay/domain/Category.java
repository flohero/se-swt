package swt6.fhbay.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category extends EntityBase {
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category parentCategory;

    @OneToMany(mappedBy="parentCategory", fetch = FetchType.LAZY)
    private Set<Category> subcategories = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        parentCategory.addSubcategory(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;

    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public void addSubcategory(Category subcategory) {
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be null");
        }
        if (subcategory.getParentCategory() != null) {
            subcategory
                    .getParentCategory()
                    .getSubcategories()
                    .remove(subcategory);
        }
        subcategory.setParentCategory(this);
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Category subcategory) {
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be null");
        }
        if(subcategory.getParentCategory() == null) {
            throw new IllegalStateException("Subcategory does not have a parent");
        }
        subcategories.remove(subcategory);
        subcategory.setParentCategory(null);
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
