package logic;
public class Item {
    private char symbol;
    private Item nextItem;      // следующий символ в слове
    private Item otherItem;     // символ на той же позиции
    
    public Item(char symbol) {
        this.symbol = symbol;
        this.nextItem = null;
        this.otherItem = null;
    }
    
    public Item(char symbol, Item nextItem, Item otherItem) {
        this.symbol = symbol;
        this.nextItem = nextItem;
        this.otherItem = otherItem;
    }


    public char getSymbol() {
        return this.symbol;
    }
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Item getNextItem() {
        return this.nextItem;
    }
    public void setNextItem(Item nextItem) {
        this.nextItem = nextItem;
    }

    public Item getOtherItem() {
        return this.otherItem;
    }
    public void setOtherItem(Item otherItem) {
        this.otherItem = otherItem;
    }

}