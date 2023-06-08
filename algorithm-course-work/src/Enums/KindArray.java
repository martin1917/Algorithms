package Enums;

public enum KindArray {
    SORTED("Упорядоченный"),
    UNSORTED("Неупорядоченный"),
    SORTED_DESC("Упорядоченный в обратной последовательности"),
    SOME_SORTED("Частично упорядоченный");

    private String title;

    KindArray(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
