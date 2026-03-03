package emp.management.system.base;

import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationFX {

    // ---------- FADE IN ----------
    public static void fadeIn(Stage stage) {

        stage.getScene().getRoot().setOpacity(0);
        stage.show();

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(300),
                        stage.getScene().getRoot()
                );

        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    // ---------- FADE OUT + OPEN ----------
    public static void fadeOut(
            Stage current,
            Runnable nextScreen) {

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(300),
                        current.getScene().getRoot()
                );

        fade.setFromValue(1);
        fade.setToValue(0);

        fade.setOnFinished(e -> {
            current.close();
            nextScreen.run();
        });

        fade.play();
    }
}