package pro.karagodin;

public class Lexeme {
    public LexemeType getType() {
        return type;
    }

    public void setType(LexemeType type) {
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    protected LexemeType type;
    protected String view;


}
