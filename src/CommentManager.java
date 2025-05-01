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
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    for (Comment c : comments) {
                        try {
                            Position start = c.getStartPos();
                            Position end = c.getEndPos();
                            Rectangle rStart = editor.modelToView(start.getOffset());
                            Rectangle rEnd = editor.modelToView(end.getOffset());
                            if (rStart != null && rEnd != null) {
                                Rectangle area = new Rectangle(
                                        rStart.x, rStart.y,
                                        rEnd.x - rStart.x, rStart.height
                                );
                                if (area.contains(e.getPoint())) {
                                    int result = JOptionPane.showConfirmDialog(editor, "Delete comment? " + c.getText(), "Delete Comment", JOptionPane.YES_NO_OPTION);
                                    if (result == JOptionPane.YES_OPTION) {
                                        removeComment(c);
                                    }
                                    return;
                                }
                            }
                        } catch (BadLocationException ignored) {}
                    }
                }
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
            DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 255, 0, 128));
            Object tag = hl.addHighlight(startPos.getOffset(), endPos.getOffset(), painter);
            Comment comment = new Comment(startPos, endPos, text, (Highlighter.Highlight) tag);
            comments.add(comment);
            editor.repaint();
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void removeComment(Comment c) {
        comments.remove(c);
        editor.getHighlighter().removeHighlight(c.getTag());
        editor.repaint();
    }

    public void installIconPainter() {
        // No-op: clickable area is the commented text range itself.
    }

    private void checkHover(MouseEvent e) {
        for (Comment c : comments) {
            try {
                Position start = c.getStartPos();
                Position end = c.getEndPos();
                Rectangle rStart = editor.modelToView(start.getOffset());
                Rectangle rEnd = editor.modelToView(end.getOffset());
                if (rStart != null && rEnd != null) {
                    Rectangle area = new Rectangle(
                            rStart.x, rStart.y,
                            rEnd.x - rStart.x, rStart.height
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