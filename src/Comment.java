import javax.swing.text.Position;
import javax.swing.text.Highlighter;

/**
 * Represents a comment anchored to positions in the document that adjust as text changes.
 * Holds the associated highlight tag for removal.
 */
public class Comment {
    private final Position startPos;
    private final Position endPos;
    private final String text;
    private final Highlighter.Highlight tag;

    public Comment(Position startPos, Position endPos, String text, Highlighter.Highlight tag) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.text = text;
        this.tag = tag;
    }

    public int getStartOffset() {
        return startPos.getOffset();
    }

    public int getEndOffset() {
        return endPos.getOffset();
    }

    public String getText() {
        return text;
    }

    public Highlighter.Highlight getTag() {
        return tag;
    }

    public Position getStartPos() {
        return startPos;
    }

    public Position getEndPos() {
        return endPos;
    }
}