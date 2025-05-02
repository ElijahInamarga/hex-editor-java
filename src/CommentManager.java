import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
public class CommentManager {
    private final JTextPane editor;
    private final List<Comment> comments = new ArrayList<>();
    private Popup currentPopup;

    public CommentManager(JTextPane editor) {
        this.editor = editor;
        editor.setHighlighter(new DefaultHighlighter());
        MouseInputAdapter mouseAdapter = new MouseInputAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                checkHover(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hidePopup();
            }
        };
        editor.addMouseMotionListener(mouseAdapter);
        editor.addMouseListener(mouseAdapter);
    }

    public void addComment(int start, int end, String text) {
        try {
            Document doc = editor.getDocument();
            Position startPos = doc.createPosition(start);
            Position endPos = doc.createPosition(end);
            Highlighter hl = editor.getHighlighter();
            DefaultHighlighter.DefaultHighlightPainter painter =
                    new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 255, 0, 128));
            Object tag = hl.addHighlight(start, end, painter);
            Comment comment = new Comment(startPos, endPos, text, tag);
            comments.add(comment);
            editor.repaint();
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void removeComment(Comment comment) {
        editor.getHighlighter().removeHighlight(comment.getTag());
        comments.remove(comment);
        hidePopup();
        editor.repaint();
    }

    public void updateComment(Comment comment, String newText) {
        comment.setText(newText);
        hidePopup();
    }

    public Comment findCommentAt(int offset) {
        for (Comment c : comments) {
            int start = c.getStartPos().getOffset();
            int end = c.getEndPos().getOffset();
            if (offset >= start && offset <= end) {
                return c;
            }
        }
        return null;
    }

    private void checkHover(MouseEvent e) {
        for (Comment c : comments) {
            try {
                int start = c.getStartPos().getOffset();
                int end = c.getEndPos().getOffset();
                Rectangle rStart = editor.modelToView(start);
                Rectangle rEnd = editor.modelToView(end);
                if (rStart != null && rEnd != null) {
                    Rectangle area = new Rectangle(
                            rStart.x,
                            rStart.y,
                            rEnd.x - rStart.x,
                            rStart.height
                    );
                    if (area.contains(e.getPoint())) {
                        showPopup(c.getText(), e);
                        return;
                    }
                }
            } catch (BadLocationException ignored) {}
        }
        hidePopup();
    }

    private void showPopup(String text, MouseEvent e) {
        if (currentPopup != null) return;
        JToolTip tip = editor.createToolTip();
        tip.setTipText(text);
        PopupFactory pf = PopupFactory.getSharedInstance();
        currentPopup = pf.getPopup(editor, tip, e.getXOnScreen(), e.getYOnScreen() + 16);
        currentPopup.show();
    }

    private void hidePopup() {
        if (currentPopup != null) {
            currentPopup.hide();
            currentPopup = null;
        }
    }
}