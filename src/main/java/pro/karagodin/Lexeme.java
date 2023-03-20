package pro.karagodin;

import java.util.Objects;

public class Lexeme {
    private LexemeType type;
    private String view;

    public Lexeme(String view, LexemeType type) {
        this.view = view;
        this.type = type;
    }

    public LexemeType getType() {
        return type;
    }

    public String getView() {
        return view;
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

    @Override
    public String toString() {
        return "{View: " + view + ", type: " + type.toString() + "}";
    }
}
