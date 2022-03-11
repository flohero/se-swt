package swt6.fhbay;

import org.junit.jupiter.api.*;
import swt6.fhbay.domain.Category;
import swt6.fhbay.repositories.impl.HibernateCategoryRepository;
import swt6.fhbay.util.JpaUtil;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class HibernateCategoryRepositoryTest {
    private EntityManager em;

    @BeforeAll
    static void setUpAll() {
        JpaUtil.getEntityManagerFactory();
    }

    @AfterAll
    static void tearDownAll() {
        JpaUtil.closeEntityManagerFactory();
    }

    @BeforeEach
    void setUp() {
        em = JpaUtil.getTransactedEntityManager();
    }

    @AfterEach
    void tearDown() {
        JpaUtil.rollback();
    }

    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new HibernateCategoryRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(Category.class, persistentClass);
    }

    @Test
    @DisplayName("Test Class is Inserted")
    void testInsertingSingleCategoryReturnsCategory() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var cat = new Category("Fish");

        // when
        var res = repo.save(cat);

        //then
        assertNotNull(res.getId());
    }

    @Test
    @DisplayName("Test Insert category with subcategory")
    void testInsertCategoryWithSubCategory() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var cat = new Category("Fish", new Category("Animals"));

        // when
        var res = repo.save(cat);

        //then
        assertNotNull(res.getId());
    }

    @Test
    @DisplayName("Test Update category name")
    void testUpdateCategoryName() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        category.setName("Bird");
        repo.save(category);

        //then
        var res = repo.findById(category.getId()).getName();
        assertEquals("Bird", res);
    }

    @Test
    @DisplayName("Test Update add subcategory")
    void testUpdateParentCategory() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var category = repo.save(new Category("Fish"));
        var parentCategory = repo.save(new Category("Animal"));
        // when
        parentCategory.addSubcategory(category);
        var res = repo.save(category);

        //then
        assertEquals(parentCategory.getId(), res.getParentCategory().getId());
    }

    @Test
    @DisplayName("Test Update remove subcategory relation")
    void testRemoveParentCategoryRelation() {
        // given
        var repo = new HibernateCategoryRepository(em);
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
    @DisplayName("Test remove category")
    void testRemoveCategory() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        repo.remove(category);

        //then
        var res = repo.findById(category.getId());
        assertNull(res);
    }

    @Test
    @DisplayName("Test remove category by ID")
    void testRemoveCategoryId() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        var res = repo.removeById(category.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Test find category by ID")
    void testFindCategoryId() {
        // given
        var repo = new HibernateCategoryRepository(em);
        var category = repo.save(new Category("Fish"));

        // when
        var res = repo.findById(category.getId());

        //then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Test find all categories")
    void testFindAllCategories() {
        // given
        var repo = new HibernateCategoryRepository(em);
        repo.save(new Category("Fish"));
        repo.save(new Category("Bird"));

        // when
        var res = repo.findAll();

        //then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Test count categories returns correct category count")
    void testCountAllCategories() {
        // given
        var repo = new HibernateCategoryRepository(em);
        repo.save(new Category("Fish"));
        repo.save(new Category("Bird"));

        // when
        var res = repo.countAll();

        //then
        assertEquals(2, res);
    }
}