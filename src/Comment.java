import javax.swing.text.Position;
import javax.swing.text.Highlighter;

/**
 * Represents a comment anchored to positions in the document that adjust as text changes.
 * Holds the associated highlight tag for removal.
 */
public class Comment {
    private final Position startPos;
    private final Position endPos;
    private String text;
    private final Object tag;

    public Comment(Position startPos, Position endPos, String text, Object tag) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.text = text;
        this.tag = tag;
    }

    public Position getStartPos() {
        return startPos;
    }

    public Position getEndPos() {
        return endPos;
    }

    public String getText() {
        return text;
    }

    public Object getTag() {
        return tag;
    }

    public void setText(String newText) {
        text = newText;
    }
}
