package hr.javafx.restaurant.model;

import java.io.Serializable;

public class Category extends Entity implements Serializable {
    private String name;
    private String description;
    private static Long idCounter=1L;

    private Category(CategoryBuilder builder) {
        super(idCounter);
        this.name = builder.name;
        this.description = builder.description;
        idCounter++;
    }

    public void setId(Long newId) {
        super.setId(newId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CategoryBuilder {
        private String name;
        private String description;

        public CategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
