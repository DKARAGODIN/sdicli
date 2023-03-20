package pro.karagodin;

import java.util.Objects;

public record Lexeme(String view, LexemeType type) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return type == lexeme.type && Objects.equals(view, lexeme.view);
    }

    @Override
    public String toString() {
        return "{View: " + view + ", type: " + type.toString() + "}";
    }
}
