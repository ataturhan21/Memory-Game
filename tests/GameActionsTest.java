import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/** Headless regression checks for delayed card resolution. */
public class GameActionsTest {
    private static MemoryGame game;
    private static JLabel turns;
    private static JLabel first;
    private static JLabel second;

    public static void main(String[] args) throws Exception {
        checkRestart(true);
        checkRestart(false);
        SwingUtilities.invokeAndWait(() -> preparePair(false));
        awaitTimers();
        SwingUtilities.invokeAndWait(() -> {
            check(game.getTurns() == 1, "mismatch must count exactly 1 turn");
            check(game.getPairsFound() == 0, "mismatch must not count as a pair");
            check(!icon(first).getIsFlipped() && !icon(second).getIsFlipped(),
                    "both unmatched cards must be available again");
        });
        SwingUtilities.invokeAndWait(() -> preparePair(true));
        awaitTimers();
        SwingUtilities.invokeAndWait(() -> {
            check(game.getTurns() == 1 && game.getPairsFound() == 1,
                    "match must count exactly 1 pair and 1 turn");
            check(icon(first).getIsDone() && icon(second).getIsDone(),
                    "matched cards must remain completed");
        });
        System.out.println("PASS: restart during match, restart during mismatch, mismatch, match");
    }

    private static void checkRestart(boolean match) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            preparePair(match);
            game.resetGame();
        });
        awaitTimers();
        SwingUtilities.invokeAndWait(() -> {
            check(game.getTurns() == 0 && game.getPairsFound() == 0,
                    "old timer must not mutate the restarted round");
            check(game.getPreviousFlippedCard() == null && game.getIconsFlipped() == 0,
                    "restarted round must remain ready for a new turn");
            for (int index = 0; index < 12; index++) {
                check(!game.getCustomIcon(index).getIsDone(), "old pair must not complete after restart");
            }
        });
    }

    private static void preparePair(boolean match) {
        game = new MemoryGame();
        turns = new JLabel("Turns: 0/15");
        GameActions actions = new GameActions();
        CustomIcon firstIcon = game.getCustomIcon(0);
        CustomIcon secondIcon = null;
        for (int index = 1; index < 12; index++) {
            CustomIcon candidate = game.getCustomIcon(index);
            if (firstIcon.getFlippedIconPath().equals(candidate.getFlippedIconPath()) == match) {
                secondIcon = candidate;
                break;
            }
        }
        check(secondIcon != null, "test pair must exist");
        first = new JLabel();
        second = new JLabel();
        first.putClientProperty("customIcon", firstIcon);
        second.putClientProperty("customIcon", secondIcon);
        actions.addMouseListener(first, game, turns);
        actions.addMouseListener(second, game, turns);
        click(first);
        click(second);
        check(game.getIconsFlipped() == 2, "exactly 2 cards must be active");
        check(firstIcon.getIsFlipped() && secondIcon.getIsFlipped(),
                "both selected cards must be marked flipped");
        click(first);
        check(game.getIconsFlipped() == 2, "extra clicks must not start another timer");
    }

    private static void click(JLabel label) {
        MouseEvent event = new MouseEvent(label, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 0, 0, 1, false);
        for (MouseListener listener : label.getMouseListeners()) listener.mouseClicked(event);
    }

    private static CustomIcon icon(JLabel label) {
        return (CustomIcon) label.getClientProperty("customIcon");
    }

    private static void awaitTimers() throws Exception {
        CountDownLatch elapsed = new CountDownLatch(1);
        SwingUtilities.invokeAndWait(() -> {
            Timer timer = new Timer(700, event -> elapsed.countDown());
            timer.setRepeats(false);
            timer.start();
        });
        check(elapsed.await(5, TimeUnit.SECONDS), "Swing event queue did not process timers");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
