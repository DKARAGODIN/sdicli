package pro.karagodin;

import java.util.Objects;

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

    public Lexeme(String view, LexemeType type) {
        this.view = view;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return type == lexeme.type && Objects.equals(view, lexeme.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, view);
    }

    protected LexemeType type;
    protected String view;


}
