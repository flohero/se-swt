package swt6.spring.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.model.Category;
import swt6.spring.services.ArticleService;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Category Commands")
public class CategoryCommands {
    private final ArticleService articleService;

    public CategoryCommands(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ShellMethod("Create category")
    public String createCategory(String name, @ShellOption(defaultValue = "") String parent) {
        if (parent.isEmpty()) {
            return articleService.createCategory(new Category(name)).getName();
        }
        return articleService.createCategory(new Category(name, new Category(parent))).getName();
    }

    @ShellMethod("List categories")
    public String listCategories() {
        return articleService.findAllCategories().stream()
                .map(Category::getName)
                .collect(Collectors.joining("\n"));
    }
}
