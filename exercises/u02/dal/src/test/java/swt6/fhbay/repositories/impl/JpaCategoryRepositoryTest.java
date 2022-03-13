package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class JpaCategoryRepositoryTest extends TestBase{

    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new JpaCategoryRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(Category.class, persistentClass);
    }

    @Test
    @DisplayName("Class is Inserted")
    void testInsertingSingleCategoryReturnsCategory() {
        // given
        var repo = new JpaCategoryRepository(em);
        var cat = new Category("Fish");

        // when
        var res = repo.save(cat);

        //then
        assertNotNull(res.getId());
    }

    @Test
    @DisplayName("Insert category with subcategory")
    void testInsertCategoryWithSubCategory() {
        // given
        var repo = new JpaCategoryRepository(em);
        var cat = new Category("Fish", new Category("Animals"));

        // when
        var res = repo.save(cat);

        //then
        assertNotNull(res.getId());
    }

    @Test
    @DisplayName("Update category name")
    void testUpdateCategoryName() {
        // given
        var repo = new JpaCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        category.setName("Bird");
        repo.save(category);

        //then
        var res = repo.findById(category.getId()).get().getName();
        assertEquals("Bird", res);
    }

    @Test
    @DisplayName("Update add subcategory")
    void testUpdateParentCategory() {
        // given
        var repo = new JpaCategoryRepository(em);
        var category = repo.save(new Category("Fish"));
        var parentCategory = repo.save(new Category("Animal"));
        // when
        parentCategory.addSubcategory(category);
        var res = repo.save(category);

        //then
        assertEquals(parentCategory.getId(), res.getParentCategory().getId());
    }

    @Test
    @DisplayName("Update remove subcategory relation")
    void testRemoveParentCategoryRelation() {
        // given
        var repo = new JpaCategoryRepository(em);
        var parentCategory = repo.save(new Category("Animal"));
        var category = repo.save(new Category("Fish", parentCategory));
        // when
        parentCategory.removeSubcategory(category);
        var res = repo.save(category);

        //then
        assertAll(
                () -> assertNull(res.getParentCategory()),
                () -> assertTrue( parentCategory.getSubcategories().isEmpty())
        );
    }

    @Test
    @DisplayName("Remove category")
    void testRemoveCategory() {
        // given
        var repo = new JpaCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        repo.remove(category);

        //then
        var res = repo.findById(category.getId());
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Remove category by ID")
    void testRemoveCategoryId() {
        // given
        var repo = new JpaCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        var res = repo.removeById(category.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent category by ID returns zero")
    void testRemoveNonExistentCategoryId_returnsZero() {
        // given
        var repo = new JpaCategoryRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }

    @Test
    @DisplayName("Find category by ID")
    void testFindCategoryId() {
        // given
        var repo = new JpaCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        var res = repo.findById(category.getId());

        //then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Find category by ID for non existent category returns null")
    void testFindCategoryIdForNonExistentCategory_returnsNull() {
        // given
        var repo = new JpaCategoryRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Find all categories")
    void testFindAllCategories() {
        // given
        var repo = new JpaCategoryRepository(em);
        repo.save(new Category("Fish"));
        repo.save(new Category("Bird"));

        // when
        var res = repo.findAll();

        //then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count categories returns correct category count")
    void testCountAllCategories() {
        // given
        var repo = new JpaCategoryRepository(em);
        repo.save(new Category("Fish"));
        repo.save(new Category("Bird"));

        // when
        var res = repo.countAll();

        //then
        assertEquals(2, res);
    }
}