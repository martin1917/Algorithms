package logic;

public class DigitTree {
    private Item root;
    private final char END_SYMBOL = '\0';

    public DigitTree() {
        root = new Item(END_SYMBOL);
    }

    public void add(String word) {
        word += END_SYMBOL;
        addWord(root, word, 0);
    }

    public boolean find(String word) {
        word += END_SYMBOL;
        return findWord(root, word, 0);
    }

    public void remove(String word) {
        word += END_SYMBOL;
        Item delItem = removeWord(root, word, 0);
        if(delItem.getNextItem() == null) {
            Item prev = null;
            Item curr = root;

            while(curr != null && curr.getSymbol() != delItem.getSymbol()) {
                prev = curr;
                curr = curr.getOtherItem();
            }

            if(prev == null) {
                root.setNextItem(curr.getOtherItem());
            } else {
                prev.setOtherItem(curr == null ? null : curr.getOtherItem());
            }
        }
    }

    private void addWord(Item item, String word, int pos) {
        Item current = item;

        //поиск символа
        while(current.getSymbol() != word.charAt(pos) && current.getOtherItem() != null) {
            current = current.getOtherItem();
        }

        if(current.getSymbol() != word.charAt(pos)) {                //если встретили новый символ -> это новое слово
            current.setOtherItem(new Item(word.charAt(pos)));
            current = current.getOtherItem();
            for(int i = pos + 1; i < word.length(); i++) {
                current.setNextItem(new Item(word.charAt(i)));
                current = current.getNextItem();
            }

        } else if (pos + 1 < word.length()) {                       //иначе смотрим на следующий символ
            addWord(current.getNextItem(), word, pos + 1);
        }
    }

    private boolean findWord(Item item, String word, int pos) {
        Item current = item;

        //поиск символа
        while(current.getSymbol() != word.charAt(pos) && current.getOtherItem() != null) {
            current = current.getOtherItem();
        }

        //если символ не совпал - такого слова нет
        if(current.getSymbol() != word.charAt(pos)) {
            return false;
        }

        //если дошли до символа конца строки - строка найдена
        if(current.getSymbol() == END_SYMBOL) {
            return true;
        }

        //смотрим следующий символ
        return findWord(current.getNextItem(), word, pos + 1);
    }
    
    private Item removeWord(Item item, String word, int pos) {
        Item current = item;

        //поиск символа для удаления
        while(current.getSymbol() != word.charAt(pos) && current.getOtherItem() != null) {
            current = current.getOtherItem();
        }

        //если такого символа нет, то выходим из рекурсии
        if((current.getSymbol() == word.charAt(pos)) && (pos + 1 < word.length())) {
            //получаем символ, который нужно удалить
            Item delItem = removeWord(current.getNextItem(), word, pos + 1);
            //если удаляемый символ используется в других словах, то поднимаемся по рекурсии выше
            if(delItem.getNextItem() != null) {
                return current;
            }

            //удаляем полученный символ
            Item prev = null;
            Item curr = current.getNextItem();

            while(curr != null && curr.getSymbol() != delItem.getSymbol()) {
                prev = curr;
                curr = curr.getOtherItem();
            }

            //если удаляемый символ - первый на уровне
            if(prev == null) {
                current.setNextItem(curr.getOtherItem());
            } else {
                prev.setOtherItem(curr == null ? null : curr.getOtherItem());
            }
        }

        return current;
    }
}
